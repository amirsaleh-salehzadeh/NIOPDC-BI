package rex;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import rex.graphics.datasourcetree.DataSourceTree;
import rex.graphics.mdxeditor.jsp.ReadEnv;
import javax.swing.JPanel;
/**	
 * Commented to avoid unused imports 
 * by Prakash. 08-05-2007.
 */
//import javax.swing.ImageIcon;

import rex.utils.*;
import rex.graphics.mdxeditor.mdxbuilder.dnd.DragElement;
/**	
 * Commented to avoid unused imports 
 * by Prakash. 08-05-2007.
 */
//import rex.utils.WaitCursorEventQueue;
/**	
 * Commented to avoid unused imports 
 * by Prakash. 08-05-2007.
 */
//import java.awt.EventQueue;
/**	
 * Commented to avoid unused imports 
 * by Prakash. 08-05-2007.
 */
//import java.awt.Toolkit;
import rex.graphics.RexTabbedPane;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Dimension;
/**	
 * Commented to avoid unused imports 
 * by Prakash. 08-05-2007.
 */
//import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
/**	
 * Commented to avoid unused imports 
 * by Prakash. 08-05-2007.
 */
//import javax.swing.BorderFactory;
/**	
 * Commented to avoid unused imports 
 * by Prakash. 08-05-2007.
 */
//import javax.swing.UIManager;
import org.apache.log4j.PropertyConfigurator;

import java.util.Locale;
/**	
 * Commented to avoid unused imports 
 * by Prakash. 08-05-2007.
 */
//import java.util.ResourceBundle;
/**	
 * Commented to avoid duplicate imports 
 * by Prakash. 08-05-2007.
 */
//import rex.utils.*;
import java.io.*;
import java.util.Properties;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */
/**
 * Copyright (C) 2006 CINCOM SYSTEMS, INC.
 * All Rights Reserved
 * Copyright (C) 2006 Igor Mekterovic
 * All Rights Reserved
 */ 
 /*  Added LanguageChangedListener to implement I18n  */
public class WarehouseExplorer extends JPanel implements LanguageChangedListener{ 
    /* declared variables to help I18n*/
    String strHelpLabel="";
    String version="0.7"; // Rex Version
    String strLanguage="";
    String strCountry="";
    /* end of moddification */
    int idx=0;
     public JLabel helpLabel= new JLabel(strHelpLabel);
     JTabbedPane tabbedPane = new RexTabbedPane();
     
    
   public WarehouseExplorer() {
   	super();
        
      // localize(); //hard-coded rex.properties file location... need to remove that.
        I18n.addOnLanguageChangedListener( this );
        applyI18n();
      
      // ImageIcon icon = new ImageIcon("images/middle.gif");
      
      


      JPanel helpPanel = new JPanel();
      helpPanel.setLayout(new BoxLayout(helpPanel, BoxLayout.Y_AXIS));
      /**
       * Commented by prakash
       * To Remove dinosaur Image.
       * 3rd Aug 2006
       */
      //helpPanel.add(new JLabel(S.getAppIcon("rex.gif")));
      helpPanel.add(Box.createRigidArea(new Dimension(50,50)));
      helpPanel.add(helpLabel);
            
      helpPanel.setOpaque(false);      
//      helpPanel.setBorder(BorderFactory.createRaisedBevelBorder());

      JPanel treeAndHelp = new JPanel(){
         {setOpaque(false);}
         public void paintComponent(Graphics g) {
            S.paintBackground(g, this);
            super.paintComponent(g);
         }

      };
      treeAndHelp.setLayout(new BoxLayout(treeAndHelp, BoxLayout.X_AXIS));
      treeAndHelp.add(new DataSourceTree(tabbedPane));
//      treeAndHelp.add(Box.createRigidArea(new Dimension(20,0)));
      treeAndHelp.add(helpPanel);
//      treeAndHelp.setBackground(Color.WHITE);
      
        /* pick the titles from the locale */
        tabbedPane.addTab( I18n.getString("tabTitle.dataSources")
                        , null
                        , treeAndHelp
                        , I18n.getString("toolTip.dataSource"));

      tabbedPane.setSelectedIndex(0);
      ((RexTabbedPane)tabbedPane).setIconAnimationOff();

      //Add the tabbed pane to this panel.
      setLayout(new GridLayout(1, 1));
      add(tabbedPane);
   }


/**
 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
 *   All Rights Reserved
 *   Copyright (C) 2006 Igor Mekterovic
 *   All Rights Reserved
 *   
 */
   /**
    * Helper method that is executed when the language is changed
    */
    public void languageChanged(LanguageChangedEvent evt) {
        this.applyI18n();
    }
    
    public static Locale lastLocale = null;

    /**
     *  Helper method to implement localization when language is changed
     */
    public void applyI18n(){
         if (lastLocale != null && 
                 lastLocale == I18n.getCurrentLocale()) {
             return;
         }
         lastLocale = I18n.getCurrentLocale();
      
        strHelpLabel= I18n.getString("label.help1")+ version;
        strHelpLabel+=I18n.getString("label.help2");
        strHelpLabel+=I18n.getString("label.help3");
        strHelpLabel+=I18n.getString("label.help4");
        strHelpLabel+=I18n.getString("label.help5");
        strHelpLabel+=I18n.getString("label.help6");
        strHelpLabel+=I18n.getString("label.help7");
        strHelpLabel+=I18n.getString("label.help8");
        helpLabel.setText(strHelpLabel);
    }
    /* end of modification for I18n */
    
    /**
     * Reads the language property from rex.properties file.
     * If the value is not found then uses the default locale value.
     */
    public void localize(){
        String file=System.getProperty("user.home")+ File.separator + "rex.properties";
        Properties rexProp = new Properties();
        try{

            rexProp.load(new FileInputStream(new File(file)));
            
        }catch(Exception e){
            S.out("Error reading rex.properties file");            
        }
        strLanguage= rexProp.getProperty("Language");
         
        if (strLanguage!=""){
            Locale oLang= new Locale(strLanguage);
            I18n.setCurrentLocale(oLang);
        }
        else{
            I18n.setCurrentLocale(Locale.getDefault()); //set default locale
        }

    }
     /* sbalda */


    
    
  


   public static void main(String[] args) {
    
        JFrame frame = new JFrame("Warehouse Explorer");
        
        frame.setGlassPane(new DragElement());
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        /**
         *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
         *   All Rights Reserved
         *   Copyright (C) 2006 Igor Mekterovic
         *   All Rights Reserved
         */
        /**
         * By Prakash 21st July.
         * Setting up logger properties. 
         */	
        try
		{
        	new LogPropertyGenerator();
        	PropertyConfigurator.configure((ReadEnv.getEnvVars()).getProperty("USERPROFILE")+"\\rex.properties");
        	RexDefaultProperties.createDefaultProperties();
		}
        catch(Exception exception)
		{
        	S.reportError(exception);
		}
        /**	
         * Commented to keep swing's default look and feel. 
         * by Prakash. 08-05-2007.
         */
        	/**	
        	 *	For getting System Look and Feel  
        	 */
        		/**
        		 try{

        		//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//Commented By Prakash
        		 }catch(Exception e){
        		 S.reportError(e);
        		 }
        		 */        
        /*
         * End of the modification.
         */
        	
        frame.getContentPane().add(new WarehouseExplorer()
                                   ,   BorderLayout.CENTER);
        
        /**	Lines added by Prakash 
	        *	Cincom Systems  
	        *	5 May 2006
	        *	For setting Frame size equivalent to Systems window size.   
	        */        
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
		Rectangle bounds = env.getMaximumWindowBounds();		
		/**
		 * Commented by Prakash.
		 */
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setSize((int)(bounds.getWidth()),(int)(bounds.getHeight()));
		//End of addition by Prakash
		
        frame.setVisible(true);

        // this doesn't work right!?
//        EventQueue waitQueue = new WaitCursorEventQueue(2500);
//        Toolkit.getDefaultToolkit().getSystemEventQueue().push(waitQueue);
    }


}
