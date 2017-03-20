package aip.common.dashboard;

import aip.common.AIPWebUser;
import aip.common.AIPWebUserInterface;

public class DashboardParam implements AIPWebUserInterface{

	AIPWebUser webUser = new AIPWebUser();
	int orderNo;

	public int getNo() {
		return orderNo;
	}

	public void setNo(int no) {
		this.orderNo = no;
	}

	public AIPWebUser getWebUser() {
		return webUser;
	}

	public void setWebUser(AIPWebUser webUser) {
		this.webUser = webUser;
	}
	
}
