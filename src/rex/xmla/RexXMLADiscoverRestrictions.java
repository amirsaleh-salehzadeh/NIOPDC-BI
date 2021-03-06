package rex.xmla;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: </p>
 *
 * <p>Company: </p>
 *
 * @author Igor Mekterovic
 * @version 0.3
 */
public class RexXMLADiscoverRestrictions extends XMLADiscoverRestrictions{
   private String catalog;
   private String cubeName;
   private String dimensionUniqueName;
   private String hierarchyUniqueName;
   private String levelUniqueName;
   private String memberUniqueName;
   private String treeOp;



   public RexXMLADiscoverRestrictions() {
   }
   public RexXMLADiscoverRestrictions(String catalogName) {
      catalog = catalogName;
   }



   public String getCatalog(){
      return catalog;
   }
   public void setCatalog(String s){
     catalog = s;
   }

   public String getCubeName(){
      return cubeName;
   }
   public void setCubeName(String s){
      cubeName = s;
   }

   public String getDimensionUniqueName(){
      return dimensionUniqueName;
   }
   public void setDimensionUniqueName(String s){
     dimensionUniqueName = s;
   }

   public String getHierarchyUniqueName(){
      return hierarchyUniqueName;
   }
   public void setHierarchyUniqueName(String s){
     hierarchyUniqueName = s;
   }

   public void setLevelUniqueName(String s){
      levelUniqueName = s;
   }
   public String getLevelUniqueName(){
      return levelUniqueName;
   }

   public void setMemberUniqueName(String s){
      memberUniqueName = s;
   }
   public String getMemberUniqueName(){
      return memberUniqueName;
   }

   public void setTreeOp(int _treeOp){
      treeOp = "" + _treeOp;
   }
   public String getTreeOp(){
      return treeOp;
   }

   protected String getRestrictionListXML(String indent){
      /*
          <Restrictions>
           <RestrictionList>
            <ns1:CATALOG_NAME>FoodMart 2000</ns1:CATALOG_NAME>
            <ns1:CUBE_NAME>Sales</ns1:CUBE_NAME>
            <ns1:DIMENSION_UNIQUE_NAME>[Yearly Income]</ns1:DIMENSION_UNIQUE_NAME>
           </RestrictionList>
   </Restrictions>
      */
      StringBuffer sb = new StringBuffer("\n" + indent + "<RestrictionList>");

      if ( catalog!= null && catalog.trim().length()>0)
         sb.append("\n" + indent + elementIndent + "<CATALOG_NAME>" + catalog.trim() + "</CATALOG_NAME>");

      if ( cubeName!= null && cubeName.trim().length()>0)
         sb.append("\n" + indent + elementIndent + "<CUBE_NAME>" + cubeName.trim() + "</CUBE_NAME>");

      if ( dimensionUniqueName!= null && dimensionUniqueName.trim().length()>0)
         sb.append("\n" + indent + elementIndent + "<DIMENSION_UNIQUE_NAME>" + dimensionUniqueName.trim() + "</DIMENSION_UNIQUE_NAME>");

      if ( hierarchyUniqueName!= null && hierarchyUniqueName.trim().length()>0)
         sb.append("\n" + indent + elementIndent + "<HIERARCHY_UNIQUE_NAME>" + hierarchyUniqueName.trim() + "</HIERARCHY_UNIQUE_NAME>");

      if ( levelUniqueName!= null && levelUniqueName.trim().length()>0)
         sb.append("\n" + indent + elementIndent + "<LEVEL_UNIQUE_NAME>" + levelUniqueName.trim() + "</LEVEL_UNIQUE_NAME>");

      if ( memberUniqueName!= null && memberUniqueName.trim().length()>0)
         sb.append("\n" + indent + elementIndent + "<MEMBER_UNIQUE_NAME>" + memberUniqueName.trim() + "</MEMBER_UNIQUE_NAME>");

      if ( treeOp!= null && treeOp.trim().length()>0)
         sb.append("\n" + indent + elementIndent + "<TREE_OP>" + treeOp.trim() + "</TREE_OP>");

      sb.append("\n" + indent + "</RestrictionList>");
      return sb.toString();
   }

   public static void main(String[] args) {
      RexXMLADiscoverRestrictions xmladiscoverrestrictions = new
         RexXMLADiscoverRestrictions();
   }
}
