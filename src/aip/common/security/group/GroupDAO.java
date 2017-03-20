package aip.common.security.group;

import aip.common.AIPSortingInterface;

public  class GroupDAO {
	private  String resetSortedByField(AIPSortingInterface prm) {
        String sortedByField;  		
		 if (prm.getIsDescending()) {
			 sortedByField=(prm.getSortedByField()+ " DESC");}
		 
	     else { sortedByField=(prm.getSortedByField()+  " ASC");}	
		 return sortedByField;
	 
	}	
}
