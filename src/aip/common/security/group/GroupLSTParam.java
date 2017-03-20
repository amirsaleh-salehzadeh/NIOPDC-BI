package aip.common.security.group;

import aip.common.AIPDefaultLSTParam;
import aip.common.common.ConstantParam;

public class GroupLSTParam  extends AIPDefaultLSTParam {
	//
	private String filter = null;
	public GroupLSTParam(){
		setDescending(false);
		setSortedByField(ConstantParam.GROUP_SORTED_BY_GROUPNAME);
	} 
	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}
}
