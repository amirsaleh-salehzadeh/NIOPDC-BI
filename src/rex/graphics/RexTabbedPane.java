package rex.graphics;

import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import rex.utils.S;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import rex.utils.I18n; 
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class RexTabbedPane extends JTabbedPane implements ActionListener {
   private static ImageIcon mdxViewerIcon
                         ,  spinIcon
                         ,  dontSpinIcon;
   static{
      mdxViewerIcon = S.getAppIcon("mdx_viewer.gif");
      spinIcon      = S.getAppIcon("earth2.gif");
      dontSpinIcon  = S.getAppIcon("earth.gif");
   }
   private boolean animationOn;

   public RexTabbedPane(){
      this.addMouseListener(new RexTabbedPanePopupListener(this));
   }

   public void setIconAnimationOn(){
      this.setIconAt(0, spinIcon);
      animationOn = true;
   }
   public void setIconAnimationOff(){
      this.setIconAt(0, dontSpinIcon);
      animationOn = false;
   }
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 

   /* implementing localization */
   public String[] getAnimationPopUpList(){
      if (animationOn){
          return new String[] {I18n.getString("str.stopSpinning")};          
      }else{    
          return new String[] {I18n.getString("str.spinAway")};
      }
   }
  /* end of modification for I18n */

   public void addMDXViewer(MDXViewer viewerToAdd, String cubeName){
      this.addTab(cubeName, mdxViewerIcon, viewerToAdd);
   }

   public void removeMDXViewer(MDXViewer viewerToRemove){
      for(int i=0; i<this.getComponentCount(); i++){
         if (this.getComponentAt(i) == viewerToRemove){
            this.remove(i);
            return;
         }
      }
   }

   public void closeViewer(Viewer viewerToClose){
      for(int i=0; i<this.getComponentCount(); i++){
         if (this.getComponentAt(i) == viewerToClose){
            this.remove(i);
            return;
         }
      }
   }
   public void actionPerformed(ActionEvent e){
      int i = 0;

   // for the time being all that can happen here is toggling of the animation state
      if (animationOn) {
         this.setIconAt(0, dontSpinIcon);
         animationOn = false;
      }else{
         this.setIconAt(0, spinIcon);
         animationOn = true;
      }

   }



   class RexTabbedPanePopupListener extends MouseAdapter {
      JTabbedPane jtp;
      JPopupMenu popup;
      RexTabbedPanePopupListener(JTabbedPane _jtp){
         jtp = _jtp;
         popup = new JPopupMenu();
      }
      public void mousePressed(MouseEvent e) {
         maybeShowPopup(e);
      }

      public void mouseReleased(MouseEvent e) {
         maybeShowPopup(e);
      }
      public void mouseClicked(MouseEvent e) {

      }


      private void maybeShowPopup(MouseEvent e) {
         if(jtp.getSelectedIndex() == 0 ) {
            if (e.isPopupTrigger()) {
               String[] al;
               JMenuItem menuItem;

               popup.removeAll();

               al = ((RexTabbedPane) jtp).getAnimationPopUpList();
               for (int i=0; al != null && i < al.length; i++){
                  menuItem = new JMenuItem(al[i]);
                  menuItem.addActionListener((RexTabbedPane) jtp);
                  popup.add(menuItem);
               }
               popup.show(e.getComponent()
                          , e.getX()
                          , e.getY());
            }
         }
      }
   }



}
