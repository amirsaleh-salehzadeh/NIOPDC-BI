package aip.common.security.user;

import aip.common.AIPBaseDeleteParam;
import aip.common.AIPDefaultParam;
import aip.common.AIPException;
import aip.common.AIPWebUser;
import aip.common.security.person.PersonLST;
import aip.common.security.person.PersonLSTParam;

public interface UserDAOInterface {
	public UserLST getUserLST(UserLSTParam param) throws AIPException; 
    public void deleteUserUser(AIPBaseDeleteParam param) throws AIPException;
    public UserFullDTO getUserFullDTO(AIPDefaultParam param) throws AIPException;
    public UserForSaveENT getUserForSaveENT(AIPDefaultParam param) throws AIPException;
    public UserComboLST getUserComboLST(AIPWebUser user) throws AIPException;
    public UserForSaveENT saveUser(UserForSaveENT  ent) throws AIPException;  // int included .
    public void changeUserPassword(ChangePasswordParam param)  throws AIPException;
    public PersonLST getPersonLST(PersonLSTParam param)  throws AIPException;
    public UserENT getUserENT (String userName)  throws AIPException;
    public String getXMLAUser(String user);
    public String getXMLAPassword(String user);
}
