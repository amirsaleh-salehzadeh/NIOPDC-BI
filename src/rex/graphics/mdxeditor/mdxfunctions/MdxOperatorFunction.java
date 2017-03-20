package rex.graphics.mdxeditor.mdxfunctions;

import rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode;
/**
 * Represents binary operators, like +,-,*,/
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MdxOperatorFunction extends MdxFunction{
   public MdxOperatorFunction(String name, String description, String syntax) {
      super(name, description, syntax);
   }
   public String getFlavor(){
      return TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_OPERATOR_FUNCTION_NODE_FLAVOR_STRING;
   }

}
