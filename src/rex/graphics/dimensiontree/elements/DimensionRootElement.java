package rex.graphics.dimensiontree.elements;

import javax.swing.ImageIcon;
import rex.metadata.ServerMetadata;
import rex.xmla.XMLADiscoverRestrictions;
import rex.xmla.XMLADiscoverProperties;
import rex.utils.*;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
 /*  Added LanguageChangedListener to implement I18n  */

public class DimensionRootElement implements 
        DimensionTreeElement,
        LanguageChangedListener{ 
   private boolean flattenDimensions;

   public DimensionRootElement() {
       I18n.addOnLanguageChangedListener(this);
   }

   static ImageIcon icon;
   static {
      icon = S.getAppIcon("DimensionRoot.gif");
   }

   public DimensionTreeElement[] getChildren(boolean noMatterWhat) {
      return null;
   }

   public String toString() {
      return "<HTML><B>Dimensions</B></HTML>";
   }

   public ImageIcon getIcon() {
      return icon;
   }

   public String getToolTip() {
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
      /* implementing localization */
        return I18n.getString("toolTip.dimensionBrowsing"); 
        /* end of modification for I18n */

   }

   public void setFlattenDimensions(boolean _flattenDimensions){
      flattenDimensions =_flattenDimensions;
   }


   public String[] getPopUpActionList() {
      //return null;
      if (flattenDimensions)
         return new String[] {PopUpActions.GROUP_HIERARCHIES_BY_DIMENSION};
      else
         return new String[] {PopUpActions.FLATTEN_DIMENSIONS};
   }
   public String getUniqueName(){
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
      /* implementing localization */
      return I18n.getString("toolTip.uniqueName");
        /* end of modification for I18n */

   }
   public String getCaption(){
      return null;
   }
   public String getQueryMembersExpression(){
      return null;
   }
   public boolean isEnabled(){
      return false;
   }
   public void setEnabled(boolean newValue){
      // no way Hose!
   }
   public int getChildrenCount(){
      return 1;
   }
   public ServerMetadata getServerMetadata(){
      return null;
   }
   public XMLADiscoverRestrictions getRestrictions(){
      return null;
   }
   public XMLADiscoverProperties   getProperties(){
      return null;
   }
   public String getDimensionUniqueName(){
      return null;
   }
      /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
/**
  * Helper method that is executed when the language is changed
  */
    public void languageChanged(LanguageChangedEvent evt) {
        PopUpActions.popUpCaptions.put(PopUpActions.FLATTEN_DIMENSIONS,I18n.getString("menu.flattenDims"));
        PopUpActions.popUpCaptions.put(PopUpActions.GROUP_HIERARCHIES_BY_DIMENSION,I18n.getString("menu.groupDim"));      
    }
    /* end of modification for I18n */

}
