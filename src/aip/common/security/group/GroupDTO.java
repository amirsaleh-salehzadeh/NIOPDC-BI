package aip.common.security.group;

import aip.common.AIPWebUser;
import aip.common.AIPWebUserInterface;

public class GroupDTO implements AIPWebUserInterface{
	private Integer id;
   private GroupENT  ent;
   private String  selectedRoleIds;
   private String  ssasSelectedRoleIds;
   private AIPWebUser webUser;
   
   
public AIPWebUser getAIPWebUser() {
	// TODO Auto-generated method stub
	return null;
}
public void setAIPWebUser(AIPWebUser aipWebUser) {
	// TODO Auto-generated method stub
	
}
public GroupENT getEnt() {
	return ent;
}
public void setEnt(GroupENT ent) {
	this.ent = ent;
}
public String getSelectedRoleIds() {
	return selectedRoleIds;
}
public void setSelectedRoleIds(String selectedRoleIds) {
	this.selectedRoleIds = selectedRoleIds;
}
public String getSsasSelectedRoleIds() {
	return ssasSelectedRoleIds;
}
public void setSsasSelectedRoleIds(String ssasRoleUniqueNames) {
	this.ssasSelectedRoleIds = ssasRoleUniqueNames;
}
public AIPWebUser getWebUser() {
	return webUser;
}
public void setWebUser(AIPWebUser webUser) {
	this.webUser = webUser;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
}
