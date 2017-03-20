package common.dashboard;

import java.util.ArrayList;

import aip.common.AIPPagingLSTInterface;
import aip.common.AIPWebUser;
import aip.common.AIPWebUserInterface;

public class DashboardLST implements   AIPWebUserInterface,
AIPPagingLSTInterface{
    private int currentPage;
    private int totalCount;
    private int pageSize;
    private AIPWebUser webUser = new AIPWebUser();
    private ArrayList<DashboardENT> ent = new ArrayList<DashboardENT>(); 
    
	public ArrayList<DashboardENT> getEnt() {
		return ent;
	}
	public void setEnt(ArrayList<DashboardENT> ent) {
		this.ent = ent;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public AIPWebUser getWebUser() {
		return webUser;
	}
	public void setWebUser(AIPWebUser webUser) {
		this.webUser = webUser;
	}
 

}
