package rex.graphics.mdxeditor.mdxbuilder.nodes;

import javax.swing.*;
import javax.swing.tree.*;

import rex.graphics.mdxeditor.mdxbuilder.dnd.*;
import rex.graphics.mdxeditor.mdxfunctions.*;
import rex.utils.*;
import java.io.Serializable;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;

/**
 * Represents an Axis node (COLUMNS, ROWS, PAGES) in the MdxBuilderTree
 * @author Igor Mekterovic
 * @version 0.3
 */
public class DefaultMBTAxisNode extends DefaultMBTNode 
        implements Serializable,
                   LanguageChangedListener{
   private String axisCaption;
   private boolean nonEmpty;
   private static String childrenIndent = "  ";
   static ImageIcon icon;
   static {
      icon = S.getAppIcon("MBTAxisNode.gif");      
   }



   public DefaultMBTAxisNode(  String _axisCaption
                                        , boolean _nonEmpty
                                        ) {
      super();
      I18n.addOnLanguageChangedListener(this);
      axisCaption = _axisCaption;
      nonEmpty = _nonEmpty;
   }
   /**
    * Returns the MDX expression for this Axis.
    * @param indent String
    * @return String
    */
   public  String getMdx(String indent){
      if (this.getChildren() == null) return "";
      int i;
      StringBuffer mdx = new StringBuffer("");
      for (i=0; i < this.getChildren().size(); i++){
         if (mdx.length()==0)
            mdx.append("\n" + this.getChild(i).getMdx(indent + childrenIndent) );
         else
            mdx.append("\n" + addCommaAfterBlanks(this.getChild(i).getMdx(indent + childrenIndent)));
      }
      if (nonEmpty)
         return  indent + "NON EMPTY {"
            + "\n" + mdx
            + "\n" + indent + "} ON " + axisCaption;
      else
         return  indent + "{"
            + "\n" + mdx
            + "\n" + indent + "} ON " + axisCaption;
   }
   /**
    * Returns the Axis caption
    * @return String
    */
   public  String toString(){
      return axisCaption;
   }

   /**
    * Sets the data flavors (classes) that can be dropped on the Axis node.
    */
   void setAcceptableFlavorsArray(){
      acceptableFlavorsMimeTypes = new String[] {
                                     TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_LEVEL_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_DIMENSION_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_HIERARCHY_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_MEASURE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_MEMBER_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_SET_FUNCTION_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_CALCULATED_MEMBER_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_NAMED_SET_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_MEMBER_FUNCTION_NODE_FLAVOR_STRING
      };
   }
   /**
    * Returns icon to be displayed in a tree.
    * @return ImageIcon
    */
   public ImageIcon getIcon(){ return icon; }


   /**
    * Handles dropped node.
    * @param droppedData Object
    * @param containerNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public  void handleDrop(  Object droppedData
                           , DefaultMutableTreeNode containerNode
                           , DefaultTreeModel treeModel){
      if (droppedData instanceof MdxFunction) {

         this.addMdxFunctionChild( ((MdxFunction)droppedData)
                                             , containerNode
                                             , treeModel);
      } else if (droppedData instanceof MBTFunctionNode){

         this.moveMBTFunctionNode( ((MBTFunctionNode)droppedData)
                                             , containerNode
                                             , treeModel);

      } else {
         // This is a "wizard" part that Malay requested:
         // Mebers from the same dimensions are grouped and crossjoined against different dimensions
         // -> I'm adding crossjoins on-the-fly

         if (droppedData instanceof DimensionTreeElement){
            int mfdp = membersFromDifferentDimensionPresent(this, ((DimensionTreeElement)droppedData).getDimensionUniqueName());
            if (mfdp == 1){
              // case A -> remove all DimensionTreeElements || CalucaltedMembers and put them in a 1st argument of the new crossjoin function
              // add new member as a child of the 2nd argument
              addCrossjoinAndMoveLevelMembers(containerNode, (DimensionTreeElement)droppedData, treeModel);
              return;

            }else if(mfdp > 1){
               // There is already a crossjoin-tree present, I must add myself to the proper place:
               // Here we can distinguish 2 cases:
               // (B) there is an empty place for "me" in one of the crossjoins arguments -> add myself there
               //   B1 -> there is an empty argument
               //   B2 -> there is an argument that contains members of my dimension
               // (C) everything is full -> replace the "deepest" crossjoin argument with new crossjoin
               // Let's first hunt for B:
               if (addDroppedNodeToTheExistingCrossjoin( containerNode,  (DimensionTreeElement)droppedData, treeModel))
                  return;
               // If it's not B then it's C:
               if (addCrossjoinAndDroppedNode(containerNode,  (DimensionTreeElement)droppedData, treeModel))
                  return;

            }
         }
         // calculated members, named sets :
         try{
            this.addChildAndMBTMembersNode(droppedData
                                           , containerNode
                                           , treeModel);
         }catch(Exception e){
             /**
              * Commented, Don't want to print trace log on console.
              * by Prakash. 10-05-2007.
              */
            //e.printStackTrace();//Commented by Prakash
             /*
              * End of modification.
              */
         }

      }
   }

   /**
    * Removes all level members at specified level(parent) and adds a new Crossjoin whose arguments are:
    * <br>1st arg: removed (in fact - moved) nodes
    * <br>2nd arg: dropped node
    * @param parent DefaultMutableTreeNode
    * @param droppedData DimensionTreeElement
    * @param treeModel DefaultTreeModel
    */
   private void addCrossjoinAndMoveLevelMembers(DefaultMutableTreeNode parent, DimensionTreeElement droppedData, DefaultTreeModel treeModel){
      DefaultMutableTreeNode nodeToMove;
      // 1. ADD CROSSJOIN
      addMdxFunctionChild(getCrossjoin(), parent, treeModel);
      // AND GET A REFERENCE TO IT:
      DefaultMutableTreeNode cjNode = (DefaultMutableTreeNode) parent.getLastChild();
      // 2. MOVE ALL parent'S NODES THAT ARE MBTMembersNode ELEMENTS TO THE 1ST ARGUMENT OF THE CROSSJOIN
//      S.out("containerNode.getChildCount() = " + parent.getChildCount() + "\ncjNode = " + cjNode);
      for (int i = 0; i < parent.getChildCount(); i++) {
         if ( ( (DefaultMutableTreeNode) parent.getChildAt(i)).getUserObject() instanceof MBTMembersNode) {
            nodeToMove = (DefaultMutableTreeNode) parent.getChildAt(i);
            removeMySelfFromTheParent(nodeToMove, treeModel);
            i--;
            //  Adding DefaultMutableTreeNode relationship:
            treeModel.insertNodeInto(  nodeToMove
                                     , (DefaultMutableTreeNode) cjNode.getChildAt(0)
                                     , cjNode.getChildAt(0).getChildCount());
            //  Adding MBTNode relationship:
            ( (MBTNode) ( (DefaultMutableTreeNode) cjNode.getChildAt(0)).getUserObject()).addChild( (MBTNode)
               nodeToMove.getUserObject());

         }else if( ((DefaultMutableTreeNode) parent.getChildAt(i)).getUserObject() instanceof MBTFunctionNode
                  && !((MBTFunctionNode)((DefaultMutableTreeNode) parent.getChildAt(i)).getUserObject()).getFunctionName().equals("Crossjoin") ){
           // moving all other functions but the crossjoin i've just added
            moveMBTFunctionNode( (MBTFunctionNode) ((DefaultMutableTreeNode) parent.getChildAt(i)).getUserObject()
                                 , (DefaultMutableTreeNode) cjNode.getChildAt(0)
                                 , treeModel);
         }
      }
// 3. ADD DROPPED DATA AS THE 2nd ARGUMENT OF CROSSJOIN FUNCTION
      try {
         addChildAndMBTMembersNode(droppedData
                                   , (DefaultMutableTreeNode) cjNode.getChildAt(1)
                                   , treeModel);
      }
      catch (Exception e) {
          /**
           * Commented, Don't want to print trace log on console.
           * by Prakash. 10-05-2007.
           */
         //e.printStackTrace();//Commented by Prakash
          /*
           * End of modification.
           */
      }

   }
   /**
    * Adds a new Crossjoin and a dropped node. (in fact, it replaces the 2nd argument of the
    * existing Crossjoin with new Crossjoin whose arguments are replaced argument and dropped node).
    * Returns true on success, false otherwise.
    * @param parent DefaultMutableTreeNode
    * @param droppedData DimensionTreeElement
    * @param treeModel DefaultTreeModel
    * @return boolean
    */
   private boolean addCrossjoinAndDroppedNode(DefaultMutableTreeNode parent, DimensionTreeElement droppedData, DefaultTreeModel treeModel) {
      DefaultMutableTreeNode child;
      for (int i = parent.getChildCount()-1; i >= 0 ; i--) {
         child = ( (DefaultMutableTreeNode) parent.getChildAt(i));
         if (child.getUserObject() instanceof MBTFunctionNode
             && ( (MBTFunctionNode) child.getUserObject()).getFunctionName().equals("Crossjoin")) {
            // All crossjoines are complete, otherwise this wouldn't be called
            DefaultMutableTreeNode arg2 = (DefaultMutableTreeNode) child.getChildAt(1);
            MBTNode child1ofArg2 = (MBTNode) ( (DefaultMutableTreeNode) arg2.getChildAt(0)).getUserObject();
//            if (child1ofArg2 instanceof MBTMembersNode){
//               addCrossjoinAndMoveLevelMembers(arg2, (DimensionTreeElement)droppedData, treeModel);
//               return true;
//            }else if(child1ofArg2 instanceof MBTFunctionNode
//             && ( (MBTFunctionNode) child1ofArg2).getFunctionName().equals("Crossjoin")){
//               if (addCrossjoinAndDroppedNode(arg2, droppedData, treeModel))
//                  return true;
//            }
            if(child1ofArg2 instanceof MBTFunctionNode
             && ( (MBTFunctionNode) child1ofArg2).getFunctionName().equals("Crossjoin")){
               if (addCrossjoinAndDroppedNode(arg2, droppedData, treeModel))
                  return true;
            }

//            if (child1ofArg2 instanceof MBTMembersNode){
               addCrossjoinAndMoveLevelMembers(arg2, (DimensionTreeElement)droppedData, treeModel);
               return true;
//            }

         }
      }
      return false;
   }

   /**
    * Adds specified node to the existing Crossjoin node's argument.
    * <br>Depending on the dimension of the dropped object, it can be added to the 1st,
    * or 2nd argument of the crossjoin(to the one with the same dimension).
    * Also, it can be added to the empty Crossjoin's argument.
    * Returns true on success, false otherwise.
    * @param parent DefaultMutableTreeNode
    * @param droppedData DimensionTreeElement
    * @param treeModel DefaultTreeModel
    * @return boolean
    */
   private boolean addDroppedNodeToTheExistingCrossjoin(DefaultMutableTreeNode parent
                                                      , DimensionTreeElement droppedData
                                                      , DefaultTreeModel treeModel) {
      DefaultMutableTreeNode child;
      for (int i = 0; i < parent.getChildCount(); i++) {
         child = ( (DefaultMutableTreeNode) parent.getChildAt(i));
         if (child.getUserObject() instanceof MBTFunctionNode
             && ( (MBTFunctionNode) child.getUserObject()).getFunctionName().equals("Crossjoin")) {
            DefaultMutableTreeNode arg1 = (DefaultMutableTreeNode) child.getChildAt(0);
            DefaultMutableTreeNode arg2 = (DefaultMutableTreeNode) child.getChildAt(1);
            // I'm recursevly checking only Crossjoin nodes
            if ( ( (MBTFunctionNode) child.getUserObject()).isComplete()) {
               // Because the function is complete there has to be children:
               MBTNode child1ofArg1 = (MBTNode) ( (DefaultMutableTreeNode) arg1.getChildAt(0)).getUserObject();
               MBTNode child1ofArg2 = (MBTNode) ( (DefaultMutableTreeNode) arg2.getChildAt(0)).getUserObject();
               if (child1ofArg1 instanceof MBTMembersNode
                   &&
                   ( (MBTMembersNode) child1ofArg1).getDimensionUniqueName().equals(droppedData.getDimensionUniqueName())) {
                  try {
                     this.addChildAndMBTMembersNode(droppedData
                        , arg1
                        , treeModel);
                  }
                  catch (Exception e) {
                      /**
                       * Commented, Don't want to print trace log on console.
                       * by Prakash. 10-05-2007.
                       */
                     //e.printStackTrace();//Commented by Prakash
                      /*
                       * End of modification.
                       */
                  }
                  return true;
               }
               if (child1ofArg2 instanceof MBTMembersNode
                   &&
                   ( (MBTMembersNode) child1ofArg2).getDimensionUniqueName().equals(droppedData.getDimensionUniqueName())) {
                  try {
                     this.addChildAndMBTMembersNode(droppedData
                        , arg2
                        , treeModel);
                  }
                  catch (Exception e) {
                      /**
                       * Commented, Don't want to print trace log on console.
                       * by Prakash. 10-05-2007.
                       */
                     //e.printStackTrace();//Commented by Prakash
                      /*
                       * End of modification.
                       */
                  }
                  return true;
               }

               // this one is full -> go deeper
               if (addDroppedNodeToTheExistingCrossjoin( (DefaultMutableTreeNode) child.getChildAt(0), droppedData,
                  treeModel))
                  return true;
               if (addDroppedNodeToTheExistingCrossjoin( (DefaultMutableTreeNode) child.getChildAt(1), droppedData,
                  treeModel))
                  return true;
            }
            else {
               // This crossjoin is NOT complete -> add myself to an empty argument (usualy 2nd, but could be the 1st also if the user messes with the tree)
               if (arg1.getChildCount() == 0) {
                  try {
                     this.addChildAndMBTMembersNode(droppedData
                        , arg1
                        , treeModel);
                  }
                  catch (Exception e) {
                      /**
                       * Commented, Don't want to print trace log on console.
                       * by Prakash. 10-05-2007.
                       */
                     //e.printStackTrace();//Commented by Prakash
                      /*
                       * End of modification.
                       */
                  }
               }
               else {
                  try {
                     this.addChildAndMBTMembersNode(droppedData
                        , arg2
                        , treeModel);
                  }
                  catch (Exception e) {
                      /**
                       * Commented, Don't want to print trace log on console.
                       * by Prakash. 10-05-2007.
                       */
                     //e.printStackTrace();//Commented by Prakash
                      /*
                       * End of modification.
                       */
                  }
               }
               return true;
            }
         }
      }
      return false;
   }

   /**
    * Returns an MDxFunction object representing Crossjoin function.
    * @return MdxFunction
    */
   private MdxFunction getCrossjoin(){
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
      return new MdxSetFunction("Crossjoin"
                             ,I18n.getString("setFunction.crossJoin") 
                             , "Crossjoin(�Set1�, �Set2�)") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgSetNode("�Set1�")
               , new MBTArgSetNode("�Set2�")
            };
         }

      };

   }



   /**
    * Returns pop-up actions available for Axis node.
    * @return String[]
    */
   public String[] getPopUpActionList(){
      if (nonEmpty){
         return new String[] {
              MBTPopUpActions.DELETE_CHILDREN
            , MBTPopUpActions.EMPTY
         };
      }else{
         return new String[] {
              MBTPopUpActions.DELETE_CHILDREN
            , MBTPopUpActions.NON_EMPTY
         };

      }


   }
   /**
    * Handles selected pop-up action.
    * @param action String
    * @param actionNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public void handlePopUpAction(  String action
                                 , DefaultMutableTreeNode actionNode
                                 , DefaultTreeModel treeModel){

      if (action.equals(MBTPopUpActions.DELETE_CHILDREN)){
         removeAllChildrenFromTheTree(actionNode, treeModel);
      }else if (action.equals(MBTPopUpActions.EMPTY)){
         nonEmpty = false;
      }else if (action.equals(MBTPopUpActions.NON_EMPTY)){
         nonEmpty = true;
      }


   }
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   /*
    * Method inserted by Prakash.
    * Cincom Systems. 
    * 22 Nov 06.
    * Set nonEmpty flag (Used in Bi-Directional).
    */
   public void setNonEmpty(boolean bool)
   {
   	nonEmpty=bool;
   }

   /*
    * Method inserted by Prakash.
    * Cincom Systems. 
    * 22 Nov 06.
    * Set nonEmpty flag (Used in Bi-Directional).
    */
   public boolean getNonEmpty()
   {
   	return nonEmpty;
   }
   //End of addition by Prakash 
   
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
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.DELETE_CHILDREN ,I18n.getString("menu.deleteChildren"));
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.EMPTY ,I18n.getString("menu.allowEmpty"));
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.NON_EMPTY ,I18n.getString("menu.nonEmpty"));
   }
   /* end of modification for I18n */

}
