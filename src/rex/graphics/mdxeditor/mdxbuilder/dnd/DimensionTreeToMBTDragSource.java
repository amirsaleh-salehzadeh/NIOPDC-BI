package rex.graphics.mdxeditor.mdxbuilder.dnd;

import java.awt.*;
import java.awt.dnd.*;
import javax.swing.*;
import javax.swing.tree.*;
import rex.graphics.*;
import rex.graphics.dimensiontree.elements.*;
import rex.metadata.*;
import rex.utils.S;
import java.awt.RenderingHints;

/**
 * Class that recognizes drag gesture (in DimensionTree) and wraps DimensionTree elements into
*  TransferableMdxBuilderTreeNode.
* @see rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode
 * @author Igor Mekterovic
 * @version 0.3
 */
public class DimensionTreeToMBTDragSource extends JComponent implements DragSourceListener, DragGestureListener , DragSourceMotionListener {

  DragSource source;
  DragGestureRecognizer recognizer;
  TransferableMdxBuilderTreeNode transferable;
  TreeElement oldNode;
  JTree sourceTree;
  
  //By Prakash.
  String uniqueName;
  private int ITEM_WIDTH = 9;
  private int ITEM_HEIGHT = 25;   
  Point p;
  public DimensionTreeToMBTDragSource(JTree tree, int actions) {
    sourceTree = tree;    
    source = new DragSource();
    recognizer = source.createDefaultDragGestureRecognizer(
                                                            sourceTree
                                                          , actions
                                                          , this
                                                           );
    source.addDragSourceMotionListener(this);   // By Prakash.     
  }
  //By Prakash
  public int getItemWidth() {
    return ITEM_WIDTH * uniqueName.length();
  }
  //By Prakash
  public int getItemHeight() {
    return ITEM_HEIGHT;
  }


  /**
   * Drag gesture handler. Wraps dragged data in the TransferableMdxBuilderTreeNode.
   * @see rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode
   * @param dge DragGestureEvent
   */
  public void dragGestureRecognized(DragGestureEvent dge) {
     TreePath path = sourceTree.getSelectionPath();
     if ((path != null)
        && (path.getPathCount() > 1) // We can't move the root node
        && (   ((((TreeElement) path.getLastPathComponent()).getUserObject()) instanceof DimensionElement)
             ||
               ((((TreeElement) path.getLastPathComponent()).getUserObject()) instanceof QueryElement)
            )
        ) {
        oldNode = (TreeElement) path.getLastPathComponent();
        DimensionTreeElement userObject = (DimensionTreeElement) oldNode.getUserObject();

        uniqueName=userObject.getUniqueName();
        
        transferable = new TransferableMdxBuilderTreeNode(userObject);
        //S.out("classname = " + userObject.getClass().getName());
        source.startDrag(   dge
                          , DragSource.DefaultCopyDrop
                          , (userObject.getIcon() == null) ? null : userObject.getIcon().getImage()
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
  }


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

  /**
   * Drag Event Handler. Does nothing.
   * @param dsde DragSourceDragEvent
   */
  public void dragEnter(DragSourceDragEvent dsde) {}

  /**
   * Drag Event Handler. Does nothing.
   * @param dse DragSourceEvent
   */
  public void dragExit(DragSourceEvent dse) {}

  /**
   * Drag Event Handler. Does nothing.
   * @param dsde DragSourceDragEvent
   */
  public void dragOver(DragSourceDragEvent dsde) {}

  /**
   * Drag Event Handler. Does nothing.
   * @param dsde DragSourceDragEvent
   */
  public void dropActionChanged(DragSourceDragEvent dsde) {
//     System.out.println("Action: " + dsde.getDropAction());
//     System.out.println("Target Action: " + dsde.getTargetActions());
//     System.out.println("User Action: " + dsde.getUserAction());

  }


  /**
   * Drag Event Handler. Does nothing.
   * @param dsde DragSourceDropEvent
   */
  public void dragDropEnd(DragSourceDropEvent dsde) {
//    S.out("Drop Action: " + dsde.getDropAction()
//    + "    dsde.getDropSuccess()=" +     dsde.getDropSuccess());
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
  }
}

