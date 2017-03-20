package rex.graphics.mdxeditor.mdxbuilder.nodes;

import rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode;
import javax.swing.JOptionPane;
import rex.utils.I18n;
/**
 * Node representing MdxFunction's logical argument.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTArgLogicalNode extends MBTArgStringNode{

   public MBTArgLogicalNode(){
      super();
   }

   public MBTArgLogicalNode(    String _argName
                            ) {
      super(false, _argName, false, false);
   }
   public MBTArgLogicalNode(    String _argName
                          , boolean _respawnArg
                            ) {
      super(false, _argName,  _respawnArg, false);
   }
   public MBTArgLogicalNode(    boolean _isHeadArg
                          , String _argName
                            ) {
      super(_isHeadArg, _argName,  false, false);
   }

   public MBTArgLogicalNode(    boolean _isHeadArg
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
                                     TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_LOGICAL_FUNCTION_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_STRING_FUNCTION_NODE_FLAVOR_STRING // I'll alow a String also
      };

   }

   /**
    * Displays a dialog to enter a logical expression.
    */
   void setValue(){
/**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
/* implementing localization */
      value = JOptionPane.showInputDialog(I18n.getString("msgText.logicalExpr"), "");
    /* end of modification for I18n */

   }

   /**
    * Displays a dialog to edit current logical expression.
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
      newValue = JOptionPane.showInputDialog(I18n.getString("msgText.logicalExpr"), value);
/* end of modification for I18n */

      if (newValue != null && newValue.trim().length()>0)
         value = newValue;
   }



}
