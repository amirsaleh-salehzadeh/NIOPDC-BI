package aip.common;

public class AIPDefaultParam implements AIPWebUserInterface {
    private AIPWebUser webUser;
    private int id;
    private String criteria;
	

	public AIPDefaultParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AIPDefaultParam(AIPWebUser webUser, int id, String criteria) {
		super();
		this.webUser = webUser;
		this.id = id;
		this.criteria = criteria;
	}

	public AIPDefaultParam(AIPWebUser webUser, int id) {
		super();
		this.webUser = webUser;
		this.id = id;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

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
