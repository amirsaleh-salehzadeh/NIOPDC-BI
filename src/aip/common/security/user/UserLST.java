package aip.common.security.user;

import java.util.ArrayList;

import aip.common.AIPDefaultPagingLST;


public class UserLST extends   AIPDefaultPagingLST { 
   
  private String filter;
  private int groupId;
  private ArrayList<UserDTO> dto=new ArrayList<UserDTO>();

public ArrayList<UserDTO> getDto() {
	return dto;
}

public int getGroupId() {
	return groupId;
}

public void setGroupId(int groupId) {
	this.groupId = groupId;
}

public void setDto(ArrayList<UserDTO> dto) {
	this.dto = dto;
}

public String getFilter() {
	return filter;
}

public void setFilter(String filter) {
	this.filter = filter;
}



}
