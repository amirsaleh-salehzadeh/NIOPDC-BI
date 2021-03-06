package rex.graphics.filtertree.elements;

import javax.swing.ImageIcon;
import rex.metadata.ServerMetadata;
import rex.utils.*;

import rex.metadata.QueryElement;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.metadata.UniqueNameElement;
import rex.graphics.dimensiontree.elements.MemberElement;
import rex.xmla.XMLADiscoverRestrictions;
import rex.xmla.XMLADiscoverProperties;


/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 0.3
 */


/*
 <row>
  <CATALOG_NAME>Test</CATALOG_NAME>
  <CUBE_NAME>Ispit</CUBE_NAME>
  <DIMENSION_UNIQUE_NAME>[RbrIzlaskaNaIspit]</DIMENSION_UNIQUE_NAME>
  <HIERARCHY_UNIQUE_NAME>[RbrIzlaskaNaIspit]</HIERARCHY_UNIQUE_NAME>
  <LEVEL_UNIQUE_NAME>[RbrIzlaskaNaIspit].[NazivRbrIzlaskaNaIspit]</LEVEL_UNIQUE_NAME>
  <LEVEL_NUMBER>1</LEVEL_NUMBER>
  <MEMBER_ORDINAL>61</MEMBER_ORDINAL>
  <MEMBER_NAME>61. izlazak</MEMBER_NAME>
  <MEMBER_UNIQUE_NAME>[RbrIzlaskaNaIspit].[All RbrIzlaskaNaIspit].[61. izlazak]</MEMBER_UNIQUE_NAME>
  <MEMBER_TYPE>1</MEMBER_TYPE>
  <MEMBER_CAPTION>61. izlazak</MEMBER_CAPTION>
  <CHILDREN_CARDINALITY>0</CHILDREN_CARDINALITY>
  <PARENT_LEVEL>0</PARENT_LEVEL>
  <PARENT_UNIQUE_NAME>[RbrIzlaskaNaIspit].[All RbrIzlaskaNaIspit]</PARENT_UNIQUE_NAME>
  <PARENT_COUNT>1</PARENT_COUNT>
  <MEMBER_KEY>61</MEMBER_KEY>
  <IS_PLACEHOLDERMEMBER>false</IS_PLACEHOLDERMEMBER>
  <IS_DATAMEMBER>false</IS_DATAMEMBER>
  </row>

 */

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
 /*  Added LanguageChangedListener to implement I18n  */


public class FilterTreeMemberElement 
        extends MemberElement 
        implements LanguageChangedListener{

//   private String catalogName;
//   private String cubeName;
//   private String dimensionUniqueName;
//   private String hierarchyUniqueName;
//   private String levelUniqueName;
//   private int levelNumber;
//   private String memberName;
//   private String memberUniqueName;
//   private String memberCaption;
//   private int memberType;
//   private int memberOrdinal;
//   private int childrenCardinality;
   private boolean nodeEnabled;


//   private ClsXMLAProxDiscoverRestrictions restrictions;
//   private ClsXMLAProxDiscoverProperties   properties;

//   private ServerMetadata parent;

   static ImageIcon iconEnabled, iconDisabled;

   static {
       iconEnabled = S.getAppIcon("member_selected.gif");
       iconDisabled = S.getAppIcon("member_not_selected.gif");
   }
   public FilterTreeMemberElement(
                             ServerMetadata svm
                           , XMLADiscoverRestrictions _restrictions
                           , XMLADiscoverProperties   _properties
                           , String _catalogName
                           , String _cubeName
                           , String _dimensionUniqueName
                           , String _hierarchyUniqueName
                           , String _levelUniqueName
                           , int    _levelNumber
                           , String _memberName
                           , String _memberUniqueName
                           , String _memberCaption
                           , int    _memberType
                           , int    _memberOrdinal
                           , int    _childrenCardinality) {

      super(svm
            , _restrictions
            , _properties
            , _catalogName
            , _cubeName
            , _dimensionUniqueName
            , _hierarchyUniqueName
            , _levelUniqueName
            , _levelNumber
            , _memberName
            , _memberUniqueName
            , _memberCaption
            , _memberType
            , _memberOrdinal
            , _childrenCardinality
              );
       /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
      /*adding this class to the list of classes that implement I18n */
      I18n.addOnLanguageChangedListener(this);
     
     /* end of modification for I18n */

      nodeEnabled = true;

   }

   public DimensionTreeElement[] getChildren(boolean noMatterWhat){
//      S.out("FilterTreeMemberElement returns 0 children");
      if (getChildrenCount() > 0){
//         S.out("-------------------------------------------\n"
//               + "Getting children for " + memberCaption + "(" + memberUniqueName+ ") + children count=" + childrenCardinality
//               + "\nwith Restrictions:\n" + restrictions.getRestrictionList());

//       All these objects SHARE the same restrictions/properties, so I must set it exeactly before
//       query execution beacause it gets overwritten !!
         getRestrictions().setMemberUniqueName(getUniqueName());
         getRestrictions().setTreeOp(XMLADiscoverRestrictions.MD_TREEOP_CHILDREN);
         // comment next line if you don't want "deep" tree (only 2 members levels)
         getRestrictions().setLevelUniqueName(null);
         return getServerMetadata().getFilterTreeMembersList(getRestrictions(), getProperties());
      }else{
         return null;
      }
   }




   public ImageIcon getIcon(){
//      S.out("filter tree member element getIcon called");
      if (isEnabled()){
         return iconEnabled;
      }else{
         return iconDisabled;
      }
   }






   public String[] getPopUpActionList(){
      if (isEnabled()){
         return new String[] {
              FilterTreePopUpActions.DISABLE_THIS_ELEMENT
            , FilterTreePopUpActions.ENABLE_ONLY_THIS_ELEMENT
            , FilterTreePopUpActions.ENABLE_ALL_BUT_THIS_ELEMENT
         };
      }else{
         return new String[] {
              FilterTreePopUpActions.ENABLE_THIS_ELEMENT
            , FilterTreePopUpActions.ENABLE_ONLY_THIS_ELEMENT
            , FilterTreePopUpActions.ENABLE_ALL_BUT_THIS_ELEMENT
         };

      }
   }

   public boolean isEnabled(){
      return nodeEnabled;
   }
   public void setEnabled(boolean newValue){
      nodeEnabled = newValue;
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
        FilterTreePopUpActions.popUpCaptions.put(FilterTreePopUpActions.ENABLE_ALL_BUT_THIS_ELEMENT,I18n.getString("menu.enableAllExceptThis") );
        FilterTreePopUpActions.popUpCaptions.put(FilterTreePopUpActions.ENABLE_ONLY_THIS_ELEMENT,I18n.getString("menu.enableJustThisElem") );
        FilterTreePopUpActions.popUpCaptions.put(FilterTreePopUpActions.ENABLE_THIS_ELEMENT,I18n.getString("menu.enableThisElem") );
        FilterTreePopUpActions.popUpCaptions.put(FilterTreePopUpActions.DISABLE_THIS_ELEMENT,I18n.getString("menu.disableElem") );
        FilterTreePopUpActions.popUpCaptions.put(FilterTreePopUpActions.LOSE_FILTER,I18n.getString("menu.loseFilter") );
        FilterTreePopUpActions.popUpCaptions.put(FilterTreePopUpActions.APPLY_FILTER,I18n.getString("menu.applyFilter") );
        FilterTreePopUpActions.popUpCaptions.put(FilterTreePopUpActions.MOVE_TO_TAB,I18n.getString("menu.moveToTab") );
        FilterTreePopUpActions.popUpCaptions.put(FilterTreePopUpActions.MOVE_TO_SPLIT_PANE,I18n.getString("menu.moveToSplitPane") );
       
 }
  /* end of modification for I18n */

}
