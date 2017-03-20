package rex.graphics.mdxeditor.mdxfunctions;

import rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode;
/**
 * Represents leve MDX function.
 * @author Igor Mekterovic
 * @version 0.3
 */

public class MdxLevelFunction extends MdxFunction{
   public MdxLevelFunction(String name, String description, String syntax) {
      super(name, description, syntax);
   }

   public String getFlavor(){
      return TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_LEVEL_FUNCTION_NODE_FLAVOR_STRING;
   }

}
