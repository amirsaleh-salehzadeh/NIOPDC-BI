package aip.xmla;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.tonbeller.jpivot.olap.model.OlapException;
import com.tonbeller.jpivot.xmla.XMLA_Dimension;
import com.tonbeller.jpivot.xmla.XMLA_Member;
import com.tonbeller.jpivot.xmla.XMLA_Model;
import com.tonbeller.jpivot.xmla.XMLA_OlapItem;
import com.tonbeller.jpivot.xmla.XMLA_SOAP;
import com.tonbeller.jpivot.xmla.XMLA_Util;

//import org.apache.log4j.xml.DOMConfigurator;

import rex.DimensionMember;
import rex.graphics.datasourcetree.elements.DataSourceTreeElement;
import rex.graphics.dimensiontree.elements.DimensionElement;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.graphics.dimensiontree.elements.HierarchyElement;
import rex.graphics.dimensiontree.elements.LevelElement;
import rex.graphics.dimensiontree.elements.MemberElement;
import rex.graphics.filtertree.elements.FilterTreeMemberElement;
import rex.metadata.ServerMetadata;
import rex.xmla.XMLADiscoverProperties;
import rex.xmla.XMLADiscoverRestrictions;
import rex.xmla.XMLAObjectsFactory;
import aip.tag.tree.AIPTree;
import aip.util.AIPUtil;
import aip.util.NVL;

public class AIPOlap {
	static String _DataSourceInfo="192.168.0.52";
//	static String _DataSourceInfo="localhost:81";
	
	static String _CubeName="حواله";
	static String _Catalog="NIOPDC_BI_Ver1";
	
	public static String getDataSource(){
		return _DataSourceInfo;
	}
	public static String getDataSourceURI(){
		return "http://"+_DataSourceInfo+"/olap/msmdpump.dll";
	}
	public static String getCatalog(){
		return _Catalog;
	}
	
	public static String getCubeName(){
		return _CubeName;
	}
	
	public static String[] getDimTreeMemberList(String aDimension,String aHierarchy,String aLevel){
		//String str[]=null;
		List<String> list = new ArrayList<String>();
		ServerMetadata smd = new ServerMetadata(getDataSourceURI());
		DataSourceTreeElement ds[] = smd.discoverDataSources();
		if (ds != null){
			for (int i = 0; ds != null && i < ds.length; i++) {
			
				XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
				XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();

				properties.setDataSourceInfo(_DataSourceInfo);
				properties.setCatalog(_Catalog);
				restrictions.setDimensionUniqueName(aDimension);
				restrictions.setHierarchyUniqueName(/*aDimension+*/aHierarchy);
				restrictions.setLevelUniqueName(/*aDimension+aHierarchy+*/aLevel);
				DimensionTreeElement mems[] = ds[i].getServerMetaData().getDimensionTreeMembersList(restrictions, properties);
				for (DimensionTreeElement mem : mems ) {
					list.add(mem.getCaption());
					//System.out.println("\t"+mem.getCaption() );
				}
			}
		}
		list = removeDuplicated(list);
		String[] arr = {};//new String[list.size()];

		arr = (String[])list.toArray(arr);
		
		return arr;
	}

	public static DimensionMember[] getDimTreeMembers(String aDimension,String aHierarchy,String aLevel){
		//String str[]=null;
		List<DimensionMember> list = new ArrayList<DimensionMember>();
		ServerMetadata smd = new ServerMetadata(getDataSourceURI());
		DataSourceTreeElement ds[] = smd.discoverDataSources();
		if (ds != null){
			for (int i = 0; ds != null && i < ds.length; i++) {
			
				XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
				XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();

				properties.setDataSourceInfo(_DataSourceInfo);
				properties.setCatalog(_Catalog);
				restrictions.setDimensionUniqueName(aDimension);
				restrictions.setHierarchyUniqueName(/*aDimension+*/aHierarchy);
				restrictions.setLevelUniqueName(/*aDimension+aHierarchy+*/aLevel);
				DimensionTreeElement mems[] = ds[i].getServerMetaData().getDimensionTreeMembersList(restrictions, properties);
				DimensionMember dmember = null;
				for (DimensionTreeElement mem : mems ) {
					dmember = new DimensionMember();
					dmember.setMemberCaption(mem.getCaption());
					
					//String n=mem.getUniqueName();
					//System.out.println(n);
					//System.out.println("\t"+mem.getCaption() );
					int lastIndex = mem.getUniqueName().lastIndexOf("]");
					int plast = mem.getUniqueName().lastIndexOf("[");
					String num = mem.getUniqueName().substring(plast+1,lastIndex);
					
					dmember.setMemberNum(num);
					list.add(dmember);
					//System.out.println("\t"+mem.getCaption() );
				}
			}
		}
		list = removeDuplicatedMember(list);
		DimensionMember[] arr = {};//new String[list.size()];

		arr = (DimensionMember[])list.toArray(arr);
		
		return arr;
	}

	public static List<DimensionMember> removeDuplicatedMember(List<DimensionMember> list){
		Hashtable<String, DimensionMember > ht = new Hashtable<String, DimensionMember>();
		for(int i=0;i<list.size();i++){
			if (!ht.containsKey(list.get(i).getMemberCaption())) 
				ht.put(list.get(i).getMemberCaption(), list.get(i));
		}
//		List<DimensionMember> list1=new ArrayList<DimensionMember>();
//		Collection<DimensionMember> list2=ht.values();
//		
//		list1.addAll(list2);
		for(int i=list.size()-1;i>=0;i--){
			if (ht.containsKey(list.get(i).getMemberCaption()) && !ht.contains(list.get(i)))
				list.remove(i);
		}

		return list;
	}


	public static List<String> removeDuplicated(List<String> list){
		Hashtable<String, String > ht = new Hashtable<String, String>();
		for(int i=0;i<list.size();i++){
			if (!ht.containsKey(list.get(i))) 
				ht.put(list.get(i), list.get(i));
		}
//		List<String> list1=new ArrayList<String>();
//		Collection<String> list2=ht.values();
//		list1.addAll(list2);
		for(int i=list.size()-1;i>=0;i--){
			if (ht.containsKey(list.get(i)) && !ht.contains(list.get(i)))
				list.remove(i);
		}

		return list;
	}
	
	public static List<DimensionTreeElement> getDimensionMemberTree(){
		ServerMetadata smd = new ServerMetadata(getDataSourceURI());
		List<DimensionMember> list = new ArrayList<DimensionMember>();
		List<DimensionTreeElement>list1 = new ArrayList<DimensionTreeElement>();
		DataSourceTreeElement ds[] = smd.discoverDataSources();
		if (ds != null){
			for (int i = 0; ds != null && i < ds.length; i++) {
			
				XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
				XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();

				properties.setDataSourceInfo(_DataSourceInfo);
				properties.setCatalog(_Catalog);
				//restrictions.setDimensionUniqueName("[زمان]");
				restrictions.setCubeName("حواله");
				DimensionTreeElement mems[] = ds[i].getServerMetaData().getDimensionList(restrictions, properties);
				DimensionMember dmember = null;
				DimensionElement eln = null;
				System.out.println("list.size="+mems.length);
				for (DimensionTreeElement mem : mems ) {
					list1.add((DimensionTreeElement) mem);
/*					DimensionTreeElement tt[]=mem.getChildren(true);
					dmember = new DimensionMember();
					dmember.setMemberCaption(mem.getCaption());
					dmember.setMemberNum(mem.getUniqueName().substring(mem.getUniqueName().lastIndexOf("[")+1,mem.getUniqueName().lastIndexOf("]")));
					dmember.setUniqueName(mem.getUniqueName());	
					list.add(dmember);
*/				}
			}
		}
		//return list;
		return list1;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static DimensionTreeElement[] getDimensionTreeElement(){
		ServerMetadata smd = new ServerMetadata(getDataSourceURI());
		DimensionTreeElement mems[] = {};
		DataSourceTreeElement ds[] = smd.discoverDataSources();
		if (ds != null){
			for (int i = 0; ds != null && i < ds.length; i++) {
			
				XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
				XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();

				properties.setDataSourceInfo(_DataSourceInfo);
				properties.setCatalog(_Catalog);
				//restrictions.setDimensionUniqueName("[زمان]");
				restrictions.setCubeName("حواله");
				//restrictions.setDimensionUniqueName("[زمان]");
				//restrictions.setHierarchyUniqueName("[زمان].[زمان]");
				//restrictions.setLevelUniqueName("[زمان].[زمان].[سال]");
				//restrictions.setMemberUniqueName("[زمان].[زمان].[سال].[1388]");
				mems = ds[i].getServerMetaData().getDimensionList(restrictions, properties);
				System.out.println("list.size="+mems.length);
				for(int j=0;j<mems.length;j++){
					System.out.println("level 1="+mems[j].getCaption());
					DimensionTreeElement children[] = mems[j].getChildren(true); 
					for(int k=0;k<children.length;k++)
						System.out.println("............caption="+children[k].getCaption());
				}
			}
		}

		return mems;
	}
	
	
	
	public static List<DimensionElement> getDimensions(String aCubeName){
		System.out.println(_DataSourceInfo);
		ServerMetadata smd = new ServerMetadata(getDataSourceURI());
		DimensionTreeElement mems[] = {};
		DataSourceTreeElement ds[] = smd.discoverDataSources();
		List<DimensionElement> list = new ArrayList<DimensionElement>();
		if (ds != null){
			for (int i = 0; ds != null && i < ds.length; i++) {
			
				XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
				XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();

				properties.setDataSourceInfo(_DataSourceInfo);
				properties.setCatalog(_Catalog);
				if(!"".equals(NVL.getString(aCubeName)))
					restrictions.setCubeName(aCubeName);
				mems = ds[i].getServerMetaData().getDimensionList(restrictions, properties);
				System.out.println("list.size="+mems.length);
				DimensionElement mem = null;
				for(int j=0;j<mems.length;j++){
					mem = (DimensionElement)mems[j];
					System.out.println("isMeasure="+mem.isMeasureDimension()+"..........DimensionCaption="+mem.getCaption());
					if(!mem.isMeasureDimension())
						list.add(mem);

				}
			}
		}
		for(int i=0;i<list.size();i++){
			System.out.println("........caption="+list.get(i).getCaption());
		}
		System.out.println(".............2       list.size="+list.size());
		return list;
	}
	
	
	public static List<HierarchyElement> getHierarchys(String aCubeName,String aDimensionName){
		System.out.println(_DataSourceInfo);
		ServerMetadata smd = new ServerMetadata(getDataSourceURI());
		DimensionTreeElement mems[] = {};
		DataSourceTreeElement ds[] = smd.discoverDataSources();
		List<HierarchyElement> list = new ArrayList<HierarchyElement>();
		if (ds != null) {
			for (int i = 0; ds != null && i < ds.length; i++) {

				XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
				XMLADiscoverProperties properties = XMLAObjectsFactory.newXMLADiscoverProperties();

				properties.setDataSourceInfo(_DataSourceInfo);
				properties.setCatalog(_Catalog);
				restrictions.setCubeName(aCubeName);
				restrictions.setDimensionUniqueName(aDimensionName);
				mems = ds[i].getServerMetaData().getHierarchyList(restrictions,properties);
				System.out.println("list.size=" + mems.length);
				HierarchyElement mem = null;
				for (int j = 0; j < mems.length; j++) {
					System.out.println("..........HierarchyCaption=" + mems[j].getCaption());
					mem = (HierarchyElement) mems[j];
					list.add(mem);
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println("........caption=" + list.get(i).getCaption());
		}
		System.out.println(".............2       list.size=" + list.size());
		return list;
	}

	public static List<LevelElement> getLevels(String aCubeName,String aDimensionName,String aHierarchy){
		System.out.println(_DataSourceInfo);
		ServerMetadata smd = new ServerMetadata(getDataSourceURI());
		DimensionTreeElement mems[] = {};
		DataSourceTreeElement ds[] = smd.discoverDataSources();
		List<LevelElement> list = new ArrayList<LevelElement>();
		if (ds != null) {
			for (int i = 0; ds != null && i < ds.length; i++) {

				XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
				XMLADiscoverProperties properties = XMLAObjectsFactory.newXMLADiscoverProperties();

				properties.setDataSourceInfo(_DataSourceInfo);
				properties.setCatalog(_Catalog);
				restrictions.setCubeName(aCubeName);
				restrictions.setDimensionUniqueName(aDimensionName);
				restrictions.setHierarchyUniqueName(aHierarchy);
				mems = ds[i].getServerMetaData().getLevelList(restrictions,properties);
				System.out.println("list.size=" + mems.length);
				LevelElement mem = null;
				for (int j = 0; j < mems.length; j++) {
					System.out.println("..........LevelCaption=" + mems[j].getCaption());
					mem = (LevelElement) mems[j];
					list.add(mem);
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println("........caption=" + list.get(i).getCaption());
		}
		System.out.println(".............2       list.size=" + list.size());
		return list;
	}

	public static List<FilterTreeMemberElement> getMembers(String aCubeName,String aDimensionName,String aHierarchy,String aLevel){
		System.out.println(_DataSourceInfo);
		ServerMetadata smd = new ServerMetadata(getDataSourceURI());
		DimensionTreeElement mems[] = {};
		DataSourceTreeElement ds[] = smd.discoverDataSources();
		List<FilterTreeMemberElement> list = new ArrayList<FilterTreeMemberElement>();
		if (ds != null) {
			for (int i = 0; ds != null && i < ds.length; i++) {

				XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
				XMLADiscoverProperties properties = XMLAObjectsFactory.newXMLADiscoverProperties();

				properties.setDataSourceInfo(_DataSourceInfo);
				properties.setCatalog(_Catalog);
				restrictions.setCubeName(aCubeName);
				restrictions.setDimensionUniqueName(aDimensionName);
				restrictions.setHierarchyUniqueName(aHierarchy);
				restrictions.setLevelUniqueName(aLevel);
				mems = ds[i].getServerMetaData().getFilterTreeMembersList(restrictions,properties);
				System.out.println("list.size=" + mems.length);
				FilterTreeMemberElement mem = null;
				for (int j = 0; j < mems.length; j++) {
					System.out.println("..........LevelCaption=" + mems[j].getCaption());
					mem = (FilterTreeMemberElement) mems[j];
					list.add(mem);
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println("........caption=" + list.get(i).getCaption());
		}
		System.out.println(".............2       list.size=" + list.size());
		return list;
	}
	public static List<MemberElement> getMemberChildren(String aCubeName,String aParentMember){
	//public static List<MemberElement> getMemberChildren(String aCubeName,String aDimensionName,String aHierarchy,String aLevel,String aParentMember){
		System.out.println(_DataSourceInfo);
		ServerMetadata smd = new ServerMetadata(getDataSourceURI());
		DimensionTreeElement mems[] = {};
		DataSourceTreeElement ds[] = smd.discoverDataSources();
		List<MemberElement> list = new ArrayList<MemberElement>();
		if (ds != null) {
			for (int i = 0; ds != null && i < ds.length; i++) {

				XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
				XMLADiscoverProperties properties = XMLAObjectsFactory.newXMLADiscoverProperties();

				properties.setDataSourceInfo(_DataSourceInfo);
				properties.setCatalog(_Catalog);
				restrictions.setCubeName(aCubeName);
				//restrictions.setDimensionUniqueName(aDimensionName);
				//restrictions.setHierarchyUniqueName(aHierarchy);
				//restrictions.setLevelUniqueName(aLevel);
				restrictions.setMemberUniqueName(aParentMember);
				mems = ds[i].getServerMetaData().getFilterTreeMembersList(restrictions,properties);
				FilterTreeMemberElement mem = null;
				if(mems!=null && mems.length>0){
					
					DimensionTreeElement[] children=mems[0].getChildren(true);

					System.out.println("!!!!!!!!!!!!list.size=" + mems.length+"mem.caption="+mems[0].getCaption()+","+mems[0].getUniqueName()+"\n\n");

					if(children!=null){
						for (int j = 0; j < children.length; j++) {
							System.out.println("..........ChildMemberCaption=" + children[j].getCaption() + ","+children[j].getUniqueName());
							mem = (FilterTreeMemberElement) children[j];
							list.add(mem);
						}
					}
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println("........caption=" + list.get(i).getCaption());
		}
		System.out.println(".............2       list.size=" + list.size());
		return list;
	}
	
	
	public static String getHierarchyName(String aCubeName,String aHierarchyUniqueName){
		String caption="";
			System.out.println(_DataSourceInfo);
			ServerMetadata smd = new ServerMetadata(getDataSourceURI());
			DimensionTreeElement mems[] = {};
			DataSourceTreeElement ds[] = smd.discoverDataSources();
			List<MemberElement> list = new ArrayList<MemberElement>();
			if (ds != null) {
				for (int i = 0; ds != null && i < ds.length; i++) {

					XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
					XMLADiscoverProperties properties = XMLAObjectsFactory.newXMLADiscoverProperties();

					properties.setDataSourceInfo(_DataSourceInfo);
					properties.setCatalog(_Catalog);
					restrictions.setCubeName(aCubeName);
					//restrictions.setDimensionUniqueName(aDimensionName);
					//restrictions.setHierarchyUniqueName(aHierarchy);
					//restrictions.setLevelUniqueName(aLevel);
					restrictions.setHierarchyUniqueName(aHierarchyUniqueName);
					mems = ds[i].getServerMetaData().getHierarchyList(restrictions,properties);
					FilterTreeMemberElement mem = null;
					if(mems!=null && mems.length>0){
						caption = mems[0].getCaption();
					}
				}
			}
			System.out.println("........caption=" + caption);
			return caption;
		}
	public static String getMemberName(String aCubeName,String aMemberUniqueName){
		String caption="";
			System.out.println(_DataSourceInfo);
			try{
				XMLA_SOAP xmla_soap=new XMLA_SOAP(AIPOlap.getDataSourceURI(),"","");

				List<XMLA_OlapItem> lstMembers=null;
				lstMembers=xmla_soap.discoverMemTree(AIPOlap.getCatalog(),AIPOlap.getCubeName(), aMemberUniqueName, 8);
				if(lstMembers.size()!=0){
					caption = lstMembers.get(0).getCaption();
				}
			} catch (OlapException e) {
				e.printStackTrace();
			}
			System.out.println("........getMemberName=" + caption);
			return caption;
	}
	public static void getMemberTree(){
		try{
			XMLA_SOAP xmla_soap=new XMLA_SOAP(AIPOlap.getDataSourceURI(),"","");
			List<XMLA_OlapItem> lstMembers=null;
			lstMembers=xmla_soap.discoverMemTree(AIPOlap.getCatalog(),AIPOlap.getCubeName()
					, "[فرآورده].[رنگ فرآورده].[رنگ]"
					, 0);
			for (XMLA_OlapItem olapItem : lstMembers) {
				AIPUtil.printObject(olapItem);
			}
			//xmla_soap.executeQuery(arg0, arg1, arg2)
		} catch (OlapException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		try {
			XMLA_SOAP xmla_soap = new XMLA_SOAP(AIPOlap.getDataSourceURI(),"","");
			
			//List<XMLA_OlapItem> lst2=xmla_soap.discoverMemTree(AIPOlap.getCatalog(),AIPOlap.getCubeName(), aMember,1);
			List<XMLA_OlapItem> lst2=xmla_soap.discoverCat();
			

			for (XMLA_OlapItem item : lst2) {
				System.out.println(""+item.toString());
			}			

		} catch (OlapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void main3(String[] args) {
		getMemberTree();
	}
	/**
	 * @param args
	 */
	public static void main1(String[] args) {
		String aDimension = "";
		String aHierarchy = "";
		String aLevel = "";
/*		aDimension ="[زمان]";
		aHierarchy = "[زمان].[سال-ماه-روز]";
		aLevel = "[زمان].[سال-ماه-روز].[Year No]";
		String[] ar = getDimTreeMemberList(aDimension, aHierarchy, aLevel);
		for(int i=0;i<ar.length;i++){
			System.out.println(ar[i]);
		}
*/
/*		aDimension="[زمان]";
		aHierarchy = "[زمان].[زمان]";
		aLevel = "[زمان].[زمان].[ماه]";

		aDimension="[مناطق]";
		System.out.println(aDimension);
		aHierarchy = aDimension+"."+"[منطقه- ناحیه]";
		System.out.println(aHierarchy);
		aLevel = aHierarchy+"."+"[Level 02]";
		System.out.println(aLevel);
		System.out.println(aDimension+"    "+aHierarchy+"    "+aLevel);

		DimensionMember[] arr = getDimTreeMembers(aDimension, aHierarchy, aLevel);
		for(int i=0;i<arr.length;i++){
			AIPUtil.printObject(arr[i]);
		}
*/
		//getDimensionTreeElement();
		String aCubeName = "حواله";
		//getDimensions(aCubeName);
		System.out.println(".............................................1");

		aDimension = "[زمان]";
		//getHierarchys(aCubeName, aDimension);
		System.out.println(".............................................2");
		
		aHierarchy = "[زمان].[سال-فصل-ماه]";
		//getLevels(aCubeName, aDimension, aHierarchy);
		System.out.println(".............................................3");

		//aLevel = "[زمان].[زمان].[سال]";
		aLevel = "[زمان].[سال-فصل-ماه].[(All)]";
		//List<FilterTreeMemberElement> lstMembers=null;
		List<MemberElement> lstMembers=null;
		//lstMembers=getMembers(aCubeName, aDimension, aHierarchy, aLevel);
		//lstMembers=getMemberChildren(aCubeName, aDimension, aHierarchy, aLevel,"[زمان].[سال-فصل-ماه].[(All)].[همه]");
		//lstMembers=getMemberChildren(aCubeName, "[زمان].[سال-فصل-ماه].[(All)].[همه]");
		//lstMembers=getMemberChildren(aCubeName, "[زمان].[سال-فصل-ماه].[سال].[1387].&[1]");
//		lstMembers=getMemberChildren(aCubeName, "[زمان].[سال-فصل-ماه].[سال].[1387]");
		
		
		String aParentMember = "[زمان].[سال-فصل-ماه].[سال].[1388].&[1]";
		//lstMembers=getMemberChildren( aCubeName,aDimension,aHierarchy,aLevel,aParentMember );
		//lstMembers=getMembers( aCubeName,aDimension,aHierarchy,aLevel);
		//lstMembers=getMemberChildren(aCubeName,aDimension,aHierarchy,aLevel,aParentMember);
		//lstMembers=getMemberChildren(aCubeName,aParentMember);
		
		
		System.out.println(".............................................");
		System.out.println("*******************************");
		aDimension = "[مناطق]";
		aHierarchy = "[مناطق].[منطقه-ناحیه]";
		aLevel = "[مناطق].[منطقه-ناحیه].[Level 02]";
		//DimensionMember[] locationCombos = getDimTreeMembers("[مناطق]","[مناطق].[منطقه-ناحیه]","[مناطق].[منطقه-ناحیه].[Level 02]");
		//DimensionMember[] locationCombos = getDimTreeMembers(aDimension, aHierarchy, aLevel);
		
		aHierarchy = "[زمان].[سال]";
		aHierarchy = "[زمان].[سال-فصل-ماه]";
		System.out.println(getHierarchyName(aCubeName, aHierarchy));
	
		String aMember = "[زمان].[سال].&[1387]";
		aMember = "[زمان].[سال-فصل-ماه].[سال].&[1388]";
		aMember = "[زمان].[سال-فصل-ماه].[سال].&[1388].&[2]";
		aMember = "[مشتری].[مشتری].[نوع مصرف].&[3.0001].&[1.70001E1]";
		aMember = "[مشتری].[مشتری].[نوع مصرف].&[3.0001]";
		System.out.println(getMemberName(aCubeName, aMember));
	
		
		//aCubeName="";
		String aDimensionName="[فرآورده]";
		aHierarchy="[فرآورده].[فرآورده-نرخ]";
		aLevel="[فرآورده].[فرآورده-نرخ].[نام فرآورده]";
		List<FilterTreeMemberElement> lst= getMembers(aCubeName, aDimensionName, aHierarchy, aLevel);
		
		try {
			XMLA_SOAP xmla_soap = new XMLA_SOAP(AIPOlap.getDataSourceURI(),"","");
			
			List<XMLA_OlapItem> lst2=xmla_soap.discoverMemTree(AIPOlap.getCatalog(),AIPOlap.getCubeName(), aMember,1);
			

			for (XMLA_OlapItem hierarchy : lst2) {
				String oid = hierarchy.getUniqueName();
				String name = hierarchy.getCaption();
				System.out.println("oid="+oid+"...name="+name);
			}			

		} catch (OlapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/*public static void main2(String [] args) {
		try {
			//org.apache.log4j.xml.DOMConfigurator.configure(args[0]);//log4j

			SOAPQueryProvider a;
			Class.forName("com.symbol.xmlaprocessor.query.SOAPQueryProvider");
			QueryProviderManager.setDefaultQueryProviderName("com.symbol.xmlaprocessor.query.SOAPQueryProvider");
			
			QueryProvider sqp = QueryProviderManager.getQueryProvider();
			java.util.Properties props = new java.util.Properties(); // you could use a file here as well
			props.setProperty(SOAPQueryProvider.TARGET_URI,getDataSourceURI()); //args[1]);

			props.setProperty(SOAPQueryProvider.DATASOURCE_PROVIDER, "MSOLAP");
			props.setProperty(SOAPQueryProvider.DATASOURCE_SOURCE, "OLAP-SERVER");
			props.setProperty(SOAPQueryProvider.CATALOG, "pss");
			props.setProperty(SOAPQueryProvider.FORMAT, "TABULAR");
			
			props.setProperty(SOAPQueryProvider.NAMESPACE, "ns1");
			sqp.setConfiguration(props);
			
//			((SOAPQueryProvider)sqp).addProperty(new DataSourceInfo("MSOLAP",getDataSource()));// args[2]));
//			((SOAPQueryProvider)sqp).addProperty(new Catalog(getCatalog()));// args[3]));
//			((SOAPQueryProvider)sqp).addProperty(Format.TABULAR);
//			((SOAPQueryProvider)sqp).addProperty(Content.DATA);
			
			// This is not a neccessary step, but it will ensure no one can
			// modify the configuration
			// QueryProviderManager.setFrozen(TestQueryProvider.class.hashCode()+"", true);
			
			sqp.setResponseProcessor(new ResultSetResponseProcessor());
			
			
			String mdxquery="select {Measures.members} on columns from [HavalehAndFiche]";
			MDXQuery query = sqp.createMDXQuery(mdxquery);//args[4]);

			//log.debug("Query Info:\n"+query.toString());
			System.out.println("Query Info:\n"+query.toString());
			
			Cube cube = (Cube)query.executeQuery();
			
			ResultSet rs = ((ResultSetCube)cube).getResultSet();

			List dimensions = cube.getDimensions();
			List members = null;
			List childMembers = null;
			Measure measure = null;
			
			while (rs.next()) {
				for (int h=0;h<dimensions.size();h++) {
					members = ((Dimension)dimensions.get(h)).getMembers();
					if (members.size() > 0) {
						for (int i=0;i<members.size();i++) {
							childMembers = ((Member)members.get(i)).getChildMembers();
							for (int j=0;j<childMembers.size();j++) {
								measure = ((Member)childMembers.get(j)).getMeasure(null);
								if (measure != null) {
									//log.debug("Member:"+rs.getRowMember().toString()+" Object:"+measure.toString());
									System.out.println("Member:"+rs.getRowMember().toString()+" Object:"+measure.toString());
								}
							}
						}
					}
					else {
						//log.debug("Member:"+rs.getRowMember().toString());
						System.out.println("Member:"+rs.getRowMember().toString());
						break;
					}
				}
			}
			
/*			Member netDlrs = cube.getDimensionByName("Measures").getMemberByName("Net Dlrs");
			Member week = netDlrs.getChildMemberByName("Week 18");
			Member member = null;
			while (rs.next()) {
				Measure m = week.getMeasure(null);
				if (m != null) {
					member = rs.getRowMember();
					log.debug("Member:"+member.toString()+" Object:"+m.toString());
				}
			}
			
			This is another way
			while (rs.next()) {
				Object o = rs.get(week);
				if (o != null) {
					member = rs.getRowMember();
					log.debug("Member:"+member.toString()+" Object:"+o.toString());
				}
			}
*/

		/*}
		catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
}
