package common.dashboard;

public class DashboardENT {
   private Integer id;
   private String Caption;
   private Integer order;
   private String dashboardQuery;
   private Integer userId;
   private Integer groupId;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getCaption() {
	return Caption;
}
public void setCaption(String caption) {
	Caption = caption;
}
public Integer getOrder() {
	return order;
}
public void setOrder(Integer order) {
	this.order = order;
}
public String getDashboardQuery() {
	return dashboardQuery;
}
public void setDashboardQuery(String dashboardQuery) {
	this.dashboardQuery = dashboardQuery;
}
public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
public Integer getGroupId() {
	return groupId;
}
public void setGroupId(Integer groupId) {
	this.groupId = groupId;
}
}
