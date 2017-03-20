package aip.common.security.user;

import aip.common.AIPWebUser;
import aip.common.AIPWebUserInterface;

public class ChangePasswordParam implements AIPWebUserInterface{
    AIPWebUser webUser;
    private String oldPassword;
    private String newPassword;

    

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	

	public AIPWebUser getWebUser() {
		return webUser;
	}

	public void setWebUser(AIPWebUser webUser) {
		this.webUser = webUser;
	} 
	
	

}
