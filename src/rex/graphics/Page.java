package rex.graphics;

import rex.utils.*;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.dnd.*;
import java.awt.datatransfer.Transferable;
import rex.graphics.dimensiontree.dnd.TreeDragSource;
import java.awt.datatransfer.DataFlavor;
import javax.swing.tree.TreePath;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import javax.swing.JTree;
import java.awt.Color;
import rex.metadata.Query;
import java.awt.Graphics;

import javax.swing.Box;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;


/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 *  This class's name should be changed to Page !!!!
 */

public class Page extends JPanel {
   private DropTarget dt;
   private JTree dimTree;
   private Query q;
   private CubeExplorer2 ce;
   private EmptyResultTable ert;
   private boolean current;
   private boolean cubeExplorerIsDisplayed;
   public Page(JTree _dimTree, Query _q) {
      q = _q;
      dimTree = _dimTree;
      ert = new EmptyResultTable(q, dimTree);
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      this.add(ert);
      cubeExplorerIsDisplayed = false;
      setOpaque(false);
      current = false;
      this.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
   }
   public Page(JTree _dimTree, Query _q, CubeExplorer2 ceToDisplay) {
      q = _q;
      dimTree = _dimTree;
      ce = ceToDisplay;
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      this.add(ce);
      this.add(Box.createVerticalGlue());
      cubeExplorerIsDisplayed = true;
      setOpaque(false);
      current = false;
      this.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
   }

   public static short getAdditionalHeight(){
//  this is height that page adds by displaying borders
      return 13;
   }

   public void refreshDisplay(){
      S.out("current=");
      if (current){
         if (q.canExecute()) {
            if(cubeExplorerIsDisplayed){
            }else{
               S.out("assert: cubeExplorerIsDisplayed=false while q.canExecute()=true !?");
            }
         }else {
            // this happens when users removes last dimension (on some axis) from query:
            // than we have to go back to EmptyResultTable
            if (cubeExplorerIsDisplayed){
               cubeExplorerIsDisplayed = false;
               this.removeAll();
               if (ert == null){
                  ert = new EmptyResultTable(q, dimTree);
               }
               this.add(ert);
            }
            ert.refreshDisplay();
            this.repaint();
            this.revalidate();
         }
      }
   }
   public void paintComponent(Graphics g) {
      S.paintBackground(g, this);
      super.paintComponent(g);
   }
   public void setCurrent(boolean isCurrent){
      current = isCurrent;
   }
   public boolean isCurrent(){
      return current;
   }
   public void setCubeExplorer(CubeExplorer2 ceToDisplay){
      ce = ceToDisplay;
      cubeExplorerIsDisplayed = true;
      this.removeAll();
      this.add(ce);
      this.add(Box.createVerticalGlue());
   }
   public CubeExplorer2 getCubeExplorer(){
      return ce;
   }



}
