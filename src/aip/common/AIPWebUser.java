package aip.common;

import javax.servlet.http.HttpServletRequest;

public class AIPWebUser {
   private String remoteUser ;
   private String remoteAddr;
   private String remoteHost;
   private int remotePort;
   private String  authType;
   private String requestedSessionId;
   private String userPrincipal;
public String getRemoteUser() {
	return remoteUser;
}
public void setRemoteUser(String remoteUser) {
	this.remoteUser = remoteUser;
}
public String getRemoteAddr() {
	return remoteAddr;
}
public void setRemoteAddr(String remoteAddr) {
	this.remoteAddr = remoteAddr;
}
public String getRemoteHost() {
	return remoteHost;
}
public void setRemoteHost(String remoteHost) {
	this.remoteHost = remoteHost;
}
public int getRemotePort() {
	return remotePort;
}
public void setRemotePort(int remotePort) {
	this.remotePort = remotePort;
}
public String getAuthType() {
	return authType;
}
public void setAuthType(String authType) {
	this.authType = authType;
}
public String getRequestedSessionId() {
	return requestedSessionId;
}
public void setRequestedSessionId(String requestedSessionId) {
	this.requestedSessionId = requestedSessionId;
}
public String getUserPrincipal() {
	return userPrincipal;
}
public void setUserPrincipal(String userPrincipal) {
	this.userPrincipal = userPrincipal;
}
 public AIPWebUser(){};
 public AIPWebUser(HttpServletRequest  request) {}
 
 public AIPWebUser(String userName) {}
 
 
}
