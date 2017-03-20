package rex.graphics.mdxeditor.mdxfunctions;
import rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode;
/**
 * Represents string MDX function.
 * @author Igor Mekterovic
 * @version 0.3
 */

public class MdxStringFunction extends MdxFunction{

   public MdxStringFunction(String name, String description, String syntax) {
      super(name, description, syntax);
   }

   public String getFlavor(){
      return TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_STRING_FUNCTION_NODE_FLAVOR_STRING;
   }

}
