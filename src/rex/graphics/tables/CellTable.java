package rex.graphics.tables;

import rex.metadata.*;
import org.w3c.dom.NodeList;

import rex.utils.*;
import rex.metadata.resultelements.Cell;
import javax.swing.JTable;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import javax.swing.table.TableColumnModel;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;
import java.awt.event.ComponentEvent;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import rex.graphics.*;
import rex.graphics.filtertree.*;
import org.w3c.dom.Document;
import rex.xmla.XMLAExecuteProperties;
import rex.xmla.RexXMLAExecuteProperties;
import rex.xmla.XMLAObjectsFactory;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class CellTable extends JTable {
   JPanel northPane2;
   int selCol=-1, selRow=-1;
   CubeExplorer2 cubeExplorer;
   CustomCellRenderer customCellRenderer;
   ExecuteResult result;
   public CellTable(ExecuteResult _result
                    , CubeSlicer _cubeSlicer
                    , boolean showColumnTotalsOn
                    , boolean showRowTotalsOn) {
      // this.setFont(new Font(""));
      result = _result;
      this.setModel(new CellTableModel(_result, _cubeSlicer, showColumnTotalsOn, showRowTotalsOn));
      TableColumnModel colModel = getColumnModel();
      customCellRenderer = new CustomCellRenderer();
      for (int i = 0; i < this.getColumnCount(); i++){
         colModel.getColumn(i).setPreferredWidth(48);
         colModel.getColumn(i).setCellRenderer(customCellRenderer);
      }
      this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      this.addComponentListener(new ResizeWatch());
      this.addMouseListener(new CellTableMouseListener());
      this.setOpaque(false);

   }

//   public CellTable(ExecuteResult _result
//                    , CubeSlicer _cubeSlicer
//                    , JPanel northPane
//                    , boolean showColumnTotalsOn
//                    , boolean showRowTotalsOn) {
//      this(_result, _cubeSlicer, showColumnTotalsOn, showRowTotalsOn);
//      northPane2 = northPane;
//
//   }
   public CellTable(ExecuteResult _result
                    , CubeSlicer _cubeSlicer
                    , CubeExplorer2 _cubeExplorer
                    , boolean showColumnTotalsOn
                    , boolean showRowTotalsOn){
      this(_result, _cubeSlicer, showColumnTotalsOn, showRowTotalsOn);
      cubeExplorer = _cubeExplorer;
   }
   public void setShowRowTotalsOn(boolean showRowTotalsOn){
      ((CellTableModel)this.getModel()).setShowRowTotalsOn(showRowTotalsOn);
      // don't know why, but table loses it's custom renderer after command above,
      // so I have to set it again:
      TableColumnModel colModel = getColumnModel();
      for (int i = 0; i < this.getColumnCount(); i++){
         colModel.getColumn(i).setPreferredWidth(48);
         colModel.getColumn(i).setCellRenderer(customCellRenderer);
      }
   }
   public void setShowColumnTotalsOn(boolean showColumnTotalsOn){
      ((CellTableModel)this.getModel()).setShowColumnTotalsOn(showColumnTotalsOn);
      // don't know why, but table loses it's custom renderer after command above,
      // so I have to set it again:
      TableColumnModel colModel = getColumnModel();
      for (int i = 0; i < this.getColumnCount(); i++){
         colModel.getColumn(i).setPreferredWidth(48);
         colModel.getColumn(i).setCellRenderer(customCellRenderer);
      }
   }

   public void copyTableToClipboard(){
      StringBuffer sbf=new StringBuffer();
      int headerCount1;
      for (int top=0; top < result.getAxis("Axis0").getHierarchyInfoCount(); top++){
         for (headerCount1 = 0; headerCount1 < result.getAxis("Axis1").getHierarchyInfoCount(); headerCount1++)
            sbf.append("\t");
         for (int tc = 0; tc < result.getAxis("Axis0").getTupleCount(); tc++){
            sbf.append(result.getAxis("Axis0").getTupleAt(tc).getMemberAt(top).getCaption());
            if (tc < result.getAxis("Axis0").getTupleCount()-1) sbf.append("\t");
         }
         sbf.append("\n");
      }

      for (int i=0; i < result.getAxis("Axis1").getTupleCount(); i++){
         for (headerCount1 = 0; headerCount1 < result.getAxis("Axis1").getHierarchyInfoCount(); headerCount1++){
            sbf.append(result.getAxis("Axis1").getTupleAt(i).getMemberAt(headerCount1).getCaption() + "\t");
         }
         for (int j=0; j < result.getAxis("Axis0").getTupleCount() ;j++){
            sbf.append(this.getValueAt(i,j));
               if (j < this.getColumnCount()-1) sbf.append("\t");
         }
         sbf.append("\n");
      }
      StringSelection stsel  = new StringSelection(sbf.toString());
      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stsel,stsel);
   }

//  public int getRowHeight(){
//     return this.getRowHeight();
//  }
   class ResizeWatch implements ComponentListener{

      public void componentHidden(ComponentEvent e) {}

      public void componentMoved(ComponentEvent e) {}

      public void componentResized(ComponentEvent e) {
//         S.out(
//            "Resize:\n------------------------------------------------------");
//         S.out("ct2.getX()=" + CellTable.this.getX()
//               + " ct2.getY()=" + CellTable.this.getY()
//               + " ct2.getWidth() = " + CellTable.this.getWidth());
//         S.out("northPane2.getX()=" + northPane2.getX()
//               + " northPane2.getY()=" + northPane2.getY()
//               + " northPane2.getWidth()= " + northPane2.getWidth());
//         S.out("column column count = " + CellTable.this.getColumnCount());
//         for (int i = 0; i < northPane2.getComponentCount(); i++)
//            S.out("Component(" + i + ") = " +
//                  northPane2.getComponent(i).getWidth());
//         S.out("---------------------------------------------------------------");
//         for (int j = 0; j < CellTable.this.getColumnCount(); j++) {
//            Rectangle r = CellTable.this.getCellRect(0, j, true);
//            S.out(j + ". r.getWidth()=" + r.getWidth() + " r.getX()=" + r.getX() +
//                  " r.getY()=" + r.getY());
//         }
//
//         for(int r = 0; r < CellTable.this.getRowCount(); r++ ){
//            S.out("row(" + r + ").height = " + CellTable.this.getRowHeight(r));
//         }

      }

      public void componentShown(ComponentEvent e) {}
   }


   class CustomCellRenderer extends JLabel implements TableCellRenderer {
       // This method is called each time a cell in a column
       // using this renderer needs to be rendered.
       CustomCellRenderer(){
          super();
          this.setOpaque(false);
       }
       public Component getTableCellRendererComponent(JTable table, Object value,
               boolean isSelected, boolean hasFocus, int rowIndex, int colIndex) {
           // 'value' is value contained in the cell located at
           // (rowIndex, vColIndex)

//           S.out("my cell renderer");

           if (hasFocus) {
               // this cell is the anchor and the table has the focus
           }

           if (((CellTableModel)CellTable.this.getModel()).isAverageCell(rowIndex, colIndex)){
              this.setOpaque(true);
              this.setBackground(AppColors.CELL_TOTAL_AVERAGE_BACKGROUND_COLOR);
           }else if (((CellTableModel)CellTable.this.getModel()).isSumCell(rowIndex, colIndex)){
              this.setOpaque(true);
              this.setBackground(AppColors.CELL_TOTAL_SUM_BACKGROUND_COLOR);
           }else  if ( (rowIndex % 2) == 0 ){
              this.setOpaque(false);
           }else{
              this.setOpaque(true);
              this.setBackground(AppColors.ODD_ROW_LINES_BACKGROUND);
           }
           if ((rowIndex==selRow) && (colIndex==selCol)){
              this.setBorder(AppColors.CELL_SELECTED_BORDER);
           }else{
              this.setBorder(null);
           }

           // Configure the component with the specified value
           if (value != null){
              setText(value.toString());
           }else{
              setText("");
           }

//           if (isSelected) {
//               // cell (and perhaps other cells) are selected
//               this.setBackground(AppColors.CELL_SELECTED);
//           }

           // Set tool tip if desired
//           setToolTipText((String)value);

           // Since the renderer is a component, return itself
           return this;
       }

       // The following methods override the defaults for performance reasons
       public void validate() {}
       public void revalidate() {}
       protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
       public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
   }
   class CellTableMouseListener extends MouseAdapter{
      //   MOUSE LISTENER STUFF:
      public void mousePressed(MouseEvent e) {
         if (e.getClickCount() == 1) {
            // highlight the hierarchy
            CellTable.this.setSelectedCell();
         }
      }

   }

   void setSelectedCell(){
      selCol = this.getSelectedColumn();
      selRow = this.getSelectedRow();
//      S.out("selRow=" + selRow + "selCol=" + selCol + " value=" + getModel().getValueAt(selRow, selCol));
      if (selCol >= 0 && selRow >= 0){
         this.repaint();
         cubeExplorer.highlightRowsAndColumns(selRow, selCol);
      }
   }





   public static void main(String[] args) {
      ServerMetadata svm = new ServerMetadata("http://localhost/xmla/msxisapi.dll");


      XMLAExecuteProperties properties = XMLAObjectsFactory.newXMLAExecuteProperties();

      properties.setDataSourceInfo("Local Analysis properties");
      properties.setCatalog("Foodmart 2000");


      try{
         Document d = svm.execute("SELECT {[Measures].members * [1997].children} ON COLUMNS, {[Store].[usa].children * [Position].[All Position].children} DIMENSION PROPERTIES [Store].[Store SQFT] ON ROWS FROM [Hr]"
                                                  , properties);
         CubeSlicer cs = new CubeSlicer( (short) 1, (short) 0);
         ExecuteResult r1 = new ExecuteResult(d, null);
         CellTable ct = new CellTable(r1, cs, true, true);

         JFrame frame = new JFrame("Test cell table");
         frame.setBackground(Color.YELLOW);
         frame.getContentPane().add(ct, BorderLayout.CENTER);
         frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
               System.exit(0);
            }
         });
         frame.pack();
         frame.setVisible(true);

      }catch(Exception e){
         //e.printStackTrace();//Commented by Prakash
      }

   }

}
