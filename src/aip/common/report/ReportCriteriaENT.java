package aip.common.report;

import java.io.Serializable;

public class ReportCriteriaENT implements Serializable {
    private Integer id;
    private Integer reportId;
    private Integer order;
	String name;
	String criteriaMdx;
	String defaultValue;
	String defaultCaption;
    
	public String getCriteriaMdx() {
		return criteriaMdx;
	}

	public void setCriteriaMdx(String criteriaMdx) {
		this.criteriaMdx = criteriaMdx;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultCaption() {
		return defaultCaption;
	}

	public void setDefaultCaption(String defaultCaption) {
		this.defaultCaption = defaultCaption;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
	
	
}
