package rex.graphics;

//import rex.metadata.datasourcetree.elements.DataSourceTreeElement;
import java.util.Vector;
import java.io.Serializable;
import rex.utils.S;

/**
 * <p>Title: WHEX</p>
 * <p>Description: Tree element is an objects that contains user object and refrences to parent and children.  </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class TreeElement implements Serializable{
   private Object userObject;
   private TreeElement parent;
   private Vector children;

   public TreeElement(Object obj, TreeElement objParent) {
      userObject = obj;
      parent = objParent;
      children = new Vector();
   }
   public Object getUserObject(){
      return userObject;
   }
   public void addChild(TreeElement child){
//      S.out("adding child: " +  child);
      children.add(child);
   }
/// getter methods ///////////////////////////////////

   public String toString() {
      return userObject.toString();
   }

   public TreeElement getParent() {
      return parent;
   }

   public int getChildCount() {
//       S.out("calling tree element " + this + " getchildcount = " + children.size());
      return children.size();
   }

   public TreeElement getChildAt(int i) {
      return (TreeElement) children.elementAt(i);
   }

   public int getIndexOfChild(TreeElement child) {
      return children.indexOf(child);
   }

   public void dropChildren() {
      children.removeAllElements();
      // garbage collect?
   }

//   public String toString(){
//       return "TreeElement for class: " + getUserObject().getClass().getName()
//               + "\n VALUE: " + getUserObject();
//   }

}

