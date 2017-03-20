package aip.niopdc.sellbi.orm.common;

import java.util.ArrayList;

import org.hibernate.Query;

import aip.common.AIPWebUser;
import aip.common.security.group.GroupDTO;
import aip.common.security.group.GroupLST;
import aip.niopdc.sellbi.orm.BaseHibernateDAO;
import aip.util.NVL;

public class CommonDAO extends BaseHibernateDAO {

	public GroupLST getGroupLST(AIPWebUser userParam){
		GroupLST lst = new GroupLST();
		Query q = getSession().getNamedQuery("spSECGetUserGroups");
		q.setString(0, userParam.getRemoteUser());
		lst.setDtos((ArrayList<GroupDTO>)q.list());
		return lst;
	}
	
	public boolean userHasRole(String userName,String role){
		Query q = getSession().getNamedQuery("fnSECUserHasRole");
		q.setString(0, userName);
		q.setString(1, "defineGroupDashboard");
		boolean userHasRole = NVL.getBool(q.uniqueResult());
		return userHasRole;
	}
}
