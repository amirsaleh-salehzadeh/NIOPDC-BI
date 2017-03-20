package aip.util;
import java.util.List;
import org.hibernate.Query;
import aip.common.security.user.UserAllRoleENT;
import aip.niopdc.sellbi.orm.BaseHibernateDAO;


public class SecurityUtil extends BaseHibernateDAO {
	
	public boolean isUserInRoleByUN(String a_username,String a_rolenames) {
		Query q = getSession().createQuery( "from UserAllRoleENT where userName=?" );
		q.setString(0, a_username);

		String[] arrRoles = a_rolenames.split(",");
		
		List<UserAllRoleENT> l = (List<UserAllRoleENT>) q.list();
		if (l != null) {
			for (int i = 0; i < l.size(); i++) {
				for (int j = 0; j < arrRoles.length; j++) {
					if (l.get(i).getRoleName().equalsIgnoreCase(arrRoles[j]))
						return true;
				}
			}
		}
		return false;
	}
}
