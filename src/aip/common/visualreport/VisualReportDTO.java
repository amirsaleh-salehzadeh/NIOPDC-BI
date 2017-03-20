package aip.common.visualreport;

import java.util.ArrayList;
import java.util.List;

public class VisualReportDTO implements java.io.Serializable{
	Integer id;
	String queryName;
	String query;
	List<VisualReportDetailDTO> children = new ArrayList<VisualReportDetailDTO>();
	


	public List<VisualReportDetailDTO> getChildren() {
		return children;
	}
	public void setChildren(List<VisualReportDetailDTO> children) {
		this.children = children;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

}
