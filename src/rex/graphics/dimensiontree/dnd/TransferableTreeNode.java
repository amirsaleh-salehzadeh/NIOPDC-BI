package rex.graphics.dimensiontree.dnd;

import java.io.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import javax.swing.tree.*;

import rex.utils.S;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.graphics.TreeElement;
import rex.graphics.filtertree.*;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author o'reilly
 * @version 1.0
 */
// TransferableTreeNode.java
// A Transferable TreePath to be used with Drag & Drop applications.
//


public class TransferableTreeNode implements Transferable {

  public static DataFlavor INTEGER_FLAVOR = new DataFlavor(Integer.class, "Integer");

  DataFlavor flavors[] = { INTEGER_FLAVOR };
  int selectedRow;

  public TransferableTreeNode(int _selectedRow) {
    selectedRow = _selectedRow;
  }

  public synchronized DataFlavor[] getTransferDataFlavors() {
    return flavors;
  }

  public boolean isDataFlavorSupported(DataFlavor flavor) {
    return (flavor.getRepresentationClass() == Integer.class);
  }

  public synchronized Object getTransferData(DataFlavor flavor)
    throws UnsupportedFlavorException, IOException {
    if (isDataFlavorSupported(flavor)) {
       //S.out("TransferableTreeNode -> returning path = " + path + " hashcode=" + path.hashCode());
       //DimensionTreeElement retVal = (DimensionTreeElement)((TreeElement)(path.getPathComponent(path.getPathCount()-1))).getUserObject();
       return (Object) new Integer(selectedRow);
    } else {
       throw new UnsupportedFlavorException(flavor);
    }
  }
}
