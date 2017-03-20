package aip.common.report;

public class ReportDTO implements java.io.Serializable{
	   private int id;
	   private String reportName;
	   private String createDate;
	   private String creatorFullname; 
	   private Boolean isVisual;
	   private Boolean isPublic;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreatorFullname() {
		return creatorFullname;
	}
	public void setCreatorFullname(String creatorFullname) {
		this.creatorFullname = creatorFullname;
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

	   
}
