package aip.common.security.user;

public class UserDTO {
   private Integer id;
   private String userName;
   private String firstName; 
   private String lastName;
   private String nationalCode; 
   private String identityNo;
   private String startDate ;
   private String endDate;
   private Boolean isApproved ;

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
public void setIsApproved(Boolean isApproved) {
	this.isApproved = isApproved;
}

}
