package rex.xmla;

import org.w3c.dom.Document;
import java.io.IOException;
import java.io.InputStream;
import rex.exceptions.*;

public interface XMLAPort {

//   public void setRestrictions(XMLARestrictions r);
//   public void setProperties(XMLAProperties p);
//	Inserted rexXMLAException class for throwing customize Exception  Added By Prakash 
   public Document execute(String mdx, XMLAExecuteProperties properties) throws RexXMLAExecuteException,RexXMLADiscoverException,IOException;

   public Document discoverDataSources(XMLARestrictions r, XMLAProperties p) throws RexXMLAExecuteException,RexXMLADiscoverException,IOException;
   public Document getCatalogList(XMLARestrictions r, XMLAProperties p)      throws RexXMLAExecuteException,RexXMLADiscoverException,IOException;
   public Document getCubeList(XMLARestrictions r, XMLAProperties p)         throws RexXMLAExecuteException,RexXMLADiscoverException,IOException;
   public Document getDimensionList(XMLARestrictions r, XMLAProperties p)    throws RexXMLAExecuteException,RexXMLADiscoverException,IOException;
   public Document getHierarchyList(XMLARestrictions r, XMLAProperties p)    throws RexXMLAExecuteException,RexXMLADiscoverException,IOException;
   public Document getLevelList(XMLARestrictions r, XMLAProperties p)        throws RexXMLAExecuteException,RexXMLADiscoverException,IOException;
   public Document getMeasureList(XMLARestrictions r, XMLAProperties p)      throws RexXMLAExecuteException,RexXMLADiscoverException,IOException;
   public Document getMemberList(XMLARestrictions r, XMLAProperties p)       throws RexXMLAExecuteException,RexXMLADiscoverException,IOException;

   public InputStream getMemberListAsStream(XMLARestrictions r, XMLAProperties p)       throws RexXMLAExecuteException,RexXMLADiscoverException,IOException;

}
