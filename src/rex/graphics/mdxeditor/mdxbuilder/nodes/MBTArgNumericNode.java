package rex.graphics.mdxeditor.mdxbuilder.nodes;

import javax.swing.*;
import javax.swing.tree.*;

import rex.graphics.dimensiontree.elements.*;
import rex.graphics.mdxeditor.mdxbuilder.dnd.*;
import rex.graphics.mdxeditor.mdxfunctions.*;
import rex.utils.*;

/**
 * Node representing MdxFunction's numeric argument.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTArgNumericNode extends MBTArgStringNode{
//   private String value;
   static ImageIcon icon;
   static {
        icon = S.getAppIcon("MBTArgNumericNode.gif");
   }
   public MBTArgNumericNode(){
      super();
   }

   public MBTArgNumericNode(    String _argName
                            ) {
      super(false, _argName, false, false);
   }
   public MBTArgNumericNode(    String _argName
                          , boolean _respawnArg
                            ) {
      super(false, _argName,  _respawnArg, false);
   }
   public MBTArgNumericNode(    boolean _isHeadArg
                          , String _argName
                            ) {
      super(_isHeadArg, _argName,  false, false);
   }

   public MBTArgNumericNode(    boolean _isHeadArg
                          , String _argName
                          , boolean _respawnArg
                          , boolean _optionalArg
                            ) {
      super(_isHeadArg, _argName, _respawnArg, _optionalArg);

   }
   /**
    * Returns an icon for the numeric node.
    * @return ImageIcon
    */
   public ImageIcon getIcon(){ return icon; }


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
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_NUMERIC_FUNCTION_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_CALCULATED_MEMBER_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_OPERATOR_FUNCTION_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_MAKE_TUPLE_FUNCTION_NODE_FLAVOR_STRING

      };

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

      super.handleDrop(droppedData, containerNode, treeModel);

   }



   /**
    * Displays a dialog to enter a numeric expression.
    */
   void setValue(){
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
    /* implementing localization */
       value = JOptionPane.showInputDialog(I18n.getString("msgText.numericExpr"), "");
    /* end of modification for I18n */

   }

   /**
    * Displays a dialog to edit current numeric expression.
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
      newValue = JOptionPane.showInputDialog(I18n.getString("msgText.numericExpr"), value);
        /* end of modification for I18n */

      if (newValue != null && newValue.trim().length()>0)
         value = newValue;
   }

}
