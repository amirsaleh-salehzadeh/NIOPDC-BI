package rex.graphics.mdxeditor.mdxbuilder;
/**
 * Class that implements this interface is capable of providing and saving (to config file) defualt save directory
 * for saving mdx queries.
 * @author Igor Mekterovic
 * @version 0.3
 */
public interface HandlesMdxEditorSettings {
   /**
    * Returns default save directory.
    * @return String
    */
   public String getDefaultSaveDirectory();

   /**
    * Sets (saves) specified directory as default save directory.
    * @param dir String
    */
   public void setDefaultSaveDirectory(String dir);
}
