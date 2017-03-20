package aip.common.security.user;

import aip.common.AIPWebUser;

public class UserForSaveENT {
   
   private UserENT   userENT;
   private UserPassword userPassword; 
   private String  userRoleIds;
   private String  ssasUserRoleIds;
   private String userGroupIds;
   private AIPWebUser webUser;

public UserENT getUserENT() {
	return userENT;
}
public void setUserENT(UserENT userENT) {
	this.userENT = userENT;
}

public String getUserRoleIds() {
	return userRoleIds;
}
public void setUserRoleIds(String userRoleIds) {
	this.userRoleIds = userRoleIds;
}
public String getSsasUserRoleIds() {
	return ssasUserRoleIds;
}
public void setSsasUserRoleIds(String ssasUserRoleIds) {
	this.ssasUserRoleIds = ssasUserRoleIds;
}
public String getUserGroupIds() {
	return userGroupIds;
}
public void setUserGroupIds(String userGroupIds) {
	this.userGroupIds = userGroupIds;
}
public AIPWebUser getWebUser() {
	return webUser;
}
public void setWebUser(AIPWebUser webUser) {
	this.webUser = webUser;
}
public UserPassword getUserPassword() {
	return userPassword;
}
public void setUserPassword(UserPassword userPassword) {
	this.userPassword = userPassword;
}
}
