package aip.common.security.user;

import aip.common.AIPSortingInterface;
import aip.common.common.ConstantParam;

public class UserDAO {

	private String resetSortedByField(AIPSortingInterface prm) {
		
		String sortedByField;
		sortedByField=prm.getSortedByField();
		if  ( prm.getSortedByField().equals( ConstantParam.USER_SORTED_BY_LASTNAME )) 
		{
			if (prm.getIsDescending()) {
				prm.setSortedByField(prm.getSortedByField()+ " DESC" + ConstantParam.USER_SORTED_BY_FIRSTNAME + " DESC");
				
			}
			else {
				prm.setSortedByField(prm.getSortedByField()+ " ASC" + ConstantParam.USER_SORTED_BY_FIRSTNAME + " ASC");
			}
		}
		else if ( prm.getSortedByField().equals( ConstantParam.USER_SORTED_BY_FIRSTNAME )) 
		{
			if (prm.getIsDescending()) {
				prm.setSortedByField(prm.getSortedByField()+ " DESC" + ConstantParam.USER_SORTED_BY_LASTNAME + " DESC");
				
			}
			else {
				prm.setSortedByField(prm.getSortedByField()+ " ASC" + ConstantParam.USER_SORTED_BY_LASTNAME + " ASC");
			}
			
		}
		else  if (prm.getIsDescending()) {
			     sortedByField=(prm.getSortedByField()+ " DESC");}
		 
	          else { sortedByField=(prm.getSortedByField()+  " ASC");}
		
		
		return sortedByField;
		
	}
	
	
}
