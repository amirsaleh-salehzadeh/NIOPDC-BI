package rex.graphics.mdxeditor.mdxbuilder.nodes;

import java.util.ArrayList;

class RightClickFunction{
   String function, rvType;
   ArrayList appTypes;
   RightClickFunction(String _function, String _rvType, String type1){
      function = _function;
      rvType   = _rvType;
      appTypes = new ArrayList();
      appTypes.add(type1);
   }
   public void addType(String type){
      appTypes.add(type);
   }
   public boolean isApplicableTo(String type){
      for (int i=0; i<appTypes.size(); i++){
         if (((String)appTypes.get(i)).equals(type))
            return true;
      }
      return false;
   }
   public String toString(){
      return function;
   }
   public String getReturnValueType(){
      return rvType;
   }
}
