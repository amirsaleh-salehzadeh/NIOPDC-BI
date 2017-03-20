package aip.util.os.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import aip.util.os.AIPOS;
import aip.util.os.AIPOSException;

public class AIPOSWeb extends AIPOS {
	
	public static String PROPERTIES_URI="aiposweb.properties";

	String uri;
	String user;
	String password;
	
	public boolean executeCommand(String command, int exTimeout,StringBuffer buf)throws AIPOSException {

		URL url;
		
		String uri= getUri() + "?" +  command;

	    System.out.println("AIPOSWeb.executeCommand():uri="+uri);
		
		try {
	      url = new URL(uri);
	    } catch (MalformedURLException e1) {
	      throw new AIPOSException(e1);
	    }

	    if (user != null && user.length() > 0) {
	      String newUri = url.getProtocol() + "://" + user;
	      if (password != null && password.length() > 0) {
	        newUri += ":" + password;
	      }
	      newUri += "@" + url.getHost() + ":" + url.getPort() + url.getPath() + "?" + url.getQuery();

		  System.out.println("AIPOSWeb.executeCommand():newUri="+newUri);
	      
//	      try {
//	        url = new URL(newUri);
//	      } catch (MalformedURLException e2) {
//	        throw new AIPOSException(e2);
//	      }
	    }
	    

	    HttpURLConnection http=null;
		InputStream in = null;
		try {
			in = url.openStream();//http.getInputStream();
			char ch;
			while((ch=(char)in.read() )!=0 && ch!=65535 ){
				System.out.print( ch );
				buf.append(ch);
			}
			return true;
		} catch (MalformedURLException e) {
			throw new AIPOSException(e);
		} catch (IOException e) {
			throw new AIPOSException(e);
		}finally{
			if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(http!=null)
				http.disconnect();
		}
	}
	public String executeCommand(String command) throws AIPOSException{
		StringBuffer buf = new StringBuffer();
		
		boolean b = executeCommand(command, 600, buf);
		return b?buf.toString():null;
	}
	public List<String> getWinUsersGroups(String command,String lineSeperator,String itemSeperator)throws AIPOSException{
		List<String> users=new ArrayList<String>();
		
		String net_userText = executeCommand(command);
		
		if(net_userText!=null){
		String lines[] = net_userText.split(lineSeperator);
		for(int i=0;i<lines.length;i++){
			String[] lineUsers=lines[i].split(itemSeperator);
			for(int j=0;j<lineUsers.length;j++){
				if(lineUsers[j].trim().length()>0 && !".".equals(lineUsers[j].trim())){
					users.add(lineUsers[j].trim());
				}
			}
		}
		}
		
		return users;
	}
	public List<String> getWinUsers()throws AIPOSException{
		return  getWinUsersGroups("reqCode=getWinUsers",".",",");
	}
	public List<String> getWinGroups()throws AIPOSException{
		return getWinUsersGroups("reqCode=getWinGroups",".",",");
	}
	public boolean addWinUser(String username,String password)throws AIPOSException{
		return addWinUser(username, password,"","");
	}
	public boolean addWinUser(String username,String password,String fullname,String comment)throws AIPOSException{
		String cmd="reqCode=addWinUser&username="+username+"&password="+password;
		System.out.println("AIPWindows.addWinUser():cmd="+cmd);
		return executeCommand(cmd,0,new StringBuffer());
		
	}
	public boolean addWinGroup(String groupname) throws AIPOSException{
		String cmd="reqCode=addWinGroup&groupname="+groupname;
		System.out.println("AIPWindows.addWinGroup():cmd="+cmd);
		return executeCommand(cmd,0,new StringBuffer());
	}
	public boolean addWinUser2Group(String groupname,String username) throws AIPOSException{
		String cmd="reqCode=addWinUser2Group&groupname="+groupname+"&username="+username;
		System.out.println("AIPWindows.addWinUser2Group():cmd="+cmd);
		return executeCommand(cmd,0,new StringBuffer());
	}

	public boolean delWinUser(String username)throws AIPOSException{
		String cmd="reqCode=delWinUser&username="+username;
		System.out.println("AIPWindows.delWinUser():cmd="+cmd);
		return executeCommand(cmd,0,new StringBuffer());
		
	}
	public boolean delWinGroup(String groupname) throws AIPOSException{
		String cmd="reqCode=delWinGroup&groupname="+groupname;
		System.out.println("AIPWindows.delWinGroup():cmd="+cmd);
		return executeCommand(cmd,0,new StringBuffer());
	}
	public boolean delWinUserInGroup(String groupname,String username) throws AIPOSException{
		String cmd="reqCode=delWinUserInGroup&groupname="+groupname+"&username="+username;
		System.out.println("AIPWindows.delWinUserInGroup():cmd="+cmd);
		return executeCommand(cmd,0,new StringBuffer());
	}

	
	void initProperties(){
		Properties properties = new Properties();
		
		String PROPERTIES_URI = "aiposweb.properties";
		File f = new  File(PROPERTIES_URI);
		String notFound="فایل تنظیمات اتصال به سرور اطلاعات تحلیلی " + f.getPath() + " وجود ندارد.یا درست نمی باشد.";
		System.out.println("AIPOSWeb.getServerURL():"+PROPERTIES_URI+"="+f.getAbsolutePath());

		if(f.exists()){
			FileInputStream fin=null;
			try {
				fin = new FileInputStream(f);
//				properties.loadFromXML( fin );
				InputStreamReader isr = new InputStreamReader(fin,"UTF-8");
				BufferedReader br = new BufferedReader(isr);
				String line=br.readLine();
				while(line!=null){
					String[] pair = line.split("=");
					if(pair.length>1){
						properties.put(pair[0], pair[1]);
					}
					line=br.readLine();
				}
				fin.close();
				
				this.setUri( properties.getProperty("serverUri") );
				this.setUser(properties.getProperty("user") );
				this.setPassword( properties.getProperty("password") );
				
			} catch (FileNotFoundException e) {
				//throw new AIPSecurityException(notFound + "\n" + e.getMessage(),e);
				e.printStackTrace();
			} catch (IOException e) {
				//throw new AIPSecurityException(notFound + "\n" + e.getMessage(),e);
				e.printStackTrace();
			}finally{
				if(fin!=null){
					try {
						fin.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}else{
			this.setUri( "http://localhost:80/olap/Default.aspx" );
			this.setUser("" );
			this.setPassword( "" );

			properties.setProperty("serverUri", this.getUri() );
			properties.setProperty("user", this.getUser() );
			properties.setProperty("password", this.getPassword() );

			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
				PrintWriter pw = new PrintWriter(f,"UTF-8");
				properties.list(pw);
				pw.close();
			} catch (FileNotFoundException e) {
				//throw new AIPSecurityException(notFound + "\n" + e.getMessage(),e);
				e.printStackTrace();
			} catch (IOException e) {
				//throw new AIPSecurityException(notFound + "\n" + e.getMessage(),e);
				e.printStackTrace();

			}finally{
				if(fos!=null){
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			//throw new AIPSecurityException(notFound);
			new AIPOSException(notFound).printStackTrace();
	}
	}
	
	public String getUri() {
		if(uri==null || "".equals(uri)){
			initProperties();
		}
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public static void main(String[] args) {
		AIPOSWeb os = new AIPOSWeb();
		 try {
				os.getWinGroups();
				os.getWinUsers();
		} catch (AIPOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main2(String[] args) {
		HttpURLConnection http = null;
		InputStream in = null;
		try {
			URL url = new  URL("http://localhost:81/olap/Default.aspx?reqCode=getWinUsers");
//			http = (HttpURLConnection) url.openConnection();
//			http.setDoInput(true);
//			http.connect();
			in = url.openStream();//http.getInputStream();
			char ch;
			while((ch=(char)in.read() )!=0 && ch!=65535 ){
				System.out.print( ch );
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(http!=null)
				http.disconnect();
		}
		
	}

}
