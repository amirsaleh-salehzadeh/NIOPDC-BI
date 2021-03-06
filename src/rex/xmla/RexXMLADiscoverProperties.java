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
public class RexXMLADiscoverProperties extends XMLADiscoverProperties{
   private String dataSourceInfo;
   private String catalog;
   private String format;
   private String content;
   private int    beginRange;
   private int    endRange;


   public RexXMLADiscoverProperties() {
      beginRange = endRange = -1;
      content = "Data";   // set content to Data or otherwise Mondrian fails
   }

   public String getDataSourceInfo() {
      return dataSourceInfo;
   }

   public void setDataSourceInfo(String s) {
      dataSourceInfo = s;
   }

   public String getCatalog() {
      return catalog;
   }

   public void setCatalog(String s) {
      catalog = s;
   }
   public int getBeginRange(){
      return beginRange;
   }
   public void setBeginRange(int br){
     beginRange = br;
   }

   public int getEndRange(){
      return endRange;
   }
   public void setEndRange(int er){
     endRange = er;
   }


   public String getFormat() {
      return format;
   }

   public void setFormat(String s) {
      format = s;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String s) {
      content = s;
   }

   protected String getPropertyListXML(String indent){
      /*
         <Properties>
           <PropertyList>
            <ns1:DataSourceInfo>Local Analysis Server</ns1:DataSourceInfo>
            <ns1:Catalog>FoodMart 2000</ns1:Catalog>
            <ns1:BeginRange>0</ns1:BeginRange>
            <ns1:EndRange>0</ns1:EndRange>
            <Format>Tabular</Format>
            <Content>SchemaData</Content>
           </PropertyList>
      */
      StringBuffer sb = new StringBuffer("\n" + indent + "<PropertyList>");

      if ( dataSourceInfo!= null && dataSourceInfo.trim().length()>0)
         sb.append("\n" + indent + elementIndent + "<DataSourceInfo>" + dataSourceInfo.trim() + "</DataSourceInfo>");

      if ( catalog!= null && catalog.trim().length()>0)
         sb.append("\n" + indent + elementIndent + "<Catalog>" + catalog.trim() + "</Catalog>");

      if ( format!= null && format.trim().length()>0)
         sb.append("\n" + indent + elementIndent + "<Format>" + format.trim() + "</Format>");

      if ( content!= null && content.trim().length()>0)
         sb.append("\n" + indent + elementIndent + "<Content>" + content.trim() + "</Content>");

      if ( beginRange >= 0)
         sb.append("\n" + indent + elementIndent + "<BeginRange>" + beginRange + "</BeginRange>");

      if ( endRange >= 0)
         sb.append("\n" + indent + elementIndent + "<EndRange>" + endRange + "</EndRange>");

      sb.append("\n" + indent + "</PropertyList>");
      return sb.toString();

   }


   public static void main(String[] args) {
      RexXMLADiscoverProperties xmladiscoverproperties = new
         RexXMLADiscoverProperties();
   }
}
