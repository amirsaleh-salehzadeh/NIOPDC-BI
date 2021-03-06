package rex.graphics.dimensiontree;


import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import rex.graphics.*;
import rex.graphics.datasourcetree.elements.DataSourceTreeElement;
import rex.graphics.dimensiontree.elements.*;
import rex.graphics.mdxeditor.*;
import rex.metadata.*;
import rex.utils.*;
import rex.xmla.*;



/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class DimensionTree extends JPanel implements ActionListener, TreeWillExpandListener{
   final JTree tree;
   JPopupMenu popup;
   Query query;
   private DimensionTreeElement popUpSource;
   private MdxEditor mdxEditor=null;

   
   public DimensionTree( XMLADiscoverRestrictions _restrictions
                      ,  XMLADiscoverProperties   _properties
                      ,  ServerMetadata _smd
                      ,  Query _q){
      this(_restrictions, _properties, _smd, false);
      query = _q;
   }
// this is a constructor for invoking via MdxEditor, it has a reference to MdxEditor
// so that it can transfer unique names to editor on double click:
   public DimensionTree( XMLADiscoverRestrictions _restrictions
                      ,  XMLADiscoverProperties   _properties
                      ,  ServerMetadata _smd
                      ,  MdxEditor _mdxEditor){
      this(_restrictions, _properties, _smd, true);
      mdxEditor = _mdxEditor;      
      
      // adding the listener ONLY for the purposes of MdxEditor:
      tree.addTreeWillExpandListener(DimensionTree.this);
   }


   public DimensionTree( XMLADiscoverRestrictions _restrictions
                      ,  XMLADiscoverProperties   _properties
                      ,  ServerMetadata _smd
                      ,  boolean        withMembersLevel){

      DimensionTreeModel treeModel = new DimensionTreeModel(_restrictions, _properties, _smd, withMembersLevel);

     //Create a tree that allows one selection at a time.

      tree = new JTree(treeModel){
         {setOpaque(false);}
         public void paintComponent(Graphics g) {
            S.paintWhiteBackground(g, this);// By Prakash
         	//.setColor(AppColors.CUBE_EXPLORER_PANEL_BACKGROUND);// Commented by Prakash.
            super.paintComponent(g);
         }

      };


      tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
      //Enable tool tips.
      ToolTipManager.sharedInstance().registerComponent(tree);
      tree.setCellRenderer(new MyRenderer2());
      tree.setOpaque(false);

      JScrollPane treeView = new JScrollPane(tree);
      treeView.setOpaque(false);

      treeView.setPreferredSize(new Dimension(400, 600));

//      beacuse of MDX editor I'll change height to 0
      treeView.setMinimumSize(new Dimension(200, 0));
      treeView.setMaximumSize(new Dimension(800,600));

      this.setLayout(new BorderLayout());
      this.setOpaque(false);
      this.add(treeView, BorderLayout.CENTER);


      popup = new JPopupMenu();

      tree.addMouseListener(new PopupListener());
//  AAAAARRRRGHHHH! THIS MUST BE OFF - OTHERWISE DRAG WILL THROW AN EXCEPTION WHEN DRAGGING THE SAME ITEM 2 TIMES IN SUCCESSION
//      tree.setDragEnabled(true);


   }

   public JTree getTree(){
      return tree;
   }
   public void setQuery(Query q){
      query = q;
   }

   public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
      TreeElement te = ((TreeElement)e.getPath().getLastPathComponent());
      if ( ((DimensionTreeElement)te.getUserObject()).getChildrenCount() > 0
         && te.getChildCount() == 0){
         if (((DimensionTreeElement)te.getUserObject()).getChildrenCount() < ((DimensionTreeModel)tree.getModel()).MAX_CHILDREN_COUNT_ASK){
            ((DimensionTreeModel)tree.getModel()).addChildrenOneLevel(te);
         }else{
         /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */
          /* implementing localization */
              int retVal = JOptionPane.showConfirmDialog(
                 null
               , I18n.getString("msgText.expandNode1") + ( (DimensionTreeElement) te.getUserObject()).getChildrenCount()
               + I18n.getString("msgText.expandNode2")
               , I18n.getString("msgTitle.expandNode") + ((DimensionTreeModel)tree.getModel()).MAX_CHILDREN_COUNT_ASK + " members."
               , JOptionPane.YES_NO_OPTION);
                /* end of modification for I18n */

            if (retVal == JOptionPane.NO_OPTION) {
               throw new ExpandVetoException(e);
            }else{
               ((DimensionTreeModel)tree.getModel()).addChildrenOneLevel(te);
            }
         }
      }
   }
   public void treeWillCollapse(TreeExpansionEvent e) {}
   public void actionPerformed(ActionEvent e){
      int i = 0;
      // To do:

//      S.out("e.paramString() = " + e.paramString() + "e.getSource()=" + e.getSource());

      if (e.getActionCommand().equals((String)PopUpActions.popUpCaptions.get(PopUpActions.FLATTEN_DIMENSIONS))){
         ((DimensionTreeModel) tree.getModel()).setFlattenDimensions(true);
      }else if (e.getActionCommand().equals((String)PopUpActions.popUpCaptions.get(PopUpActions.GROUP_HIERARCHIES_BY_DIMENSION))){
         ((DimensionTreeModel) tree.getModel()).setFlattenDimensions(false);
      }else if (e.getActionCommand().equals((String)PopUpActions.popUpCaptions.get(PopUpActions.SEND_TO_COLUMNS))){
         query.addToColumns((QueryElement)popUpSource);
         // disable this one, so it can't be dragged into the query once again:
         ((DimensionTreeModel)tree.getModel()).disableTreeElements(((QueryElement)popUpSource));
         tree.repaint();
      }else if (e.getActionCommand().equals((String)PopUpActions.popUpCaptions.get(PopUpActions.SEND_TO_ROWS))){
         query.addToRows((QueryElement)popUpSource);
         ((DimensionTreeModel)tree.getModel()).disableTreeElements(((QueryElement)popUpSource));
         tree.repaint();
      }else if (e.getActionCommand().equals((String)PopUpActions.popUpCaptions.get(PopUpActions.SEND_TO_MEASURES ))){
         query.addToMeasures((QueryElement)popUpSource);
         ((DimensionTreeModel)tree.getModel()).disableTreeElements(((QueryElement)popUpSource));
         tree.repaint();
      }else if (e.getActionCommand().equals((String)PopUpActions.popUpCaptions.get(PopUpActions.SEND_TO_PAGES))){
         query.addToPages((QueryElement)popUpSource);
         ((DimensionTreeModel)tree.getModel()).disableTreeElements(((QueryElement)popUpSource));
         tree.repaint();
      }

   }



// INNER CLASSES AND MAIN:

//    private class MyRenderer2 extends JLabel implements TreeCellRenderer{// Commented by Prakash.
   private class MyRenderer2 extends DefaultTreeCellRenderer {
         ImageIcon tutorialIcon;

         public MyRenderer2() {
           this.setOpaque(false);
//           setBackground(Color.RED);
 //          setBackground(new Color( 255, 255, AppColors.PANEL_BACKGROUND_BLUE));
         }
         public Component getTreeCellRendererComponent(
                             JTree tree,
                             Object value,
                             boolean sel,
                             boolean expanded,
                             boolean leaf,
                             int row,
                             boolean hasFocus) {

         	super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
             DimensionTreeElement curr = (DimensionTreeElement)((TreeElement)value).getUserObject();
             setText(curr.toString());
             setIcon(curr.getIcon());
             setToolTipText(curr.getToolTip());
             /**
              * Commenting to avoid empty if statement.
              * by Prakash. 08-05-2007.
              */
             /*
             if (curr.isEnabled()){
                //setForeground(AppColors.ENABLED_DIM_TREE_NODE_COLOR);//By Prakash.
             }else{
             */
             /*
              * End of modification.
              */
             
             /**
              * Inserting if condition to check the state of DimensionTreeElement.
              * by Prakash. 08-05-2007.
              */
             if (!(curr.isEnabled())){
               /*
                *	End of the Insertion. 
                */
                setForeground(AppColors.DISABLED_DIM_TREE_NODE_COLOR);
             }
             
             return this;
         }
     }

    class PopupListener extends MouseAdapter {
       public void mousePressed(MouseEvent e) {
         maybeShowPopup(e);
       }

       public void mouseReleased(MouseEvent e) {
          maybeShowPopup(e);
       }
       public void mouseClicked(MouseEvent e) {
          if ((e.getClickCount()==2)){

             int selRow = tree.getRowForLocation(e.getX(), e.getY());
             TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
             if (    (selRow != -1)
                 && (((TreeElement)selPath.getLastPathComponent())).getUserObject() instanceof QueryElement ) {

                QueryElement qe = ((QueryElement)((TreeElement)(selPath.getLastPathComponent())).getUserObject());

                if (DimensionTree.this.mdxEditor != null) {
                   if (qe instanceof MeasureElement){
                      mdxEditor.addTextToCurrentPosition(((MeasureElement) qe).getUniqueName());
                   }else if (qe instanceof LevelElement){
                      mdxEditor.addTextToCurrentPosition(((LevelElement)qe).getUniqueName() + ".Members");
                   }else if (qe instanceof HierarchyElement){
                      mdxEditor.addTextToCurrentPosition(((HierarchyElement)qe).getUniqueName() + ".Members");
                   }
                   return; // don't disable it
                }

                // double click will send item to rows (only if it IS a QueryElement)
                // -> that can be a measure, or a dimension level, (maybe a hierarchy?)
                if (qe instanceof MeasureElement){
                   DimensionTree.this.query.addToMeasures(qe);
                }else{
                   DimensionTree.this.query.addToRows(qe);
                }
                ((DimensionTreeModel)tree.getModel()).disableTreeElements(qe);
                tree.repaint();
             }
          }
       }


       private void maybeShowPopup(MouseEvent e) {
          int      selRow  = tree.getRowForLocation(e.getX(), e.getY());
          TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
          if(selRow != -1) {
             if (e.isPopupTrigger()) {
                String[] al;
                JMenuItem menuItem;

                popup.removeAll();
                // get the selected TreeElement's action list:
                DimensionTree.this.popUpSource = ((DimensionTreeElement)((TreeElement)(selPath.getLastPathComponent())).getUserObject());
                //S.out("DimensionTree.this.popUpSource=" + DimensionTree.this.popUpSource);
                al = DimensionTree.this.popUpSource.getPopUpActionList();
                
                if(mdxEditor == null)//Temp code by Prakash
                {//Temp code by Prakash
                    for (int i=0; al != null && i < al.length; i++){
                        menuItem = new JMenuItem((String)PopUpActions.popUpCaptions.get(al[i]));
                        menuItem.addActionListener(DimensionTree.this);
                        popup.add(menuItem);
                    }
                    popup.show(e.getComponent()
                           , e.getX()
                           , e.getY());
                /*
                 * Temp code by Prakash
                 */
                }
                else
                {
                    for (int i=0; al != null && i < al.length; i++){
                        /**
                         * Commenting to avoid empty if statement.
                         * by Prakash. 08-05-2007.
                         */
                        /*
                        if(al[i].trim().equals(PopUpActions.SEND_TO_ROWS) || al[i].trim().equals(PopUpActions.SEND_TO_COLUMNS) || al[i].trim().equals(PopUpActions.SEND_TO_MEASURES) || al[i].trim().equals(PopUpActions.SEND_TO_PAGES))
                        {}
                        else
                        */
                         /*
                          * End of the modification.
                          */
                        /**
                         * Inserting if condition to add options other than "send to rows", "send to columns", "send to measures" and "send to pages". 
                         * by Prakash. 08-05-2007.
                         */
                        if(!(al[i].trim().equals(PopUpActions.SEND_TO_ROWS) || al[i].trim().equals(PopUpActions.SEND_TO_COLUMNS) || al[i].trim().equals(PopUpActions.SEND_TO_MEASURES) || al[i].trim().equals(PopUpActions.SEND_TO_PAGES)))
                         /*
                          * End of the Insertion.
                          */
                        {
                            menuItem = new JMenuItem((String)PopUpActions.popUpCaptions.get(al[i]));
                            menuItem.addActionListener(DimensionTree.this);
                            popup.add(menuItem);
                        }
                    }
                    popup.show(e.getComponent()
                           , e.getX()
                           , e.getY());
                }
                // End
             }
          }
       }
    }


    public static void main(String[] args) {
       ServerMetadata svm = new ServerMetadata("http://localhost/xmla/msxisapi.dll");

       XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
       XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();


       restrictions.setCatalog("Foodmart 2000");
       restrictions.setCubeName("Sales");


       properties.setDataSourceInfo("Local Analysis Server");
       properties.setCatalog("Sales");
       properties.setFormat("Tabular");
       properties.setContent("SchemaData");


       JFrame frame = new JFrame("Testing DimensionTree...");
       DimensionTree dst = new DimensionTree(restrictions, properties, svm, false);
       frame.getContentPane().add(dst);
//       frame.setContentPane(dst);

       frame.addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent e) {
                System.exit(0);
             }
          });

      frame.pack();
      frame.setVisible(true);
      EventQueue waitQueue = new WaitCursorEventQueue(500);
      Toolkit.getDefaultToolkit().getSystemEventQueue().push(waitQueue);

    }



}
