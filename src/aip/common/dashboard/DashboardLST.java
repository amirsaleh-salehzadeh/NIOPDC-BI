package aip.common.dashboard;

import java.util.ArrayList;
import aip.common.AIPWebUser;
import aip.common.AIPWebUserInterface;

public class DashboardLST implements   AIPWebUserInterface {
    
    private AIPWebUser webUser = new AIPWebUser();
    private ArrayList<DashboardENT> dashboardENTs = new ArrayList<DashboardENT>(); 
    
	
	public AIPWebUser getWebUser() {
		return webUser;
	}
	public void setWebUser(AIPWebUser webUser) {
		this.webUser = webUser;
	}
	public ArrayList<DashboardENT> getDashboardENTs() {
		return dashboardENTs;
	}
	public void setDashboardENTs(ArrayList<DashboardENT> dashboardENTs) {
		this.dashboardENTs = dashboardENTs;
	}
}
