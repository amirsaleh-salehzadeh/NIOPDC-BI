package rex.graphics.filtertree.elements;


import javax.swing.ImageIcon;

import rex.utils.*;
import rex.metadata.UniqueNameElement;
import rex.metadata.ServerMetadata;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.graphics.dimensiontree.elements.DimensionElement;
import rex.graphics.dimensiontree.elements.HierarchyElement;
import rex.graphics.dimensiontree.elements.LevelElement;
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
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
 /*  Added LanguageChangedListener to implement I18n  */
 
public class FilterTreeRootElement implements 
        DimensionTreeElement, 
        UniqueNameElement ,
        LanguageChangedListener{ 
   private boolean flattenDimensions;
   private DimensionTreeElement filterElement;
   private boolean displayInTab;


   public FilterTreeRootElement(DimensionTreeElement _filterElement) {
//      super();
/**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ /*adding this class to the list of classes that implement I18n */

       I18n.addOnLanguageChangedListener(this);
         /* end of modification for I18n */

      filterElement  = _filterElement;
      displayInTab = false; //default value

   }

   static ImageIcon icon;
   static {
      icon = S.getAppIcon("FilterTreeRootElement.gif");
   }
//
//   public DimensionTreeElement[] getChildren() {
//      boolean tempFlattenDim = filterElement.getFlattenDimensions();
//      filterElement.setFlattenDimensions(flattenDimensions);
//      DimensionTreeElement[] retVal = filterElement.getChildren();
//      filterElement.setFlattenDimensions(tempFlattenDim);
//      return retVal;
//   }
   public DimensionTreeElement[] getChildren(boolean noMatterWhat){
      // other restrictons are already set, i.e. cube and catalog name
      filterElement.getRestrictions().setDimensionUniqueName(filterElement.getDimensionUniqueName());

      // depending on the way dimensions are layed(flattened, grouped) out in dimTree I can get different objects here
      // In fact I'll get DimensionElement only if I'm testing (main)
      if (filterElement instanceof DimensionElement){
         S.out("instanceof DimensionElement setting NULL");
         filterElement.getRestrictions().setHierarchyUniqueName(null);
      }else if (filterElement instanceof HierarchyElement){
         filterElement.getRestrictions().setHierarchyUniqueName(((HierarchyElement)filterElement).getUniqueName());
         S.out("instanceof HierarchyElement setting " + ((HierarchyElement)filterElement).getUniqueName());
      }else if (filterElement instanceof LevelElement){
         S.out("instanceof LevelElement setting " + ((LevelElement)filterElement).getHierarchyUniqueName());
         filterElement.getRestrictions().setHierarchyUniqueName(((LevelElement)filterElement).getHierarchyUniqueName());
      }
//      S.out("filterElement.class= " + filterElement.getClass());

      return filterElement.getServerMetadata().getFilterLevelList(filterElement.getRestrictions()
                                                                , filterElement.getProperties());

   }


   public String toString() {
      //return "<HTML><B>Filter on:" + filterElement.getCaption() + "</B></HTML>";
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      *//* implementing localization */
       return "<HTML><B>" + I18n.getString("toolTip.filterOn") +
               filterElement.getCaption() + "</B></HTML>";
         /* end of modification for I18n */

   }

   public  ImageIcon getIcon() {
      return icon;
   }

   public static ImageIcon getFilterIcon() {
      return icon;
   }

   public String getToolTip() {
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  *//* implementing localization */
      return I18n.getString("toolTip.filterDimensions");
        /* end of modification for I18n */

   }

   public void setFlattenDimensions(boolean _flattenDimensions){
      flattenDimensions =_flattenDimensions;
   }


   public String[] getPopUpActionList() {
      if (displayInTab){
         return new String[] {FilterTreePopUpActions.LOSE_FILTER
                         , FilterTreePopUpActions.APPLY_FILTER
                         , FilterTreePopUpActions.MOVE_TO_SPLIT_PANE };
      }else{
         return new String[] {FilterTreePopUpActions.LOSE_FILTER
                         , FilterTreePopUpActions.APPLY_FILTER
                         , FilterTreePopUpActions.MOVE_TO_TAB };
      }
   }

   public String getUniqueName(){
/**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  *//* implementing localization */
      return I18n.getString("toolTip.filteTree");
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
      return filterElement.getServerMetadata();
   }
   public XMLADiscoverRestrictions getRestrictions(){
      return filterElement.getRestrictions();
   }
   public XMLADiscoverProperties   getProperties(){
      return filterElement.getProperties();
   }
   public String getDimensionUniqueName(){
      return filterElement.getDimensionUniqueName();
   }

   public boolean isDisplayInTab(){
      return displayInTab;
   }
   public void setDisplayInTab(boolean _displayInTab){
      displayInTab = _displayInTab;
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
        FilterTreePopUpActions.popUpCaptions.put(FilterTreePopUpActions.LOSE_FILTER,I18n.getString("menu.loseFilter") );
        FilterTreePopUpActions.popUpCaptions.put(FilterTreePopUpActions.APPLY_FILTER,I18n.getString("menu.applyFilter") );
        FilterTreePopUpActions.popUpCaptions.put(FilterTreePopUpActions.MOVE_TO_TAB,I18n.getString("menu.moveToTab") );
       
 }
   /* end of modification for I18n */

}
