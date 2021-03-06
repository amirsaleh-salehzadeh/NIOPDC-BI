package rex.graphics.mdxeditor.mdxbuilder.nodes;

import java.awt.datatransfer.*;
import javax.swing.*;
import javax.swing.tree.*;

/**
 * Interface for the nodes that will be stored/contained in the MdxBuilderTree
 * @author Igor Mekterovic
 * @version 0.3
 */
public interface MBTNode {

   /**
    * Returns an array representing children nodes.
    * @return MBTNode[]
    */
   public MBTNode[] getMdxBuilderTreeNodes();

   /**
    * Returns corresponding MDX statement with specified indent.
    * @param indent String
    * @return String
    */
   public String getMdx(String indent);

   /**
    * Adds a child node.
    * @param child MBTNode
    */
   public void addChild(MBTNode child);

   /**
    * Adds a child node at specified index.
    * @param child MBTNode
    * @param index int
    */
   public void addChildAt(MBTNode child, int index);

   /**
    * Removes all children nodes.
    */
   public void removeAllChildren();

   public void removeChildFromParent(MBTNode child);

   /**
    * Returns a String representation. This is displayed in a JTree.
    * @return String
    */
   public String toString();

   /**
    * Returns true if this node accepts the specified DataFlavor.
    * <br>This method checks the mime types that each node sets with <code>setAcceptableFlavorsArray()</code>
    * <br>This is called during d'n'd when a mouse cursor dragging some object is hovering over this node.
    * @param flavor DataFlavor
    * @return boolean
    */
   public boolean acceptDrop(DataFlavor flavor);


   /**
    * Handles a drop action for the specified dropped data, parent(container) node onto which data is dropped, and a treeModel
    * @param droppedData Object
    * @param containerNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public void handleDrop(  Object droppedData
                          , DefaultMutableTreeNode containerNode
                          , DefaultTreeModel treeModel);





   /**
    * Returns an icon to be displayed in a JTree.
    * @return ImageIcon
   */
   public ImageIcon getIcon();

   /**
    * Returns pop-up actions available (on right-click) for this node.
    * @return String[]
    */
   public String[] getPopUpActionList();

   /**
    * Handles selected pop-up action.
    * @param action String
    * @param actionNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public void handlePopUpAction(  String action
                                 , DefaultMutableTreeNode actionNode
                                 , DefaultTreeModel treeModel);

   /**
   * Returns a tooltip.
   * @return String
   */
   public String getToolTip();


   /**
    * Returns true if this is a complete node. All nodes are always complete except function nodes.
    * Function nodes are complete only if all of their non-optional arguments are specified.
    * @return boolean
    */
   public boolean isComplete();

}
