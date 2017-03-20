package rex.graphics.mdxeditor.mdxbuilder.dnd;

import java.awt.datatransfer.*;
import java.awt.dnd.*;

import rex.graphics.dimensiontree.elements.*;
import rex.graphics.mdxeditor.mdxbuilder.*;
import rex.graphics.mdxeditor.mdxbuilder.nodes.*;
import rex.graphics.mdxeditor.mdxfunctions.*;
import rex.utils.*;

/**
 * This is a tree drop listener. It listens to the drop events and does two things:
* <br>- listens and decides whether to accept drop (by calling the MBTNode's: <code> public boolean acceptDrop(DataFlavor flavor))</code>
* <br>- handles drop by forwarding the data to MBTNode's  <code> public void handleDrop(  Object droppedData
                          , DefaultMutableTreeNode containerNode
                          , DefaultTreeModel treeModel);</code>
* @see rex.graphics.mdxeditor.mdxbuilder.nodes.MBTNode
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MdBuilderTreeDropListener implements DropTargetListener{

   MdxBuilderTree mdxBuilderTree;
   public MdBuilderTreeDropListener(MdxBuilderTree _mdxBuilderTree) {
       mdxBuilderTree = _mdxBuilderTree;
   }
   /**
    * Decides wether to accept drag.
    * @param dtde DropTargetDragEvent
    */
   public void dragEnter(DropTargetDragEvent dtde) {
      if (! acceptDrop(dtde)) {
         dtde.rejectDrag();
         return;
      }
      dtde.acceptDrag(dtde.getDropAction());

   }

   /**
    * Handler. Does nothing.
    * @param dte DropTargetEvent
    */
   public void dragExit(DropTargetEvent dte) {}


   /**
    * Decides wether to accept drag.
    * @param dtde DropTargetDragEvent
    */
   public void dragOver(DropTargetDragEvent dtde) {
      if (!acceptDrop(dtde)) {
         dtde.rejectDrag();
         return;
      }
      dtde.acceptDrag(dtde.getDropAction());
   }


   /**
    * Decides wether drop is acceptable. Used by dragOver and dragEnter handlers.
    * @param dtde DropTargetDragEvent
    * @return boolean
    */
   private boolean acceptDrop(DropTargetDragEvent dtde){
      try {
          // Ok, get the object and try to figure out what it is
         DataFlavor[] flavors = dtde.getCurrentDataFlavors();
         String s;
         for (int i = 0; flavors!=null && i < flavors.length; i++) {
//            S.out("calling acceptDropOnPoint for " + flavors[i].getMimeType());
            if (mdxBuilderTree.acceptDropOnPoint(dtde.getLocation(), flavors[i])) {
               return true;
            }
         }
      } catch (Exception e) {
          /**
           * Commented, Don't want to print trace log on console.
           * by Prakash. 10-05-2007.
           */
          //e.printStackTrace();//Commented by Prakash
          /*
           * End of modification.
           */
      }
      return false;
   }

   /**
    * Handler. Does nothing.
    * @param dtde DropTargetDragEvent
    */
   public void dropActionChanged(DropTargetDragEvent dtde) {}


   /**
    * Handles drop. Forwards the dropped data to appropriate MBTNode.
    * @see rex.graphics.mdxeditor.mdxbuilder.nodes.MBTNode
    * @param dtde DropTargetDropEvent
    */
   public void drop(DropTargetDropEvent dtde) {
       try {
           // Ok, get the dropped object and try to figure out what it is
          Transferable tr = dtde.getTransferable();
          DataFlavor[] flavors = tr.getTransferDataFlavors();
          String s;
          for (int i = 0; i < flavors.length; i++) {
//             System.out.println("\n\nPossible flavor: " + flavors[i].getMimeType()
//                                + "\n data = " + tr.getTransferData(flavors[i])
//                                + "\nisFlavorSerializedObjectType()=" + (flavors[i].isFlavorSerializedObjectType())
//                                + "\nclass = " + tr.getTransferData(flavors[i]).getClass().getName());
//           doesn't have to be:   flavors[i].isFlavorSerializedObjectType() && (because i'm using local JVM dnd)
             if (  (tr.getTransferData(flavors[i]) instanceof DimensionTreeElement)
                || (tr.getTransferData(flavors[i]) instanceof MBTNode)
                || (tr.getTransferData(flavors[i]) instanceof MdxFunction)) {

                dtde.acceptDrop(DnDConstants.ACTION_COPY);

                mdxBuilderTree.handleDropOnPoint(dtde.getLocation(), tr.getTransferData(flavors[i]));
                dtde.dropComplete(true);
                return;
             }
          }
          S.out("Drop failed: " + dtde);
          dtde.rejectDrop();
          dtde.dropComplete(true);
       } catch (Exception e) {
           //e.printStackTrace();//Commented by Prakash
           dtde.rejectDrop();
           dtde.dropComplete(true);
       }
   }

}
