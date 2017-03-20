package aip.common.security.user;

import aip.common.AIPWebUser;
import aip.common.AIPWebUserInterface;

public class UserFullDTO implements AIPWebUserInterface{
	private Integer id;
    private String userName;
    private String firstName; 
    private String lastName;
    private String nationalCode; 
    private String identityNo;
    private String startDate ;
    private String endDate;
    private String phone; 
    private String cellPhone; 
	private String mailAddress;
	private String address;
	private String description; 
    private Boolean isApproved ;
    private String  userRoleIds;
    private String  ssasUserRoleIds;
    private String userGroupIds;
    private String winUser;
    private String winPassword;
    private AIPWebUser webUser;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getNationalCode() {
		return nationalCode;
	}
	public void setNationalCode(String nationalCode) {
		this.nationalCode = nationalCode;
	}
	public String getIdentityNo() {
		return identityNo;
	}
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Boolean getIsApproved() {
		return isApproved;
	}
	public String getUserRoleIds() {
		return userRoleIds;
	}
	public void setUserRoleIds(String userRoleIds) {
		this.userRoleIds = userRoleIds;
	}
	public String getSsasUserRoleIds() {
		return ssasUserRoleIds;
	}
	public void setSsasUserRoleIds(String ssasUserRoleIds) {
		this.ssasUserRoleIds = ssasUserRoleIds;
	}
	public String getUserGroupIds() {
		return userGroupIds;
	}
	public void setUserGroupIds(String userGroupIds) {
		this.userGroupIds = userGroupIds;
	}
	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}
//	public Integer getPersonId() {
//		return personId;
//	}
//	public void setPersonId(Integer personId) {
//		this.personId = personId;
//	}
		public AIPWebUser getWebUser() {
		return webUser;
	}
	public void setWebUser(AIPWebUser webUser) {
		this.webUser = webUser;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWinUser() {
		return winUser;
	}
	public void setWinUser(String winUser) {
		this.winUser = winUser;
	}
	public String getWinPassword() {
		return winPassword;
	}
	public void setWinPassword(String winPassword) {
		this.winPassword = winPassword;
	}

}