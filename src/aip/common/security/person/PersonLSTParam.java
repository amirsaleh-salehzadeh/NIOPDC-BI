package aip.common.security.person;

import aip.common.AIPDefaultLSTParam;
import aip.common.common.ConstantParam;
import aip.common.security.user.UserLSTParam;

public class PersonLSTParam extends AIPDefaultLSTParam{
    private String filter;

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}
	public PersonLSTParam(){
		setDescending(false);
		setSortedByField(ConstantParam.USER_SORTED_BY_LASTNAME);
	} 
	
}
