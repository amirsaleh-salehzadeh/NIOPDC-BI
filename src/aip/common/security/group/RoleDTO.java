package aip.common.security.group;

public class RoleDTO {
   private Integer id;
   private String  roleName;
   private String  groupInherited;
public Integer getId() {
	return id;
}
public String getGroupInherited() {
	return groupInherited;
}
public void setGroupInherited(String groupInherited) {
	this.groupInherited = groupInherited;
}
public void setId(Integer id) {
	this.id = id;
}
public String getRoleName() {
	return roleName;
}
public void setRoleName(String roleName) {
	this.roleName = roleName;
}
 

}
