package rex.graphics.mdxeditor.mdxfunctions;

import rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode;

/**
 * Represents dimension MDX function.
 * @author Igor Mekterovic
 * @version 0.3
 */

public class MdxDimensionFunction  extends MdxFunction{
   public MdxDimensionFunction(String name, String description, String syntax) {
      super(name, description, syntax);
   }
   public String getFlavor(){
      return TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_DIMENSION_FUNCTION_NODE_FLAVOR_STRING;
   }


}
