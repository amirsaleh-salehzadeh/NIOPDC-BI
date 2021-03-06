package rex.graphics.filtertree;


import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeSelectionModel;

import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import rex.metadata.ServerMetadata;

import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.ImageIcon;

import rex.utils.S;
import rex.graphics.dimensiontree.*;
import javax.swing.tree.TreePath;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import rex.utils.WaitCursorEventQueue;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureEvent;
import rex.utils.AppColors;
import rex.metadata.Query;
import rex.metadata.QueryElement;
import javax.swing.JLabel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.tree.ExpandVetoException;
import javax.swing.event.TreeWillExpandListener;
import rex.graphics.filtertree.elements.FilterTreePopUpActions;
import java.util.LinkedList;
import rex.metadata.resultelements.Member;
import rex.metadata.UniqueNameElement;
import rex.graphics.filtertree.elements.*;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.graphics.dimensiontree.elements.DimensionElement;
import rex.graphics.*;
import rex.xmla.*;
import rex.xmla.XMLAObjectsFactory;
import rex.utils.I18n;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class FilterTree extends JPanel implements ActionListener, TreeWillExpandListener{
   final JTree tree;
   JPopupMenu popup;
   Query query;
   IViewer viewer;
   private DimensionTreeElement popUpSource;
   private JLabel enabledMembers;

   public FilterTree(    ServerMetadata                  _smd
                      ,  Query                           _q
                      ,  DimensionTreeElement            _filterDimension
                      ,  LinkedList                      _enabledMembersList
                      ,  IViewer                          _viewer){
      this( _smd, _filterDimension, _enabledMembersList);
      query = _q;
      viewer = _viewer;
   }


   public FilterTree(    ServerMetadata                  _smd
                      ,  DimensionTreeElement            _filterDimension
                      ,  LinkedList                      _enabledMembersList){

      FilterTreeModel treeModel = new FilterTreeModel(_smd
                                                    , _filterDimension
                                                    , _enabledMembersList);

     //Create a tree that allows one selection at a time.

      tree = new JTree(treeModel){
         {setOpaque(false);}
         public void paintComponent(Graphics g) {
            S.paintBackground(g, this);
            super.paintComponent(g);
         }

      };


      tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
      tree.addTreeWillExpandListener(this);
      //Enable tool tips.
      ToolTipManager.sharedInstance().registerComponent(tree);
      tree.setCellRenderer(new MyRenderer2());
      tree.setOpaque(false);

      JScrollPane treeView = new JScrollPane(tree);
      treeView.setOpaque(false);

//      treeView.setPreferredSize(new Dimension(400, 600));

//      treeView.setMinimumSize(new Dimension(200, 600));
      treeView.setMaximumSize(new Dimension(250,Short.MAX_VALUE));
      treeView.setMinimumSize(new Dimension(200, 400));

      enabledMembers = new JLabel();
      enabledMembers.setText(treeModel.getEnabledMembersCaption());
      enabledMembers.setBackground(AppColors.ENABLED_MEMBERS_LABEL_BACKGROUND);
      enabledMembers.setOpaque(true);

      this.setLayout(new BorderLayout());
      this.setOpaque(false);
      JSplitPane treeAndLabel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, treeView, new JScrollPane(enabledMembers));
      treeAndLabel.setDividerSize(8);
      treeAndLabel.setContinuousLayout(true);
      treeAndLabel.setOneTouchExpandable(true);

      this.add(treeAndLabel, BorderLayout.CENTER);


      popup = new JPopupMenu();

      tree.addMouseListener(new PopupListener());

      tree.setDragEnabled(true);


   }

   public JTree getTree(){
      return tree;
   }
   public boolean isDisplayedInTab(){
      return ((FilterTreeModel)tree.getModel()).isDisplayedInTab();
   }
   public String getCaption(){
      return ((FilterTreeModel)tree.getModel()).getCaption();
   }
   void loseFilter(){
/**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
/* implementing localization */
       int retVal = JOptionPane.showConfirmDialog(
                    null
                  , I18n.getString("msgText.dropFilter")
                  , I18n.getString("msgTitle.loseFilter")
                  , JOptionPane.YES_NO_OPTION);
         /* end of modification for I18n */

      if (retVal == JOptionPane.NO_OPTION) {
      }else{
         query.dropFilterFromQuery(this);
         // drop me!!
      }
   }
   public void actionPerformed(ActionEvent e){
      int i = 0;

      if (e.getActionCommand().equals((String)FilterTreePopUpActions.popUpCaptions.get(FilterTreePopUpActions.DISABLE_THIS_ELEMENT))){
         ((FilterTreeModel)tree.getModel()).disableMember(popUpSource);
//         enabledMembers.repaint();
//         tree.repaint();
      }else if (e.getActionCommand().equals((String)FilterTreePopUpActions.popUpCaptions.get(FilterTreePopUpActions.ENABLE_THIS_ELEMENT))){
         ((FilterTreeModel)tree.getModel()).enableMember(popUpSource);
//         enabledMembers.repaint();
//         tree.repaint();
      }else if (e.getActionCommand().equals((String)FilterTreePopUpActions.popUpCaptions.get(FilterTreePopUpActions.ENABLE_ALL_BUT_THIS_ELEMENT))){
         ((FilterTreeModel)tree.getModel()).setAllButThisMember((UniqueNameElement)popUpSource);
//         enabledMembers.repaint();
//         tree.repaint();
      }else if (e.getActionCommand().equals((String)FilterTreePopUpActions.popUpCaptions.get(FilterTreePopUpActions.ENABLE_ONLY_THIS_ELEMENT))){
         ((FilterTreeModel)tree.getModel()).setOnlyThisMember((UniqueNameElement)popUpSource);
//         enabledMembers.repaint();
//         tree.repaint();
      }else if (e.getActionCommand().equals((String)FilterTreePopUpActions.popUpCaptions.get(FilterTreePopUpActions.LOSE_FILTER))){
         loseFilter();
      }else if (e.getActionCommand().equals((String)FilterTreePopUpActions.popUpCaptions.get(FilterTreePopUpActions.APPLY_FILTER))){
         query.checkRunQuery();
      }else if (e.getActionCommand().equals((String)FilterTreePopUpActions.popUpCaptions.get(FilterTreePopUpActions.MOVE_TO_SPLIT_PANE))){
         ((FilterTreeRootElement)((TreeElement)tree.getModel().getRoot()).getUserObject()).setDisplayInTab(false);
         viewer.refreshFilterDisplay();
         return;
      }else if (e.getActionCommand().equals((String)FilterTreePopUpActions.popUpCaptions.get(FilterTreePopUpActions.MOVE_TO_TAB))){
         ((FilterTreeRootElement)((TreeElement)tree.getModel().getRoot()).getUserObject()).setDisplayInTab(true);
         viewer.refreshFilterDisplay();
         return;
      }

      enabledMembers.setText(((FilterTreeModel)tree.getModel()).getEnabledMembersCaption());
      FilterTree.this.repaint();
      if (!((FilterTreeModel)tree.getModel()).isFilttering()){
         loseFilter();
      }
   }
//////////////////// <TreeWillExpandListener methods>


      public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
         TreeElement te = ((TreeElement)e.getPath().getLastPathComponent());
         if ( ((DimensionTreeElement)te.getUserObject()).getChildrenCount() > 0
             && te.getChildCount() == 0){
            if (((DimensionTreeElement)te.getUserObject()).getChildrenCount() < ((FilterTreeModel)tree.getModel()).MAX_CHILDREN_COUNT_ASK){
               ((FilterTreeModel)tree.getModel()).addChildrenOneLevel(te);
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
                  +  I18n.getString("msgText.expandNode2")
                  ,  I18n.getString("msgTitle.expandNode") + ((FilterTreeModel)tree.getModel()).MAX_CHILDREN_COUNT_ASK + " members."
                  , JOptionPane.YES_NO_OPTION);
               /* end of modification for I18n */

               if (retVal == JOptionPane.NO_OPTION) {
                  throw new ExpandVetoException(e);
               }else{
                  ((FilterTreeModel)tree.getModel()).addChildrenOneLevel(te);
               }
            }
         }
     }


     public void treeWillCollapse(TreeExpansionEvent e) {}

//////////////////// </TreeWillExpandListener methods>



// INNER CLASSES AND MAIN:


    private class MyRenderer2 extends JLabel implements TreeCellRenderer{
         ImageIcon tutorialIcon;

         public MyRenderer2() {
           this.setOpaque(false);
//           setBackground(Color.RED);
         }

         public Component getTreeCellRendererComponent(
                             JTree tree,
                             Object value,
                             boolean sel,
                             boolean expanded,
                             boolean leaf,
                             int row,
                             boolean hasFocus) {

             DimensionTreeElement curr = (DimensionTreeElement)((TreeElement)value).getUserObject();
             setText(curr.toString());
             setIcon(curr.getIcon());
             setToolTipText(curr.getToolTip());
             if (curr.isEnabled() && curr instanceof FilterTreeMemberElement){
                setForeground(AppColors.ENABLED_FILTER_TREE_NODE_COLOR);
             }else{
                setForeground(AppColors.DISABLED_FILTER_TREE_NODE_COLOR);
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
             if ( selRow != -1
                  && (((TreeElement)selPath.getLastPathComponent())).getUserObject() instanceof FilterTreeMemberElement) {
                DimensionTreeElement dte = (DimensionTreeElement)(((TreeElement)selPath.getLastPathComponent())).getUserObject();
               // toggle enabled flag here:
                if (dte.isEnabled()){
                   ((FilterTreeModel)tree.getModel()).disableMember(dte);
                }else{
                   ((FilterTreeModel)tree.getModel()).enableMember(dte);
                }
                enabledMembers.setText(((FilterTreeModel)tree.getModel()).getEnabledMembersCaption());
                FilterTree.this.repaint();
//                tree.repaint();
//                enabledMembers.repaint();
                if (!((FilterTreeModel)tree.getModel()).isFilttering()){
                   loseFilter();
                }

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
                FilterTree.this.popUpSource = ((DimensionTreeElement)((TreeElement)(selPath.getLastPathComponent())).getUserObject());
                al = FilterTree.this.popUpSource.getPopUpActionList();
                for (int i=0; al != null && i < al.length; i++){
                   menuItem = new JMenuItem((String)FilterTreePopUpActions.popUpCaptions.get(al[i]));
                   menuItem.addActionListener(FilterTree.this);
                   popup.add(menuItem);
                }
                popup.show(e.getComponent()
                           , e.getX()
                           , e.getY());
             }
          }
       }
    }







// TEST main:


   public static void main(String[] args) {

       ServerMetadata svm = new ServerMetadata("http://localhost/xmla/msxisapi.dll");

       XMLADiscoverRestrictions restrictions =  XMLAObjectsFactory.newXMLADiscoverRestrictions();
       XMLADiscoverProperties   properties   =  XMLAObjectsFactory.newXMLADiscoverProperties();

       properties.setDataSourceInfo("Local Analysis Server");
       restrictions.setCatalog("Test");
       restrictions.setCubeName("Ispit");
       restrictions.setDimensionUniqueName("[VrijemeRok]");

       properties.setCatalog("Test");
       properties.setFormat("Tabular");
       properties.setContent("SchemaData");




       JFrame frame = new JFrame("Testing FilterTree...");


     DimensionElement de = new DimensionElement(  svm
                                                , restrictions
                                                , properties
                                                , "Test"
                                                , "Ispit"
                                                , "[Student]"
                                                , "[Student]"
                                                , "[Student]" );
                                             // OcjenaPismeni VrijemeRok Predmet

      UniqueNameElement enabledMember = new Member(
          "[Student].[CistaGeneracija]"
         ,"[Student].[CistaGeneracija].[Svi studenti].[Da]"
         ,"[Da]"
         , true);



       LinkedList enabledMembers = new LinkedList();
       enabledMembers.add(enabledMember);
       FilterTree dst = new FilterTree(  svm
                                        , de
                                        , enabledMembers);
       frame.getContentPane().add(dst);


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
