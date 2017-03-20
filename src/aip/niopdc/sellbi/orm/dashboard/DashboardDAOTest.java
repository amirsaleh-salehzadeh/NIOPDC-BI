package aip.niopdc.sellbi.orm.dashboard;

import aip.common.AIPDefaultParam;
import aip.common.AIPException;
import aip.common.AIPWebUser;
import aip.common.dashboard.DashboardENT;
import aip.common.dashboard.DashboardParam;
import aip.util.AIPUtil;
import junit.framework.TestCase;

public class DashboardDAOTest extends TestCase {
	DashboardDAO dao = new DashboardDAO();
	public static void main(String[] args) {
		DashboardDAOTest t = new DashboardDAOTest();
		//t.testGetDashboardENTDashboardParam();
		//t.testGetDashboardENTAIPDefaultParam();
		//t.testGetDashboardLST();
		//t.testGetUserDashboardComboLST();
		t.testSaveDashboard();
	}

	public void testDeleteDashboard() {
		fail("Not yet implemented");
	}

	public void testGetDashboardENTDashboardParam() {
		DashboardParam param = new DashboardParam();
		param.setNo(1);
		AIPUtil.printObject(dao.getDashboardENT(param));
	}

	public void testGetDashboardENTAIPDefaultParam() {
		AIPDefaultParam param = new AIPDefaultParam();
		param.setId(1);
		AIPUtil.printObject(dao.getDashboardENT(param));
	}

	public void testGetDashboardLST() {
		AIPWebUser userParam = new AIPWebUser();
		AIPUtil.printObject(dao.getDashboardLST(userParam));
	}

	public void testGetUserDashboardComboLST() {
		AIPWebUser userParam = new AIPWebUser();
		userParam.setRemoteUser("amirsaleh");
		AIPUtil.printObject(dao.getUserDashboardComboLST(userParam));
	}

	public void testSaveDashboard() {
		DashboardENT ent = new DashboardENT();
		//ent.getWebUser().setRemoteUser("amirsaleh");
		ent.setDashboardNo(3);
		ent.setGroupId(2);
		ent.setDashboardQuery("DDD2");
		ent.setCaption("CC");
		try {
			AIPUtil.printObject(dao.saveDashboard(ent));
		} catch (AIPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
