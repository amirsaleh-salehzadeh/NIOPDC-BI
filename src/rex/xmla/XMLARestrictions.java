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
public abstract class XMLARestrictions {
   public static String elementIndent = "  ";
   public XMLARestrictions() {
   }
   public String getXML(String indent){
      /*
          <Restrictions>
           <RestrictionList>
            <ns1:CATALOG_NAME>FoodMart 2000</ns1:CATALOG_NAME>
            <ns1:CUBE_NAME>Sales</ns1:CUBE_NAME>
            <ns1:DIMENSION_UNIQUE_NAME>[Yearly Income]</ns1:DIMENSION_UNIQUE_NAME>
           </RestrictionList>
   </Restrictions>
      */
     return "\n" + indent + "<Restrictions>"
           + getRestrictionListXML(indent + elementIndent)
           + "\n" + indent + "</Restrictions>";

   }

   protected abstract String getRestrictionListXML(String indent);

}
