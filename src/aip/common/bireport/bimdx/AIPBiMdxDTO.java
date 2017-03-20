package aip.common.bireport.bimdx;

import java.util.ArrayList;
import java.util.List;

public class AIPBiMdxDTO {
	
	String reportName;
	String mdxQueryNC;
	String mdxQuery;
	
	List<AIPBiMdxCriteria> criterias=new ArrayList<AIPBiMdxCriteria>(); 


	
	public List<AIPBiMdxCriteria> getCriterias() {
		return criterias;
	}

	public void setCriterias(List<AIPBiMdxCriteria> criterias) {
		this.criterias = criterias;
	}

	public String getMdxQueryNC() {
		return mdxQueryNC;
	}

	public void setMdxQueryNC(String mdxQueryNC) {
		this.mdxQueryNC = mdxQueryNC;
	}

	public String getMdxQuery() {
		return mdxQuery;
	}

	public void setMdxQuery(String mdxQuery) {
		this.mdxQuery = mdxQuery;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
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
	

}
