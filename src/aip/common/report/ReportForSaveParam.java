package aip.common.report;

public class ReportForSaveParam {

	private ReportENT reportENT = new ReportENT();
	private String[] rows;
	private String[] columns;
	private String[] filters;
	private String[] measures;
	private String parameters;
	
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public ReportENT getReportENT() {
		return reportENT;
	}
	public void setReportENT(ReportENT reportENT) {
		this.reportENT = reportENT;
	}
	public String[] getRows() {
		return rows;
	}
	public void setRows(String[] rows) {
		this.rows = rows;
	}
	public String[] getColumns() {
		return columns;
	}
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	public String[] getFilters() {
		return filters;
	}
	public void setFilters(String[] filters) {
		this.filters = filters;
	}
	public String[] getMeasures() {
		return measures;
	}
	public void setMeasures(String[] measures) {
		this.measures = measures;
	}
}
