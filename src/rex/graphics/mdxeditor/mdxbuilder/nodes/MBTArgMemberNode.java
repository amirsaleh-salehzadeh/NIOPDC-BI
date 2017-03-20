package rex.graphics.mdxeditor.mdxbuilder.nodes;

import rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import rex.graphics.dimensiontree.elements.MemberElement;
import rex.utils.I18n;
/**
 * Node representing MdxFunction's member argument.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTArgMemberNode extends MBTArgStringNode{

   public MBTArgMemberNode(){
      super();
   }

   public MBTArgMemberNode(    String _argName
                            ) {
      super(false, _argName, false, false);
   }
   public MBTArgMemberNode(    String _argName
                          , boolean _respawnArg
                            ) {
      super(false, _argName,  _respawnArg, false);
   }
   public MBTArgMemberNode(    boolean _isHeadArg
                          , String _argName
                            ) {
      super(_isHeadArg, _argName,  false, false);
   }

   public MBTArgMemberNode(    boolean _isHeadArg
                          , String _argName
                          , boolean _respawnArg
                          , boolean _optionalArg
                            ) {
      super(_isHeadArg, _argName, _respawnArg, _optionalArg);

   }

   /**
    *
    */
   void setAcceptableFlavorsArray(){
      acceptableFlavorsMimeTypes = new String[] {
                                     TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_MEMBER_FUNCTION_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_MEMBER_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_CALCULATED_MEMBER_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_STRING_FUNCTION_NODE_FLAVOR_STRING // I'll alow a String also
      };

   }


   /**
    * Displays a dialog to enter a member expression.
    */
   void setValue(){
  
/**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
/* implementing localization */
       value = JOptionPane.showInputDialog(I18n.getString("msgText.memberExpr"), "");
         /* end of modification for I18n */

   }

   /**
    * Displays a dialog to edit current member expression.
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
      newValue = JOptionPane.showInputDialog(I18n.getString("msgText.memberExpr"), value);
        /* end of modification for I18n */

             
      if (newValue != null && newValue.trim().length()>0)
         value = newValue;
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

      if (droppedData instanceof MemberElement){
         value = ( (MemberElement) droppedData).getUniqueName();
         maybeRespawnOnDrop(containerNode, treeModel);
      } else {
         super.handleDrop(droppedData, containerNode, treeModel);
      }

   }


}
