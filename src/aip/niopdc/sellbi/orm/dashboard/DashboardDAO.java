package aip.niopdc.sellbi.orm.dashboard;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aip.common.AIPBaseDeleteParam;
import aip.common.AIPDefaultParam;
import aip.common.AIPException;
import aip.common.AIPWebUser;
import aip.common.common.ConstantParam;
import aip.common.dashboard.DashboardDAOInterface;
import aip.common.dashboard.DashboardENT;
import aip.common.dashboard.DashboardLST;
import aip.common.dashboard.DashboardParam;
import aip.common.security.group.GroupLST;
import aip.niopdc.sellbi.orm.BaseHibernateDAO;
import aip.niopdc.sellbi.orm.common.CommonDAO;
import aip.util.NVL;

public class DashboardDAO extends BaseHibernateDAO implements
		DashboardDAOInterface {

	public void deleteDashboard(AIPBaseDeleteParam param) throws AIPException {
		if(param.getId()>0)
			super.delete(DashboardENT.class, param.getId());
		else if(!"".equals(NVL.getString(param.getIds()))){
			super.deleteSelectedIds(DashboardENT.class, param.getIds());
		}else if(!"".equals(NVL.getString(param.getCriteria()))){
			super.deleteByCondition(DashboardENT.class, param.getCriteria());
		}
	}

	public DashboardENT getDashboardENT(DashboardParam param){
        //int userId= getUserId(param.getWebUser().getRemoteUser());
        DashboardENT ent = new DashboardENT();
        
        Query q = getSession().getNamedQuery("spBIgetUserDashboards");
        q.setString(0, param.getWebUser().getRemoteUser());
        q.setInteger(1, param.getNo());
        ent = (DashboardENT)q.uniqueResult();

		return ent;
	}

	public DashboardENT getDashboardENT(AIPDefaultParam param) {
		return (DashboardENT)super.get(DashboardENT.class, param.getId());
	}
	
	public DashboardLST getDashboardLST(AIPWebUser userParam){
		DashboardLST lst = new DashboardLST();
        Query q = getSession().getNamedQuery("spBIgetUserDashboards");
		
		for(int i=1;i<=ConstantParam.DASHBOARD_NUMBER;i++){
	        q.setString(0, userParam.getRemoteUser());
	        q.setInteger(1, i);
			lst.getDashboardENTs().add((DashboardENT)q.uniqueResult());
		}
		lst.setWebUser(userParam);
		return lst;
	}

	public GroupLST getUserDashboardComboLST(AIPWebUser userParam){
		GroupLST lst = new GroupLST();
//		Query q = getSession().getNamedQuery("fnSECUserHasRole");
//		q.setString(0, userParam.getRemoteUser());
//		q.setString(1, "defineGroupDashboard");
		CommonDAO dao = new CommonDAO();
//		boolean userHasRole = dao.userHasRole(userParam.getRemoteUser(), "defineGroupDashboard");
//        if(userHasRole){
        	lst = dao.getGroupLST(userParam);
//        }

        return lst;                 
	}

	public DashboardENT saveDashboard(DashboardENT ent) throws AIPException {
		ent.setGroupId(NVL.getInteger(ent.getGroupId()));
//		ent.setUserName(NVL.getStringNull(ent.getWebUser().getRemoteUser()));
		ent.setUserName(NVL.getStringNull(ent.getUserName()));
		
		DashboardENT oldENT = getOldDashboardENT(ent);
		CommonDAO dao = new CommonDAO();
		
		try{
			if("".equals(NVL.getString(ent.getUserName())) && NVL.getInt(ent.getGroupId()) == 0){
				boolean userHasRole = dao.userHasRole(ent.getWebUser().getRemoteUser(), "definePublicDashboard");
				if(userHasRole){
					deleteOldSaveNew(ent, oldENT);
//					if(oldENT!=null)
//						super.delete(oldENT);
//					ent = (DashboardENT)super.save(ent);
				}else
					throw getAIPException("کاربر مجاز به تعریف داشبورد عمومی نمی باشد. ",null);
			}else if("".equals(NVL.getString(ent.getUserName())) && NVL.getInt(ent.getGroupId()) != 0){
				boolean userHasRole = dao.userHasRole(ent.getWebUser().getRemoteUser(), "defineGroupDashboard");
				if(userHasRole){
					deleteOldSaveNew(ent, oldENT);
				}else
					throw getAIPException("کاربر مجاز به تعریف داشبورد گروهی نمی باشد. ",null);
		
			}else if(!"".equals(NVL.getString(ent.getUserName())) && NVL.getInt(ent.getGroupId()) == 0){
				boolean userHasRole = dao.userHasRole(ent.getWebUser().getRemoteUser(), "definePersonalDashboard");
				if(userHasRole){
					deleteOldSaveNew(ent, oldENT);
				}else
					throw getAIPException("کاربر مجاز به تعریف داشبورد شخصی نمی باشد. ",null);
			}else
				throw getAIPException("نوع داشبورد مشخص نشده است. ",null);
		} catch (HibernateException ex) {
			ex.printStackTrace();
//			if (ex.getCause().getMessage().indexOf("Duplicate entry") > -1) {
//				deleteOldBeforSave(ent);
//			}
		}
		return ent;
	}

	private DashboardENT getOldDashboardENT(DashboardENT ent) {
		Query q = getSession().getNamedQuery("spBIGetDashboard");
		q.setString(0, ent.getUserName());
		q.setInteger(1, NVL.getInt(ent.getGroupId()));
		q.setInteger(2, NVL.getInt(ent.getDashboardNo()));
		return (DashboardENT)q.uniqueResult();
	}

	private DashboardENT deleteOldSaveNew(DashboardENT ent,DashboardENT oldENT) throws AIPException{
		Session session = getSession();
		Transaction tx = null;
		try{
			tx=session.beginTransaction();
			if(oldENT!=null)
				super.deleteNoTran(oldENT,session);
			ent = (DashboardENT)super.saveNoTran(ent,session);

//			Query q = getSession().createQuery("from DashboardENT where userName=?  and groupId=? dashboardNo=?");
//			q.setString(0, ent.getUserName());
//			q.setInteger(1, ent.getGroupId());
//			q.setInteger(1, ent.getDashboardNo());
//			q.executeUpdate();
//			session.flush();
			
//			saveDashboard( ent);

			tx.commit();
		}catch (HibernateException e) {
			e.printStackTrace();
			if(tx!=null && tx.isActive() )
				tx.rollback();
			session.clear();
			session.close();
			throw getAIPException(AIPEX_DELETE,e);

		}
		return ent;
	}

}
