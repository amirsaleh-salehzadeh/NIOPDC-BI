package rex.graphics.mdxeditor;


import java.awt.*;
import javax.swing.*;

import rex.graphics.*;
import rex.utils.*;


/**
 * Class that displays a SINGLE PAGE (more acurrately, that is a single slice, but in this app cubes are always sliced
*  along pages axis and displayed in tabs) of the result of the MDX query.
 *
 * <br>Example: if MDX query has something like <code>...Years.Members on PAGES..</code> it might evaluate to 3 years: 1997, 1998, 1999.
 * <br> Result from that query would be sliced along PAGES axis, at values 1997, 1998, 1999.
 * <br> MdxResultViewer would create 3 CubeExplorer objects sliced at those points
 * <b>and 3 MdxResultPage objects to represent every single slice</b>.
 * <br>Those objects would be displayed in 3 tabs, labeled 1997,1998,1999.
 * @see rex.graphics.mdxeditor.MdxResultViewer
 * @see rex.graphics.CubeExplorer
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MdxResultPage extends JPanel{

   private CubeExplorer2 ce;
   private EmptyMdxResultTable ert;
   private boolean current;
   private boolean cubeExplorerIsDisplayed;


   /**
    *
    */
   public MdxResultPage() {
      ert = new EmptyMdxResultTable();
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      this.add(ert);
      cubeExplorerIsDisplayed = false;
      setOpaque(false);
      current = false;
      this.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
   }
   public MdxResultPage(CubeExplorer2 ceToDisplay) {
      ce = ceToDisplay;
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      this.add(ce);
      this.add(Box.createVerticalGlue());
      cubeExplorerIsDisplayed = true;
      setOpaque(false);
      current = false;
      this.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
   }

   /**
    * Refreshes the display. If there is no CubeExplorer to display, EmptyResultTable is displayed.
    * @see rex.graphics.CubeExplorer
    */
   public void refreshDisplay(){
//      S.out("current=");
      if (current){
         // this happens when users removes last dimension (on some axis) from query:
         // than we have to go back to EmptyResultTable
         if (cubeExplorerIsDisplayed) {
            cubeExplorerIsDisplayed = false;
            this.removeAll();
            if (ert == null) {
               ert = new EmptyMdxResultTable();
            }
            this.add(ert);
         }
//            ert.refreshDisplay();
         this.repaint();
         this.revalidate();
      }
   }
   /**
    * Overrides to paint gradient blue background.
    * @param g Graphics
    */
   public void paintComponent(Graphics g) {
      S.paintBackground(g, this);
      super.paintComponent(g);
   }

   /**
    * Sets the current property (weather this is a currently displayed page) to the specified value.
    * @param isCurrent boolean
    */
   public void setCurrent(boolean isCurrent){
      current = isCurrent;
   }

   /**
    * Returns true if this is a currently displayed page, otherwise false.
    * @return boolean
    */
   public boolean isCurrent(){
      return current;
   }

   /**
    * Sets a CubeExplorer to be displayed.
    * @see rex.graphics.CubeExplorer
    * @param ceToDisplay CubeExplorer
    */
   public void setCubeExplorer(CubeExplorer2 ceToDisplay){
      ce = ceToDisplay;
      cubeExplorerIsDisplayed = true;
      this.removeAll();
      this.add(ce);
      this.add(Box.createVerticalGlue());
   }

   /**
    * Returns CubeExplorer that is used for displaying the results.
    * @return CubeExplorer
    */
   public CubeExplorer2 getCubeExplorer(){
      return ce;
   }

   public static short getAdditionalHeight(){
//  this is height that page adds by displaying borders
      return 13;
   }


}
