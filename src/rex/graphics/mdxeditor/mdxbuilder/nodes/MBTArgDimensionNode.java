package rex.graphics.mdxeditor.mdxbuilder.nodes;

import rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode;
import javax.swing.JOptionPane;
import rex.utils.I18n;

/**
 * Node representing MdxFunction's dimension argument.
 * @author Igor Mekterovic
 * @version 0.3
 */

public class MBTArgDimensionNode extends MBTArgStringNode{

   public MBTArgDimensionNode(){
      super();
   }

   public MBTArgDimensionNode(    String _argName
                            ) {
      super(false, _argName, false, false);
   }
   public MBTArgDimensionNode(    String _argName
                          , boolean _respawnArg
                            ) {
      super(false, _argName,  _respawnArg, false);
   }
   public MBTArgDimensionNode(    boolean _isHeadArg
                          , String _argName
                            ) {
      super(_isHeadArg, _argName,  false, false);
   }

   public MBTArgDimensionNode(    boolean _isHeadArg
                          , String _argName
                          , boolean _respawnArg
                          , boolean _optionalArg
                            ) {
      super(_isHeadArg, _argName, _respawnArg, _optionalArg);

   }

   /**
    * Overrides setAcceptableFlavorsArray() to accept only nodes that can be evaluated as a Dimension.
    */
   void setAcceptableFlavorsArray(){
      acceptableFlavorsMimeTypes = new String[] {
                                     TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_DIMENSION_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_DIMENSION_FUNCTION_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_STRING_FUNCTION_NODE_FLAVOR_STRING // I'll alow a String also
      };

   }


   /**
    * Displays a dialog to enter a custom expression.
    */
   void setValue(){
       /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
       value = JOptionPane.showInputDialog(I18n.getString("msgText.dimensionExpr"), "");
        /* end of modification for I18n */

   }

   /**
    * Displays a dialog to edit current expression.
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
      newValue = JOptionPane.showInputDialog(I18n.getString("msgText.dimensionExpr"), value);
        /* end of modification for I18n */

      if (newValue != null && newValue.trim().length()>0)
         value = newValue;
   }


}
