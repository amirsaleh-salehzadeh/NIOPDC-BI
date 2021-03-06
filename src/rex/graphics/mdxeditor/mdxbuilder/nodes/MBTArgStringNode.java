package rex.graphics.mdxeditor.mdxbuilder.nodes;

import java.awt.datatransfer.*;
import javax.swing.*;
import javax.swing.tree.*;

import rex.graphics.dimensiontree.elements.*;
import rex.graphics.mdxeditor.mdxbuilder.dnd.*;
import rex.graphics.mdxeditor.mdxfunctions.*;
import rex.utils.*;

/**
 * Node representing MdxFunction's string argument.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTArgStringNode 
        extends DefaultMBTArgNode
        implements LanguageChangedListener{
   protected String value;
   static ImageIcon icon;
   static {
        icon = S.getAppIcon("MBTArgStringNode.gif");
   }
   public MBTArgStringNode(){
      super();
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

   public MBTArgStringNode(    String _argName
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
        /* end of modification for I18n */

   }
   public MBTArgStringNode(    String _argName
                          , boolean _respawnArg
                            ) {
      super(false, _argName,  _respawnArg);
      I18n.addOnLanguageChangedListener(this);
        /* end of modification for I18n */

   }
   public MBTArgStringNode(    boolean _isHeadArg
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
   public MBTArgStringNode(    boolean _isHeadArg
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
    * @param indent String
    * @return String
    */
   public  String getMdx(String indent){
      if (value != null)
         return indent + value;
      else if (this.getChildren() == null)
         return "";
      else
         return this.getChild(0).getMdx(indent);
   }

   /**
    *
    * @return String
    */
   public String toString(){
      if(value == null)
         return super.toString();
      if (isOptionalArg()){
         return "<html><i>" + value + "</i></html>";
      }
      return value;
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
                                   ,  TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_CALCULATED_MEMBER_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_STRING_FUNCTION_NODE_FLAVOR_STRING

      };

   }

   /**
    * Returns an icon for the string node.
    * @return ImageIcon
    */
   public ImageIcon getIcon(){ return icon; }


   /**
    * Overrides the default metohod to allow only ONE node to be dropped here.
    * @param flavor DataFlavor
    * @return boolean
    */
   public boolean acceptDrop(DataFlavor flavor){
      return  acceptDropOnlyOneChild(flavor);
   }


   /**
    *
    * @param droppedData Object
    * @param containerNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public  void handleDrop(  Object droppedData
                           , DefaultMutableTreeNode containerNode
                           , DefaultTreeModel treeModel){

      if (droppedData instanceof DimensionTreeElement){
//         Something like this could be used to SWAP this string argument fro MBTMembersNode
//         Problem is,   MBTMembersNode is NOT a DefaultMBTArgNode :(
//         MBTMembersNode m = new MBTMembersNode((DimensionTreeElement)droppedData);
//         containerNode.setUserObject(m);
         value = ( (DimensionTreeElement) droppedData).getUniqueName();

      } else if (droppedData instanceof MBTCalculatedMemberNode) {

         value =  ( (MBTCalculatedMemberNode) droppedData).getName();

      } else if (droppedData instanceof MBTNamedSetNode) {

         value =  ( (MBTNamedSetNode) droppedData).getName();
      } else if (droppedData instanceof MdxFunction) {

         value = null;
         this.addMdxFunctionChild( ((MdxFunction)droppedData)
                                              , containerNode
                                              , treeModel);
      } else if (droppedData instanceof MBTFunctionNode) {

         this.moveMBTFunctionNode( ( (MBTFunctionNode) droppedData)
                                             , containerNode
                                             , treeModel);

      }


      maybeRespawnOnDrop(containerNode, treeModel);


   }

   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   /**
    * By Prakash
    * Set formula from MDX Query.
    * 19th Sept 06 
    */
   void setValue(String formula){
   	if(formula != null)
      value = formula;
   }
   
   /**
    * Displays a dialog to enter a string expression.
    */
   void setValue(){
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
       
       value = JOptionPane.showInputDialog(I18n.getString("msgText.stringExpr"), "");
         /* end of modification for I18n */

   }

   /**
    * Displays a dialog to edit current string expression.
    */
   void editValue(){
      String newValue;
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
      newValue = JOptionPane.showInputDialog(I18n.getString("msgText.stringExpr"), value);
        /* end of modification for I18n */

      if (newValue != null && newValue.trim().length()>0)
         value = newValue;
   }

   /**
    *
    * @return String[]
    */
   public String[] getPopUpActionList(){
      if (this.getChildren() == null)
         return new String[]{ MBTPopUpActions.CLEAR_VALUE
                         , MBTPopUpActions.INSERT_VALUE
                         , MBTPopUpActions.EDIT
                      };
      else
         return new String[]{ MBTPopUpActions.DELETE_CHILDREN };

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

      if (action.equals(MBTPopUpActions.CLEAR_VALUE)){
         value = null;
      }else if (action.equals(MBTPopUpActions.INSERT_VALUE)){
         setValue();
         maybeRespawnOnDrop(actionNode, treeModel);
      }else if (action.equals(MBTPopUpActions.EDIT)){
         editValue();
      }else if (action.equals(MBTPopUpActions.DELETE_CHILDREN)){
         this.removeAllChildrenFromTheTree(actionNode, treeModel);
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
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.INSERT_VALUE ,I18n.getString("menu.insertValue"));
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.CLEAR_VALUE ,I18n.getString("menu.clearValue"));
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.EDIT ,I18n.getString("menu.editValue"));
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.DELETE_CHILDREN ,I18n.getString("menu.deleteChildren"));

 }
    /* end of modification for I18n */

}
