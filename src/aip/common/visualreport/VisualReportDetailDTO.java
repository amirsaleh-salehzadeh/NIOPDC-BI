package aip.common.visualreport;

public class VisualReportDetailDTO implements java.io.Serializable{
	Integer id;
	Integer visualReportId;
	Integer orderNo;
	String type;
	String dimension;
	String selectedMembers;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVisualReportId() {
		return visualReportId;
	}
	public void setVisualReportId(Integer visualReportId) {
		this.visualReportId = visualReportId;
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
	

}
