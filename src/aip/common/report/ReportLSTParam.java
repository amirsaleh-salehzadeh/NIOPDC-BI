package aip.common.report;

import aip.common.AIPDefaultLSTParam;
import aip.common.common.ConstantParam;

public class ReportLSTParam extends AIPDefaultLSTParam{
	    private String filter;
	    public ReportLSTParam(){
	    	//ReportLSTParam param = new ReportLSTParam();
			this.setDescending(true);
			this.setSortedByField(ConstantParam.REPORT_SORTED_BY_FIELD_CREATE_DATE);
		}
		public String getFilter() {
			return filter;
		}
		public void setFilter(String filter) {
			this.filter = filter;
		} 
}
