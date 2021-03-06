package rex.graphics.mdxeditor.mdxbuilder.nodes;

import rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import rex.graphics.dimensiontree.elements.HierarchyElement;
import rex.utils.I18n;
/**
 * Node representing MdxFunction's hierarchy argument.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTArgHierarchyNode extends MBTArgStringNode{

   public MBTArgHierarchyNode(){
      super();
   }

   public MBTArgHierarchyNode(    String _argName
                            ) {
      super(false, _argName, false, false);
   }
   public MBTArgHierarchyNode(    String _argName
                          , boolean _respawnArg
                            ) {
      super(false, _argName,  _respawnArg, false);
   }
   public MBTArgHierarchyNode(    boolean _isHeadArg
                          , String _argName
                            ) {
      super(_isHeadArg, _argName,  false, false);
   }

   public MBTArgHierarchyNode(    boolean _isHeadArg
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
                                     TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_HIERARCHY_FUNCTION_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_HIERARCHY_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_STRING_FUNCTION_NODE_FLAVOR_STRING // I'll alow a String also
      };

   }


   /**
    * Displays a dialog to enter a hierarchy expression.
    */
   void setValue(){
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 

       value = JOptionPane.showInputDialog(I18n.getString("msgText.hierarchyExpr"), "");
         /* end of modification for I18n */

   }

   /**
    * Displays a dialog to edit current hierarchy expression.
    */
   void editValue(){
      String newValue;
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 

       newValue = JOptionPane.showInputDialog(I18n.getString("msgText.hierarchyExpr"), value);
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

      if (droppedData instanceof HierarchyElement){
         value = ( (HierarchyElement) droppedData).getUniqueName();
         maybeRespawnOnDrop(containerNode, treeModel);
      } else {
         super.handleDrop(droppedData, containerNode, treeModel);
      }

   }

}
