package rex.graphics.mdxeditor.mdxfunctions;

import rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode;

/**
 * Represents a custom "make tuple" function. It is used for creating tuples with members.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MdxMakeTupleFunction extends MdxFunction{
   public MdxMakeTupleFunction(String name, String description, String syntax) {
      super(name, description, syntax);
   }
   public String getFlavor(){
      return TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_MAKE_TUPLE_FUNCTION_NODE_FLAVOR_STRING;
   }

}
