package rex.graphics.datasourcetree.elements;

import javax.swing.ImageIcon;
import rex.metadata.ServerMetadata;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public interface DataSourceTreeElement {
  // public void refresh();
   public DataSourceTreeElement[] getChildren();
   public ImageIcon getIcon();
   public String getToolTip();
  // public String getName();
   public String toString();
   public String[] getPopUpActionList();
   public ServerMetadata getServerMetaData();
   public String getDataSourceInfo();

}