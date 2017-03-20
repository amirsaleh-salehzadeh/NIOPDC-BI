package rex.xmla;

public class XMLAObjectsFactory {
   public XMLAObjectsFactory() {
   }

   // For the time being this factory is only making Rex* implementations
   // In the future it will be easy to replace these imeplementations with some
   // else implementation, or augment this with some server specific (which shouldn't happen)
   // implementation.

   public static XMLADiscoverProperties newXMLADiscoverProperties(){
      return new RexXMLADiscoverProperties();
   }
   public static XMLAExecuteProperties newXMLAExecuteProperties(){
      return new RexXMLAExecuteProperties();
   }
   public static XMLADiscoverRestrictions newXMLADiscoverRestrictions(){
      return new RexXMLADiscoverRestrictions();
   }


}
