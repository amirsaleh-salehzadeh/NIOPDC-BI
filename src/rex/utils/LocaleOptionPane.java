/*
 * Created on Dec 29, 2006
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  * @author PYadav
  */

package rex.utils;

import javax.swing.JMenu;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

//import java.util.Properties;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.OutputStream;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
import java.util.*;
import java.util.jar.*;
import java.net.*;
import java.io.*;
/**
 * @author pyadav
 *
 */
public class LocaleOptionPane extends JMenu{	
	private ButtonGroup group;
	private Locale currentLocale;
	private Document document;
	/**
	 * 
	 */
	public LocaleOptionPane() 
	{
		super("Set Locale");
		group=new ButtonGroup();
		generateLocaleMenu();
		// TODO Auto-generated constructor stub		
	}
	
	/*
	 * Generates Locale menu. 
	 */
    public void generateLocaleMenu() {
		String defaultLocale=setDefaultMenu();
    	java.util.List list=getListOfAvailLanguages();
    	final Iterator it=list.iterator();
    	while(it.hasNext()){
			final Locale temp=(Locale)it.next();
    		JRadioButtonMenuItem item=new JRadioButtonMenuItem(temp.getDisplayLanguage()+" "+temp.getDisplayCountry());
    		  item.addActionListener( 	new ActionListener() { 
    			public void actionPerformed(ActionEvent h) 
    			{
    				//setNewLocale(temp.getLanguage());
    				setNewLocale(temp);
    			}
    			});
    		group.add(item);
    		add(item);
    		if(defaultLocale.trim().equalsIgnoreCase((temp.getDisplayLanguage()+" "+temp.getDisplayCountry()).trim()))
    		{
    			item.setSelected(true);
    			setNewLocale(temp);
    		}
    	}
    }
    
    /*
     * Returns default saved locale.   
     */
	public String setDefaultMenu() {
		  String lastLocaleLanguage="";
		  String lastLocaleCountry="";		  
	      DocumentBuilder builder;
	      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      try {
	         builder = factory.newDocumentBuilder();

	      try {
	         document = builder.parse( new File("mdxeditor.defaults.xml"));
	      } catch (Exception ioe) {
	      }
	      NodeList nl = document.getDocumentElement().getChildNodes();
	      for(int i=0; i < nl.getLength(); i++){
	         if (nl.item(i).getNodeName().equalsIgnoreCase("LOCALE_LANGUAGE")){
	            lastLocaleLanguage = new String(DOM.getTextFromDOMElement(nl.item(i)));
	         }else if(nl.item(i).getNodeName().equalsIgnoreCase("LOCALE_COUNTRY")){
	         	lastLocaleCountry = new String(DOM.getTextFromDOMElement(nl.item(i)));
	         }	         
	      }
	      } catch(ParserConfigurationException pce){
	         //pce.printStackTrace();//Commented by Prakash
	         return "";
	      }
	      return lastLocaleLanguage+" "+lastLocaleCountry;

	} 
	    
       /**
         * Enumerates the resouces in a give package name.
         * This works even if the resources are loaded from a jar file!
         *
         * Adapted from code by mikewse
         * on the java.sun.com message boards.
         * http://forum.java.sun.com/thread.jsp?forum=22&thread=30984
         *
         * @param packageName The package to enumerate
         * @return A Set of Strings for each resouce in the package.
          */
        public static Set getResoucesInPackage(String packageName) throws IOException {
                String localPackageName;
                if( packageName.endsWith("/") ) {
                        localPackageName = packageName;
                } else {
                        localPackageName = packageName + '/';
                }

                Enumeration dirEnum = ClassLoader.getSystemResources( localPackageName );
                
                Set names = new HashSet();

                // Loop CLASSPATH directories
                while( dirEnum.hasMoreElements() ) {
                        URL resUrl = (URL) dirEnum.nextElement();
                       
                        // Pointing to filesystem directory
                        if ( resUrl.getProtocol().equals("file") ) {
                            try {
                              File dir = new File( new URI(resUrl.toString()).getPath());
                                File[] files = dir.listFiles();
                                if ( files != null ) {
                                        for( int i=0; i<files.length; i++ ) {
                                                File file = files[i];
                                               if ( file.isDirectory() || !(file.getName().startsWith("Rex")))
                                               {
                                                        continue;
                                               }
                                                names.add( localPackageName + file.getName() );
                                        }
                                }
                            } catch (Exception ex) {}
                            
                                // Pointing to Jar file
                        } else if ( resUrl.getProtocol().equals("jar") ) {
                                JarURLConnection jconn = (JarURLConnection) resUrl.openConnection();
                                JarFile jfile = jconn.getJarFile();
                                Enumeration entryEnum = jfile.entries();
                                while( entryEnum.hasMoreElements() ) {
                                        JarEntry entry = (JarEntry) entryEnum.nextElement();
                                        String entryName = entry.getName();
                                        // Exclude our own directory
                                        if ( entryName.equals(localPackageName) )
                                        {
                                                continue;
                                        }
                                        String parentDirName = entryName.substring( 0, entryName.lastIndexOf('/')+1 );
                                        if ( ! parentDirName.equals(localPackageName) )
                                        {
                                                continue;
                                        }
                                        names.add( entryName );
                                }
                        } else {
                                // Invalid classpath entry
                        }
                }

                return names;
        }
       public static java.util.List getListOfAvailLanguages()
      {
          java.util.List supportedLocales = new java.util.ArrayList();
          try
          {
              Set names = getResoucesInPackage( I18n.localPackageName );
              Iterator it = names.iterator();
              while( it.hasNext() )
              {
                  String n = (String)it.next();  
                  String lang = n.substring( n.lastIndexOf('/')+1 );
                  if ( lang.indexOf(".properties") < 0 )
                  {
                      continue; 
                  }  
                  lang = lang.substring(0, lang.indexOf(".properties") );  
                  StringTokenizer tokenizer = new StringTokenizer( lang, "_");
                  if ( tokenizer.countTokens() <=  1 )
                  {                      
                      continue;                       
                  }
  
                  String language = "";
                  String country  = "";
                  String variant  = "";
  
                  String[] parts = new String[tokenizer.countTokens()];
                  int i = 0;
                  while (tokenizer.hasMoreTokens() )
                  {
                      String token = tokenizer.nextToken();
                      switch (i)
                      {
                          case 0:
                              //the word Rex
                              break;
                          case 1:
                              language = token;
                              break;
                          case 2:
                              country = token;
                              break;
                          case 3:
                              variant = token;
                              break;
                          default:
                      }
                      i++;  
                  }  
                  Locale model = new Locale( language, country, variant );
                  supportedLocales.add( model ); 
              }
          }
          catch(Exception e)
          {
              //e.printStackTrace();//Commented by Prakash
          }  
          return supportedLocales;
      }
        private void setNewLocale(Locale strLanguage) {    
        I18n.setCurrentLocale(strLanguage);
    }  
        
    	public Locale getCurrentLocale()
    	{
    		return currentLocale;
    	}

    	public void setCurrentLocale(Locale current)
    	{
    	  currentLocale=current;
    	  String currentName=current.getDisplayLanguage();
    	  currentName=currentName+" "+current.getDisplayCountry();
          Enumeration localeElements=group.getElements();
          while(localeElements.hasMoreElements())
          {
          	JRadioButtonMenuItem tempItem=(JRadioButtonMenuItem)localeElements.nextElement();
          	if(currentName.trim().equalsIgnoreCase(tempItem.getText().trim()))
          	{
          		tempItem.setSelected(true);
          		break;
          	}
          }
          saveLocale(currentLocale);
    	}

    	public void saveLocale(Locale locale)
    	{
    	   	Node n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "LOCALE_LANGUAGE");
    	   	try
    		{
    	    n.removeChild(n.getChildNodes().item(0));
    	    n.appendChild(document.createTextNode(locale.getDisplayLanguage().length()>0 ? locale.getDisplayLanguage() : " "));
    		}
    	   	catch(Exception exc)
    		{
    	   		n.appendChild(document.createTextNode(locale.getDisplayLanguage().length()>0 ? locale.getDisplayLanguage() : " "));
    		}
    	   	n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "LOCALE_COUNTRY");
    	   	try
    		{
    	    n.removeChild(n.getChildNodes().item(0));
    	    n.appendChild(document.createTextNode(locale.getDisplayCountry().length()>0 ? locale.getDisplayCountry() : " "));
    		}
    	  	catch(Exception exc)
    		{
    	  		n.appendChild(document.createTextNode(locale.getDisplayCountry().length()>0 ? locale.getDisplayCountry() : " "));
    		}
    	  	TransformerFactory tFactory =
    	          TransformerFactory.newInstance();
    	      try{
    	         Transformer transformer = tFactory.newTransformer();
    	         DOMSource source = new DOMSource(document);
    	         //StreamResult result = new StreamResult(new File("mdxeditor.defaults.xml"));
    	         StreamResult result = new StreamResult(new BufferedWriter(new FileWriter(new File("mdxeditor.defaults.xml"))));
    	         transformer.transform(source, result);
    	      } catch (Exception e){
    	         S.out("Unable to save defaults:");
    	      }
    	}
}
