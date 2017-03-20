package aip.util.os.windows;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import aip.util.os.AIPOS;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

public class AIPOSWindows extends AIPOS {
	
	public String executeCommand(String command) {
		StringBuffer buf = new StringBuffer();
		
		boolean b = executeCommand(command, 600, buf);
		return b?buf.toString():null;
	}
	public boolean executeCommand(String command, int exTimeout,StringBuffer buf) {
		
	    Process process = null;

	    try {
	        System.out.println("Executing external command " + command);
	        process = Runtime.getRuntime().exec(command);
	    }
	    catch (IOException e) {
	        System.out.println(e.toString());
	        return false;
	    }

	    InputStream inputStream = process.getInputStream();
	    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
	    InputStream errorStream = process.getErrorStream();
	    BufferedInputStream bufferedErrorStream = new BufferedInputStream(errorStream);
	    boolean ok = false;
	    int timeout = exTimeout;
	    int exitValue = -999;

	    while (!ok) {
	        try {
	            while (bufferedInputStream.available() > 0 || bufferedErrorStream.available() > 0) {
	                while (bufferedInputStream.available() > 0) {
	                	char ch = (char)bufferedInputStream.read();
	                	buf.append(ch);
	                    //System.out.print(ch);
	                }
	                while (bufferedErrorStream.available() > 0) {
	                	char ch = (char)bufferedErrorStream.read();
	                	buf.append(ch);
	                    //System.out.print(ch);
	                }
	            }
	        }
	        catch (IOException e) {
	            System.out.println("Couldn't read response");
	        }
	        try {
	            exitValue = process.exitValue();
	            ok = true;
	        }
	        catch (IllegalThreadStateException e) {
	            try {
	                // still running.
	                Thread.sleep(300);
	                timeout = timeout - 300;
	                if (timeout < 0 && timeout >= -300) {
	                    System.out.println("ALERT: Command doesn't terminate:");
	                    System.out.println(command);
	                    System.out.println("Shutting down command...");
	                    process.destroy();
	                }
	                else if (timeout <0) {
	                    System.out.println("ALERT: Command STILL doesn't	terminate:");
	                    System.out.println(command);
	                    Thread.sleep(1000);
	                }
	            } catch (InterruptedException e1) {
	                // doesn't matter
	            }
	        }
	    }
	    if (ok) {
	        // finished running
	        if (exitValue == 0) {
	            System.out.println("Terminated without errors");
	        }
	        else {
	            System.out.println("Exit code " + exitValue + " while performing command " + command);
	        }
	    }
	    else {
	        process.destroy();
	    }
	    try {
	        while (bufferedInputStream.available() > 0) {
	        	char ch = (char)bufferedInputStream.read();
	        	buf.append(ch);
	            //System.out.print(ch);
	        }
	        while (bufferedErrorStream.available() > 0) {
	        	char ch = (char)bufferedErrorStream.read();
	        	buf.append(ch);
	            //System.out.print(ch);
	        }
	    }
	    catch (IOException e) {
	        System.out.println("Couldn't read response");
	    }
	    return exitValue==0;
	}
	public List<String> getWinUsersGroups(String command,String lineSeperator,String itemSeperator){
		List<String> users=new ArrayList<String>();
		
		String net_userText = executeCommand(command);
		if(net_userText!=null){
		String startText="-------------------------------------------------------------------------------";
		String endText="The command completed successfully.";
		
		int beginIndex=net_userText.indexOf(startText)+startText.length();
		int endIndex = net_userText.lastIndexOf(endText);
		
		String usersText = net_userText.substring(beginIndex, endIndex);
		String lines[] = usersText.split(lineSeperator);
		for(int i=0;i<lines.length;i++){
			String[] lineUsers=lines[i].split(itemSeperator);
			for(int j=0;j<lineUsers.length;j++){
				if(lineUsers[j].trim().length()>0){
					users.add(lineUsers[j].trim());
				}
			}
		}
		}
		
		return users;
	}
	public List<String> getWinUsers(){
		return  getWinUsersGroups("net user","\n"," ");
	}
	public List<String> getWinGroups(){
		return getWinUsersGroups("net localgroup","\n","[*]");
	}
	public boolean addWinUser(String user,String pass){
		return addWinUser(user, pass,"","");
	}
	public boolean addWinUser(String user,String pass,String fullname,String comment){
		String cmd="NET USER "+user+" "+pass+" /ADD /fullname:\""+fullname+"\" /comment:\""+comment+"\" ";
		System.out.println("AIPWindows.addWinUser():cmd="+cmd);
		return executeCommand(cmd,0,new StringBuffer());
		
	}
	public boolean addWinGroup(String groupname) {
		String cmd="NET LOCALGROUP "+groupname+" /add ";
		System.out.println("AIPWindows.addWinGroup():cmd="+cmd);
		return executeCommand(cmd,0,new StringBuffer());
	}
	public boolean addWinUser2Group(String groupname,String username) {
		String cmd="NET LOCALGROUP "+groupname+" "+username+" /add ";
		System.out.println("AIPWindows.addWinUser2Group():cmd="+cmd);
		return executeCommand(cmd,0,new StringBuffer());
	}

	public boolean delWinUser(String user){
		String cmd="NET USER "+user+" /DELETE ";
		System.out.println("AIPWindows.delWinUser():cmd="+cmd);
		return executeCommand(cmd,0,new StringBuffer());
		
	}
	public boolean delWinGroup(String groupname) {
		String cmd="NET LOCALGROUP "+groupname+" /delete ";
		System.out.println("AIPWindows.delWinGroup():cmd="+cmd);
		return executeCommand(cmd,0,new StringBuffer());
	}
	public boolean delWinUserInGroup(String groupname,String username) {
		String cmd="NET LOCALGROUP "+groupname+" "+username+" /delete ";
		System.out.println("AIPWindows.delWinUserInGroup():cmd="+cmd);
		return executeCommand(cmd,0,new StringBuffer());
	}
	

}
