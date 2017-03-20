package rex.graphics.mdxeditor.mdxbuilder.nodes;

import java.awt.datatransfer.DataFlavor;
import java.io.Serializable;

public class MBTFromNode extends DefaultMBTAxisNode implements Serializable{
   String cubeName;
   public MBTFromNode(String _cubeName) {
      super("FROM " + _cubeName , false);
      cubeName = _cubeName;
   }

   public String[] getPopUpActionList(){ return null;}

   public String getMdx(String indent){
      if (cubeName.trim().startsWith("[")){
         return indent + "FROM " + cubeName;
      }else{
         return indent + "FROM [" + cubeName + "]";
      }

   }
   public  boolean acceptDrop(DataFlavor f){return false;} /* cannot drop onto this node */
}
