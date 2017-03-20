package aip.common;

import aip.common.common.ConstantParam;

public class AIPDefaultLSTParam implements AIPPagingParamInterface,
                                           AIPWebUserInterface{
    public AIPDefaultLSTParam() {
	}
	private int pageSize=ConstantParam.Page_SIZE;
    private int reqPage;
    private AIPWebUser webUser;
    private boolean isDescending = false;
	private String sortedByField;
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getReqPage() {
		return reqPage;
	}
	public void setReqPage(int reqPage) {
		this.reqPage = reqPage;
	}
	public AIPWebUser getWebUser() {
		return webUser;
	}
	public void setWebUser(AIPWebUser webUser) {
		this.webUser = webUser;
	}
	public boolean isDescending() {
		return isDescending;
	}
	public void setDescending(boolean isDescending) {
		this.isDescending = isDescending;
	}
	public String getSortedByField() {
		return sortedByField;
	}
	public void setSortedByField(String sortedByField) {
		this.sortedByField = sortedByField;
	}
    


	

	
}
