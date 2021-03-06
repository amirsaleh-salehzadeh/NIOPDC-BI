package rex.graphics.dimensiontree.elements;

import javax.swing.ImageIcon;
import java.io.Serializable;
import rex.metadata.ServerMetadata;
import rex.xmla.XMLADiscoverRestrictions;
import rex.xmla.XMLADiscoverProperties;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public interface DimensionTreeElement extends Serializable{

    public DimensionTreeElement[] getChildren(boolean noMatterWhat);
    public ImageIcon getIcon();
    public String getToolTip();
    public String toString();
    public String[] getPopUpActionList();
    public String getUniqueName();
    public String getCaption();
//    public String getQueryMembersExpression(); --> moved to QueryElement
    public boolean isEnabled();
    public void setEnabled(boolean newValue);
    public int getChildrenCount();

// FilterTree needs these guys ( FilterTreeRootElement )
// also: FilterTreeLevelElement and FilterTreeMemberElement extend and ovverride these methods
    public ServerMetadata getServerMetadata();
    public XMLADiscoverRestrictions getRestrictions();
    public XMLADiscoverProperties   getProperties();

    public String getDimensionUniqueName();


}
