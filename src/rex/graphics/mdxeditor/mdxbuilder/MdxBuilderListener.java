package rex.graphics.mdxeditor.mdxbuilder;

/**
 * Interface that all objects that want to listen to the MdxBuilder must implement.
 * @author Igor Mekterovic
 * @version 0.3
 */
public interface MdxBuilderListener {
   /**
    * MdxBuilder uses this method to signal to the listener that it has a new, updated, MDX statement.
    * @param newMdx String
    */
   public void mdxChanged(String newMdx);
   public void clearEditor();

}
