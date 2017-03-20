package aip.common.security.group;

public class SSASRoleDTO {
	   private String uniqueName;
	   private String  roleName;
	   private String  groupInherited;
	public String getUniqueName() {
		return uniqueName;
	}
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getGroupInherited() {
		return groupInherited;
	}
	public void setGroupInherited(String groupInherited) {
		this.groupInherited = groupInherited;
	}
}
