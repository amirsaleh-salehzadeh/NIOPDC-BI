package aip.common.security.person;

import java.util.ArrayList;

import aip.common.AIPDefaultPagingLST;

public class PersonLST extends AIPDefaultPagingLST{
   private String filter;	
   private ArrayList<PersonENT> personENTs=new ArrayList<PersonENT>();


public ArrayList<PersonENT> getPersonENTs() {
	return personENTs;
}

public void setPersonENTs(ArrayList<PersonENT> personENTs) {
	this.personENTs = personENTs;
}

public String getFilter() {
	return filter;
}

public void setFilter(String filter) {
	this.filter = filter;
}
}
