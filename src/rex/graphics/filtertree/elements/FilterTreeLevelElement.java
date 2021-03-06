package rex.graphics.filtertree.elements;


import rex.metadata.ServerMetadata;
import org.w3c.dom.Node;


import rex.utils.*;

import rex.graphics.dimensiontree.elements.*;
import rex.metadata.UniqueNameElement;
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

public class FilterTreeLevelElement extends    LevelElement
                                    implements DimensionTreeElement, UniqueNameElement{

//   private String catalogName;
//   private String schemaName;
//   private String cubeName;
//   private String dimensionUniqueName;
//   private String hierarchyUniqueName;
//   private String levelName;
//   private String levelUniqueName;
//   private String levelGuid;
//   private String levelCaption;
//   private int levelNumber;
//   private int levelCardinality;
//   private int levelType;
//   private String description;
//   private int customRollupSettings;
//   private int levelUniqueSettings;
//   private boolean levelIsVisible;
//   private String levelOrderingProperty;
//   private int levelDbtype;
//   private String levelMasterUniqueName;
//   private String levelNameSqlColumnName;
//   private String levelKeySqlColumnName;
//   private String levelUniqueNameSqlColumnName;
//   private boolean nodeEnabled;


//   private ClsXMLAProxDiscoverRestrictions restrictions;
//   private ClsXMLAProxDiscoverProperties   properties;

//   private ServerMetadata parent;

//   static ImageIcon[] icon;
//
//   static {
//       icon = new ImageIcon[]{
//            new ImageIcon("C:/javasource/warehouse explorer/images/level_1.gif")
//          , new ImageIcon("C:/javasource/warehouse explorer/images/level_2.gif")
//          , new ImageIcon("C:/javasource/warehouse explorer/images/level_3.gif")
//          , new ImageIcon("C:/javasource/warehouse explorer/images/level_4.gif")
//          , new ImageIcon("C:/javasource/warehouse explorer/images/level_5.gif")
//          , new ImageIcon("C:/javasource/warehouse explorer/images/level_6.gif")
//          , new ImageIcon("C:/javasource/warehouse explorer/images/level_7.gif")
//          , new ImageIcon("C:/javasource/warehouse explorer/images/level_8.gif")
//          , new ImageIcon("C:/javasource/warehouse explorer/images/level_9.gif")
//          , new ImageIcon("C:/javasource/warehouse explorer/images/level_10.gif")
//       };
//   }
   public FilterTreeLevelElement(  ServerMetadata svm
                           , Node rowNode
                           , XMLADiscoverRestrictions _restrictions
                           , XMLADiscoverProperties   _properties) {

      super(svm, rowNode, _restrictions, _properties);
      S.out("creatd FilterTreeLevelElement");


   }

   public DimensionTreeElement[] getChildren(boolean noMatterWhat){
      if (getUniqueName().startsWith("[Measures]")){
         S.out("assert:FilterTree shouldn't hold Measure dimension!");
         return getServerMetadata().getMeasuresList(getRestrictions(), getProperties());
      }else{
//         S.out("calling getFilterTreeMembersList");
         return getServerMetadata().getFilterTreeMembersList(getRestrictions(), getProperties());
      }
   }


//   public String toString(){
//      if (levelName == null)
//         return "not initialized";
//      else
//         return levelName;
//
//   }
//
//   public ImageIcon getIcon(){
//      return icon[levelNumber];
//   }
//
//   public String getToolTip(){
//      if (levelName == null)
//         return "not initialized";
//      else
//         return "<html>Level unique name:" + levelUniqueName
//                   +  "<br>Dimension unique name:" + dimensionUniqueName
//                   + "<br>Hierarchy unique name:" + hierarchyUniqueName
//                   + "</html>";
//
//   }




   public String[] getPopUpActionList(){
      // NOT FOR NOW - LATER I COULD ALLOW THIS, BUT ENABLING A LEVEL MEMEBER
      //  WOULD MEAN ENABLING A
      return null;
//      if (isEnabled()){
//         return new String[] {
//              FilterTreePopUpActions.DISABLE_THIS_ELEMENT
//            , FilterTreePopUpActions.ENABLE_ONLY_THIS_ELEMENT
//            , FilterTreePopUpActions.ENABLE_ALL_BUT_THIS_ELEMENT
//         };
//      }else{
//         return new String[] {
//              FilterTreePopUpActions.ENABLE_THIS_ELEMENT
//            , FilterTreePopUpActions.ENABLE_ONLY_THIS_ELEMENT
//            , FilterTreePopUpActions.ENABLE_ALL_BUT_THIS_ELEMENT
//         };
//
//      }
   }

//   public String getUniqueName(){
//      return levelUniqueName;
//   }
//   public String getCaption(){
//      return levelCaption;
//   }
//   public String getQueryMembersExpression(){
//      return levelUniqueName + ".Members";
//   }
//
//   public boolean isEnabled(){
//      return nodeEnabled;
//   }
//   public void setEnabled(boolean newValue){
//      nodeEnabled = newValue;
//   }
//   public String getHierarchyUniqueName(){
//      return hierarchyUniqueName;
//   }
//   public boolean isMeasure(){
//      return false;
//   }
}
