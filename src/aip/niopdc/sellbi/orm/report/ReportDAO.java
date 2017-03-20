package aip.niopdc.sellbi.orm.report;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aip.common.AIPBaseDeleteParam;
import aip.common.AIPDefaultParam;
import aip.common.AIPException;
import aip.common.common.ConstantParam;
import aip.common.report.ReportCriteriaENT;
import aip.common.report.ReportDTO;
import aip.common.report.ReportDetailENT;
import aip.common.report.ReportENT;
import aip.common.report.ReportForSaveParam;
import aip.common.report.ReportInterface;
import aip.common.report.ReportLST;
import aip.common.report.ReportLSTParam;
import aip.niopdc.sellbi.orm.BaseHibernateDAO;
import aip.util.AIPUtil;
import aip.util.DateCnv;
import aip.util.NVL;
import aip.xmla.AIPOlap;

public class ReportDAO extends BaseHibernateDAO implements ReportInterface {

	public void deleteReport(AIPBaseDeleteParam param) {
		if(param.getId()>0)
			super.delete(ReportENT.class, param.getId());
		else if(!"".equals(NVL.getString(param.getIds()))){
			super.deleteSelectedIds(ReportENT.class, param.getIds());
		}else if(!"".equals(NVL.getString(param.getCriteria()))){
			super.deleteByCondition(ReportENT.class, param.getCriteria());
		}
		
	}
	public ReportLST getAllReports(ReportLSTParam param) {
		ReportLST lst = new ReportLST();
		
		Query q = getSession().getNamedQuery("spBIGetReportsCount");
		q.setString(0, NVL.getString(param.getFilter()));
		q.setString(1, NVL.getString(param.getWebUser()));
		lst.setTotalItems(NVL.getInt(q.uniqueResult()));
		lst.setProperties(lst.getTotalItems(),param.getReqPage(),param.getPageSize());

		q = getSession().getNamedQuery("spBIGetReports");
		q.setString(0, NVL.getString(param.getFilter()));
		q.setInteger(1, NVL.getInt(lst.getFirst()));
		q.setInteger(2, NVL.getInt(lst.getPageSize()));
		q.setString(3, NVL.getString(param.getSortedByField()));
		q.setString(4, NVL.getString(param.getWebUser()));
		
		lst.setDtos((ArrayList<ReportDTO>)q.list());
		return lst;
	}
	public ReportLST getMDXReports(ReportLSTParam param) {
		ReportLST lst = new ReportLST();

		Query q = getSession().getNamedQuery("spBIGetMDXReportsCount");
		q.setString(0, NVL.getString(param.getFilter()));
		q.setString(1, NVL.getString(param.getWebUser()));
		lst.setTotalItems(NVL.getInt(q.uniqueResult()));
		lst.setProperties(lst.getTotalItems(),param.getReqPage(),param.getPageSize());

		q = getSession().getNamedQuery("spBIGetMDXReports");
		q.setString(0, NVL.getString(param.getFilter()));
		q.setInteger(1, NVL.getInt(lst.getFirst()));
		q.setInteger(2, NVL.getInt(lst.getPageSize()));
		q.setString(3, NVL.getString(param.getSortedByField()));
		q.setString(4, NVL.getString(param.getWebUser()));
		
		lst.setDtos((ArrayList<ReportDTO>)q.list());
		return lst;
	}
	public ReportENT getReportENT(AIPDefaultParam param) {
		ReportENT ent = (ReportENT)get(ReportENT.class, param.getId());
		//List<ReportDetailENT> list = new ArrayList<ReportDetailENT>();
		
		Query q = getSession().createQuery("from ReportDetailENT where reportId="+ent.getId());
		ent.setReportDetailENTs(q.list());

		q = getSession().createQuery("from ReportCriteriaENT where reportId="+ent.getId());
		ent.setCriterias(q.list());
		
		if(ent.getCriterias().size()>0){
			ent.generateMdxQueryFromNCAndCriterias();
		}
		
		return ent;
	}
	public ReportLST getVisualReports(ReportLSTParam param) {
		ReportLST lst = new ReportLST();

		Query q = getSession().getNamedQuery("spBIGetVisualReportsCount");
		q.setString(0, NVL.getString(param.getFilter()));
		q.setString(1, NVL.getString(param.getWebUser()));
		lst.setTotalItems(NVL.getInt(q.uniqueResult()));
		lst.setProperties(lst.getTotalItems(),param.getReqPage(),param.getPageSize());

		q = getSession().getNamedQuery("spBIGetVisualReports");
		q.setString(0, NVL.getString(param.getFilter()));
		q.setInteger(1, NVL.getInt(lst.getFirst()));
		q.setInteger(2, NVL.getInt(lst.getPageSize()));
		q.setString(3, NVL.getString(param.getSortedByField()));
		q.setString(4, NVL.getString(param.getWebUser()));
		
		lst.setDtos((ArrayList<ReportDTO>)q.list());
		return lst;
	}
	public void makePrivate(AIPDefaultParam param) {
		ReportENT ent = (ReportENT)get(ReportENT.class, param.getId());
		if(NVL.getString(ent.getUserName()).equals(NVL.getString(param.getWebUser()))){
			ent.setIsPublic(false);
			ent = (ReportENT) super.save(ent);
		}
	}
	public void makePublic(AIPDefaultParam param) {
		ReportENT ent = (ReportENT)get(ReportENT.class, param.getId());
		if(NVL.getString(ent.getUserName()).equals(NVL.getString(param.getWebUser()))){
			ent.setIsPublic(true);
			ent = (ReportENT) super.save(ent);
		}
	}
	public ReportENT saveReport(ReportForSaveParam param) throws AIPException{
		ReportENT ent = param.getReportENT();
		ent.setId(NVL.getInteger(ent.getId()));
		ent.setCreateDate(DateCnv.nowJalali());
		AIPUtil.printObject(param);
		String query = ""; 
		if(NVL.getBool(ent.getIsVisual())){
		
			String rowIds = getUniqueIds4Query(param.getRows());
	 		String columnIds = getUniqueIds4Query(param.getColumns());
	 		String filterIds = getUniqueIds4Query(param.getFilters());
	 		String measureIds = getUniqueIds4Query(param.getMeasures());
	 		query = createVisualQuery(measureIds, rowIds,columnIds, filterIds);
	 		query = query.replaceAll("%26","&");
	 		ent.setMdxQueryNC(query);

		}
		fillReportDetailENTs(ent,param);
		Session session = getSession();
		Transaction tx = null;
		try{
			tx=session.beginTransaction();
			List<ReportDetailENT> list = ent.getReportDetailENTs();
			List<ReportCriteriaENT> criteriaList = ent.getCriterias();
			
			ent = (ReportENT) super.saveNoTran(ent,session);
			session.flush();
			
			/*
			 * save Details
			 */
			ReportDetailENT detailENT = null;
			for(int i=0;i<list.size();i++){
				detailENT = list.get(i);
				detailENT.setReportId(ent.getId());
				detailENT = (ReportDetailENT)super.saveNoTran(detailENT, session);
				list.set(i, detailENT);
			}
			
			/*
			 * save
			 */
			
			ReportCriteriaENT criteriaENT = null;
			for(int i=0;i<criteriaList.size();i++){
				criteriaENT = criteriaList.get(i);
				criteriaENT.setReportId(ent.getId());
				criteriaENT = (ReportCriteriaENT)super.saveNoTran(criteriaENT, session);
				criteriaList.set(i, criteriaENT);
			}
			
			ent.setReportDetailENTs(list);
			ent.setCriterias(criteriaList);
	    	tx.commit();
	    	//session.clear();
		}catch (HibernateException e) {
			e.printStackTrace();
			if(tx!=null && tx.isActive() )
				tx.rollback();
			session.clear();
			session.close();
			throw getAIPException(AIPEX_SAVE,e);
		}

		return ent;
	}
	private void fillReportDetailENTs(ReportENT ent, ReportForSaveParam param) {
		ArrayList<ReportDetailENT> list = new ArrayList<ReportDetailENT>();
		
		/*
 		 * get rowIds & dim 
 		 */
		if(param.getRows() != null && param.getRows().length>0){
			list.addAll(getReportDetailENTs(param.getRows(),ConstantParam.REPORT_DIMENSION_ADD_TO_ROW));
		}
 		/*
 		 * get columnIds & dim  
 		 */
 		if(param.getColumns() != null && param.getColumns().length>0){
 			list.addAll(getReportDetailENTs(param.getColumns(),ConstantParam.REPORT_DIMENSION_ADD_TO_COLUMN));	 		
 		}

 		/*
 		 * get filterIds & dim 
 		 */
 		if(param.getFilters() != null && param.getFilters().length>0){
 			list.addAll(getReportDetailENTs(param.getFilters(),ConstantParam.REPORT_DIMENSION_ADD_TO_FILTER));
 		}

 		/*
 		 * get measureIds & dim 
 		 */
 		if(param.getMeasures() != null && param.getMeasures().length>0){
 			list.addAll(getReportDetailENTs(param.getMeasures(),ConstantParam.REPORT_DIMENSION_ADD_TO_MEASURE));
 		}

		ent.getReportDetailENTs().addAll(list);
	}
	private ArrayList<ReportDetailENT> getReportDetailENTs(String[] arr, String reportDimensionAddToType) {
		ArrayList<ReportDetailENT> list = new ArrayList<ReportDetailENT>();
		ReportDetailENT detailENT = new ReportDetailENT();
		String ids="";
		String st = "";
		for(int i=0;i<arr.length;i++){
			st = arr[i];
			if(st.indexOf('{')>0 && st.indexOf("}")>0){
				detailENT = new ReportDetailENT();
				String dim = st.substring(st.indexOf('{')+1, st.indexOf('}'));
	 			
				ids+=st.substring(st.indexOf('}')+1);
				ids = ids.replaceAll("%26","&");
				
				ids = removeDuplicated(ids);
				
				detailENT.setDimension(dim);
				detailENT.setSelectedMembers(NVL.getStringNull(ids));
				detailENT.setType(reportDimensionAddToType);
				detailENT.setOrderNo(i+1);
				
				list.add(detailENT);
			}
 		}

		return list;
	}
	private String removeDuplicated(String ids) {
		String[] arr = AIPUtil.splitString(ids,  ",");
		List<String> list = new ArrayList<String>();
		for(String s:arr){
			if(!list.contains(s))
				list.add(s);
		}
		String st = (list.size()>0 ? ","+AIPUtil.joinSelectedIds(list.toArray(), ",")+"," : "");
		return st;
	}

	private String getUniqueIds4Query(String[] arr){
		String ids="";
		if(arr != null && arr.length>0){
			for(String st :arr){
				//st = UTF8.cnvUTF8(st);
				ids+=st.substring(st.indexOf('}')+1);
			}
		}
		return ids;
	}
	public String createVisualQuery(String measureUniqueName,String rowUniqueName,String columnUniqueName,String filterUniqueName) {
		String q = "";
		String rowStr = "";
		String columnStr = "";
		String measureStr = "";
		String filterStr = "";
		if(/*!"".equals(measureUniqueName) || */!"".equals(rowUniqueName) || !"".equals(columnUniqueName) /*|| !"".equals(filterUniqueName)*/){
			
			if(!"".equals(rowUniqueName)){
				String rowArr[] = AIPUtil.splitString(rowUniqueName, ",");
				rowStr = " { ";
				for(int i=0;i<rowArr.length;i++){
					if(rowArr[i]!=",")
						rowStr+=rowArr[i]+",";
					
				}
				rowStr = rowStr.substring(0, rowStr.length()-1);
				rowStr+=" } on rows ";
			}
			if(!"".equals(columnUniqueName)){
				String columnArr[] = AIPUtil.splitString(columnUniqueName, ",");
				columnStr = " { ";
				for(int i=0;i<columnArr.length;i++){
					columnStr+=columnArr[i]+",";
					
				}
				columnStr = columnStr.substring(0, columnStr.length()-1);
				columnStr+=" } on columns ";
			}
			
			if(!"".equals(measureUniqueName)){
				String measureArr[] = AIPUtil.splitString(measureUniqueName, ",");
				measureStr = " { ";
				for(int i=0;i<measureArr.length;i++){
					measureStr+=measureArr[i]+",";
					
				}
				measureStr = measureStr.substring(0, measureStr.length()-1);
				measureStr+=" } on pages ";
			}


			if(!"".equals(filterUniqueName)){
				String filterArr[] = AIPUtil.splitString(filterUniqueName, ",");
				filterStr = " { ";
				for(int i=0;i<filterArr.length;i++){
					filterStr+=filterArr[i]+",";
					
				}
				filterStr = filterStr.substring(0, filterStr.length()-1);
				filterStr+=" } ";
			}
			
			q = "select "+rowStr+( !"".equals(rowStr) && !"".equals(columnStr) ? "," : "")+columnStr+
				((!"".equals(rowStr) || !"".equals(columnStr)) && !"".equals(measureStr) ? "," : "")+measureStr+
				" from ["+AIPOlap.getCubeName()+"] "+
				(!"".equals(filterStr) ? " where " : "")+filterStr;

		}
		
		return q;
	}
	public String createVisualQuery(String[] rows,String[] columns,String[] filters,String[] measures){
		String rowIds = getUniqueIds4Query(rows);
 		String columnIds = getUniqueIds4Query(columns);
		try {
			System.out.println("********888888888***="+URLEncoder.encode( columnIds.trim() ,"iso8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

 		String filterIds = getUniqueIds4Query(filters);
 		String measureIds = getUniqueIds4Query(measures);
 		String query = createVisualQuery(measureIds, rowIds,columnIds, filterIds);
 		return query;
	}

	
	public ReportENT saveReport(ReportENT ent) throws AIPException{
		//ReportENT ent = param.getReportENT();
		ent.setId(NVL.getInteger(ent.getId()));
		ent.setCreateDate(DateCnv.nowJalali());
		AIPUtil.printObject(ent);
		String query = ""; 
//		if(NVL.getBool(ent.getIsVisual())){
//			String rowIds = getUniqueIds4Query(param.getRows());
//	 		String columnIds = getUniqueIds4Query(param.getColumns());
//	 		String filterIds = getUniqueIds4Query(param.getFilters());
//	 		String measureIds = getUniqueIds4Query(param.getMeasures());
//	 		query = createVisualQuery(measureIds, rowIds,columnIds, filterIds);
//	 		query = query.replaceAll("%26","&");
//	 		ent.setMdxQueryNC(query);
//
//		}
//		fillReportDetailENTs(ent,param);
		Session session = getSession();
		Transaction tx = null;
		try{
			tx=session.beginTransaction();
			List<ReportDetailENT> list = ent.getReportDetailENTs();
			List<ReportCriteriaENT> criteriaList = ent.getCriterias();
			if(ent.getId()!=null){
				session.createQuery("delete from ReportCriteriaENT where reportId = ?")
				.setInteger(0, ent.getId()).executeUpdate();
			}			
			ent = (ReportENT) super.saveNoTran(ent,session);
			session.flush();
			
			/*
			 * save Details
			 */
			ReportDetailENT detailENT = null;
			for(int i=0;i<list.size();i++){
				detailENT = list.get(i);
				detailENT.setReportId(ent.getId());
				detailENT = (ReportDetailENT)super.saveNoTran(detailENT, session);
				list.set(i, detailENT);
			}
			
			/*
			 * save
			 */
			
			ReportCriteriaENT criteriaENT = null;
			for(int i=0;i<criteriaList.size();i++){
				criteriaENT = criteriaList.get(i);
				criteriaENT.setReportId(ent.getId());
				criteriaENT = (ReportCriteriaENT)super.saveNoTran(criteriaENT, session);
				criteriaList.set(i, criteriaENT);
			}
			
			ent.setReportDetailENTs(list);
			ent.setCriterias(criteriaList);
	    	tx.commit();
	    	//session.clear();
		}catch (HibernateException e) {
			e.printStackTrace();
			if(tx!=null && tx.isActive() )
				tx.rollback();
			session.clear();
			session.close();
			throw getAIPException(AIPEX_SAVE,e);
		}

		return ent;
	}
}
