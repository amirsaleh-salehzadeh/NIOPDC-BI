package aip.common.dashboard;

import aip.common.AIPWebUser;
import aip.common.AIPWebUserInterface;

public class DashboardENT implements AIPWebUserInterface{
	
   private AIPWebUser webUser = new AIPWebUser();
   
   private Integer id;
   private String caption;
   private Integer dashboardNo;
   private String dashboardQuery;
   //private Integer order;
   private Integer diagramType;
   private String userName;
   private Integer groupId;
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
public String getCaption() {
	return caption;
}
public void setCaption(String caption) {
	this.caption = caption;
}
public Integer getDashboardNo() {
	return dashboardNo;
}
public void setDashboardNo(Integer dashboardNo) {
	this.dashboardNo = dashboardNo;
}
public String getDashboardQuery() {
	return dashboardQuery;
}
public void setDashboardQuery(String dashboardQuery) {
	this.dashboardQuery = dashboardQuery;
}

public Integer getDiagramType() {
	return diagramType;
}
public void setDiagramType(Integer diagramType) {
	this.diagramType = diagramType;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public Integer getGroupId() {
	return groupId;
}
public void setGroupId(Integer groupId) {
	this.groupId = groupId;
}

   
}
