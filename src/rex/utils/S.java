package rex.utils;

import java.awt.Graphics;
import java.awt.Component;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.net.URL;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;


/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class S {
   private static boolean DEBUG=true;
   /**
    * Line inserted by Prakash
    * Setting Logger for S.
    */
   static Logger logger=Logger.getLogger(S.class);
   public S() {
   }
   public static final void out(String txtToPrintOut){
      if (DEBUG) 
      {
      	
      	/**
      	 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
      	 *   All Rights Reserved
      	 *   Copyright (C) 2006 Igor Mekterovic
      	 *   All Rights Reserved
      	 */
      	
      	/**
      	 * Line Inserted by Prakash.
      	 * Log debug information in logger.
      	 */
      	logger.debug(txtToPrintOut);
      }
   }
   public static void reportError(Exception e){
       reportError(null, e);
   }
   public static void reportError(Component panel, Exception e){
   	
   	/**
   	 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
   	 *   All Rights Reserved
   	 *   Copyright (C) 2006 Igor Mekterovic
   	 *   All Rights Reserved
   	 */
   	
	/**
  	 * Line Inserted by Prakash.
  	 * Log Error information in logger.
  	 */   	
   	logger.error(e.getMessage());
      JOptionPane.showMessageDialog(panel
                                    , "Error:" + e.getMessage()
                                    , "Sorry, error occured"
                                    , JOptionPane.ERROR_MESSAGE);
   }
   public static void reportError(Component panel, String e){
   	
   	/**
   	 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
   	 *   All Rights Reserved
   	 *   Copyright (C) 2006 Igor Mekterovic
   	 *   All Rights Reserved
   	 */
   	
	/**
  	 * Line Inserted by Prakash.
  	 * Log Error information in logger.
  	 */   	
   	logger.error(e);
      JOptionPane.showMessageDialog(panel
                                    , "Error:" + e
                                    , "Sorry, error occured"
                                    , JOptionPane.ERROR_MESSAGE);
   }
   public static void paintBackground(Graphics g, Component panel) {
//      out("painting component= " + panel);
      double red, redStep, green, greenStep;
      red   = 255;
      green = 255;
      redStep   = (255. - AppColors.PANEL_BACKGROUND_RED_TO)/panel.getHeight();
      greenStep = (255. - AppColors.PANEL_BACKGROUND_GREEN_TO)/panel.getHeight();
      for (int y = 0; y < panel.getHeight(); y++) {
         g.setColor(new Color( (int) red, (int) green, AppColors.PANEL_BACKGROUND_BLUE));
         red -= redStep;
         green -= greenStep;
         g.drawLine(0, y, panel.getWidth(), y);
      }
   }
 
   public static void paintWhiteBackground(Graphics g, Component panel) {
    g.setColor(new Color( 255,255,255));
  for (int y = 0; y < panel.getHeight(); y++) {
     g.drawLine(0, y, panel.getWidth(), y);
  }
}
   
   public static void paintBackgroundGreen(Graphics g, Component panel) {
//      out("painting component= " + panel);
      double red, redStep, blue, blueStep;
      red   = 255;
      blue = 255;
      redStep   = (255. - AppColors.PANEL_BACKGROUND_RED_TO)/panel.getHeight();
      blueStep = (255. - AppColors.PANEL_BACKGROUND_RED_TO)/panel.getHeight();
      for (int y = 0; y < panel.getHeight(); y++) {
         g.setColor(new Color( (int) red,  AppColors.PANEL_BACKGROUND_BLUE, (int) blue));
         red -= redStep;
         blue -= blueStep;
         g.drawLine(0, y, panel.getWidth(), y);
      }
   }
   public static void paintBackgroundHorizontal(Graphics g, Component panel) {
      double red, redStep, green, greenStep, blue, blueStep;
      red   = 255;
      green = 255;
      blue  = 255;
      redStep   = (255. - AppColors.TOOLBAR_BACKGROUND_RED_TO)/panel.getWidth()*2;
      greenStep = (255. - AppColors.TOOLBAR_BACKGROUND_GREEN_TO)/panel.getWidth()*2;
      for (int x = 0; x < panel.getWidth(); x++) {
         g.setColor(new Color( (int) red, (int) green, AppColors.PANEL_BACKGROUND_BLUE));
         red -= redStep;
         green -= greenStep;
         g.drawLine(x, 0, x, panel.getHeight());
      }
   }
//   public static void paintBackgroundHorizontal(Graphics g, Component panel) {
//      double red, redStep, green, greenStep;
//      red   = AppColors.TOOLBAR_BACKGROUND_RED_FROM * 1.1;
//      green = AppColors.TOOLBAR_BACKGROUND_GREEN_FROM * 1.1;
//      redStep   = (255. - AppColors.TOOLBAR_BACKGROUND_RED_TO)/panel.getWidth();
//      greenStep = (255. - AppColors.TOOLBAR_BACKGROUND_GREEN_TO)/panel.getWidth();
//      for (int x = 0; x < panel.getWidth(); x++) {
//         g.setColor(new Color( (int) red, (int) green, AppColors.PANEL_BACKGROUND_BLUE));
//         red -= redStep;
//         green -= greenStep;
//         g.drawLine(x, 0, x, panel.getHeight());
//      }
//   }
   public static ImageIcon getAppIcon(String filename) {
      ImageIcon icon = null;
      URL url = ClassLoader.getSystemResource("rex/images/" + filename);
    //  return null;
//      out(url.toString());

      if (url != null) {
         icon = new ImageIcon(url);
      }
      return icon;

   }

}
