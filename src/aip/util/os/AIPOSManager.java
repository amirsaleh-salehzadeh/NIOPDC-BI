package aip.util.os;

import aip.util.os.web.AIPOSWeb;

public class AIPOSManager {
	static AIPOS defaultOS=new AIPOSWeb();

	public static void installOS(String className)throws AIPOSException{
		try {
			defaultOS=(AIPOS) Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			throw new AIPOSException( e );
		} catch (IllegalAccessException e) {
			throw new AIPOSException( e );
		} catch (ClassNotFoundException e) {
			throw new AIPOSException( e );
		} catch (Exception e) {
			throw new AIPOSException( e );
		}
		
	}
	public static AIPOS getOS(){
		return defaultOS;
	}
	public static AIPOS getOS(String className)throws AIPOSException{
		installOS(className);
		return defaultOS;
	}
	

}
