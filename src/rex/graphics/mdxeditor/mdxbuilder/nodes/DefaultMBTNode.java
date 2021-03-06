package rex.graphics.mdxeditor.mdxbuilder.nodes;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.datatransfer.DataFlavor;
import rex.utils.S;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeModel;
import rex.graphics.mdxeditor.mdxfunctions.MdxFunction;
import java.util.Enumeration;
import rex.graphics.dimensiontree.elements.*;
import java.io.Serializable;
import rex.graphics.mdxeditor.mdxfunctions.MdxOperatorFunction;
import rex.graphics.mdxeditor.mdxfunctions.MdxMakeTupleFunction;
import rex.utils.I18n;
/**
 * Abstract class that implements many important methods
* , mainly related to adding and removing children nodes, and handling d'n'd and pop-up actions.
 * @author Igor Mekterovic
 * @version 0.3
 */
public abstract class DefaultMBTNode implements MBTNode, Serializable{
   private ArrayList children;
   protected String[] acceptableFlavorsMimeTypes;
   public DefaultMBTNode() {
      setAcceptableFlavorsArray();
   }


   /**
    * Returns an array representing children nodes.
    * @return MBTNode[]
    */
   public MBTNode[] getMdxBuilderTreeNodes(){
      if (children != null)
         return (MBTNode[]) children.toArray( new MBTNode[]{});
      else
         return null;
   }

   /**
    * Adds a child node.
    * @param child MBTNode
    */
   public void addChild(MBTNode child){
      if (children == null) children = new ArrayList();
      children.add(child);
   }

   /**
    * Adds a child node at specified index.
    * @param child MBTNode
    * @param index int
    */
   public void addChildAt(MBTNode child, int index){
      if (children == null) children = new ArrayList();
      children.add(index, child);
   }


   /**
    * Removes all children nodes.
    */
   public void removeAllChildren(){
      children = null;
   }

   /**
    * Removes given child from the parent.
    * @param child MBTNode
    */
   public void removeChildFromParent(MBTNode child){
//      for(int i=0; children != null && i<children.size(); i++){
//         S.out("i=" + i + " comparing " + child + " == " + children.get(i));
//         if (child == children.get(i)){
//            children.remove(i);
//         }
//      }
      if (children != null ){
         children.remove(child);
      }
      if (children.size()==0)
         removeAllChildren();
   }

// this is a often used combo, so I've moved it up here:
   /**
    * Adds a child to the JTree (to the <code>parentNode</code>) and also adds a child to the
    * userObject (<code>MBTNode</code>) that is contained in the parentNode. Depending on the type of the droppedObject
    * different <code>MBTNode</code> child nodes are generated.
    * Throws an exception in the case of unsupported type of droppedObject
    * @param droppedObject Object
    * @param parentNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    * @throws Exception
    */
   void addChildAndMBTMembersNode(Object droppedObject, DefaultMutableTreeNode parentNode, DefaultTreeModel treeModel) throws Exception{
//     depending on the type of the object dropped, I'm instantiating  MBTMembersNode or MBTImmutableMembersNode
      MBTNode dd;
      if (droppedObject instanceof DimensionTreeElement){
         dd = new MBTMembersNode(  (DimensionTreeElement)droppedObject);
      } else if (droppedObject instanceof MBTCalculatedMemberNode){
         dd = new MBTImmutableMembersNode(  ((MBTCalculatedMemberNode)droppedObject).getName()
                                          , ((MBTCalculatedMemberNode)droppedObject).getDimensionUniqueName());         
      } else if (droppedObject instanceof MBTNamedSetNode){
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */          
          dd = new MBTImmutableMembersNode(((MBTNamedSetNode)droppedObject).getName()
                                          , I18n.getString("str.noParent"));
      } else {
        
          throw new Exception(I18n.getString("exception.unsupportObj"));
            /* end of modification for I18n */

      }

      ((MBTNode)parentNode.getUserObject()).addChild(dd);
      DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(dd);
      treeModel.insertNodeInto(  childNode
                               , parentNode
                               , parentNode.getChildCount());
   }

//   void addChildAndMBTImmutableMembersNode(String childName, DefaultMutableTreeNode parentNode, DefaultTreeModel treeModel){
//      MBTImmutableMembersNode dd = new MBTImmutableMembersNode(childName);
//      addChild(dd);
//      DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(dd);
//      treeModel.insertNodeInto(  childNode
//                               , parentNode
//                               , parentNode.getChildCount());
//   }



   /**
    * Adds a MBTFunctionNode type child. This action occurs when a users drags an MdxFunction
    * from the MdxFunctionTree and drops it to the MdxBuilderTree.
    * @param droppedFunction MdxFunction
    * @param parentNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   void addMdxFunctionChild(MdxFunction droppedFunction, DefaultMutableTreeNode parentNode, DefaultTreeModel treeModel){
      MBTFunctionNode func;
      // I have to check by hand for these non-mdx functions because they create mdx differently:
      if (droppedFunction instanceof MdxOperatorFunction){
         func = new MBTOperatorFunctionNode(  droppedFunction.getName()
                                           , droppedFunction.getDescription()
                                           , droppedFunction.getSyntax()
                                           , droppedFunction.getFlavor());
      }else if (droppedFunction instanceof MdxMakeTupleFunction){
         func = new MBTMakeTupleFunctionNode(  droppedFunction.getName()
                                           , droppedFunction.getDescription()
                                           , droppedFunction.getSyntax()
                                           , droppedFunction.getFlavor());
      }else{
         func = new MBTFunctionNode(  droppedFunction.getName()
                                  , droppedFunction.getDescription()
                                  , droppedFunction.getSyntax()
                                  , droppedFunction.getFlavor());

      }

      DefaultMutableTreeNode funcNode =  new DefaultMutableTreeNode(func);

      ((MBTNode)parentNode.getUserObject()).addChild(func);
      treeModel.insertNodeInto(    funcNode
                                 , parentNode
                                 , parentNode.getChildCount());


      MBTNode[] args = droppedFunction.getFunctionArguments();
      for (int i=0; args!=null && i<args.length; i++){
         func.addChild(args[i]);
         treeModel.insertNodeInto( new DefaultMutableTreeNode(args[i])
                                 , funcNode
                                 , funcNode.getChildCount());

      }
   }

   /**
    * Moves a MBTFunctionNode. This action occurs when a user drags MdxFunction from and to the MdxBuilderTree.
    * <br>Node (in fact the whole subtree whose root is this node) is removed from its original position
    * and then placed as a child of <code>parentNode</code> argument.
    * @param fNode MBTFunctionNode
    * @param parentNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   void moveMBTFunctionNode( MBTFunctionNode fNode, DefaultMutableTreeNode parentNode, DefaultTreeModel treeModel){
      DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
      DefaultMutableTreeNode nodeToMove=null;
      if (root != null){
         for (Enumeration e = root.breadthFirstEnumeration(); e.hasMoreElements(); ) {
            DefaultMutableTreeNode current = (DefaultMutableTreeNode) e.nextElement();

            if (fNode.equals(current.getUserObject())) {
               nodeToMove = current;
//               S.out("found it");
               break;
            }
         }
      }

      if (nodeToMove == null){
         S.out("ERROR - Could not find a node to move!");
      }

//    Now, for the sensitive part: there are 2 relationships to break an 2 to make
//    1. Breaking both MBTNode and DefaultMutableTreeNode relationships:
      removeMySelfFromTheParent(nodeToMove, treeModel);
//    2. Adding DefaultMutableTreeNode relationship:
      treeModel.insertNodeInto(    nodeToMove
                                 , parentNode
                                 , parentNode.getChildCount());
//    3. Adding MBTNode relationship:
//      MBTNode parentUserObject = (MBTNode) ((DefaultMutableTreeNode)nodeToMove.getParent()).getUserObject();
      ((MBTNode)parentNode.getUserObject()).addChild(fNode);

//    That's it - the children of the moved node are not messed with and they "move" correctly.

   }




   /**
    * Removes a given node from it's parent. This is called from pop-up actions available on the right-click.
    * @param actionNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   void removeMySelfFromTheParent(DefaultMutableTreeNode actionNode, DefaultTreeModel treeModel){

      //       remove myself from the parent:
      MBTNode node = (MBTNode)((DefaultMutableTreeNode)actionNode).getUserObject();
      ((MBTNode)((DefaultMutableTreeNode)actionNode.getParent()).getUserObject()).removeChildFromParent(node);
      treeModel.removeNodeFromParent(actionNode);
   }

   /**
    * Removes all the children from a given JTree node. This is called from pop-up actions available on the right-click.
    * @param actionNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public void removeAllChildrenFromTheTree(DefaultMutableTreeNode actionNode, DefaultTreeModel treeModel){
      ((MBTNode)actionNode.getUserObject()).removeAllChildren();
      int cnt = actionNode.getChildCount();
      for (int i = 0; i < cnt; i++) {
         treeModel.removeNodeFromParent((DefaultMutableTreeNode) actionNode.getChildAt(0));
      }
   }

   /**
    * Returns positive number if there are members from different dimension (than specified) present
    * <br>as children of Axis node (returns 1), or as members of Crossjoin function's arguments (returns >1).
    * <br>Otherwise returns 0.
    * @param parent MBTNode
    * @param dimName String
    * @return int
    */
   protected int membersFromDifferentDimensionPresent(MBTNode parent, String dimName){
      MBTNode[] children = parent.getMdxBuilderTreeNodes();
      for (int i=0; children != null && i<children.length; i++){
         if (   children[i] instanceof MBTMembersNode
             && !((MBTMembersNode)children[i]).getDimensionUniqueName().equals(dimName)){
            return 1;
         }else if (   children[i] instanceof MBTCalculatedMemberNode
             && !((MBTCalculatedMemberNode)children[i]).getDimensionUniqueName().equals(dimName)){
            return 1;
         }else if (children[i] instanceof MBTFunctionNode
                   && ((MBTFunctionNode)children[i]).getFunctionName().equals("Crossjoin")){
            // I'm recursevly checking only Crossjoin nodes
            int rv = membersFromDifferentDimensionPresent(children[i].getMdxBuilderTreeNodes()[0], dimName);
            if (rv > 0)
               return 1 + rv;
            rv = membersFromDifferentDimensionPresent(children[i].getMdxBuilderTreeNodes()[1], dimName);
            if (rv > 0)
               return 2 + rv;
         }

      }
      return 0;
   }


   /**
    * Returns an ArrayList holding children nodes.
    * @return ArrayList
    */
   public ArrayList getChildren(){
      return children;
   }

   /**
    * Returns a child for the specified position.
    * @param i int
    * @return MBTNode
    */
   public MBTNode getChild(int i){
      return (children == null) ? null: (MBTNode)children.get(i) ;
   }

   /**
    * Returns true if this node accepts the specified DataFlavor.
    * <br>This method checks the mime types that each node sets with <code>setAcceptableFlavorsArray()</code>
    * <br>This is called during d'n'd when a mouse cursor dragging some object is hovering over this node.
    * @param flavor DataFlavor
    * @return boolean
    */
   public boolean acceptDrop(DataFlavor flavor){
      for (int i=0; acceptableFlavorsMimeTypes!= null && (i < acceptableFlavorsMimeTypes.length); i++){
//         S.out("comparing " + flavor.getMimeType() + " == " + acceptableFlavorsMimeTypes[i]);
//         TO BE DONE - WHY DOESNT THIS WORK?
//         if (flavor.isMimeTypeEqual(acceptableFlavorsMimeTypes[i])){
         if (flavor.getMimeType().equalsIgnoreCase(acceptableFlavorsMimeTypes[i])){
//            S.out("TRUE: " + flavor.getMimeType() + " = " + acceptableFlavorsMimeTypes[i]);
            return true;
         }
      }
      return false;
   }

//
   /**
    * Same as acceptDrop, but allows for only one child.
    * <br>Some classes(e.g. MBTArgStringNode) override acceptDrop to call acceptDropOnlyOneChild
    * @see rex.graphics.mdxeditor.mdxbuilder.nodes.MBTArgStringNode
    * @param flavor DataFlavor
    * @return boolean
    */
   boolean acceptDropOnlyOneChild(DataFlavor flavor){
      for (int i=0; acceptableFlavorsMimeTypes!= null && (i < acceptableFlavorsMimeTypes.length); i++){
//         S.out("comparing " + flavor.getMimeType() + " == " + acceptableFlavorsMimeTypes[i]);
//         TO BE DONE - WHY DOESNT THIS WORK?
//         if (flavor.isMimeTypeEqual(acceptableFlavorsMimeTypes[i])){
         if (this.getChildren()==null && flavor.getMimeType().equalsIgnoreCase(acceptableFlavorsMimeTypes[i])){
//            S.out("TRUE: " + flavor.getMimeType() + " = " + acceptableFlavorsMimeTypes[i]);
            return true;
         }
      }
      return false;
   }

   /**
    * Add comma after leading spaces, e.g.
    * <p><code>
    * "   {Measures.Members}" -> "   , {Measures.Members}"
    * </code></p>
    * This is used when formatting MDX statement.
    * @param mdx String
    * @return String
    */
   String addCommaAfterBlanks(String mdx){
      int i=0;
      if (mdx.length()==0) return mdx;
      while (mdx.charAt(i) == ' ')
         i++;
      if (i>0)
         return mdx.substring(0, i) + ", " + mdx.substring(i);
      else
         return ", " + mdx.substring(i);
   }
   /**
    * Returns a tooltip.
    * @return String
    */
   public String getToolTip(){
      return toString();
   }

   /**
    * Returns true if this is a complete node. All nodes are always complete except function nodes.
    * Function nodes are complete only if all of their non-optional arguments are specified.
    * @return boolean
    */
   public boolean isComplete(){return true;}

   /**
    * Returns corresponding MDX statement with specified indent.
    * @param indent String
    * @return String
    */
   public abstract String getMdx(String indent);

   /**
    * Returns a String representation. This is displayed in a JTree.
    * @return String
    */
   public abstract String toString();

   /**
    * Sets the mime types that are acceptable for the drop action.
    */
   abstract void setAcceptableFlavorsArray();

   /**
    * Handles a drop action for the specified dropped data, parent(container) node onto which data is dropped, and a treeModel
    * @param droppedData Object
    * @param containerNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public abstract void handleDrop(  Object droppedData
                                   , DefaultMutableTreeNode containerNode
                                   , DefaultTreeModel treeModel);





 /**
  * Returns an icon to be displayed in a JTree.
  * @return ImageIcon
  */
   public abstract ImageIcon getIcon();

   /**
    * Returns pop-up actions available (on right-click)  for this node.
    * @return String[]
    */
   public abstract String[] getPopUpActionList();

   /**
    * Handles selected pop-up action.
    * @param action String
    * @param actionNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public abstract void handlePopUpAction(  String action
                              , DefaultMutableTreeNode actionNode
                              , DefaultTreeModel treeModel);


}
