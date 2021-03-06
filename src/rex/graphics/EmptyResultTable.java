package rex.graphics;

import rex.utils.S;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import java.awt.Color;

// for dnd:
import java.awt.dnd.*;
import java.awt.datatransfer.Transferable;
import rex.graphics.dimensiontree.dnd.TreeDragSource;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTargetListener;
import rex.metadata.Query;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.metadata.QueryElement;
import rex.graphics.dimensiontree.*;
import rex.utils.*;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
 /*  Added LanguageChangedListener to implement I18n  */

public class EmptyResultTable extends JPanel 
        implements LanguageChangedListener{
   JLabel columns, rows, measures;
   DropTarget dtCol, dtRow, dtMeasures;
   Query q;
   JTree dimTree;
   public EmptyResultTable(Query _q, JTree _dimTree) {
      q = _q;
      dimTree = _dimTree;

 
      /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 

      /* implementing localization */
       columns = new DNDJLabel(I18n.getString("label.dropColumns") ,
               DndLabelType.DND_LABEL_TYPE_COLUMN);
      rows    = new DNDJLabel(I18n.getString("label.dropRows"),
              DndLabelType.DND_LABEL_TYPE_ROW);
      measures= new DNDJLabel(I18n.getString("label.dropMeasures"),
              DndLabelType.DND_LABEL_TYPE_MEASURE);
      /* end of modification for I18n */
  
      Dimension prefSize = new Dimension(200, 20);
      columns.setPreferredSize(prefSize);
      rows.setPreferredSize(prefSize);
      measures.setPreferredSize(prefSize);
      columns.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
      rows.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
      measures.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

      this.setLayout(new GridLayout(2,2));
      //this.setBackground(Color.WHITE);
      this.setOpaque(false);
      this.add(Box.createRigidArea(prefSize));
      this.add(columns);
      this.add(rows);
      this.add(measures);
      dtRow = new DropTarget(rows, (DropTargetListener)rows);
      dtCol = new DropTarget(columns, (DropTargetListener)columns);
      dtMeasures = new DropTarget(measures, (DropTargetListener)measures);
        /* implementing localization */
      I18n.addOnLanguageChangedListener(this);
      applyI18n();
        /* end of modification for I18n */

   }

   public void refreshDisplay(){
      columns.setText(q.getCaptionForColumns());
      rows.setText(q.getCaptionForRows());
      measures.setText(q.getCaptionForMeasures());
   }


   public interface DndLabelType{
      int   DND_LABEL_TYPE_ROW = 0
          , DND_LABEL_TYPE_COLUMN = 1
          , DND_LABEL_TYPE_MEASURE= 2;
   }
   class DNDJLabel extends JLabel implements DropTargetListener{
      // dodao igor:
      int dndLabelType;
      public DNDJLabel(String s, int _dndLabelType){
         super(s);
         this.setBackground(Color.WHITE);
         dndLabelType =_dndLabelType;
      }
      public void dragEnter(DropTargetDragEvent dtde) {
//         System.out.println("Drag Enter");
      }

      public void dragExit(DropTargetEvent dte) {
//         System.out.println("Source: " + dte.getSource());
//         System.out.println("Drag Exit");
      }

      public void dragOver(DropTargetDragEvent dtde) {
         //System.out.println("Drag Over");
      }

      public void dropActionChanged(DropTargetDragEvent dtde) {
//         System.out.println("Drop Action Changed");
      }

      public void drop(DropTargetDropEvent dtde) {
         try {
            // Ok, get the dropped object and try to figure out what it is
            Transferable tr = dtde.getTransferable();
            DataFlavor[] flavors = tr.getTransferDataFlavors();
            String s;
            for (int i = 0; i < flavors.length; i++) {
//               System.out.println("Possible flavor: " + flavors[i].getMimeType());
               // Check for file lists specifically
               if (flavors[i].isFlavorSerializedObjectType()
                   && (tr.getTransferData(flavors[i])instanceof Integer)) {

                  dtde.acceptDrop(DnDConstants.ACTION_COPY);

                  Integer row = (Integer) tr.getTransferData(flavors[i]);
//                  S.out("dte:1:" + row.intValue() + " hashcode = " +
//                        row.hashCode());
                  TreePath tp = dimTree.getPathForRow(row.intValue());
                  DimensionTreeElement droppedElement = (DimensionTreeElement) ( (TreeElement) (tp.getPathComponent(tp.getPathCount() - 1))).getUserObject();
//                  S.out("" + this.getText() + " = " + row);
//                  s = droppedElement.getUniqueName();
                  if (droppedElement != null){
                     switch(dndLabelType){
                        case DndLabelType.DND_LABEL_TYPE_COLUMN:
                           EmptyResultTable.this.q.addToColumns((QueryElement)droppedElement);
                           this.setText(EmptyResultTable.this.q.getCaptionForColumns());
                           break;
                        case DndLabelType.DND_LABEL_TYPE_ROW:
                           EmptyResultTable.this.q.addToRows((QueryElement)droppedElement);
                           this.setText(EmptyResultTable.this.q.getCaptionForRows());
                           break;
                        case DndLabelType.DND_LABEL_TYPE_MEASURE:
                           EmptyResultTable.this.q.addToMeasures((QueryElement)droppedElement);
                           this.setText(EmptyResultTable.this.q.getCaptionForMeasures());
                           break;
                     }
                     //droppedElement.setEnabled(false);
                     ((DimensionTreeModel)dimTree.getModel()).disableTreeElements(((QueryElement)droppedElement));
                     dimTree.repaint();
                  }

                  dtde.dropComplete(true);
                  return;
               }
            }
            // Hmm, the user must not have dropped a file list
            System.out.println("Drop failed: " + dtde);
            dtde.rejectDrop();
            dtde.dropComplete(false);
         }
         catch (Exception e) {
            //e.printStackTrace();//By Prakash.
            dtde.rejectDrop();
            dtde.dropComplete(false);
         }
      }

   }
    /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
 /**
  * Helper method that is executed when the language is changed
  */

    public void languageChanged(LanguageChangedEvent evt) {
        this.applyI18n();
    }
  /**
  *  Helper method to implement locatization when language is changed
  */
    public void applyI18n(){
      columns.setText(I18n.getString("label.dropColumns") );
      rows.setText(I18n.getString("label.dropRows"));
      measures.setText(I18n.getString("label.dropMeasures"));
    }
     /* end of modification for I18n */

}
