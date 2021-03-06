package rex.xmla;

public class RexXMLAExecuteProperties extends XMLAExecuteProperties{

   private String dataSourceInfo;
   private String catalog;
   private String format;
   private String content;
   private String axisFormat;

   public RexXMLAExecuteProperties() {
      // defaults:
      axisFormat = "TupleFormat";
      format = "Multidimensional"; // Mondrian 2.1 doens't support "Native" !?
      content = "Data";
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

   public String getAxisFormat() {
      return axisFormat;
   }

   public void setAxisFormat(String s) {
      axisFormat = s;
   }
   protected String getPropertyListXML(String indent){
      /*
           <PropertyList>
             <DataSourceInfo>Provider=Mondrian;Jdbc=jdbc:odbc:MondrianFoodMart;Catalog=file:/C:/Program Files/Apache Software Foundation/Tomcat 5.5/webapps/mondrian/WEB-INF/queries/FoodMart.xml;JdbcDrivers=sun.jdbc.odbc.JdbcOdbcDriver;</DataSourceInfo>
             <Catalog>FoodMart</Catalog>
             <Format>Multidimensional</Format>
             <AxisFormat>TupleFormat</AxisFormat>
           </PropertyList>

           <PropertyList xsi:type="ns1:clsXMLAProx.Execute.PropertyList">
            <ns1:DataSourceInfo xsi:type="xsd:string">Local Analysis Server</ns1:DataSourceInfo>
            <ns1:Catalog xsi:type="xsd:string">FoodMart 2000</ns1:Catalog>
            <ns1:Format xsi:type="xsd:string">Native</ns1:Format>
            <ns1:Content xsi:type="xsd:string">Data</ns1:Content>
            <ns1:AxisFormat xsi:type="xsd:string">TupleFormat</ns1:AxisFormat>
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

      if ( axisFormat!= null && axisFormat.trim().length()>0)
         sb.append("\n" + indent + elementIndent + "<AxisFormat>" + axisFormat.trim() + "</AxisFormat>");

      sb.append("\n" + indent + "</PropertyList>");
      return sb.toString();

   }

}
