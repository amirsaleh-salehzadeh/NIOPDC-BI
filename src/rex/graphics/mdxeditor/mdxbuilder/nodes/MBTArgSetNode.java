package rex.graphics.mdxeditor.mdxbuilder.nodes;


import javax.swing.*;
import javax.swing.tree.*;

import rex.graphics.mdxeditor.mdxbuilder.dnd.*;
import rex.graphics.mdxeditor.mdxfunctions.*;
import rex.utils.*;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;

/**
 *
 * Node representing MdxFunction's set argument. Unlike other arguments, set is a container node and can have children.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTArgSetNode extends DefaultMBTArgNode
    implements LanguageChangedListener{

   private static String childrenIndent = "  ";
   static ImageIcon icon;
   static {
      icon = S.getAppIcon("MBTArgSetNode.gif");
   }
   public MBTArgSetNode(){
      super();
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /*adding this class to the list of classes that implement I18n */

      I18n.addOnLanguageChangedListener(this);
   }

   public MBTArgSetNode(    String _argName
                            ) {
      super(false, _argName, false);
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /*adding this class to the list of classes that implement I18n */

      I18n.addOnLanguageChangedListener(this);
   }
   public MBTArgSetNode(    String _argName
                          , boolean _respawnArg
                            ) {
      super(false, _argName,  _respawnArg);
      /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
/*adding this class to the list of classes that implement I18n */

      I18n.addOnLanguageChangedListener(this);
        /* end of modification for I18n */

   }
   public MBTArgSetNode(    boolean _isHeadArg
                          , String _argName
                            ) {
      super(_isHeadArg, _argName,  false);
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /*adding this class to the list of classes that implement I18n */

      I18n.addOnLanguageChangedListener(this);
        /* end of modification for I18n */

   }
   public MBTArgSetNode(    boolean _isHeadArg
                          , String _argName
                          , boolean _respawnArg
                          , boolean _optionalArg
                            ) {
      super(_isHeadArg, _argName, _respawnArg, _optionalArg);
      /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /*adding this class to the list of classes that implement I18n */

      I18n.addOnLanguageChangedListener(this);
        /* end of modification for I18n */

   }

   /**
    *
    * @return String
    */
   public String getToolTip(){
      return "SET NODE:" + getArgName();
   }

   /**
    *
    * @param indent String
    * @return String
    */
   public  String getMdx(String indent){
      if (this.getChildren() == null) return "";
      int i;
      StringBuffer mdx = new StringBuffer("");
      for (i=0; i < this.getChildren().size(); i++){
         if (mdx.length()==0)
            mdx.append(this.getChild(i).getMdx(indent + childrenIndent));
         else
            mdx.append("\n" +  addCommaAfterBlanks(this.getChild(i).getMdx(indent + childrenIndent)));
      }
      if (mdx.indexOf("\n") == -1)
//         If there is only one function/member then return single line MDX
         return indent + "{" + mdx.toString().trim() + "}";
      else
         return indent + "{"
            + "\n" + mdx
            + "\n" + indent + "}";

   }



   /**
    *
    */
   void setAcceptableFlavorsArray(){
      acceptableFlavorsMimeTypes = new String[] {
                                     TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_LEVEL_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_DIMENSION_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_HIERARCHY_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_MEASURE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_MEMBER_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_SET_FUNCTION_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_NAMED_SET_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_CALCULATED_MEMBER_NODE_FLAVOR_STRING
      };
   }

   /**
    * Returns an icon for the set node.
    * @return ImageIcon
    */
   public ImageIcon getIcon(){ return icon; }


   /**
    *
    * @param droppedData Object
    * @param containerNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public  void handleDrop(  Object droppedData
                           , DefaultMutableTreeNode containerNode
                           , DefaultTreeModel treeModel){
     if (droppedData instanceof MdxFunction){

         this.addMdxFunctionChild( ((MdxFunction)droppedData)
                                             , containerNode
                                             , treeModel);
      } else if (droppedData instanceof MBTFunctionNode){

         this.moveMBTFunctionNode( ( (MBTFunctionNode) droppedData)
                                  , containerNode
                                  , treeModel);

      } else if (droppedData instanceof DimensionTreeElement){
         if (membersFromDifferentDimensionPresent(this, ((DimensionTreeElement)droppedData).getDimensionUniqueName())>0){
             /**
              * Copyright (C) 2006 CINCOM SYSTEMS, INC.
              * All Rights Reserved
              * Copyright (C) 2006 Igor Mekterovic
              * All Rights Reserved
              */ 
             /*adding this class to the list of classes that implement I18n */

            JOptionPane.showMessageDialog(   null
                                        , I18n.getString("msgText.setMember")
                                        , I18n.getString("msgTitle.addMember")
                                        , JOptionPane.INFORMATION_MESSAGE);
              /* end of modification for I18n */


         }else{
            try {
               this.addChildAndMBTMembersNode(droppedData
                                              , containerNode
                                              , treeModel);
            }
            catch (Exception e) {
               //e.printStackTrace();//Commented by Prakash
            }
         }

      } else{
         try {
            this.addChildAndMBTMembersNode(droppedData
                                           , containerNode
                                           , treeModel);
         }
         catch (Exception e) {
            //e.printStackTrace();//Commented by Prakash
         }

      }


      maybeRespawnOnDrop(containerNode, treeModel);

   }


   /**
    *
    * @return String[]
    */
   public String[] getPopUpActionList(){
      return new String[]{MBTPopUpActions.DELETE_CHILDREN};
   }

   /**
    *
    * @param action String
    * @param actionNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public void handlePopUpAction(  String action
                                 , DefaultMutableTreeNode actionNode
                                 , DefaultTreeModel treeModel){

      if (action.equals(MBTPopUpActions.DELETE_CHILDREN)){
         removeAllChildrenFromTheTree(actionNode, treeModel);
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
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.DELETE_CHILDREN ,I18n.getString("menu.deleteChildren"));
}
    /* end of modification for I18n */

}
