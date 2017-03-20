package aip.common.security.user;

import java.util.ArrayList;

import aip.common.security.group.GroupDTO;
import aip.common.security.group.RoleDTO;
import aip.common.security.group.SSASRoleDTO;

public class UserComboLST {
	private ArrayList<aip.common.security.group.RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
	private ArrayList<SSASRoleDTO>  ssasRoleDTOs = new ArrayList<SSASRoleDTO>();
	private ArrayList<GroupDTO>  groupDTOs = new ArrayList<GroupDTO>();
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
	public ArrayList<GroupDTO> getGroupDTOs() {
		return groupDTOs;
	}
	public void setGroupDTOs(ArrayList<GroupDTO> groupDTOs) {
		this.groupDTOs = groupDTOs;
	}
}	
	