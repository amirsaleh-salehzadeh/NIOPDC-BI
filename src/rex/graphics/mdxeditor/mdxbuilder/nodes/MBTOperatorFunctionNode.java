package rex.graphics.mdxeditor.mdxbuilder.nodes;

import rex.utils.S;

public class MBTOperatorFunctionNode extends MBTFunctionNode{
   private static String childrenIndent = "";
   public MBTOperatorFunctionNode(  String _functionName, String desc, String syntax, String _flavor ) {
      super(_functionName, desc, syntax, _flavor);
   }

   /**
    *
    * @param indent String
    * @return String
    */
   public  String getMdx(String indent){
      boolean firstArgument = true;
      int i;
      StringBuffer mdx = new StringBuffer("");
      DefaultMBTArgNode child;

      for (i=0; i < this.getChildren().size(); i++){
         child = (DefaultMBTArgNode) this.getChild(i);
         if (child.isHeadArg()){
//          There HAS TO BE exactly one head argument per function
            mdx.append(indent + "(" + this.getChild(i).getMdx("") +  " " + getFunctionName() + " " );
         } else {
//            skip the optional argument if it is not set
            if ( !((DefaultMBTArgNode)this.getChild(i)).isOptionalArg()
                 || !this.getChild(i).getMdx("").trim().equals("")){
               if (firstArgument){
                  mdx.append(this.getChild(i).getMdx(indent + childrenIndent));
                  firstArgument = false;
               }else{
                  mdx.append(" " + getFunctionName() + " " + this.getChild(i).getMdx(indent + childrenIndent));
               }
            }
         }

      }
      mdx.append(")");
      return mdx.toString();

   }


}
