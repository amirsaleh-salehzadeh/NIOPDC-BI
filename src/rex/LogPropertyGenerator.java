/*
 * Created on Jul 21, 2006
 */
package rex;

//import java.io.FileOutputStream;
//import java.io.File;
import java.util.Properties;
import rex.utils.S;
import rex.graphics.mdxeditor.jsp.ReadEnv;
//import java.util.Locale;
import java.io.*;
/**
 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
 *   All Rights Reserved
 *   Copyright (C) 2006 Igor Mekterovic
 *   All Rights Reserved
 */
/**
 * @author prakash
 */

public class LogPropertyGenerator {
	private String strPropertyContent;
	private String logFileLocation;
        Properties rexProp;
        boolean created=false;
	public LogPropertyGenerator()
	{	
            
            
		try
		{
			File logFolder=new File("logs");
			/**
			 * 	Modified to avoid PMD voilation of "Empty if Statements".
			 *	by Prakash. 08-05-2007
			 */
			if(!(logFolder.exists() && logFolder.isDirectory()))
			{
			      created=logFolder.mkdir();
			}
			/**
			 * 	End of the modification.
			 */
			Properties prop=ReadEnv.getEnvVars();
			logFileLocation=prop.getProperty("USERPROFILE")+"\\rex.properties";
                         File file=new File(logFileLocation);
			if(!file.exists())
			{
				generateLogProperty();
				FileOutputStream out = new FileOutputStream(logFileLocation);
				out.write(strPropertyContent.getBytes());
				out.close();
			}
                       java.util.Locale locale = rex.utils.I18n.getCurrentLocale();
       
                         File f = new File ("rex/locale/Rex_"+ locale.toString());
                 if(!f.exists()){
                     rex.utils.I18n.setCurrentLocale("en","US");
                 }
                       // use the language previous used.
              //          this.setLocale();
		}
		catch(Exception exception)
		{
			S.reportError(exception);
		}
	}

	private void generateLogProperty()
	{
		strPropertyContent="# This Property file is means to append logs with Console as well as to log file.\n"; 
		strPropertyContent=strPropertyContent.concat("# file (By default).\n");

		strPropertyContent=strPropertyContent.concat("log4j.rootLogger=debug, stdout, R\n");

		strPropertyContent=strPropertyContent.concat("####################### Binding logs with console #######################\n");

		strPropertyContent=strPropertyContent.concat("log4j.appender.stdout=org.apache.log4j.ConsoleAppender\n");
		strPropertyContent=strPropertyContent.concat("log4j.appender.stdout.layout=org.apache.log4j.PatternLayout\n");

		strPropertyContent=strPropertyContent.concat("# Pattern to output the caller's file name and line number.\n");
		strPropertyContent=strPropertyContent.concat("log4j.appender.stdout.layout.ConversionPattern=%5p [%t] (%F:%L) - %m%n\n");

		strPropertyContent=strPropertyContent.concat("#########################################################################\n");

		strPropertyContent=strPropertyContent.concat("###################### Binding logs with log file #######################\n");
		strPropertyContent=strPropertyContent.concat("log4j.appender.R=org.apache.log4j.RollingFileAppender\n");
		if(created)
		{
		    strPropertyContent=strPropertyContent.concat("log4j.appender.R.File=logs/rex.log\n");
		}
		else
		{
		    strPropertyContent=strPropertyContent.concat("log4j.appender.R.File=rex.log\n");
		}
		//strPropertyContent=strPropertyContent.concat("log4j.appender.R.MaxFileSize=100KB\n");
		strPropertyContent=strPropertyContent.concat("# Keep one backup file\n");
		strPropertyContent=strPropertyContent.concat("log4j.appender.R.MaxBackupIndex=1\n");

		strPropertyContent=strPropertyContent.concat("log4j.appender.R.layout=org.apache.log4j.PatternLayout\n");
		strPropertyContent=strPropertyContent.concat("log4j.appender.R.layout.ConversionPattern=%5p [%t] (%F:%L) - %m%n\n");

		strPropertyContent=strPropertyContent.concat("#########################################################################\n");		
                //adding the language property
        //        strPropertyContent=strPropertyContent.concat("Language="+ Locale.getDefault().getLanguage());		
	}
	public static void main(String[] args) {
		new LogPropertyGenerator();
	}

        
        
}