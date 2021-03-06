package rex.graphics.mdxeditor.mdxfunctions;
import rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode;
/**
 * Represents numeric MDX function.
 * @author Igor Mekterovic
 * @version 0.3
 */

public class MdxNumericFunction extends MdxFunction{

   public MdxNumericFunction(String name, String description, String syntax) {
      super(name, description, syntax);
   }
   public String getFlavor(){
      return TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_NUMERIC_FUNCTION_NODE_FLAVOR_STRING;
   }

}
