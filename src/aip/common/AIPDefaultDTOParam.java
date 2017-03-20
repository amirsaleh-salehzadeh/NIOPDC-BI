package aip.common;

public class AIPDefaultDTOParam implements AIPWebUserInterface {
    private AIPWebUser webUser;
    private int id;
	

	public AIPWebUser getWebUser() {
		return webUser;
	}

	public void setWebUser(AIPWebUser webUser) {
		this.webUser = webUser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
