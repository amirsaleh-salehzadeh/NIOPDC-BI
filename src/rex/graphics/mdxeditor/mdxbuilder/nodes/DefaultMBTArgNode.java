package rex.graphics.mdxeditor.mdxbuilder.nodes;


import rex.utils.S;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Abstract class for all argument nodes to inherit.
 * @author Igor Mekterovic
 * @version 0.3
 */
public abstract class DefaultMBTArgNode extends DefaultMBTNode{

   private String argName;
   private boolean isHeadArg, respawnArg, optionalArg;
// HeadArg is the one that will be placed before function name when constructing the function expression
   static ImageIcon icon;
   static {
      icon = S.getAppIcon("todo.gif");
   }
   public ImageIcon getIcon(){ return icon; }

   public DefaultMBTArgNode(){
   }

   public DefaultMBTArgNode(    String _argName
                            ) {
      this(_argName, false);
   }

   public DefaultMBTArgNode(    String _argName
                              , boolean _respawnArg
                            ) {
      this(false, _argName,  _respawnArg);
   }
   public DefaultMBTArgNode(    boolean _isHeadArg
                              , String _argName
                            ) {
      this(_isHeadArg, _argName,  false);
   }
   public DefaultMBTArgNode(    boolean _isHeadArg
                              , String _argName
                              , boolean _respawnArg
                            ) {
      this(_isHeadArg, _argName,  _respawnArg, false);
   }
   public DefaultMBTArgNode(    boolean _isHeadArg
                              , String _argName
                              , boolean _respawnArg
                              , boolean _optionalArg
                            ) {
      super();
      argName = _argName;
      isHeadArg = _isHeadArg;
      respawnArg = _respawnArg;
      optionalArg = _optionalArg;
   }
   /**
    * Return true if this is a "head" argument, false otherwise.
    * Head argument is the on that comes before function name, e.g. for the <code>Item</code> function:
    * <p><code>
    *  Arg1.Item(Arg2)
    * </code></p>
    * Arg1 IS a head argument, and Arg2 is NOT.
    * @return boolean
    */
   public boolean isHeadArg(){
      return isHeadArg;
   }

   public void setHeadArg(boolean _isHeadArg){
      isHeadArg = _isHeadArg;
   }

   /**
    * Return true if this is a respawning argument. Respawing argument is argument that can be repeated N times in a function, e.g.
    * <p><code>
    * fName(Arg1 [, Arg1...], Arg2)
    * </code></p>
    * Arg1 is respawning, and Arg2 is NOT.
    * @return boolean
    */
   public boolean respawnOnDrop(){
      return respawnArg;
   }

   /**
    * Return true if this is an optional argument.
    * @return boolean
    */
   public boolean isOptionalArg(){
      return optionalArg;
   }

   public void setOptionalArg(boolean _optionalArg){
      optionalArg = _optionalArg;
   }

   /**
    * Sets the respawn property.
    * @see rex.graphics.mdxeditor.mdxbuilder.nodes.DefaultMBTArgNode#respawnOnDrop()
    * @param respawn boolean
    */
   public void setRespawn(boolean respawn){
      respawnArg = respawn;
   }



   /**
    * Returns argument name.
    * @return String
    */
   public String getArgName() {
      return argName;
   }


   public void setArgName(String _argName){
      argName = _argName;
   }

   /**
    * Returns a String representation. This is called when rendering a tree.
    * Return value is formatted according to the type of argument (optional, respawing)
    * @return String
    */
   public  String toString(){

      if (respawnOnDrop()){
         return argName + "[, " + argName + "...]";
      } else if (optionalArg){
         return "<html><i>[, " + argName + "]</i></html>";
      } else{
         return argName;
      }
   }
   /**
    * Respawns an argument if it is a respawn argument (adds another node just like this to the parent).
    * After that, calls <code>setRespawn(false);</code>
    * @param containerNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   void maybeRespawnOnDrop(  DefaultMutableTreeNode containerNode
                                , DefaultTreeModel treeModel){
         if (respawnOnDrop()) {
            setRespawn(false);
            S.out("respawning " + this.getClass().getName());
            Class c = this.getClass();
            DefaultMBTArgNode respawnedArg;
            try{
               respawnedArg = (DefaultMBTArgNode)c.newInstance();
               respawnedArg.setHeadArg (isHeadArg());
               respawnedArg.setArgName(this.getArgName());
               respawnedArg.setRespawn(true);
               respawnedArg.setOptionalArg(true); // anyone who gets respawned is optional

               //  I have to find index of this object and add a respawned item below it
               for (int i = 0; i < containerNode.getParent().getChildCount(); i++) {
                  if (containerNode.getParent().getChildAt(i) == containerNode) {
                     treeModel.insertNodeInto(new DefaultMutableTreeNode(respawnedArg)
                                              , (DefaultMutableTreeNode) containerNode.getParent()
                                              , i + 1);
                     ( (MBTNode) ( (DefaultMutableTreeNode) containerNode.getParent()).getUserObject()).addChildAt(
                        respawnedArg, i + 1);
                     break;
                  }
               }
            }catch(InstantiationException e){
                /**
                 * Commented, Don't want to print trace log on console.
                 * by Prakash. 10-05-2007.
                 */
               //e.printStackTrace();//Commented by Prakash
                /*
                 * End of modification.
                 */
            }catch(IllegalAccessException e){
                /**
                 * Commented, Don't want to print trace log on console.
                 * by Prakash. 10-05-2007.
                 */
               //e.printStackTrace();//Commented by Prakash
                /*
                 * End of modification.
                 */
            }



         }

      }



}
