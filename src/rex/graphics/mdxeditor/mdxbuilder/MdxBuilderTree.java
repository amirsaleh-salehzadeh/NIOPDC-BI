package rex.graphics.mdxeditor.mdxbuilder;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import rex.graphics.mdxeditor.MdxEditor;
import rex.graphics.mdxeditor.jsp.ReadEnv;
import rex.graphics.mdxeditor.mdxbuilder.dnd.*;
import rex.graphics.mdxeditor.mdxbuilder.nodes.*;
import rex.utils.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.awt.FileDialog;
import java.io.File;
import java.util.Enumeration;
import java.util.ArrayList;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;


/**
 * Main MdxBuilder class that displays the Mdx Builder Tree in a JPanel.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MdxBuilderTree extends JPanel implements CanSaveAndResumeState{
   JTree tree;
   private MdxBuilderListener listener;
   DefaultMutableTreeNode root;
   protected DefaultTreeModel treeModel;
   JPopupMenu popup;
   private String cubeName;
   private MdxBuilderToolbar toolbar;
   //JFileChooser jfc;
   public static String OPEN_MDX_GENERATED_EXPRESSION_TAG = "-- **********  < Mdx generated with MdxBuilder > **********"
                      , CLOSE_MDX_GENERATED_EXPRESSION_TAG = "-- ********** </ Mdx generated with MdxBuilder > **********";

   public MdxBuilderTree(MdxBuilderListener _listener, HandlesMdxEditorSettings settingsHandler, String _cubeName) {
      this(settingsHandler, _cubeName);
      listener = _listener;
   }
   public MdxBuilderTree(HandlesMdxEditorSettings settingsHandler, String _cubeName) {
//      MdxFunctionTreeModel treeModel = new MdxFunctionTreeModel();
      cubeName = _cubeName;
      root =  new DefaultMutableTreeNode(new MBTRootNode());

      buildFunctionList(root);      
      treeModel = new DefaultTreeModel(root);
     //Create a tree that allows one selection at a time.
      tree = new JTree(treeModel);
      DropTarget dt = new DropTarget(tree, new MdBuilderTreeDropListener(this));

      tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

      //Enable tool tips.
      ToolTipManager.sharedInstance().registerComponent(tree);



        //Create the scroll pane and add the tree to it.
      JScrollPane treeView = new JScrollPane(tree);



      treeView.setPreferredSize(new Dimension(200, 600));

      treeView.setMaximumSize(new Dimension(800,600));
      toolbar = new MdxBuilderToolbar(this, settingsHandler);
      this.setLayout(new BorderLayout());
      this.add(toolbar, BorderLayout.NORTH);
      this.add(treeView, BorderLayout.CENTER);


      MdxBuilderTreeRenderer renderer = new MdxBuilderTreeRenderer(null, null);

      tree.setCellRenderer(renderer);

      popup = new JPopupMenu();
      tree.addMouseListener(new PopupListener());

   }
   /**
    * Returns a mdx builder tree.
    * @return JTree
    */
   public JTree getTree(){
      return tree;
   }

  public void saveState(OutputStream memStream){
      saveQueryStream(memStream);
   }
   public void resumeState(InputStream memStream){
      loadQueryStream(memStream);
   }
   public void clearState(){
      toolbar.actionToBePerformed();
      for (int i=0; i<root.getChildCount(); i++){
         ((DefaultMBTNode)((DefaultMutableTreeNode)root.getChildAt(i)).getUserObject()).removeAllChildrenFromTheTree(  (DefaultMutableTreeNode)root.getChildAt(i)
                                                                                                                     , (DefaultTreeModel)tree.getModel());
         ((DefaultTreeModel)tree.getModel()).nodeChanged(root.getChildAt(i));
      }
      tree.repaint();
      /*
       * 	Commented by Prakash.
       *	Cincom Systems.
       *	13th Oct 2006
       * 	Clearing Text Area.
       */
      listener.mdxChanged(getMdx());
      //listener.clearEditor();
   }

   protected void saveQueryStream(OutputStream stream){
      try {
         ObjectOutputStream s = new ObjectOutputStream(stream);
         MBTNode r = (MBTNode) root.getUserObject();
         MBTNode[] children = r.getMdxBuilderTreeNodes();
         for (int i = 0; children != null && i < children.length; i++) {
            saveNode(s, children[i]);
         }
         //     saving the state of the tree (expanded rows) - that's how nice I am
         Enumeration enumt = tree.getExpandedDescendants(new TreePath(root.getPath()));
//         Enumeration is NOT serilizable but TreePath IS
         TreePath n;
         while(enumt!=null && enumt.hasMoreElements()){
            n = (TreePath)enumt.nextElement();
            s.writeObject(n);
         }

         s.flush();
      }
      catch (Exception e) {
         //e.printStackTrace();//Commented by Prakash
          /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
         JOptionPane.showMessageDialog(null
                                        , I18n.getString("msgText.errSave")
                                        , I18n.getString("msgTitle.saveQuery")
                                        , JOptionPane.ERROR_MESSAGE);
           /* end of modification for I18n */

         return;

      }

   }

   private void saveNode( ObjectOutputStream s, MBTNode parent){
     try {
//       S.out("SERIALIZING " + parent);
       s.writeObject(parent);
     }catch(Exception e){
         /**
          * Commented. Don't want to print trace log on console.
          * by Prakash. 10-05-2007.
          */
       //e.printStackTrace();//Commented by Prakash
         /*
          * End of modification.
          */
     }
     MBTNode[] children = parent.getMdxBuilderTreeNodes();
     for (int i=0; children != null && i<children.length; i++){
       saveNode(s, children[i]);
     }

   }



   protected void loadQueryStream(InputStream memStream){

      ArrayList expandedPaths = new ArrayList();

      try {
         ObjectInputStream s = new ObjectInputStream(memStream);
         root.removeAllChildren();
         ( (MBTNode) (root.getUserObject())).removeAllChildren();
         ( (DefaultTreeModel) tree.getModel()).nodeChanged(root);
//         S.out("1. root.getChildCount() = " + root.getChildCount());
         Object o;
         while (true) {
            o = s.readObject();
//            S.out("deserializing " + o.toString());
            if (o instanceof TreePath) {
//               S.out("found TreePath " + o);
               expandedPaths.add(o);
            }
            else if (o instanceof DefaultMBTAxisNode) {
//               S.out("ADDING " + o);
               ( (MBTNode) (root).getUserObject()).addChild( (MBTNode) o);
               root.add(new DefaultMutableTreeNode(o));
            }
         }
      }
      catch (java.io.EOFException e) {
//        stupid ObjectInputStream has no method to tell me when it it EOF
      }
      catch (Exception e) {
         //e.printStackTrace();//Commented by Prakash
         /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
         JOptionPane.showMessageDialog(null
                                        , I18n.getString("msgText.errLoadingQuery")
                                        ,  I18n.getString("msgTitle.loadingQuery") 
                                        , JOptionPane.ERROR_MESSAGE);
           /* end of modification for I18n */

         return;

      }

      ( (DefaultTreeModel) tree.getModel()).nodeChanged(root);

      for (int i = 0; i < root.getChildCount(); i++) {
         loadNode( (DefaultMutableTreeNode) root.getChildAt(i));
      }
      ((DefaultTreeModel) tree.getModel()).reload();
      Enumeration enumt = root.breadthFirstEnumeration();
      TreePath tp1, tp2;
      while (enumt!=null && enumt.hasMoreElements()){
         DefaultMutableTreeNode node = (DefaultMutableTreeNode)enumt.nextElement();
         tp1 = new TreePath(node.getPath());
         for(int i=0; i < expandedPaths.size(); i++){

            tp2 = (TreePath)expandedPaths.get(i);
            if (tp1.toString().equals(tp2.toString())){
               tree.expandPath(tp1);
            }

         }

      }

      tree.repaint();
      listener.mdxChanged(getMdx());

   }

   private void loadNode(DefaultMutableTreeNode parent){
      MBTNode[] children = ((MBTNode)parent.getUserObject()).getMdxBuilderTreeNodes();
      for (int i=0; children!=null && i<children.length; i++){
         DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(children[i]);
         parent.add(newNode);
         loadNode(newNode);
      }
   }

  public boolean getSaveStatus()
  {
      return ((MdxEditor)listener).getSaveStatus();
  }

  public void setSaveStatus(boolean bool)
  {
      ((MdxEditor)listener).setSaveStatus(bool);
  }  
  public String getFileName()
  {
      try{
          return ((MdxEditor)listener).getFileName();
      }
      catch(Exception exc)
      {
          return "";
      }
  }
  
  public void setFileName(String fName)
  {
      ((MdxEditor)listener).setFileName(fName);
  }
  public void newQuery()
  {
      ((MdxEditor)listener).newQuery();
  }
  public void openQuery()
  {
      ((MdxEditor)listener).openQuery();
  }
  public void saveQuery()
  {
      ((MdxEditor)listener).saveQuery();
  }

   /**
    * Creates the root node and adds child nodes (WITH, ROWS, COLUMNS, PAGES, FROM, WHERE) to the root node.
    * @param root DefaultMutableTreeNode
    */
   private void buildFunctionList(DefaultMutableTreeNode root){
      MBTNode
           calculatedMembersNode
         , rowsNode
         , columnsNode
         , pagesNode
         , fromNode
         , whereNode;

      calculatedMembersNode = new MBTWithMembersNode("WITH");
      /**
       * Changed second parameter from 'true' to 'false' to allow empty results on axis.
       * By Prakash. 8 June 2007.     
       */
     rowsNode = new DefaultMBTAxisNode("ROWS", false);
     columnsNode = new DefaultMBTAxisNode("COLUMNS", false);
     pagesNode = new DefaultMBTAxisNode("PAGES", false);
     /*
      *	End of the modification.
      */
      fromNode = new MBTFromNode(cubeName);
      whereNode =new MBTWhereNode("WHERE");

      ((MBTNode) (root).getUserObject()).addChild(calculatedMembersNode);
      ((MBTNode) (root).getUserObject()).addChild(columnsNode);
      ((MBTNode) (root).getUserObject()).addChild(rowsNode);
      ((MBTNode) (root).getUserObject()).addChild(pagesNode);
      ((MBTNode) (root).getUserObject()).addChild(fromNode);
      ((MBTNode) (root).getUserObject()).addChild(whereNode);

      root.add(new DefaultMutableTreeNode(calculatedMembersNode));
      root.add(new DefaultMutableTreeNode(columnsNode));
      root.add(new DefaultMutableTreeNode(rowsNode));
      root.add(new DefaultMutableTreeNode(pagesNode));
      root.add(new DefaultMutableTreeNode(fromNode));
      root.add(new DefaultMutableTreeNode(whereNode));

   }

   /**
    * This method is called from MdBuilderTreeDropListener. It finds the
    * MBTNode object that is under the node to be dropped and forwards the object-to-be-dropped to  its drop handler.
    * <br>Afterwards, it refreshes the tree.
    * @param p Point
    * @param droppedNode Object
    */
   public void handleDropOnPoint(Point p, Object droppedNode) {
      TreePath path = tree.getPathForLocation(p.x, p.y);      
      tree.setCellRenderer(new MdxBuilderTreeRenderer(null, null));
      if(path != null) {
         toolbar.actionToBePerformed();
         DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)path.getLastPathComponent();
         ((MBTNode) (currentNode).getUserObject()).handleDrop(droppedNode, currentNode, treeModel);
         tree.expandPath(path);
         ((DefaultTreeModel)tree.getModel()).nodeChanged(currentNode);
         tree.repaint();
         listener.mdxChanged(getMdx());
      }
   }
   /**
    * Finds MBTNode object that is under the node to be dropped and forwards the data flavor to its accept drop handler.
    * <br>Returns true if drop is allowed for the specified data flavor, otherwise false.
    * @param p Point
    * @param flavor DataFlavor
    * @return boolean
    */
   public boolean acceptDropOnPoint(Point p, DataFlavor flavor) {
      TreePath path = tree.getPathForLocation(p.x, p.y);
      if(path != null) {
         DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)path.getLastPathComponent();
         MBTNode node = (MBTNode) (currentNode).getUserObject();
         boolean acceptDrop = node.acceptDrop(flavor);
         if (acceptDrop){
            tree.setCellRenderer(new MdxBuilderTreeRenderer(node, Color.green));
         }else{
            tree.setCellRenderer(new MdxBuilderTreeRenderer(node, Color.red));
         }
//         S.out("accept drop returning " + acceptDrop);
         return acceptDrop;
      }
      return false;
   }
  
   /**
    * Returns the MDX statement generated with the Mdx Builder Tree.
    * @return String
    */
   public String getMdx(){
      return  ((MBTNode) (root).getUserObject()).getMdx("");
   }

   /**
    * Inner class that displays the right-click menu actions on MBT nodes.
    * <br>Available right-click(pop-up) actions are retrieved from the underlying MBTNode objects.
    * @author Igor Mekterovic
    * @version 0.3
    */
   class PopupListener extends MouseAdapter {

      public void mousePressed(MouseEvent e) {
         maybeShowPopup(e);
      }

      public void mouseReleased(MouseEvent e) {
         maybeShowPopup(e);
      }

      public void mouseClicked(MouseEvent e) {
      }
      private void maybeShowPopup(MouseEvent e) {
         int      selRow  = tree.getRowForLocation(e.getX(), e.getY());
         TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
//         S.out("maybe...");
         if(selRow != -1) {
            if (e.isPopupTrigger()) {
               String[] al;
               JMenuItem menuItem;
               String caption;
               popup.removeAll();

               // get the selected item's action list:
               final DefaultMutableTreeNode actionNode = ((DefaultMutableTreeNode)(selPath.getLastPathComponent()));
               al = ((MBTNode)actionNode.getUserObject()).getPopUpActionList();
               for (int i=0; al != null && i < al.length; i++){

                  caption = (String)MBTPopUpActions.popUpCaptions.get(al[i]);
//                function arguments enums are not enumerated in MBTPopUpActions, so I have to handle them as they are:
                  if (caption == null){
                     menuItem = new JMenuItem(al[i]);
                     menuItem.addActionListener(new MdxBuilderTreePopUpActionListener(al[i], actionNode));
                     popup.add(menuItem);

                  } else if (al[i].equals(MBTPopUpActions.SEPARATOR)){
                     popup.addSeparator();

                  } else if (al[i].equals(MBTPopUpActions.OPEN_SUBMENU)){
//                   ********* adding submenu *********************************
//                   open submenu tag is followed by a submenu caption:
                     JMenu submenu = new JMenu(al[i + 1]);
                     i = i + 2;
                     while (!al[i].equals(MBTPopUpActions.CLOSE_SUBMENU)){
//                      for the time being, submenu items are plain strings, they are NOT enumerated in   MBTPopUpActions
                        menuItem = new JMenuItem(al[i]);
                        menuItem.addActionListener(new MdxBuilderTreePopUpActionListener(al[i], actionNode));
                        submenu.add(menuItem);
                        i = i + 1;
                     }
                     popup.add(submenu);

                  } else {
                     menuItem = new JMenuItem( (String) MBTPopUpActions.popUpCaptions.get(al[i]));
                     menuItem.addActionListener(new MdxBuilderTreePopUpActionListener(al[i], actionNode));
                     popup.add(menuItem);
                  }



               }
               popup.show(e.getComponent()
                          , e.getX()
                          , e.getY());
            }
         }
       }
   }

   /**
    *
    * Inner class that handles the selected (right-click)pop-up action.
    * <br>Forwards the action to the underlying MBTNode's handler.
    * @author Igor Mekterovic
    * @version 0.3
    */
   private class MdxBuilderTreePopUpActionListener implements ActionListener{
      String action;
      DefaultMutableTreeNode actionNode;
//      DefaultTreeModel _treeModel;

      public MdxBuilderTreePopUpActionListener(  String _action
                                               , DefaultMutableTreeNode _actionNode
                                               ){
         action = _action;
         actionNode = _actionNode;
//         treeModel = _treeModel;
      }
      public void actionPerformed(ActionEvent e){
         toolbar.actionToBePerformed();
         ((MBTNode) actionNode.getUserObject()).handlePopUpAction(
              action
            , actionNode
            , MdxBuilderTree.this.treeModel);
         ((DefaultTreeModel)tree.getModel()).nodeChanged(actionNode);
         tree.repaint();
         listener.mdxChanged(getMdx());
      }
   }

   /**
    * Inner class used for rendering tree nodes.
    * @author Igor Mekterovic
    * @version 0.3
    */
   private class MdxBuilderTreeRenderer extends JLabel implements TreeCellRenderer {
      private Color color;
      private MBTNode dragOverNode;
      public MdxBuilderTreeRenderer(MBTNode _dragOverNode, Color _color) {
         dragOverNode = _dragOverNode;
         color = _color;
         this.setOpaque(true);
      }

      public Component getTreeCellRendererComponent(
         JTree tree,
         Object value,
         boolean sel,
         boolean expanded,
         boolean leaf,
         int row,
         boolean hasFocus) {
         MBTNode node = (MBTNode)((DefaultMutableTreeNode) value).getUserObject();

         setText(node.toString());
         setIcon(node.getIcon());
         setToolTipText(node.getToolTip());
         if (node.isComplete())
            this.setBackground(null);
         else
            this.setBackground(AppColors.MBT_NODE_NOT_COMPLETE_BACKGROUND);

         if (node == dragOverNode ){
            this.setForeground(color);
         }else{
            this.setForeground(Color.black);
         }
         return this;
      }

   }


   /**
    * Entry point for testing the display of the tree.
    * @param args String[]
    */
   public static void main(String[] args) {
       JFrame frame = new JFrame("Testing MdxBuilderTree...");
       MdxBuilderTree dst = new MdxBuilderTree(null, "Sales");
       frame.setContentPane(dst);
       frame.addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent e) {
                System.exit(0);
             }
          });

      frame.pack();
      frame.setVisible(true);
    }

}
