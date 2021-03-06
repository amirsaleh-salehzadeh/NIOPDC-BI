package rex.graphics.mdxeditor.mdxfunctions;

import javax.swing.ImageIcon;
import rex.utils.S;
import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTNode;

/**
 * Abstract class representing an MDX function.
 * @author Igor Mekterovic
 * @version 0.3
 */
public abstract class MdxFunction{
      private String name, description, syntax;
      private MBTNode[] args;

      static ImageIcon icon;
      static {
         icon = S.getAppIcon("mdxfunction.gif");
      }


      public MdxFunction(String _name, String _description, String _syntax){
         name = _name;
         description = _description;
         syntax = _syntax;
      }

      /**
       * Returns function name.
       * @return String
       */
      public String getName(){
         return name;
      }

      /**
       * Returns function description.
       * @return String
       */
      public String getDescription(){
         return description;
      }

      /**
       * Returns function syntax.
       * @return String
       */
      public String getSyntax(){
         return syntax;
      }

      /**
       * Returns a string representation of this function.
       * @return String
       */
      public String toString(){
         return name;
      }

      /**
       * Returns a tooltip for the function.
       * @return String
       */
      public String getToolTip(){
         return "<html><b>Name:</b>   " + name
                + "<br><b>Desc:</b>   " + description
                + "<br><b>Sytnax:</b> " + syntax
                + "</html>";
      }

      /**
       * Returns function icon.
       * @return ImageIcon
       */
      public ImageIcon getIcon(){
         return icon;
      }
//      public void setFunctionArguments(MBTNode[] _args){
//         args = _args;
//      }

      /**
       * Returns MBTNodes representing function's arguments.
       * @return MBTNode[]
       */
      public MBTNode[] getFunctionArguments(){
         return args;
      }

      public abstract String getFlavor();

   }
