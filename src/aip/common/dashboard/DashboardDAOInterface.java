package aip.common.dashboard;

import aip.common.security.group.GroupLST;

import aip.common.AIPBaseDeleteParam;
import aip.common.AIPDefaultParam;
import aip.common.AIPException;
import aip.common.AIPWebUser;

public interface DashboardDAOInterface {
   public DashboardLST getDashboardLST(AIPWebUser user) throws AIPException;
   public DashboardENT saveDashboard(DashboardENT ent) throws AIPException;
   public DashboardENT getDashboardENT(DashboardParam param );
   public DashboardENT getDashboardENT(AIPDefaultParam param );
   public void deleteDashboard(AIPBaseDeleteParam param) throws AIPException;
   public GroupLST getUserDashboardComboLST(AIPWebUser user)throws AIPException;
}
