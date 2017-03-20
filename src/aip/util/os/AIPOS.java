package aip.util.os;

import java.util.List;

public abstract class AIPOS {
	
	public abstract String executeCommand(String command) throws AIPOSException;
	public abstract boolean executeCommand(String command, int exTimeout,StringBuffer buf) throws AIPOSException;
	public abstract List<String> getWinUsersGroups(String command,String lineSeperator,String itemSeperator)throws AIPOSException;
	public abstract List<String> getWinUsers()throws AIPOSException;
	public abstract List<String> getWinGroups()throws AIPOSException;
	public abstract boolean addWinUser(String user,String pass)throws AIPOSException;
	public abstract boolean addWinUser(String user,String pass,String fullname,String comment)throws AIPOSException;
	public abstract boolean addWinGroup(String groupname) throws AIPOSException;
	public abstract boolean addWinUser2Group(String groupname,String username) throws AIPOSException;
	public abstract boolean delWinUser(String user)throws AIPOSException;
	public abstract boolean delWinGroup(String groupname) throws AIPOSException;
	public abstract boolean delWinUserInGroup(String groupname,String username) throws AIPOSException;

}
