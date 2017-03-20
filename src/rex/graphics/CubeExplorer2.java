package rex.graphics;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import rex.graphics.tables.*;
import rex.metadata.*;
import rex.metadata.resultelements.*;
import rex.utils.*;
import org.w3c.dom.Document;
import rex.xmla.XMLAExecuteProperties;
import rex.xmla.XMLAObjectsFactory;


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
public class CubeExplorer2 extends JPanel 
        implements LanguageChangedListener{
//   JPanel northPane, leftPane;
   CellTable  ct;
   JSplitPane northSplitPane;
   JSplitPane southSplitPane;
   JScrollPane scrollPane;

   double divLoc;
   private ExecuteResult execResult;

   private JPanel columnsPane, columnsResultPane, rowsResultPane;
   private JPanel rowsPane;
   private CubeSlicer cubeSlicer;
   private JLabel btnCopyTable;

   private int cubeExplorerRowNumber;
   private boolean vScrollbarDisplayed;

   public CubeExplorer2(  ExecuteResult _execResult
                       , CubeSlicer _cubeSlicer
                       , boolean showColumnTotalsOn
                       , boolean showRowTotalsOn) {
      execResult = _execResult;
      cubeSlicer = _cubeSlicer;
      ct = new CellTable(execResult, cubeSlicer, this, showColumnTotalsOn, showRowTotalsOn);
      int rh = ct.getRowHeight();
      int rw = ct.getWidth() / ct.getColumnCount();


      columnsPane = new JPanel();
      columnsPane.setLayout(new BoxLayout(columnsPane, BoxLayout.X_AXIS));
      columnsResultPane = execResult.getHorizontalTreePanel("Axis0", rw, rh, showRowTotalsOn);
      columnsPane.add(columnsResultPane);
      columnsPane.add(Box.createHorizontalGlue());
      cubeExplorerRowNumber = execResult.getAxis("Axis0").getHierarchyInfoCount();

      rowsPane = new JPanel();
      rowsPane.setOpaque(false);
      rowsPane.setLayout(new BoxLayout(rowsPane, BoxLayout.Y_AXIS));
      rowsResultPane = execResult.getVerticalTreePanel("Axis1", 40, rh, showColumnTotalsOn);
      rowsResultPane.setMaximumSize(new Dimension(Short.MAX_VALUE, execResult.getAxis("Axis1").getTupleCount() * rh));
      rowsPane.add(rowsResultPane);
      rowsPane.add(Box.createVerticalGlue());

      cubeExplorerRowNumber += execResult.getAxis("Axis1").getTupleCount();
      if (showColumnTotalsOn) cubeExplorerRowNumber += 2;


      btnCopyTable = new JLabel(S.getAppIcon("copy.gif"));
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 

      /* implementing localization */
      btnCopyTable.setToolTipText(I18n.getString("toolTip.copyTable"));
      Dimension prefSize = new Dimension(execResult.getAxis("Axis1").getHierarchyInfoCount() * 40, execResult.getAxis("Axis0").getHierarchyInfoCount() * rh );
      btnCopyTable.setMinimumSize(prefSize);
      btnCopyTable.setMaximumSize(prefSize);
      btnCopyTable.setPreferredSize(prefSize);
      btnCopyTable.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            CubeExplorer2.this.copyTableToClipboard();
         }
      });

//      leftPane.add(Box.createRigidArea(new Dimension(1, execResult.getAxis("Axis0").getHierarchyInfoCount() * rh )));
//      leftPane.add(btnCopyTable);


      northSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, btnCopyTable, columnsPane){
       //  MUST FIX THIS: THIS IS NOT GOOD
       //  JScrollPane should be simply transparent, but I can't get setOpaque(false) to do the trick!
       //  so I should deal with this later...
         {
            setOpaque(false);
         }

         public void paintComponent(Graphics g) {
            S.paintBackground(g, this);
            super.paintComponent(g);
         }
      };
      northSplitPane.setContinuousLayout(false);
      northSplitPane.setOneTouchExpandable(true);
      northSplitPane.setDividerSize(3);
      northSplitPane.setOpaque(false);



      southSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, rowsPane, ct){
       //  MUST FIX THIS: THIS IS NOT GOOD
       //  JScrollPane should be simply transparent, but I can't get setOpaque(false) to do the trick!
       //  so I should deal with this later...
         {
            setOpaque(false);
         }

         public void paintComponent(Graphics g) {
            S.paintBackground(g, this);
            super.paintComponent(g);
         }
      };
      southSplitPane.setContinuousLayout(false);
      southSplitPane.setOneTouchExpandable(true);
      southSplitPane.setDividerSize(3);
      southSplitPane.setOpaque(false);


      northSplitPane.setMaximumSize(new Dimension(Short.MAX_VALUE, execResult.getAxis("Axis0").getHierarchyInfoCount() * rh));
      northSplitPane.setMinimumSize(new Dimension(0, execResult.getAxis("Axis0").getHierarchyInfoCount() * rh));




      ct.addComponentListener(new ComponentListener(){

         public void componentHidden(ComponentEvent e) {}

         public void componentMoved(ComponentEvent e) {}

         public void componentResized(ComponentEvent e){
            /// this is an integer div!
            int w = ct.getWidth()/ct.getColumnCount();
            // this method adjusts/compensates the size of the left/right pane
            // so that there is no integer divide error that causes the labels
            // in the header to be out of sync with columns in a cell table
            // (JTable - if there width/column_count is not an
            // integer then it paints some columns wider than the other,
            // and then the columns fall out of sync with header)
            //
//            S.out("w=" + w
//                  + " ct.getColumnCount()=" + ct.getColumnCount()
//                  + " w*ct.getColumnCount()=" + ct.getColumnCount() * w
//                  + " rightPane.getWidth()=" + rightPane.getWidth());
            if (w * ct.getColumnCount() != ct.getWidth()){
//               S.out("splitPane.getDividerLocation()="+splitPane.getDividerLocation());
               int reduce;
               reduce = ct.getColumnCount() - ct.getWidth()%ct.getColumnCount();
//               S.out("Reducing by " + reduce + " to :" + (southSplitPane.getWidth() - reduce));
               southSplitPane.setDividerLocation(southSplitPane.getDividerLocation() - reduce);
            }
            // nothh split pane divider follows south one:
            northSplitPane.setDividerLocation(southSplitPane.getDividerLocation());
            northSplitPane.invalidate();
            northSplitPane.revalidate();
            northSplitPane.repaint();

         }

         public void componentShown(ComponentEvent e) {}
      }
      );




      // simple, stupid calculation:
//      splitPane.setDividerLocation(divLoc);
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      northSplitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
      this.add(northSplitPane);
      scrollPane = new JScrollPane(southSplitPane);
//      S.out("w = " + scrollPane.getVerticalScrollBar().getSize().getWidth());
      vScrollbarDisplayed = false;
      scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){
         public void adjustmentValueChanged(AdjustmentEvent e)  {
//            S.out("adjustment w = " + scrollPane.getVerticalScrollBar().getSize().getWidth() );
//            S.out("adj scrollPane.getViewport().getViewSize().getHeight()  = " + scrollPane.getViewport().getViewSize().getHeight()
//               + "  scrollPane.getViewport().getExtentSize().getHeight() = " + scrollPane.getViewport().getExtentSize().getHeight());
            if (scrollPane.getViewport().getViewSize().getHeight() > scrollPane.getViewport().getExtentSize().getHeight()){
               if (!CubeExplorer2.this.vScrollbarDisplayed){
                  CubeExplorer2.this.vScrollbarDisplayed = true;
                  columnsPane.add(Box.createRigidArea(new Dimension(15, 0)));
               }
//               S.out("displayed");
            }else{
//               S.out("removing rigid area");
               if (CubeExplorer2.this.vScrollbarDisplayed){
                  CubeExplorer2.this.vScrollbarDisplayed = false;
                  columnsPane.removeAll();
                  columnsPane.add(columnsResultPane);
                  columnsPane.add(Box.createHorizontalGlue());
               }

//               S.out("not displayed");
            }
         }
      });
      this.add(scrollPane);
      this.add(Box.createVerticalGlue());

      this.setOpaque(true);
      this.setBorder(AppColors.CUBE_EXPLORER_BORDER);
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 

      /* implementing localization */
      I18n.addOnLanguageChangedListener(this);
      applyI18n();
      /* end of modification for I18n */



      setPrefferedDividerLocation();
   }

   public void copyTableToClipboard(){
      ct.copyTableToClipboard();
   }

   private double getPrefferedDividerLocation(){
      // this is not a sophisticated calculation
      return divLoc;
   }
   public short getPrefferedHeight(){
//      S.out("cubeExplorerRowNumber=" + cubeExplorerRowNumber);
      return (short)(ct.getRowHeight() *   cubeExplorerRowNumber);
   }
   public void setPrefferedDividerLocation(){
      divLoc = ((Toolkit.getDefaultToolkit().getScreenSize()).getWidth()-rowsPane.getMinimumSize().getWidth());
      divLoc *= (double)execResult.getAxis("Axis1").getHierarchyInfoCount()/(execResult.getAxis("Axis1").getHierarchyInfoCount() + execResult.getAxis("Axis0").getTupleCount());
      southSplitPane.setDividerLocation((int)divLoc);
      northSplitPane.setDividerLocation((int)divLoc);
//      S.out("Setting div loc at:" + divLoc
//            + "\nrowsPane.getMinimumSize().getWidth()" + rowsPane.getMinimumSize().getWidth()
//            + "\nexecResult.getAxis(\"Axis1\").getHierarchyInfoCount()=" + execResult.getAxis("Axis1").getHierarchyInfoCount()
//            + "\nexecResult.getAxis(Axis0).getTupleCount()=" + execResult.getAxis("Axis1").getTupleCount()
//            + "\n(Toolkit.getDefaultToolkit().getScreenSize()).getWidth() = " + (Toolkit.getDefaultToolkit().getScreenSize()).getWidth());
   }
   public void highlightRows(int rowNum, int colNum){
      Tuple t = execResult.getAxis("Axis1").getTupleAt(rowNum);
      int memberOrdinal = execResult.getAxis("Axis1").getHierarchyInfoCount();

      int levelsHighlighted = 0; // boost performance
      int i;
      for(i=0; i < rowsResultPane.getComponentCount() && (levelsHighlighted <= memberOrdinal); i++){
         if (rowsResultPane.getComponent(i) instanceof RowTupleMemberLabel) {
            levelsHighlighted += ((RowTupleMemberLabel)rowsResultPane.getComponent(i)).setHighlight(t, memberOrdinal);
         }
      }
      if (i < rowsResultPane.getComponentCount()){
         for(; i < rowsResultPane.getComponentCount(); i++)
            ((JLabel)rowsResultPane.getComponent(i)).setOpaque(false);
      }
      rowsPane.revalidate();
      rowsPane.repaint();
   }
   public void highlightColumns(int rowNum, int colNum){
      Tuple t = execResult.getAxis("Axis0").getTupleAt(colNum);
      int memberOrdinal = execResult.getAxis("Axis0").getHierarchyInfoCount();
//      S.out("t=" + t);
      int levelsHighlighted = 0; // boost performance
      int i;
      for (i = 0;i < columnsResultPane.getComponentCount() && (levelsHighlighted <= memberOrdinal); i++) {
         if (columnsResultPane.getComponent(i) instanceof ColumnTupleMemberLabel){
            levelsHighlighted +=((ColumnTupleMemberLabel) columnsResultPane.getComponent(i)).setHighlight(t, memberOrdinal);
         }
      }
      if (i < columnsResultPane.getComponentCount()) {
         for (; i < columnsResultPane.getComponentCount(); i++)
            ( (JLabel) columnsResultPane.getComponent(i)).setOpaque(false);
      }
      columnsResultPane.revalidate();
      columnsResultPane.repaint();
   }
   public void highlightRowsAndColumns(int rowNum, int colNum){
      highlightRows(rowNum, colNum);
      highlightColumns(rowNum, colNum);
   }
   public void setShowColumnTotalsOn(boolean showColumnTotalsOn){
      if (showColumnTotalsOn)
         cubeExplorerRowNumber -= 2;
      else
         cubeExplorerRowNumber += 2;

      ct.setShowColumnTotalsOn(showColumnTotalsOn);

      int rh = ct.getRowHeight();

      rowsResultPane  = execResult.getVerticalTreePanel("Axis1", 40, rh, showColumnTotalsOn);

      S.out("execResult.getAxis(Axis1).getTupleCount()" +  execResult.getAxis("Axis1").getTupleCount());
      rowsPane.removeAll();
      rowsResultPane.setMaximumSize(new Dimension(Short.MAX_VALUE, execResult.getAxis("Axis1").getTupleCount() * rh));
      rowsPane.add(rowsResultPane);
      rowsPane.add(Box.createVerticalGlue());

      this.invalidate();
      this.revalidate();
      this.repaint();
   }
   public void setShowRowTotalsOn(boolean showRowTotalsOn){
      // table sets it's model
      ct.setShowRowTotalsOn(showRowTotalsOn);
//      ct.invalidate();
//      ct.revalidate();
//      ct.repaint();
      // redo rightPane:
      int rh = ct.getRowHeight();
      int rw = ct.getWidth() / ct.getColumnCount();

//      columnsPane = execResult.getHorizontalTreePanel("Axis0", rw, rh, showRowTotalsOn);
      columnsPane.removeAll();
      columnsPane.add(execResult.getHorizontalTreePanel("Axis0", rw, rh, showRowTotalsOn));
      columnsPane.add(Box.createHorizontalGlue());
//      columnsPane.setMaximumSize(new Dimension(Short.MAX_VALUE, execResult.getAxis("Axis0").getHierarchyInfoCount() * rh));
//      northPane.setMinimumSize(new Dimension(0, execResult.getAxis("Axis0").getHierarchyInfoCount() * rh));
//
//      rightPane.removeAll();
//      rightPane.add(northPane);
//      rightPane.add(ct);
//      rightPane.add(Box.createVerticalGlue());

      // refresh all
      this.invalidate();
      this.revalidate();
      this.repaint();
   }

   public static void main(String[] args) {

      ServerMetadata svm2 = new ServerMetadata("http://localhost:8080/mondrian/xmla");


      XMLAExecuteProperties   properties2   = XMLAObjectsFactory.newXMLAExecuteProperties();


      properties2.setDataSourceInfo("FoodMartSource");
      properties2.setCatalog("Foodmart");



      try{


         Document d2 = svm2.execute("SELECT"
            + " NON EMPTY"
            + "{ {[Measures].Members}} ON COLUMNS, "
            + " NON EMPTY "
            + "{[Promotions].[Promotion Name].Members} ON ROWS"
            + " FROM Sales "
            , properties2);

         // S.out("" + r);

         ExecuteResult r12 = new ExecuteResult(d2, null);
         CubeExplorer2 ceTest= new CubeExplorer2(r12, new CubeSlicer((short)1, (short)0), true, true);
         JFrame frame2 = new JFrame("Test CubeExplorer 2");

         frame2.setBackground(Color.GREEN);
         frame2.getContentPane().add(ceTest, BorderLayout.CENTER);

         frame2.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
               System.exit(0);
            }
         });
         frame2.pack();
         frame2.setVisible(true);

      }catch(Exception e){
         e.printStackTrace();
      }


//      for(int c=0; c< ceTest.westPane.getComponentCount(); c++){
//         S.out("Component(" + c + ")" + " HEIGHT = " + ceTest.westPane.getComponent(c).getHeight() + " -->" + ceTest.westPane.getComponent(c) );
//      }
//      S.out("PACK: westPane.height =" + ceTest.westPane.getHeight() + " cellTable.height=" + ceTest.ct.getHeight());

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
     btnCopyTable.setToolTipText(I18n.getString("toolTip.copyTable"));
    }
  /* end of modification for I18n */


}
