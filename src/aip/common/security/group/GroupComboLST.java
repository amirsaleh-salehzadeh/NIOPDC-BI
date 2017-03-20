package aip.common.security.group;

import java.util.ArrayList;

public class GroupComboLST {
   private ArrayList<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
   private ArrayList<SSASRoleDTO>  ssasRoleDTOs = new ArrayList<SSASRoleDTO>();
public ArrayList<RoleDTO> getRoleDTOs() {
	return roleDTOs;
}
public void setRoleDTOs(ArrayList<RoleDTO> roleDTOs) {
	this.roleDTOs = roleDTOs;
}
public ArrayList<SSASRoleDTO> getSsasRoleDTOs() {
	return ssasRoleDTOs;
}
public void setSsasRoleDTOs(ArrayList<SSASRoleDTO> ssasRoleDTOs) {
	this.ssasRoleDTOs = ssasRoleDTOs;
}
 }
