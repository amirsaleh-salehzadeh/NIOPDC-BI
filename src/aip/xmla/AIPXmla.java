package aip.xmla;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aip.common.AIPException;
import aip.util.NVL;

import com.tonbeller.jpivot.olap.model.Axis;
import com.tonbeller.jpivot.olap.model.Hierarchy;
import com.tonbeller.jpivot.olap.model.OlapException;
import com.tonbeller.jpivot.olap.model.OlapModel;
import com.tonbeller.jpivot.olap.model.Position;
import com.tonbeller.jpivot.xmla.XMLA_Member;
import com.tonbeller.jpivot.xmla.XMLA_Model;
import com.tonbeller.jpivot.xmla.XMLA_OlapItem;
import com.tonbeller.jpivot.xmla.XMLA_Result;

public class AIPXmla {

	String dataSource="";//="http://localhost:81/olap/msmdpump.dll";
	String catalog="";//="NIOPDC_MRS_Ver1";
	String cube="";//="حواله";
	String user="";
	String password="";
	AIPXMLA_SOAP xmla_soap = null;
	
	public static String PROPERTIES_URI="aipxmla.properties";

	private AIPXmla(){}
	
	public static AIPXmla getXmla(String dataSource, String user, String password, String catalog, String cube)throws AIPException{
		AIPXmla xmla=new AIPXmla();
		
		xmla.dataSource=dataSource;
		xmla.catalog=catalog;
		xmla.cube=cube;
		xmla.user=user;
		xmla.password=password;
		
		System.out.println("AIPXmla.getXmla():\ndataSource="+dataSource+"\n,catalog="+catalog+"\n,cube="+cube+"\n,user="+user+"\n,password="+password);
		
		try {
			xmla.xmla_soap = new AIPXMLA_SOAP(dataSource,user,password);
		} catch (OlapException e) {
			e.printStackTrace();
			throw new AIPException(e.getMessage(),e);
		}
		return xmla;
	}
	public static AIPXmla getXmla(String xmla_user,String xmla_password)throws AIPException{
		Properties properties = new Properties();
		File f = new  File(PROPERTIES_URI);
		String notFound="فایل تنظیمات اتصال به بانک اطلاعات تحلیلی " + f.getPath() + " وجود ندارد.یا درست نمی باشد.";
		System.out.println("AIPXmla.getXmla():"+PROPERTIES_URI+"="+f.getAbsolutePath());

		if(f.exists()){
			FileInputStream fin=null;
			try {
				fin = new FileInputStream(f);
//				properties.loadFromXML( fin );
				InputStreamReader isr = new InputStreamReader(fin,"UTF-8");
				BufferedReader br = new BufferedReader(isr);
				String line=br.readLine();
				while(line!=null){
					String[] pair = line.split("=");
					if(pair.length>1){
						properties.put(pair[0], pair[1]);
					}
					line=br.readLine();
				}
				fin.close();
				
				return getXmla( properties.getProperty("dataSource")
					,xmla_user
					,xmla_password
					,properties.getProperty("catalog")
					,properties.getProperty("cube")
					);
				
			} catch (FileNotFoundException e) {
				throw new AIPException(notFound + "\n" + e.getMessage(),e);
			} catch (IOException e) {
				throw new AIPException(notFound + "\n" + e.getMessage(),e);
			}finally{
				if(fin!=null){
					try {
						fin.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}else{
			properties.setProperty("dataSource", "http://localhost:80/olap/msmdpump.dll");
			properties.setProperty("catalog", "");
			properties.setProperty("cube", "");
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
//				properties.storeToXML(fos ,"درگاه هوش مصنوعی\n www.aip-co.com \n tel:88758380 ");
//				fos.close();
				PrintWriter pw = new PrintWriter(f,"UTF-8");
				properties.list(pw);
				pw.close();
			} catch (FileNotFoundException e) {
				throw new AIPException(notFound + "\n" + e.getMessage(),e);
			} catch (IOException e) {
				throw new AIPException(notFound + "\n" + e.getMessage(),e);
			}finally{
				if(fos!=null){
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			throw new AIPException(notFound);
		}
		
	}

	
	public List<XMLA_OlapItem> getCatalogs()throws AIPException{
		try {
			return xmla_soap.discoverCat();
		} catch (OlapException e) {
			throw new AIPException(e.getMessage(),e);
		}
	}
	public List<XMLA_OlapItem> getCatalogs(String catalog)throws AIPException{
		try {
			return xmla_soap.discoverCat(catalog);
		} catch (OlapException e) {
			throw new AIPException(e.getMessage(),e);
		}
	}
	public List<XMLA_OlapItem> getDimensions()throws AIPException{
		return getDimensions(cube);
	}
	public List<XMLA_OlapItem> getDimensions(String cube)throws AIPException{
		try {
			return xmla_soap.discoverDim(getCatalog(), cube);
		} catch (OlapException e) {
			throw new AIPException(e.getMessage(),e);
		}
	}
	public List<XMLA_OlapItem> getHierarchys(String dimension)throws AIPException{
		return getHierarchys(cube,dimension);
	}
	public List<XMLA_OlapItem> getHierarchys(String cube,String dimension)throws AIPException{
		try {
			return xmla_soap.discoverHier(catalog, cube,dimension);
		} catch (OlapException e) {
			throw new AIPException(e.getMessage(),e);
		}
	}
	public List<XMLA_OlapItem> getLevels(String dimension,String hierarchy)throws AIPException{
		return getLevels(cube,dimension,hierarchy);
	}
	public List<XMLA_OlapItem> getLevels(String cube,String dimension,String hierarchy)throws AIPException{
		try {
			return xmla_soap.discoverLev(catalog, cube, dimension, hierarchy);
		} catch (OlapException e) {
			throw new AIPException(e.getMessage(),e);
		}
	}
	public List<XMLA_OlapItem> getMembers(String dimension,String hierarchy,String level)throws AIPException{
		return getMembers(cube,dimension,hierarchy,level);
	}
	public List<XMLA_OlapItem> getMembers(String cube,String dimension,String hierarchy,String level)throws AIPException{
		try {
			return xmla_soap.discoverMem(catalog, cube, dimension, hierarchy,level);
		} catch (OlapException e) {
			throw new AIPException(e.getMessage(),e);
		}
	}
	public String getRoles(String catalog)throws AIPException{
		List<XMLA_OlapItem> cats = getCatalogs(catalog);
		for(XMLA_OlapItem item : cats){
			if(catalog.equalsIgnoreCase( item.getName() ) ){
				return item.getProperty("ROLES");
			}
		}
		
		return null;
	}
	public String getRoles()throws AIPException{
		return getRoles(catalog);
//		return "*,role,آستارا";
	}
	public String getUserRoles(String catalog,String user)throws AIPException{
		StringBuffer sb = new StringBuffer();
		try{
			List lst = xmla_soap.discoverUserRoles(catalog, user);
			for (int i = 0; i < lst.size(); i++) {
				sb.append(lst.get(i));
				sb.append(",");
			}
			return sb.toString();
		} catch (OlapException e) {
			throw new AIPException(e.getMessage(),e);
		}
	}
	public String getUserRoles(String user)throws AIPException{
		return getUserRoles(catalog, user);
	}
	public String getRoleUsers(String catalog,String role)throws AIPException{
		StringBuffer sb = new StringBuffer();
		try{
			List lst = xmla_soap.discoverRoleUsers(catalog, role);
			for (int i = 0; i < lst.size(); i++) {
				sb.append(lst.get(i));
				sb.append(",");
			}
			return sb.toString();
		} catch (OlapException e) {
			throw new AIPException(e.getMessage(),e);
		}
	}


	public static final int MDTREEOP_CHILDREN = 1;
	public static final int MDTREEOP_SIBLINGS = 2;
	public static final int MDTREEOP_PARENT = 4;
	public static final int MDTREEOP_SELF = 8;
	public static final int MDTREEOP_DESCENDANTS = 16;
	public static final int MDTREEOP_ANCESTORS = 32;
   //treeop bit combination according to TREEOP specification
	public List<XMLA_OlapItem> getMemberTree(String member,int treeop)throws AIPException{
		return getMemberTree(cube,member,treeop);
	}
	public List<XMLA_OlapItem> getMemberTree(String cube,String member,int treeop)throws AIPException{
		try {
			return xmla_soap.discoverMemTree(catalog, cube, member, treeop);
		} catch (OlapException e) {
			throw new AIPException(e.getMessage(),e);
		}
	}
	
	public List<XMLA_Member> getFilterItemMembers(String aFilterItemCondition)throws AIPException{
		//" [فرآورده].[رنگ].[رنگ] "
		String mdx = "select { "
			+ aFilterItemCondition 
			+" } on columns "
			+" from ["+cube+"]";
		

		try {

			XMLA_Model model = new XMLA_Model();
	
			model = new XMLA_Model();
			model.setCatalog(catalog);
	
			model.setMdxQuery(mdx);
			//model.setID("Drill" + model.getID());
			model.setUri(dataSource);
			model.setUser(user);
			model.setPassword(password);

			model.initialize();
			XMLA_Result res = (XMLA_Result) model.getResult();
			
			ArrayList<XMLA_Member> lst = new ArrayList<XMLA_Member>();
			
			if(res.getAxes().length>0){
				Axis columnAxis = res.getAxes()[0];
				Iterator<Position> iterPos = columnAxis.getPositions().iterator();
				while(iterPos.hasNext()){
					Position pos = iterPos.next();
					for(int i=0;i< pos.getMembers().length;i++){
						XMLA_Member mem = (XMLA_Member) pos.getMembers()[i];
						
						lst.add(mem);
						break;//only first position added to members list
					}
				}
			}
			return lst;
		} catch (OlapException e) {
			e.printStackTrace();
			throw new AIPException(e.getMessage(),e);
		}
		
	}
	
	private XMLA_Model getMdxModel(String mdx)throws AIPException{
//		String mdx = "select { "
//			+ aFilterItemCondition 
//			+" } on columns "
//			+" from ["+cube+"]";
		try {

			XMLA_Model model = new XMLA_Model();
	
			model = new XMLA_Model();
			model.setCatalog(catalog);
	
			model.setMdxQuery(mdx);
			//model.setID("Drill" + model.getID());
			model.setUri(dataSource);
			model.setUser(user);
			model.setPassword(password);

			model.initialize();
			return model;
		} catch (OlapException e) {
			e.printStackTrace();
			throw new AIPException(e.getMessage(),e);
		}
		
	}
	private String getAxisMembersString(Axis axis){
		StringBuffer sb=new StringBuffer();
		Iterator<Position> iterPos = axis.getPositions().iterator();
		while(iterPos.hasNext()){
			Position pos = iterPos.next();
			for(int i=0;i< pos.getMembers().length;i++){
				XMLA_Member mem = (XMLA_Member) pos.getMembers()[i];
				
				sb.append(mem.getLevel().getLabel() + " = " +  mem.getLabel()  );
				sb.append(",");
				break;//only first position added to members list
			}
		}
		return sb.toString();
	}
	public AIPXmlaModelTopValue getModelTopValues(OlapModel model)throws AIPException{
		AIPXmlaModelTopValue modelTopValue = new AIPXmlaModelTopValue();

		
		try {
			XMLA_Result res = (XMLA_Result) model.getResult();
			/**
			 * slicer
			 */
			if(res.getSlicer()!=null){
				modelTopValue.setSlicer( getAxisMembersString( res.getSlicer() ) );
			}
			/**
			 * measureName
			 */
			if(model.getMeasures().length>0){
				modelTopValue.setMeasureName(model.getMeasures()[0].getLabel());
			}else{
				boolean found=false;
				for (int i = 0; i < res.getAxes().length && !found; i++) {
					Axis axis = res.getAxes()[i];
					Iterator<Position> iterPos = axis.getPositions().iterator();
					while(iterPos.hasNext()){
						Position pos = iterPos.next();
						for(int j=0;j< pos.getMembers().length && !found;i++){
							XMLA_Member mem = (XMLA_Member) pos.getMembers()[i];
							if(mem.getDimension().isMeasure()){
								modelTopValue.setMeasureName( mem.getLabel() );
								found=true;
							}
						}
					}
						
					
				}
			}
			
			/**
			 * dimensionName
			 */
			boolean found=false;
			for (int i = 0; i < res.getAxes().length && !found; i++) {
				Axis axis = res.getAxes()[i];
				
				for (int j = 0; j < axis.getHierarchies().length  && !found; j++) {
					Hierarchy hier = axis.getHierarchies()[j];
					if(!hier.getDimension().isMeasure()){
						modelTopValue.setDimensionName("["+hier.getDimension().getLabel()+"].["+hier.getLabel()+"]");
						found=true;
					}
				}
			}
			
			/**
			 * totalValue
			 */
			
			
			
			
			res.printOut(System.out);
			
			
			/**
			 * 
			 */
			return modelTopValue;
		} catch (OlapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AIPException(e.getMessage(),e);
		}
		
	}
	
	public void createRole(String roleName,String[] membership)throws AIPException{
		createRole(catalog,roleName, membership);
	}
	public void createRole(String catalog,String roleName,String[] membership)throws AIPException{
		try {
			xmla_soap.createRole(catalog, roleName, membership);
		} catch (OlapException e) {
			throw new AIPException(e.getMessage(),e);
		}
	}
	public void addRoleMemberships(String roleName,String[] membership)throws AIPException{
		addRoleMemberships(catalog,roleName, membership);
	}
	public void addUserRoles(String user,String roles)throws AIPException{
		addUserRoles(catalog,user, roles);
	}
	public void addUserRoles(String catalog,String user,String roles)throws AIPException{
		String[] arRoles =  NVL.getString(roles).split(",") ;
		try {
			xmla_soap.addUserRoles(catalog, user, arRoles);
		} catch (OlapException e) {
			throw new AIPException(e.getMessage(),e);
		}
		
	}
	
	
	public void addRoleMemberships(String catalog,String roleName,String[] membership)throws AIPException{
		try {
			xmla_soap.addRoleMemberships(catalog, roleName, membership);
		} catch (OlapException e) {
			throw new AIPException(e.getMessage(),e);
		}
	}
	
	public void updateUserRoles(String user,String roles)throws AIPException{
		String[] arRoles =  NVL.getString(roles).split(",") ;
		try {
			xmla_soap.updateUserRoles(catalog, user, arRoles);
		} catch (OlapException e) {
			throw new AIPException(e.getMessage(),e);
		}
	}

	
	public String getDataSource() {
		return dataSource;
	}

	public String getCatalog() {
		return catalog;
	}

	public String getCube() {
		return cube;
	}

	public AIPXMLA_SOAP getXmla_soap() {
		return xmla_soap;
	}

	
	public static void main(String[] args) {

		try {
			//XMLA_SOAP xmla_soap = new XMLA_SOAP("http://localhost:81/olap/msmdpump.dll","","");
			
			String cube="حواله";
			String dim="[فرآورده]";
			String hier="[فرآورده].[رنگ فرآورده]";
			String level="[فرآورده].[رنگ فرآورده].[رنگ]";
			String member="[فرآورده].[رنگ فرآورده].[رنگ].&[6.]";
			//آبی
			
			
//			AIPXmla aipxmla = AIPXmla.getXmla( "" , "" );
			AIPXmla aipxmla = AIPXmla.getXmla( "test" , "123" );
//			AIPXmla aipxmla = AIPXmla.getXmla( "aip" , "aip" );
			
//			System.out.println("\n  getDimensions >>> ");
//			List<XMLA_OlapItem> lst1=aipxmla.getDimensions(cube);
//			for (XMLA_OlapItem item : lst1) {
//				System.out.println(""+item.getUniqueName());
//			}
//			
//			System.out.println("\n  getHierarchys >>> ");
//			List<XMLA_OlapItem> lst2=aipxmla.getHierarchys(cube,dim);
//			for (XMLA_OlapItem item : lst2) {
//				System.out.println(""+item.getUniqueName());
//			}			
//			
//			System.out.println("\n  getLevels >>> ");
//			List<XMLA_OlapItem> lst3=aipxmla.getLevels(cube,dim,hier);
//			for (XMLA_OlapItem item : lst2) {
//				System.out.println(""+item.getUniqueName());
//			}			
//
//			System.out.println("\n  getMembers >>> ");
//			List<XMLA_OlapItem> lst4=aipxmla.getMembers(cube,dim,hier,level);
//			for (XMLA_OlapItem item : lst4) {
//				System.out.println(""+item.getUniqueName() 
//						+ ",caption=" + item.getCaption()
//						+ ",label=" + item.getLabel()
//						+ ",name=" + item.getName()
//						+ ",type=" + item.getType());
//			}			

			/**
			 * test getMemberTree
			 */
			System.out.println("\n  getMemberTree >>> ");
			member="[فرآورده].[رنگ فرآورده].[رنگ].&[2.]";
			member="[مناطق].[منطقه-ناحیه].[همه]";
			List<XMLA_OlapItem> lst5=aipxmla.getMemberTree(cube,member,MDTREEOP_CHILDREN);
			for (XMLA_OlapItem item : lst5) {
				System.out.println(""+item.getUniqueName() 
						+ ",caption=" + item.getCaption()
						+ ",label=" + item.getLabel()
						+ ",name=" + item.getName()
						+ ",type=" + item.getType());
			}			

			/**
			 * test getFilterItemMembers
			 */
//			//String filterItemCondition ="[فرآورده].[رنگ].[رنگ]";
//			String filterItemCondition ="[فرآورده].[رنگ فرآورده].members";
//			//String filterItemCondition ="[فرآورده].[رنگ فرآورده].[رنگ].members"
//			//	 + "*[مشتری].[منطقه].[منطقه].members";
//
//			System.out.println("\n  getFilterItemMembers >>> ");
//			List<XMLA_Member> lst6=aipxmla.getFilterItemMembers(filterItemCondition);
//			for (XMLA_Member item : lst6) {
//				System.out.println(""+item.getUniqueName() 
//				+ ",caption=" + item.getCaption()
//				+ ",label=" + item.getLabel()
//				+ ",name=" + item.getName()
//				+ ",type=" + item.getType());
//			}			

			/**
			 * test getModelTopValues
			 */
//			String mdx = "select { " +
//					" [Measures].[مبلغ کل]	" +
//					"} on columns " +
//					", {" +
//					" [فرآورده].[رنگ].members " +
//					"} on rows"+
//					" from ["+cube+"]";
//			OlapModel model = aipxmla.getMdxModel(mdx);
//			AIPXmlaModelTopValue modelTopValue = aipxmla.getModelTopValues(model);
//			System.out.println("AIPXmla.main():modelTopValue="+modelTopValue.toString());
			
			/**
			 * getRoles
			 */
//			String catalog ;
////			catalog="Waremart 2005";
//			catalog="NIOPDC_BI_Ver1";
//			String roles=aipxmla.getRoles(catalog);
//			System.out.println("all roles="+roles);
//
//			String user="test";
//			System.out.println("user :"+user+" roles=");
//			String roles2=aipxmla.getUserRoles(catalog,user);
//				System.out.println(roles2);
			/**
			 * create role
			 */
//			String catalog="NIOPDC_MRS_Ver1";
//			String role="xmlaRole15";
//			String[] membership={"Everyone"};
//			aipxmla.createRole(role, membership);

			/**
			 * get Role Users
			 */
//			String catalog="NIOPDC_MRS_Ver1";
//			String role="Role";
//			String users = aipxmla.getRoleUsers(catalog, role);
//			System.out.println("AIPXmla.main():role users="+users);

			/**
			 * addRoleMemberships
			 */
//			String catalog="NIOPDC_MRS_Ver1";
//			String role="Role";
//			String[] membership={"aip-vaio"};
//			aipxmla.addRoleMemberships(catalog, role, membership);

			/**
			 * getUserRoles
			 */
//			String catalog="NIOPDC_BI_Ver1";
//			String user="acc3";
//			String roles = aipxmla.getUserRoles(catalog,user);
//			System.out.println("AIPXmla.main():user roles="+roles);
			

			
			//System.out.println("AIPXmla.main():roles="+roles);
		} catch (AIPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	
	
	
}
