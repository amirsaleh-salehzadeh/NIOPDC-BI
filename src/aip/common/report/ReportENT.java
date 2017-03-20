package aip.common.report;

import java.util.ArrayList;
import java.util.List;

import aip.common.bireport.bimdx.AIPBiMdxCriteria;

public class ReportENT implements java.io.Serializable{
	   private Integer id;
	   private String reportName;//adl replaced queryName
	   private String mdxQueryNC;//adl replaced query
	   private String mdxQuery;//adl added
	   private String createDate;
	   private String userName;
	   private String defaultDiagramType;
	   private Boolean isVisual;
	   private Boolean isPublic;
	   private Integer folderId;
	   private List<ReportDetailENT> reportDetailENTs = new ArrayList<ReportDetailENT>();
	   private List<ReportCriteriaENT> criterias=new ArrayList<ReportCriteriaENT>();  // adl replaced reportParam
	   
	   
	public Integer getId() {
		return id;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public List<ReportDetailENT> getReportDetailENTs() {
		return reportDetailENTs;
	}

	public void setReportDetailENTs(List<ReportDetailENT> reportDetailENTs) {
		this.reportDetailENTs = reportDetailENTs;
	}

//	public List<ReportParamENT> getReportParamENTs() {
//		return reportParamENTs;
//	}
//
//	public void setReportParamENTs(List<ReportParamENT> reportParamENTs) {
//		this.reportParamENTs = reportParamENTs;
//	}
//
	public void setId(int id) {
		this.id = id;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getMdxQueryNC() {
		return mdxQueryNC;
	}
	public void setMdxQueryNC(String mdxQueryNC) {
		this.mdxQueryNC = mdxQueryNC;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public Boolean getIsVisual() {
		return isVisual;
	}
	public void setIsVisual(Boolean isVisual) {
		this.isVisual = isVisual;
	}
	public Boolean getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getDefaultDiagramType() {
		return defaultDiagramType;
	}

	public void setDefaultDiagramType(String defaultDiagramType) {
		this.defaultDiagramType = defaultDiagramType;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMdxQuery() {
		return mdxQuery;
	}

	public void setMdxQuery(String mdxQuery) {
		this.mdxQuery = mdxQuery;
	}
	
	
	public void generateMdxQueryFromNCAndCriterias(){
		StringBuffer sb=new StringBuffer();
		int count_ques=0;
		for(int i=0;i<mdxQueryNC.length();i++){
			if(mdxQueryNC.charAt(i)=='?'){
				if(criterias.size()>count_ques){
					sb.append(criterias.get(count_ques).getDefaultValue());
				}
				count_ques++;
			}else{
				sb.append(mdxQueryNC.charAt(i));
			}
		}
		mdxQuery = sb.toString();
	}

	public List<ReportCriteriaENT> getCriterias() {
		return criterias;
	}

	public void setCriterias(List<ReportCriteriaENT> criterias) {
		this.criterias = criterias;
	}

	public Integer getFolderId() {
		return folderId;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}
	
}
