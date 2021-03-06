package rex.graphics.mdxeditor.mdxbuilder;

import java.io.OutputStream;
import java.io.InputStream;

/**
 * Class that implements this interface is capable of saving (serializing) it's state into a stream
 *  so that it can resume it later.
 * @see rex.graphics.mdxeditor.mdxbuilder.MdxBuilderToolbar
 * @author Igor Mekterovic
 * @version 0.3
 */
public interface CanSaveAndResumeState {
   /**
    * Saves a state (serializes the object that it wants to resume later) to the specified stream.
    * @see rex.graphics.mdxeditor.mdxbuilder.MdxBuilderToolbar
    * @param memStream OutputStream
    */
   public void saveState(OutputStream memStream);

   /**
    * Resumes a state (deserializes) of the object from the specified stream.
    * @see rex.graphics.mdxeditor.mdxbuilder.MdxBuilderToolbar
    * @param memStream InputStream
    */
   public void resumeState(InputStream memStream);

   /**
    * Sets a state to the initial position, e.g. Clears a query.
    */
   public void clearState();

}
