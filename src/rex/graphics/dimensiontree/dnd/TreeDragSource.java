package rex.graphics.dimensiontree.dnd;

import rex.utils.S;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

// TreeDragSource.java
// A drag source wrapper for a JTree.  This class can be used to make
// a rearrangeable DnD tree with the TransferableTreeNode class as the
// transfer data type.
//

import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.graphics.TreeElement;
import rex.metadata.QueryElement;
import rex.graphics.filtertree.*;
import rex.graphics.mdxeditor.mdxbuilder.dnd.DragElement;

public class TreeDragSource extends JComponent implements DragSourceListener, DragGestureListener, DragSourceMotionListener {

  DragSource source;
  DragGestureRecognizer recognizer;
  TransferableTreeNode transferable;
  TreeElement oldNode;
  JTree sourceTree;

//By Prakash.
  String uniqueName;
  private int ITEM_WIDTH = 9;
  private int ITEM_HEIGHT = 25;   
  Point p;
  
  public TreeDragSource(JTree tree, int actions) {
    sourceTree = tree;
    source = new DragSource();
    recognizer = source.createDefaultDragGestureRecognizer(
                 sourceTree, actions, this);
    source.addDragSourceMotionListener(this);   // By Prakash.  
  }

  //	Image image = (Image) ((JList)c).getSelectedValue();
  //	return new ImageTransferable(image);

//By Prakash
  public int getItemWidth() {
    return ITEM_WIDTH * uniqueName.length();
  }
  //By Prakash
  public int getItemHeight() {
    return ITEM_HEIGHT;
  }
  
  /*
   * Drag Gesture Handler
   */
  public void dragGestureRecognized(DragGestureEvent dge) {
    TreePath path = sourceTree.getSelectionPath();
    if ((path == null)
        || (path.getPathCount() <= 1)
        || !(((DimensionTreeElement)(((TreeElement)path.getLastPathComponent()).getUserObject())).isEnabled())
        || !((((TreeElement)path.getLastPathComponent()).getUserObject()) instanceof QueryElement )) {
      // We can't move the root node
      // or an empty selection
      // or disabled nodes - the ones that already been dragged, i.e. they are already included in the query
      // or nodes that aren't QueryElements
      return;
    }
    oldNode = (TreeElement)path.getLastPathComponent();
    transferable = new TransferableTreeNode(sourceTree.getRowForPath(path));

    uniqueName=((DimensionTreeElement) oldNode.getUserObject()).getUniqueName();//By Prakash
    
    source.startDrag(dge
                     , DragSource.DefaultCopyDrop
                     , ((DimensionTreeElement)oldNode.getUserObject()).getIcon().getImage()
                     , new Point(10, 10)
                     , transferable
                     , this);
    
    //By Prakash
    DragElement glassPane = (DragElement) SwingUtilities.getRootPane(sourceTree).getGlassPane();
    glassPane.setVisible(true);
    Point point = (Point) dge.getDragOrigin().clone();
    SwingUtilities.convertPointToScreen(point, this);
    SwingUtilities.convertPointFromScreen(point, glassPane);
    glassPane.setPoint(point);
    glassPane.setImage(uniqueName,getItemHeight(),getItemWidth());
    glassPane.showString(true);
    glassPane.repaint();
    //End
  }

  
  /*
   * Drag Event Handlers
   */
//By Prakash.
  public void dragMouseMoved(DragSourceDragEvent dsde) {
  	try
  	{
          DragElement glassPane = (DragElement) SwingUtilities.getRootPane(sourceTree).getGlassPane();
          p = (Point) dsde.getLocation().clone();
          SwingUtilities.convertPointFromScreen(p, glassPane);
          glassPane.setPoint(p);
          glassPane.showString(true);
          glassPane.repaint();
  	}
  	catch(ClassCastException cce)
  	{
  		S.out(cce.getMessage());
  	}
  }
//  End
  public void dragEnter(DragSourceDragEvent dsde) { }
  public void dragExit(DragSourceEvent dse) { }
  public void dragOver(DragSourceDragEvent dsde) { }
  public void dropActionChanged(DragSourceDragEvent dsde) { }
  public void dragDropEnd(DragSourceDropEvent dsde) {
    /*
     * to support move or copy, we have to check which occurred:
     */
//    System.out.println("Drop Action: " + dsde.getDropAction()
//                       + "    dsde.getDropSuccess()=" +     dsde.getDropSuccess());
    if (dsde.getDropSuccess() &&
        (dsde.getDropAction() == DnDConstants.ACTION_MOVE)) {
      // ((DefaultTreeModel)sourceTree.getModel()).removeNodeFromParent(oldNode);
      // I SHOULD DISABLE THIS ELEMENT HERE!
    }


    /*
     * to support move only...
    if (dsde.getDropSuccess()) {
      ((DefaultTreeModel)sourceTree.getModel()).removeNodeFromParent(oldNode);
    }
    */
  	//By Prakash
    DragElement glassPane = (DragElement) SwingUtilities.getRootPane(sourceTree).getGlassPane();
    glassPane.setVisible(true);
    //System.out.println("BBB");
    Point point = (Point) dsde.getLocation().clone();
    SwingUtilities.convertPointToScreen(point, this);
    SwingUtilities.convertPointFromScreen(point, glassPane);
    //System.out.println("CCC");
    glassPane.setPoint(point);
    glassPane.showString(false);
    glassPane.repaint();
    //End
  }
}

