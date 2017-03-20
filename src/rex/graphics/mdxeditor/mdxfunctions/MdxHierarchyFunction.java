package rex.graphics.mdxeditor.mdxfunctions;

import rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode;
/**
 * Represents hierarchical MDX function.
 * @author Igor Mekterovic
 * @version 0.3
 */

public class MdxHierarchyFunction extends MdxFunction{
   public MdxHierarchyFunction(String name, String description, String syntax) {
      super(name, description, syntax);
   }
   public String getFlavor(){
      return TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_HIERARCHY_FUNCTION_NODE_FLAVOR_STRING;
   }

}
