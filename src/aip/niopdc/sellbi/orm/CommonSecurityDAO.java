package aip.niopdc.sellbi.orm;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aip.common.AIPException;
import aip.common.security.group.RoleDTO;
import aip.common.security.user.UserAllRoleENT;
import aip.common.security.user.UserENT;
import aip.util.NVL;

public class CommonSecurityDAO extends BaseHibernateDAO {
	


	public void recalcUserAllRoles(String userName) throws AIPException{
		System.out.println("CommonSecurityDAO.recalcUserAllRoles():::::::::::userName="+userName);
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Query q = getSession().getNamedQuery("sqlUserAllRoleQuery");
			q.setString("userName", userName);
			List<RoleDTO> rolesByQuery = q.list();

			q = getSession().createQuery("from UserAllRoleENT where userName=?");
			q.setString(0, userName);
			List<UserAllRoleENT> userAllRoles4jboss =  q.list();

			Hashtable<String, UserAllRoleENT> htUserAllRoles = new Hashtable<String, UserAllRoleENT>();

			for (UserAllRoleENT ur : userAllRoles4jboss) {
				htUserAllRoles.put(ur.getRoleName(), ur);
			}

			// add new roles
			for (RoleDTO r : rolesByQuery) {
				if(r.getRoleName() != null || !"".equals(r.getRoleName())){
					if (htUserAllRoles.containsKey(r.getRoleName())) {
						htUserAllRoles.remove(r.getRoleName());
					} else {
						UserAllRoleENT ur = new UserAllRoleENT();
						ur.setRoleName(r.getRoleName());
						ur.setUserName(userName);
						session.save(ur);
					}
				}
			}
			// delete removed roles
			Enumeration<UserAllRoleENT> iter = htUserAllRoles.elements();
			while (iter.hasMoreElements()) {
				session.delete(iter.nextElement());
			}
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			session.clear();
			throw getAIPException(AIPEX_SAVE,ex);
		}
	}

	public void recalcGroupUsersAllRoles(Integer groupId) throws AIPException {
		Query q = getSession().getNamedQuery( "sqlGroupUsers" );
		q.setInteger("groupId", groupId);
		List<UserENT> groupUsers =  q.list();
		for(UserENT u : groupUsers) {
			recalcUserAllRoles(u.getUserName());
		}

	}


}
