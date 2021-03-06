 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  * @author PYadav, SBalda
  */
package rex.utils;


import java.util.*;
import java.util.jar.*;
import java.net.*;
import java.io.*;

public class I18n
{

    public static final String localPackageName = "rex/locale/";
    public static final String baseName = "Rex";
    private static java.util.ResourceBundle oLanguage = null;

    public static java.util.Vector languageChangedListeners = null;

    static
    {

        languageChangedListeners = new Vector();
    }

    public static void addOnLanguageChangedListener(LanguageChangedListener listener)
    {
        languageChangedListeners.add( listener );
    }

    // Default to english
    private static Locale currentLocale = Locale.getDefault();
   

    public static void setCurrentLocale( String language )
    {       
        setCurrentLocale(language, null);
    }

    public static void setCurrentLocale( String language, String country )
    {
        if(language != null && !language.equals(""))
        {
            if(country != null && !country.equals(""))
            {
                setCurrentLocale(new java.util.Locale(language, country));
            }
            else
            {
                setCurrentLocale(new java.util.Locale(language));
            }
        }
        else
        {
            setCurrentLocale(java.util.Locale.getDefault());
        }

    }

    public static void setCurrentLocale( Locale locale )
    {
        try
        {
            currentLocale=locale;
            oLanguage = null;

            Enumeration enum_listeners = languageChangedListeners.elements();
            while (enum_listeners.hasMoreElements())
            {
                ((LanguageChangedListener)(enum_listeners.nextElement())).languageChanged(new LanguageChangedEvent(locale));
            }
        }
        catch (Exception ex)
        {
            setCurrentLocaleEng(new java.util.Locale("en","US"));
        }
    }
    
    public static void setCurrentLocaleEng( Locale locale )
    {
        currentLocale=locale;
        oLanguage = null;

        Enumeration enum_listeners = languageChangedListeners.elements();
        while (enum_listeners.hasMoreElements())
        {
            try
            {
                ((LanguageChangedListener)(enum_listeners.nextElement())).languageChanged(new LanguageChangedEvent(locale));
            }
            catch (Exception ex)
            {}
        }
    }

    public static Locale getCurrentLocale()
    {
        return currentLocale;
    }

    /**
     * Retreive a resource string using the current locale.
     * @param cID The resouce sting identifier
     * @return The locale specific string
     */
    public static String getString(String cID)
    {
        return getString(cID, currentLocale );
    }

    public static String getString(String cID,String defaultValue)
    {
        return getString(cID, currentLocale, defaultValue );
    }

    /**
     * Retreive a resource string using the current locale.
     * @param cID The resouce sting identifier
     * @return The locale specific string
     */
    public static String getFormattedString(String cID, String defaultValue, Object[] args)
    {
        String pattern = getString(cID, getCurrentLocale(), defaultValue );
        java.text.MessageFormat mf = new java.text.MessageFormat(pattern, I18n.getCurrentLocale());
        return mf.format(args);
    }


    private static String getString(String cID, Locale currentLocale)
    {
        if (currentLocale == null)
        {
            currentLocale = Locale.getDefault();
        }
        if(oLanguage == null)
        {
            oLanguage = java.util.ResourceBundle.getBundle( localPackageName + baseName ,
                    currentLocale);
        }

        try {
            return oLanguage.getString(cID);
        } catch (Exception ex)
        {
            return cID;
        }
    }

    public static String getString(String cID, Locale currentLocale, String defaultValue)
    {
        try
        {
            if(oLanguage == null)
            { 
                 oLanguage = java.util.ResourceBundle.getBundle( localPackageName + baseName,
                        currentLocale);

            }
            return oLanguage.getString(cID);
        }
        catch (MissingResourceException ex)
        {
            System.out.println("Can't find the translation for key = " + cID +": using default (" + defaultValue + ")");
        }
        catch (Exception ex)
        {
            System.out.println("Exception loading cID = " + cID +": " + ex.getMessage());
        }
        return defaultValue;
    }

     public static String getCurrentLocaleID()
    {
         return "";
    }
    
      /**
  * Change the contents of text file in its entirety, overwriting any
  * existing text.
  *
  * This style of implementation throws all exceptions to the caller.
  *
  * @param aFile is an existing file which can be written to.
  * @throws IllegalArgumentException if param does not comply.
  * @throws FileNotFoundException if the file does not exist.
  * @throws IOException if problem encountered during write.
  */
  static public void setContents( String aContents)
                                 throws FileNotFoundException, IOException {
      File aFile=null;
      try{
      aFile = new File(rex.graphics.mdxeditor.jsp.ReadEnv.getEnvVars().getProperty("USERPROFILE")+ "\\rex.properties");
      }catch(Exception e){
          //e.printStackTrace();//need to remove this... sbalda
      }
    if (aFile == null) {
      throw new IllegalArgumentException("File should not be null.");
    }
    if (!aFile.exists()) {
      throw new FileNotFoundException ("File does not exist: " + aFile);
    }
    if (!aFile.isFile()) {
      throw new IllegalArgumentException("Should not be a directory: " + aFile);
    }
    if (!aFile.canWrite()) {
      throw new IllegalArgumentException("File cannot be written: " + aFile);
    }

    //declared here only to make visible to finally clause; generic reference
    Writer output = null;
    try {
      //use buffering
      //FileWriter always assumes default encoding is OK!
      output = new BufferedWriter( new FileWriter(aFile) );
    //  output.
      output.write( aContents );
    }
    finally {
      //flush and close both "output" and its underlying FileWriter
      if (output != null)
      {
          output.close();
      }
    }
  }

    
    
    
}
