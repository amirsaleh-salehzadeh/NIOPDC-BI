package aip.common.dashboard;

import aip.common.security.group.GroupLST;

public class DashboardComboLST {
    GroupLST userGroups = new GroupLST() ;

	public GroupLST getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(GroupLST userGroups) {
		this.userGroups = userGroups;
	}
}
