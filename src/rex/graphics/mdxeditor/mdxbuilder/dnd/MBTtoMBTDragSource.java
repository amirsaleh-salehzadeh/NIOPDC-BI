package rex.graphics.mdxeditor.mdxbuilder.dnd;

import java.awt.*;
import java.awt.dnd.*;
import javax.swing.*;
import javax.swing.tree.*;

import rex.graphics.mdxeditor.mdxbuilder.nodes.*;

/**
*  Class that recognizes drag gesture (in MdxBuildertTree) and wraps MBTNode objects into
*  TransferableMdxBuilderTreeNode.
*  @see rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTtoMBTDragSource implements DragSourceListener, DragGestureListener{

   DragSource source;
   DragGestureRecognizer recognizer;
   TransferableMdxBuilderTreeNode transferable;
   DefaultMutableTreeNode oldNode;
   JTree sourceTree;

   public MBTtoMBTDragSource(JTree tree, int actions) {
     sourceTree = tree;
     source = new DragSource();
     recognizer = source.createDefaultDragGestureRecognizer(
                                                             sourceTree
                                                           , actions
                                                           , this
                                                            );
   }

   /**
    * Wraps MBTNode objects into TransferableMdxBuilderTreeNode.
    * @see rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode
    * @see rex.graphics.mdxeditor.mdxbuilder.nodes.MBTNode
    * @param dge DragGestureEvent
    */
   public void dragGestureRecognized(DragGestureEvent dge) {
      TreePath path = sourceTree.getSelectionPath();
      if ((path != null)
         && (path.getPathCount() > 1) // We can't move the root node
         && (   ((((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject()) instanceof MBTNode) )
         ) {
         oldNode = (DefaultMutableTreeNode) path.getLastPathComponent();
         MBTNode userObject = (MBTNode) oldNode.getUserObject();
         if (userObject instanceof MBTFunctionNode){
            transferable = new TransferableMdxBuilderTreeNode((MBTFunctionNode)userObject);
         } else {
            transferable = new TransferableMdxBuilderTreeNode(userObject);
         }
//         S.out("classname = " + userObject.getClass().getName());

         source.startDrag(   dge
                           , DragSource.DefaultCopyDrop
                           , (userObject.getIcon() == null) ? null : userObject.getIcon().getImage()
                           , new Point(10, 10)
                           , transferable
                           , this);
//        S.out("dragGestureRecognized-> path = " + path);
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
//      System.out.println("Action: " + dsde.getDropAction());
//      System.out.println("Target Action: " + dsde.getTargetActions());
//      System.out.println("User Action: " + dsde.getUserAction());

   }

   /**
    * Drag Event Handler. Does nothing.
    * @param dsde DragSourceDropEvent
    */
   public void dragDropEnd(DragSourceDropEvent dsde) {
//     S.out("Drop Action: " + dsde.getDropAction()
//     + "    dsde.getDropSuccess()=" +     dsde.getDropSuccess());

  }
}
