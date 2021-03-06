package rex.graphics.mdxeditor.mdxbuilder.nodes;

/**
 *
 * Implememnts a custom (non-mdx) function that creates tuple.
 * <br>Children items are enclosed in brackets and delimited with comma.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTMakeTupleFunctionNode extends MBTFunctionNode{
   private static String childrenIndent = "";

   public MBTMakeTupleFunctionNode(  String _functionName, String desc, String syntax, String _flavor ) {
      super(_functionName, desc, syntax, _flavor);
   }

   /**
    * Returns mdx statement that creates tuple out of child members.
    * <br>Children items are enclosed in brackets and delimited with comma.
    * @param indent String
    * @return String
    */
   public  String getMdx(String indent){
      boolean firstArgument = true;
      int i;
      StringBuffer mdx = new StringBuffer("(");
      DefaultMBTArgNode child;

      for (i=0; i < this.getChildren().size(); i++){
         child = (DefaultMBTArgNode) this.getChild(i);

         if ( !((DefaultMBTArgNode)this.getChild(i)).isOptionalArg()
              || !this.getChild(i).getMdx("").trim().equals("")){
            if (firstArgument){
               mdx.append(this.getChild(i).getMdx(indent + childrenIndent));
               firstArgument = false;
            }else{
               mdx.append( addCommaAfterBlanks(this.getChild(i).getMdx(indent + childrenIndent)));
            }
         }


      }
      mdx.append(")");
      return mdx.toString();

   }


}
