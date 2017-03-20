package aip.common.security.group;

import aip.common.AIPBaseDeleteParam;
import aip.common.AIPDefaultParam;
import aip.common.AIPException;
import aip.common.AIPWebUser;

public interface GroupDAOInterface {
    public GroupLST getGroupLST(GroupLSTParam param) throws AIPException; 
    public void deleteUserGroup(AIPBaseDeleteParam param) throws AIPException;
    public GroupDTO getGroupDTO(AIPDefaultParam param) throws AIPException;
    public GroupComboLST getGroupComboLST(AIPWebUser user) throws AIPException;
    public GroupDTO saveGroup(GroupDTO dto) throws AIPException;  // int included .
    //private SSASRoleDTO[] getSSASRoleDTOs(); 
}
