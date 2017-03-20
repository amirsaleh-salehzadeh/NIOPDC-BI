package aip.niopdc.sellbi.orm.security.user;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import aip.common.AIPBaseDeleteParam;
import aip.common.AIPDefaultParam;
import aip.common.AIPException;
import aip.common.AIPWebUser;
import aip.common.security.group.GroupDTO;
import aip.common.security.group.RoleDTO;
import aip.common.security.person.PersonENT;
import aip.common.security.person.PersonLST;
import aip.common.security.person.PersonLSTParam;
import aip.common.security.user.ChangePasswordParam;
import aip.common.security.user.UserComboLST;
import aip.common.security.user.UserDAOInterface;
import aip.common.security.user.UserDTO;
import aip.common.security.user.UserENT;
import aip.common.security.user.UserForSaveENT;
import aip.common.security.user.UserFullDTO;
import aip.common.security.user.UserGroupENT;
import aip.common.security.user.UserLST;
import aip.common.security.user.UserLSTParam;
import aip.common.security.user.UserPassword;
import aip.common.security.user.UserRoleENT;
import aip.niopdc.sellbi.orm.CommonSecurityDAO;
import aip.niopdc.sellbi.orm.BaseHibernateDAO;
import aip.niopdc.sellbi.orm.security.group.GroupDAO;
import aip.util.AIPEncrypter;
import aip.util.AIPUtil;
import aip.util.NVL;
import aip.util.SyncIds;
import aip.util.os.AIPOSException;
import aip.util.os.AIPOSManager;

/**
 * A data access object (DAO) providing persistence and search support for
 * Scgroup entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see aip.niopdc.sellbi.orm.security.group.Scgroup
 * @author MyEclipse Persistence Tools
 */

public class UserDAO extends BaseHibernateDAO implements UserDAOInterface{

	public void changeUserPassword(ChangePasswordParam param)throws AIPException {
		Query q = getSession().getNamedQuery("sqlGetUserPass");
		q.setString(0, param.getWebUser().getRemoteUser());
		String olPass = (String) q.uniqueResult(); 
		param.setOldPassword(AIPEncrypter.encode(param.getOldPassword()));
		param.setNewPassword(AIPEncrypter.encode(param.getNewPassword()));
		if ("".equals(olPass) || " ".equals(olPass)) {
			throw getAIPException("چنین کاربری وجود ندارد.", null);
		}

		if (!NVL.getString(param.getOldPassword()).equals(NVL.getString(olPass))) {
			throw getAIPException("کلمه عبور قبلی درست نیست.", null);
		}
		

		Transaction tx = null;
		Session session = getSession();

		try {
			tx = session.beginTransaction();
			q = session.createQuery("update UserPassword set userPassword=? where userName=?");
			q.setString(0, param.getNewPassword());
			q.setString(1, param.getWebUser().getRemoteUser());
			q.executeUpdate();
			tx.commit();
		} catch (HibernateException ex) {
			if (tx.isActive() && tx != null)
				tx.rollback();
			session.close();

			throw getAIPException(AIPEX_SAVE, ex);
		}
		
	}

	public void deleteUserUser(AIPBaseDeleteParam param) throws AIPException {
		Session session = getSession();
		Transaction tx = null;

		
		try {
			tx=session.beginTransaction();
			
	        List<Integer> roleIds = new ArrayList<Integer>();
	        List<Integer> groupIds = new ArrayList<Integer>();

	        
	        if(param.getIds()!=null){
	        	String[] ids = param.getIds().split(",");
	    		AIPWebUser webUser = new AIPWebUser();
	    		UserFullDTO fullDTO = new UserFullDTO();
	    		UserDAO userDAO = new UserDAO();
	    		AIPDefaultParam param2 = new AIPDefaultParam();
	    		for(int i = 0; i<ids.length; i++){
	        		param2.setId(NVL.getInt(ids[i]));
	        		fullDTO = userDAO.getUserFullDTO(param2);
	        		webUser.setRemoteUser(NVL.getString(fullDTO.getUserName()));
	        		param.setWebUser(webUser);
	        		try {
						AIPOSManager.getOS().delWinUser(fullDTO.getWinUser());
					} catch (AIPOSException e) {
						e.printStackTrace();
						throw getAIPException("حذف کاربر سیستم عامل با اشکال مواجه شده است", null);
					}
	        		Query q = getSession().getNamedQuery("sqlGetUserRoleId");
	                q.setString(0, param.getWebUser().getRemoteUser());
	                roleIds = q.list();
	                q = getSession().getNamedQuery("sqlGetUserGroupId");
	                q.setString(0, param.getWebUser().getRemoteUser());
	                groupIds = q.list();
	                if(roleIds.size() != 0 || groupIds.size() != 0)
	                	throw getAIPException("عملیات حذف نا موفق. برای کاربر مورد نظر گروه و یا سطح دسترسی ثبت شده است ", null);
	                q = getSession().getNamedQuery("sqlGetUserDashboards");
	                q.setString(0, param.getWebUser().getRemoteUser());
	                if(q.list().size()>0)
	                	throw getAIPException("عملیات حذف نا موفق. برای کاربر مورد نظر داشبورد ثبت شده است ", null);
	                q = getSession().getNamedQuery("sqlGetUserReports");
	                q.setString(0, param.getWebUser().getRemoteUser());
	                if(q.list().size()>0)
	                	throw getAIPException("عملیات حذف نا موفق. برای کاربر مورد نظر گزارش ثبت شده است ", null);
	        	}
	        }
    		if(param.getId() != 0){
    	        Query q = getSession().getNamedQuery("sqlGetUserRoleId");
    	        q.setString(0, param.getWebUser().getRemoteUser());
    	        roleIds = q.list();
    	        
    	        q = getSession().getNamedQuery("sqlGetUserGroupId");
    	        q.setString(0, param.getWebUser().getRemoteUser());
    	        groupIds = q.list();
    	        if(roleIds.size() != 0 || groupIds.size() != 0)
                	throw getAIPException("عملیات حذف نا موفق. برای کاربر مورد نظر گروه و یا سطح دسترسی ثبت شده است ", null);
    	        q = getSession().getNamedQuery("sqlGetUserDashboards");
                q.setString(0, param.getWebUser().getRemoteUser());
                if(q.list().size() != 0){
                	throw getAIPException("عملیات حذف نا موفق. برای کاربر مورد نظر داشبورد ثبت شده است ", null);
                }
                q = getSession().getNamedQuery("sqlGetUserReports");
                q.setString(0, param.getWebUser().getRemoteUser());
                if(q.list().size()!=0){
                	throw getAIPException("عملیات حذف نا موفق. برای کاربر مورد نظر گزارش ثبت شده است ", null);
                }
    			super.delete(UserENT.class, param.getId());
    		}else if(!"".equals(NVL.getString(param.getIds()))){
    			super.deleteSelectedIds(UserENT.class, param.getIds());
    		}else if(!"".equals(NVL.getString(param.getCriteria()))){
    			super.deleteByCondition(UserENT.class, NVL.getString(param.getCriteria()));
    		}
//    		tx.commit();
//        	session.close();
	        
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
        	
       
	}

	public PersonLST getPersonLST(PersonLSTParam param) throws AIPException {
		PersonLST lst = new PersonLST();
		Query q = getSession().getNamedQuery("spSECGetPersonsCount");
		q.setString(0, NVL.getStringNull(param.getFilter()));
		int totalItems = NVL.getInt(q.uniqueResult());
		lst.setProperties(totalItems, param.getReqPage(), param.getPageSize());
		
		q = getSession().getNamedQuery("spSECGetPersons");
		q.setString(0, NVL.getStringNull(param.getFilter()));
		q.setInteger(1, NVL.getInt(lst.getFirst()));
		q.setInteger(2, NVL.getInt(param.getPageSize()));
		q.setString(3, NVL.getString(param.getSortedByField()));
		
		lst.setPersonENTs((ArrayList<PersonENT>)q.list());
		lst.setFilter(param.getFilter());
		
		return lst;
	}

	public UserComboLST getUserComboLST(AIPWebUser user) throws AIPException {
		UserComboLST userComboLST = new UserComboLST();
		
		Query q = getSession().createQuery("from RoleDTO");
		ArrayList<RoleDTO> roleDTOs = (ArrayList<RoleDTO>)q.list();		
		
		q = getSession().createQuery("from GroupENT");
		ArrayList<GroupDTO> groupDTOs = (ArrayList<GroupDTO>)q.list();
		
		userComboLST.setRoleDTOs(roleDTOs);
		userComboLST.setGroupDTOs(groupDTOs);
		return userComboLST;
	}

	public UserForSaveENT getUserForSaveENT(AIPDefaultParam param) throws AIPException {
		UserForSaveENT saveENT = new UserForSaveENT();
		Query q = getSession().getNamedQuery("sqlGetUserENT");
	    q.setString(0, param.getWebUser().getRemoteUser());
	    UserENT ent = new UserENT();
	    ent = (UserENT)q.uniqueResult();
		saveENT.setUserENT(ent);
        saveENT.setUserGroupIds(getUserGroupIds(NVL.getString(param.getWebUser().getRemoteUser())));
        saveENT.setUserRoleIds(getUserRoleIds(NVL.getString(param.getWebUser().getRemoteUser())));
        saveENT.setWebUser(param.getWebUser());
        
		return saveENT;
	}

	public UserFullDTO getUserFullDTO(AIPDefaultParam param) throws AIPException {
		Query q = getSession().getNamedQuery("sqlGetUserFullDTO");
		q.setInteger("userId", param.getId());
		UserFullDTO dto = (UserFullDTO) q.uniqueResult();
		if(param.getWebUser()!=null){
			dto.setUserGroupIds(getUserGroupIds(param.getWebUser().getRemoteUser()));
			dto.setUserRoleIds(getUserRoleIds(param.getWebUser().getRemoteUser()));
			dto.setSsasUserRoleIds(getUserSSASRoleIds(param.getWebUser().getRemoteUser()));
			dto.setWebUser(param.getWebUser());
		}
		return dto;
	}

	public UserLST getUserLST(UserLSTParam param) throws AIPException {
		UserLST userLST = new UserLST();
		Query q = null;
		try{
			q = getSession().getNamedQuery("spSECGetUsersCount")
			.setString(0, param.getFilter())
			.setInteger(1, param.getGroupId());
			int totalItems = NVL.getInt( q.uniqueResult() );
			userLST.setProperties(totalItems,param.getReqPage(),param.getPageSize());

			q = getSession().getNamedQuery("spSECGetUsers")
			.setString(0, NVL.getStringNull(param.getFilter()))
			.setInteger(1, userLST.getFirst())
			.setInteger(2, param.getPageSize())
			.setString(3, param.getSortedByField())
			.setInteger(4, param.getGroupId());
			
			ArrayList<UserDTO> userDTOs = (ArrayList<UserDTO>)q.list();
			userLST.setDto(userDTOs);
			userLST.setGroupId(param.getGroupId());
				
		}catch (HibernateException e) {
			e.printStackTrace();
		}
		return userLST;
	}

	public UserForSaveENT saveUser(UserForSaveENT ent) throws AIPException {
		
		UserForSaveENT forSaveENT = new UserForSaveENT();
		Session session = getSession();
		Transaction tx = null;
		try{
			UserENT userENT = ent.getUserENT();
			if(getUserENT(userENT.getUserName()) != null && userENT.getId() == null){
				throw getAIPException("نام کاربری انتخاب شده تکراری و در سیستم موجود می باشد لطفا نام دیگری را انتخاب کنید", null);
			}
//			if(getUserENT(userENT.getUserName()).getWinUser().equalsIgnoreCase(ent.getUserENT().getWinUser())){
//				throw getAIPException("نام کاربری برای سیستم عامل انتخاب شده تکراری و در سیستم موجود می باشد لطفا نام دیگری را انتخاب کنید", null);
//			}			
			SessionFactory fact = new Configuration().configure().buildSessionFactory();
			session = fact.openSession();
			tx = session.beginTransaction();
				session.saveOrUpdate(userENT);
			if(ent.getUserPassword()!=null){
				UserPassword userPassword = new UserPassword();
				userPassword.setUserPassword(AIPEncrypter.encode(ent.getUserPassword().getUserPassword()));
				userPassword.setId(ent.getUserPassword().getId());
				userPassword.setUserName(ent.getUserPassword().getUserName());
				ent.setUserPassword(userPassword);
				session.update(ent.getUserPassword());
			}
			tx.commit();
			session.clear();
			forSaveENT.setUserENT(userENT);
//////////////////////////////////SAVE ROLES/////////////////////////////////////////
			tx = session.beginTransaction();
			Query q = session.getNamedQuery("sqlGetUserRoles");
			q.setString(0, userENT.getUserName());
			ArrayList<UserRoleENT> roleENTs = (ArrayList<UserRoleENT>)q.list(); 
			ArrayList<Integer> oldRoleIds = new ArrayList<Integer>();
			for (int i = 0 ; i< roleENTs.size(); i++){
				oldRoleIds.add(NVL.getInteger(roleENTs.get(i).getRoleId()));
			}			
			SyncIds sy = AIPUtil.getSyncIds(ent.getUserRoleIds(), AIPUtil.convertToString(oldRoleIds));
			ent.setUserRoleIds(AIPUtil.convertToString(sy.getNewIds()));
			for (int i = 0; i < sy.getNewIds().size(); i++) {
				UserRoleENT roleENT = new UserRoleENT();
				roleENT.setUserName(userENT.getUserName());
				roleENT.setRoleId(NVL.getInteger(sy.getNewIds().get(i)));
				roleENT = (UserRoleENT) super.saveNoTran(roleENT,session);
			}
			
			for (int i = 0; i < sy.getDelIds().size(); i++){
				int roleId = NVL.getInt(sy.getDelIds().get(i));
				if (roleId > 0) {
					session.createQuery("delete from UserRoleENT where userName=? and roleId=?")
							.setString(0, userENT.getUserName())
							.setInteger(1, roleId).executeUpdate();
				}
			}
			session.flush();
			tx.commit();
			tx=session.beginTransaction();
////////////////////////////////SAVE GROUPS/////////////////////////////////////////			
			q = session.getNamedQuery("sqlGetUserGroups");
			q.setString(0, userENT.getUserName());
			ArrayList<UserGroupENT> groupENTs = (ArrayList<UserGroupENT>)q.list(); 
			ArrayList<Integer> oldGroupIds = new ArrayList<Integer>();
			
			for (int i = 0 ; i< groupENTs.size(); i++){
				oldGroupIds.add(groupENTs.get(i).getGroupId());
			}			
			SyncIds sy2 = AIPUtil.getSyncIds(arrayGenerator(NVL.getString(ent.getUserGroupIds())), AIPUtil.convertToString(oldGroupIds));
			ent.setUserRoleIds(AIPUtil.convertToString(sy2.getNewIds()));
			for (int i = 0; i < sy2.getDelIds().size(); i++){
				int groupId = NVL.getInt(sy2.getDelIds().get(i));
				if (groupId > 0) {
					session.createQuery("delete from UserGroupENT where userName=? and groupId=?")
							.setString(0, userENT.getUserName())
							.setInteger(1, groupId).executeUpdate();
				}
			}

			for (int i = 0; i < sy2.getNewIds().size(); i++) {
				UserGroupENT groupENT = new UserGroupENT();
				groupENT.setUserName(userENT.getUserName());
				groupENT.setGroupId(NVL.getInteger(sy2.getNewIds().get(i)));
				groupENT = (UserGroupENT) super.saveNoTran(groupENT,session);
			}
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if(tx.isActive() && tx!=null)
				tx.rollback();
			session.clear();
			session.close();
			throw getAIPException(AIPEX_SAVE , e);
		}
		try {
			CommonSecurityDAO cs = new CommonSecurityDAO();
			cs.recalcUserAllRoles(ent.getUserENT().getUserName());
		} catch (AIPException e) {
			e.printStackTrace();
		}
		session.close();
		return forSaveENT;
	}

    private String getUserGroupIds(String userName){
        Query q = getSession().getNamedQuery("sqlGetUserGroupIds");
        q.setString("userName", userName);
		return arrayGenerator(NVL.getString(q.uniqueResult()));		
    }
    
    private String getUserRoleIds(String userName){
        Query q = getSession().getNamedQuery("sqlGetUserRoleIds");
        q.setString("userName", userName);
        return arrayGenerator(NVL.getString(q.uniqueResult()));
    }
    private String arrayGenerator(String x){
        String[] Ids = x.split(",");
        String p = ""; 
        for(int i = 0; i<Ids.length ; i++){
        	if(NVL.getInt(Ids[i]) > 0 ){
        		p = p + Ids[i] + ","; 
        	}
        }
    	
    	return p;
    }
    
    
    private String getUserSSASRoleIds(String userName){
         // getFromSSAS DB
    	return "";
     }

	public String getXMLAPassword(String user) {
		String XMLAPass="";
        Query q = getSession().createSQLQuery("select s.WinPassword from scuser s where s.UserName = ?");
        q.setString(0, user);
        XMLAPass = (String) q.uniqueResult();
		return XMLAPass;
	}

	public String getXMLAUser(String user) {
		String XMLAUser="";
        Query q = getSession().createSQLQuery("select s.WinUser from scuser s where s.UserName = ?");
        q.setString(0, user);
        XMLAUser = (String) q.uniqueResult();
		return XMLAUser;
	}

	public UserENT getUserENT(String userName) throws AIPException {
    	UserENT userENT = new UserENT();
    	Query q = getSession().getNamedQuery("sqlGetUserENT");
	    q.setString(0, userName);
	    userENT = (UserENT)q.uniqueResult();		
	    return userENT;
	}

	
	
}