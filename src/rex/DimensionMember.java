package rex;

public class DimensionMember {
	public DimensionMember(String memberCaption, String uniqueName) {
		super();
		this.memberCaption = memberCaption;
		this.uniqueName = uniqueName;
	}
	public DimensionMember() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String memberCaption;
	public String memberNum;
	public String uniqueName;
	public String getUniqueName() {
		return uniqueName;
	}
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
	public String getMemberCaption() {
		return memberCaption;
	}
	public void setMemberCaption(String memberCaption) {
		this.memberCaption = memberCaption;
	}
	public String getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(String memberNum) {
		this.memberNum = memberNum;
	}
	

}
