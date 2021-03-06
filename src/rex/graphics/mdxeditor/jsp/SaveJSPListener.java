/*
 * Created by Prakash 
 * Cincom Systems  
 * Created on May 8, 2006
 *
 * 
 */
package rex.graphics.mdxeditor.jsp;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import java.util.Properties;
import rex.utils.S;
import rex.utils.I18n;
/**
 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
 *   All Rights Reserved
 *   Copyright (C) 2006 Igor Mekterovic
 *   All Rights Reserved
 */

/**
 * @author pyadav
 *
 * This class is generating JSP contents for the MDX Queries and store it in a 
 * jsp file under Queries folder of mondrian and execute it in default browser 
 * by using jpivot and wcf tags
 * 
 */
public class SaveJSPListener  {
	/**
	 * 
	 */
	String strMdx,strDSN,strCatalog;
	JSPFileFilter jspFilter;
	
	public SaveJSPListener(String strmdx,String strdsn,String strcatalog) {
		super();
		strMdx=strmdx;
		strDSN=strdsn;
		strCatalog=strcatalog;
	    String []ss=strMdx.split("\n");
	    jspFilter=new JSPFileFilter("jsp");
        int j=0;
        for(int i=0;i<ss.length;i++)
        {
        	if(ss[i].startsWith("--")||(ss[i].trim()).length()==0)
        	{
        	continue;
        	}
        	j++;
        }
        String bss[]=new String[j];
        j=0;
        for(int i=0;i<ss.length;i++)
        {
        	if(ss[i].startsWith("--")||(ss[i].trim()).length()==0)
        	{
        	continue;
        	}
        	bss[j]=ss[i];
        	j++;
        }
        for(int i=0;i<bss.length;i++)
        {
        	System.out.println(bss[i]);
        }
        String strToJSP="<%@ page session=\"true\" contentType=\"text/html; charset=ISO-8859-1\" %>\n";
        strToJSP=strToJSP.concat("<%@ page import=\"com.tonbeller.jpivot.chart.ChartComponent\" %>\n");
        strToJSP=strToJSP.concat("<%@ taglib uri=\"http://www.tonbeller.com/jpivot\" prefix=\"jp\" %>\n");
        strToJSP=strToJSP.concat("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jstl/core\" %>\n");
        strToJSP=strToJSP.concat("<%@ taglib uri=\"http://www.tonbeller.com/wcf\" prefix=\"wcf\" %>\n");
        strToJSP=strToJSP.concat("<jp:mondrianQuery id=\"query01\" dataSource=\"jdbc/");
        strToJSP=strToJSP.concat(strDSN.concat("\""));
        strToJSP=strToJSP.concat(" catalogUri=\"/WEB-INF/queries/");
        strToJSP=strToJSP.concat(strCatalog.concat(".xml\">\n"));
        for(int i=0;i<bss.length;i++)
        {         
        	strToJSP=strToJSP.concat((bss[i]).concat("\n"));
        }
        strToJSP=strToJSP.concat("</jp:mondrianQuery>\n");
        strToJSP=strToJSP.concat("<c:set var=\"title01\" scope=\"session\">Query Generated through REX</c:set>");
        strToJSP=strToJSP.concat("<jp:chart id=\"chart01\" query=\"#{query01}\" visible=\"true\"/>\n");
        strToJSP=strToJSP.concat("<wcf:form id=\"chartform01\" xmlUri=\"/WEB-INF/jpivot/chart/chartpropertiesform.xml\" model=\"#{chart01}\" visible=\"false\"/>\n");
        strToJSP=strToJSP.concat("<wcf:table id=\"query01.drillthroughtable\" visible=\"false\" selmode=\"none\" editable=\"true\"/>\n");
        strToJSP=strToJSP.concat("<% ChartComponent chart = (ChartComponent) request.getSession(true).getAttribute(\"chart01\");\n");
        strToJSP=strToJSP.concat("chart.setChartType(4);\n");
        strToJSP=strToJSP.concat("chart.setBgColorR(208);\n");
        strToJSP=strToJSP.concat("chart.setBgColorG(228);\n");
        strToJSP=strToJSP.concat("chart.setBgColorB(208);\n");
        strToJSP=strToJSP.concat("chart.setChartHeight(300);\n");
        strToJSP=strToJSP.concat("chart.setChartWidth(700);\n");
        strToJSP=strToJSP.concat("%>");         
        System.out.println(strToJSP);
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        try
		{
        	Properties prop=ReadEnv.getEnvVars();
        	chooser.setCurrentDirectory(new File((prop.getProperty("CATALINA_HOME")).trim()+"\\webapps\\mondrian\\WEB-INF\\queries\\"));
        	chooser.setAcceptAllFileFilterUsed(false);
        	chooser.addChoosableFileFilter(jspFilter);
		}
        catch(Exception exception)
		{
        	S.reportError(exception);
		}
        FileOutputStream out = null;
        try {
        		while(true)
        		{
        			int returnVal = chooser.showSaveDialog(null);
        			if (returnVal != JFileChooser.APPROVE_OPTION) 
        			{
        				return;
        			}
              
        			if(((chooser.getSelectedFile()).getName()).endsWith(".jsp"))
        			{
        			    if(exists(chooser.getSelectedFile().getAbsolutePath()))
        			    {
        			        int confirm=JOptionPane.showConfirmDialog(null,I18n.getString("msgText.fileExists"));
        			        if(confirm==JOptionPane.NO_OPTION)
        			        {
        			            return;
        			        }
        			    }
        			    /**
        			     * Extension should be .jsp.
        			     * by Prakash. 10-05-2007. 
        			     */
        				out = new FileOutputStream(chooser.getSelectedFile().getAbsolutePath());        			
        				break;
        			}
        			/**
        			 * Searching of .jsp extension before saving.
        			 * Modified by Prakash. 29th May 2007.
        			 */
        			else if (chooser.getFileFilter().getDescription().startsWith(".jsp"))
        			{
        		        if(!((chooser.getSelectedFile()).getName()).endsWith(".jsp"))
        		        {
            			    if(exists(chooser.getSelectedFile().getAbsolutePath().concat(".jsp")))
            			    {
            			        int confirm=JOptionPane.showConfirmDialog(null,I18n.getString("msgText.fileExists"));
            			        if(confirm==JOptionPane.NO_OPTION)
            			        {
            			            return;
            			        }
            			    }
            				out = new FileOutputStream(chooser.getSelectedFile().getAbsolutePath()+".jsp");        			
            				break;
            			    /*
            		        File tempFile=new File(chooser.getSelectedFile().getAbsolutePath().concat(".jsp"));

                            if(tempFile.exists())
                            {
                                int confirm=JOptionPane.showConfirmDialog(null,I18n.getString("msgText.fileExists"));
                                if(confirm==JOptionPane.NO_OPTION)
                                {
                                    return;
                                }
                            }
                            */
        		        }
        			}
        			else
        			{
                                     /**
                                      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                                      * All Rights Reserved
                                      * Copyright (C) 2006 Igor Mekterovic
                                      * All Rights Reserved
                                      */
                                    JOptionPane.showMessageDialog(null,I18n.getString("msgText.jspFile"));
                                      /* end of modification for I18n */

        			}
        		}
        		out.write(strToJSP.getBytes());
        		out.close();
        		 /**
                          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                          * All Rights Reserved
                          * Copyright (C) 2006 Igor Mekterovic
                          * All Rights Reserved
                          */
                        int iOpenJPivot=JOptionPane.showConfirmDialog(null, I18n.getString("mstText.jpivotResult"));
        		  /* end of modification for I18n */

        		if(iOpenJPivot==0)
        		{
        			Runtime rt=Runtime.getRuntime();
        			Process p=rt.exec("cmd /c "+"start http://localhost:8080/mondrian/testpage.jsp?query="+((chooser.getSelectedFile()).getName()).replaceFirst(".jsp",""));
        			p.waitFor();
        		}
            } catch (Exception exc){
                 /**
                  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                  * All Rights Reserved
                  * Copyright (C) 2006 Igor Mekterovic
                  * All Rights Reserved
                  */
                JOptionPane.showMessageDialog(null
                                              , I18n.getString("msgText.fileNotFound")+ chooser.getSelectedFile().getAbsolutePath()
                                              , I18n.getString("msgTitle.saveQuery")
                                              , JOptionPane.ERROR_MESSAGE);
                  /* end of modification for I18n */

              return;
            }
     }
	
    /**
     * Check for the existence of file. 
     * Method inserted by Prakash. 29th May 2007 
     */
    private boolean exists(String pathName)
    {
        if(new File(pathName).exists())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
	private class JSPFileFilter extends FileFilter {


	    private Hashtable filters = null;
	    /*
	     * Commented. Not bieng used.
	     * by Prakash. 10-05-2007.
	     */
	    
	    //private String description = "jsp";

	    public JSPFileFilter(String extension) {
	    this.filters = new Hashtable();
	    /**
	     * Inserting curli braces to avoid PMD violation named IfStmtsMustUseBraces.
	     * by Prakash. 10-05-2007. 
	     */
		if(extension!=null) { addExtension(extension); }
		/*
		 * End of modification.
		 */
	    }

	    public boolean accept(File f) {
		if(f != null) {
		    if(f.isDirectory()) {
			return true;
		    }
		    String extension = getExtension(f);
		    if(extension!= null && filters.get(getExtension(f)) != null) {
			return true;
			/**
			 * Removed semicolon.
			 * by Prakash. 10-05-2007. 
			 */
		    }
		}
		return false;
	    }

	   
	     public String getExtension(File f) {
		if(f != null) {
		    String filename = f.getName();
		    int i = filename.lastIndexOf('.');
		    if(i>0 && i<filename.length()-1) {
			return filename.substring(i+1).toLowerCase();
			/**
			 * Removed semicolon.
			 * by Prakash. 10-05-2007. 
			 */
		    }
		}
		return null;
	    }

	    public void addExtension(String extension) {
		if(filters == null) {
		    filters = new Hashtable();
		}
		filters.put(extension.toLowerCase(), this);		
	    }

	    public String getDescription() {
		return ".jsp";
	    }
	}
}
//End of addition by Prakash