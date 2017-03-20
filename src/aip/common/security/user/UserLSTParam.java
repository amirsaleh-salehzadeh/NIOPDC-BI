package aip.common.security.user;

import aip.common.AIPDefaultLSTParam;
import aip.common.AIPSortingInterface;
import aip.common.common.ConstantParam;

public class UserLSTParam extends AIPDefaultLSTParam{
    private String filter;
    private int groupId;
    public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public UserLSTParam(){
		setDescending(false);
		setSortedByField(ConstantParam.USER_SORTED_BY_LASTNAME);
	} 
	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	} 
	
	public String resetSortedByField(AIPSortingInterface prm) {
        String sortedByField;  		
		 if (prm.getIsDescending()) {
			 sortedByField=(prm.getSortedByField()+ " DESC");}
		 
	     else { sortedByField=(prm.getSortedByField()+  " ASC");}	
		 return sortedByField;
	 
	}	
}
