package aip.common.report;

public class ReportDetailENT implements java.io.Serializable{
	private Integer id;
	private Integer reportId;
	private Integer orderNo;
	private String type;
	private String dimension;
	private String selectedMembers;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getSelectedMembers() {
		return selectedMembers;
	}
	public void setSelectedMembers(String selectedMembers) {
		this.selectedMembers = selectedMembers;
	}
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

}
