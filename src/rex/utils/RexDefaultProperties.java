/*
 * Created on Jan 18, 2007
 *
 * 
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  * @author PYadav
  */

package rex.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
//import java.io.IOException;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import rex.graphics.mdxeditor.MdxEditorToolbar;

/**
 * @author pyadav
 *
 */
public class RexDefaultProperties {

	public static void createDefaultProperties()
	{
		Document document;
		if(!new File("mdxeditor.defaults.xml").exists())
		{			
			DocumentBuilder builder;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			try 
			{
				builder = factory.newDocumentBuilder();
			}
			catch(ParserConfigurationException pce)
			{
				//pce.printStackTrace(); //Commented by Prakash
				return;
			}
			try 
			{
				document = builder.newDocument();
				Element root = document.createElement("root");
				Element e;
				document.appendChild(root);
				e = document.createElement("FONT_NAME");
				e.appendChild(document.createTextNode("Courier New"));
				root.appendChild(e);

				e = document.createElement("FONT_SIZE");
				e.appendChild(document.createTextNode("13"));
				root.appendChild(e);

				e = document.createElement("BOLD");
				e.appendChild(document.createTextNode("false"));
				root.appendChild(e);

				e = document.createElement("ITALIC");
				e.appendChild(document.createTextNode("false"));
				root.appendChild(e);

				e = document.createElement("TAB_SIZE");
				e.appendChild(document.createTextNode("2"));
				root.appendChild(e);

				e = document.createElement("FOREGROUND_COLOR");
				e.appendChild(document.createTextNode("-16777216"));
				root.appendChild(e);

				e = document.createElement("BACKGROUND_COLOR");
				e.appendChild(document.createTextNode("-1"));
				root.appendChild(e);

				e = document.createElement("RESULT_POSITION");
				e.appendChild(document.createTextNode(MdxEditorToolbar.VERTICAL_SPLIT_PANE));
				root.appendChild(e);

				e = document.createElement("SAVE_DIRECTORY");
				// 	S.out("System.getProperty(user.home)=" + System.getProperty("user.home"));
				e.appendChild(document.createTextNode(System.getProperty("user.home")));
				root.appendChild(e);

				//	By Prakash
				e = document.createElement("RECENT_OPENED_FILE1");
				e.appendChild(document.createTextNode(""));
				root.appendChild(e);
				e = document.createElement("RECENT_OPENED_FILE2");
				e.appendChild(document.createTextNode(""));
				root.appendChild(e);
				e = document.createElement("RECENT_OPENED_FILE3");
				e.appendChild(document.createTextNode(""));
				root.appendChild(e);
				e = document.createElement("RECENT_OPENED_FILE4");
				e.appendChild(document.createTextNode(""));
				root.appendChild(e);
				e = document.createElement("LOCALE_LANGUAGE");
				e.appendChild(document.createTextNode(Locale.getDefault().getLanguage())); 
				root.appendChild(e);
				e = document.createElement("LOCALE_COUNTRY");
				e.appendChild(document.createTextNode(Locale.getDefault().getCountry())); 
				root.appendChild(e);
				TransformerFactory tFactory = TransformerFactory.newInstance();
				try
				{
					Transformer transformer = tFactory.newTransformer();
					DOMSource source = new DOMSource(document);				
					File file=new File("mdxeditor.defaults.xml");
					/*					
					//StreamResult result = new StreamResult(new File(""+file.toURI().toURL()));					
					//StreamResult result = new StreamResult(file.toURI().toURL());
					*/
					java.io.Writer output = new BufferedWriter( new FileWriter(file) );
					StreamResult result = new StreamResult(output);
					transformer.transform(source, result);
				}
				catch (Exception exc)
				{
				    System.out.println("Unable to save defaults: "+exc.getMessage());
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		} 
	}
}
