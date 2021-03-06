package rex.graphics.mdxeditor.mdxbuilder.nodes;


import javax.swing.JOptionPane;
import rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode;
import rex.utils.I18n;
/**
 * Node representing MdxFunction's tuple argument.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTArgTupleNode extends MBTArgStringNode{
//   private String value;
   public MBTArgTupleNode(){
      super();
   }

   public MBTArgTupleNode(    String _argName
                            ) {
      super(false, _argName, false, false);
   }
   public MBTArgTupleNode(    String _argName
                          , boolean _respawnArg
                            ) {
      super(false, _argName,  _respawnArg, false);
   }
   public MBTArgTupleNode(    boolean _isHeadArg
                          , String _argName
                            ) {
      super(_isHeadArg, _argName,  false, false);
   }
   public MBTArgTupleNode(    boolean _isHeadArg
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
                                     TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_TUPLE_FUNCTION_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_MEMBER_FUNCTION_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_CALCULATED_MEMBER_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_STRING_FUNCTION_NODE_FLAVOR_STRING // I'll alow a String also
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_TUPLE_FUNCTION_NODE_FLAVOR_STRING
      };

   }


   /**
    * Displays a dialog to enter a tuple expression.
    */
   void setValue(){
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /* implementing localization */       
     value = JOptionPane.showInputDialog(I18n.getString("msgText.tupleExpr"), "");
       /* end of modification for I18n */

   }

        
   /**
    * Displays a dialog to edit current tuple expression.
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
      newValue = JOptionPane.showInputDialog(I18n.getString("msgText.tupleExpr"), value);
        /* end of modification for I18n */

      if (newValue != null && newValue.trim().length()>0)
         value = newValue;
   }



}
