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
public abstract class XMLAProperties {
   public static String elementIndent = "  ";

   public XMLAProperties() {
   }

   public String getXML(String indent){
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
     return "\n" + indent + "<Properties>"
           + getPropertyListXML(indent + elementIndent)
           + "\n" + indent + "</Properties>";

   }

   protected abstract String getPropertyListXML(String indent);

}
