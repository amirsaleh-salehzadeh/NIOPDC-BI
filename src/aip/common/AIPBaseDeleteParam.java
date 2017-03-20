package aip.common;

public class AIPBaseDeleteParam implements AIPWebUserInterface {
    private int id;
    private String ids;
    private String criteria;
    private AIPWebUser webUser;
	

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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

}
