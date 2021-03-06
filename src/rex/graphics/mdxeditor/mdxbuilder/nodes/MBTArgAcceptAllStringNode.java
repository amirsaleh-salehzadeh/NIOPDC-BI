package rex.graphics.mdxeditor.mdxbuilder.nodes;

import rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode;

/**
 * <p>Title: Warehouse Explorer</p>
 *
 * <p>Description: XMLA Client</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
* Provides a node that accepts everything - you can drop any DimensionTreeElement or MdxFunction on it.
* This class simply overrides the   void setAcceptableFlavorsArray(), everything else is inherited from MBTArgStringNode.
 * @author Igor
 * @version 0.3
* @see MBTArgStringNode
 */


public class MBTArgAcceptAllStringNode extends MBTArgStringNode{

   public MBTArgAcceptAllStringNode(){
      super();
   }

   public MBTArgAcceptAllStringNode(    String _argName
                                  ) {
      super(false, _argName, false, false);
   }

   public MBTArgAcceptAllStringNode(String _argName
                              , boolean _respawnArg
                              ) {
      super(false, _argName, _respawnArg, false);
   }

   public MBTArgAcceptAllStringNode(boolean _isHeadArg
                              , String _argName
                              ) {
      super(_isHeadArg, _argName, false, false);
   }

   public MBTArgAcceptAllStringNode(boolean _isHeadArg
                              , String _argName
                              , boolean _respawnArg
                              , boolean _optionalArg
                              ) {
      super(_isHeadArg, _argName, _respawnArg, _optionalArg);

   }
   /**
    * Overrides setAcceptableFlavorsArray() to accept all available flavors (objects).
    */
   void setAcceptableFlavorsArray() {
//    "come as you are..."
      acceptableFlavorsMimeTypes = new String[] {
         TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_DIMENSION_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_HIERARCHY_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_LEVEL_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_MEASURE_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_MEMBER_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_ARRAY_FUNCTION_NODE_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_CALCULATED_MEMBER_NODE_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_DIMENSION_FUNCTION_NODE_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_HIERARCHY_FUNCTION_NODE_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_LEVEL_FUNCTION_NODE_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_LEVEL_FUNCTION_NODE_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_LOGICAL_FUNCTION_NODE_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_MEMBER_FUNCTION_NODE_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_NAMED_SET_NODE_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_NUMERIC_FUNCTION_NODE_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_SET_FUNCTION_NODE_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_STRING_FUNCTION_NODE_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_TUPLE_FUNCTION_NODE_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_OPERATOR_FUNCTION_NODE_FLAVOR_STRING
         , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_MAKE_TUPLE_FUNCTION_NODE_FLAVOR_STRING

      };

   }

}
