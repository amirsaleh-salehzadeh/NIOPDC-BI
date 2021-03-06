package rex.xmla;

import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.Map;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.io.IOException;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import rex.utils.S;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.BufferedInputStream;

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
public class TestXMLA {
   public TestXMLA() {
   }
   public Map defineDiscoverMimeHeaders() {
      Map map = new HashMap();
      map.put("SOAPAction", "\"urn:schemas-microsoft-com:xml-analysis:Discover\"");
      map.put("Content-Type", "text/xml; charset=utf-8");
      map.put("Accept", "application/soap+xml, application/dime, multipart/related, text/*");
      return map;
   }
   public Map defineExecuteMimeHeaders() {
      Map map = new HashMap();
      map.put("SOAPAction", "\"urn:schemas-microsoft-com:xml-analysis:Execute\"");
      map.put("Content-Type", "text/xml; charset=utf-8");
      map.put("Accept", "application/soap+xml, application/dime, multipart/related, text/*");
      return map;
   }
   public void testDiscover(){
      URL url = null;
      String request =
           "<?xml version=\"1.0\" encoding=\"UTF-8\"?>                                                                                                                                             "
         + "\n<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
         + "\n <soapenv:Body>                                                                                                                                                                      "
         + "\n  <ns1:Discover soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:ns1=\"urn:schemas-microsoft-com:xml-analysis\">                                            "
         + "\n   <RequestType>DISCOVER_DATASOURCES</RequestType>                                                                                                                                   "
         + "\n   <Restrictions>                                                                                                                                                                    "
         + "\n    <RestrictionList xsi:nil=\"true\"/>                                                                                                                                              "
         + "\n   </Restrictions>                                                                                                                                                                   "
         + "\n   <Properties>                                                                                                                                                                      "
         + "\n    <PropertyList xsi:nil=\"true\"/>                                                                                                                                                 "
         + "\n   </Properties>                                                                                                                                                                     "
         + "\n  </ns1:Discover>                                                                                                                                                                    "
         + "\n </soapenv:Body>                                                                                                                                                                     "
         + "\n</soapenv:Envelope>                                                                                                                                                                  ";
;
      try {
         url = new URL("http://localhost/xmla/msxisapi.dll");
         HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
         urlConnection.setRequestMethod("POST");
         urlConnection.setDoInput(true);
         urlConnection.setDoOutput(true);
         Iterator it = defineDiscoverMimeHeaders().entrySet().iterator();
         while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            urlConnection.setRequestProperty(me.getKey().toString(),
                                             me.getValue().toString());
         }
         byte[] b = request.getBytes("UTF8");
         S.out("writing:\n" + request );
         writeToStream(b, urlConnection.getOutputStream());
         b = readFromStream(urlConnection.getInputStream());
         String response = new String(b, "UTF8");
         S.out("response = \n" + response);


      } catch(MalformedURLException e){
         e.printStackTrace();
         return;
      } catch (IOException e) {
         e.printStackTrace();
      }


  }

  public void testDiscover2(){
     URL url = null;
     String request =
        "<SOAP-ENV:Envelope                                                           "
         + "    xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"          "
         + "    SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"> "
         + "    <SOAP-ENV:Body>                                                       "
         + "        <Discover xmlns=\"urn:schemas-microsoft-com:xml-analysis\"        "
         + "    SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"> "
         + "    <RequestType>DISCOVER_DATASOURCES</RequestType>                       "
         + "    <Restrictions>                                                        "
         + "        <RestrictionList/>                                                "
         + "    </Restrictions>                                                       "
         + "    <Properties>                                                          "
         + "        <PropertyList/>                                                   "
         + "    </Properties>                                                         "
         + "</Discover>                                                               "
         + "    </SOAP-ENV:Body>                                                      "
         + "</SOAP-ENV:Envelope>                                                      ";


     // <Discover xmlns="urn:schemas-microsoft-com:xml-analysis"    SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">


     try {
        url = new URL("http://localhost/xmla/msxisapi.dll");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        Iterator it = defineDiscoverMimeHeaders().entrySet().iterator();
        while (it.hasNext()) {
           Map.Entry me = (Map.Entry) it.next();
           urlConnection.setRequestProperty(me.getKey().toString(),
                                            me.getValue().toString());
        }
        byte[] b = request.getBytes("UTF8");
        S.out("writing:\n" + request );
        writeToStream(b, urlConnection.getOutputStream());
        b = readFromStream(urlConnection.getInputStream());
        String response = new String(b, "UTF8");
        S.out("response = \n" + response);


     } catch(MalformedURLException e){
        e.printStackTrace();
        return;
     } catch (IOException e) {
        e.printStackTrace();
     }


  }

  public void testExecute(){
     URL url = null;
     String request =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>                                                                                                                                              "
        + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> "
        + " <soapenv:Body>                                                                                                                                                                       "
        + "  <ns1:Execute soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:ns1=\"urn:schemas-microsoft-com:xml-analysis\">                                              "
        + "   <Command xsi:type=\"ns1:clsXMLAProx.Execute.Command\">                                                                                                                             "
        + "    <Statement xsi:type=\"xsd:string\">SELECT                                                                                                                                         "
        + "NON EMPTY                                                                                                                                                                             "
        + "{                                                                                                                                                                                     "
        + "      {     {[Marital Status].[Marital Status].Members}                                                                                                                               "
        + "}                                                                                                                                                                                     "
        + "    * {     {[Measures].[Profit]}                                                                                                                                                     "
        + "}                                                                                                                                                                                     "
        + "}  ON COLUMNS,                                                                                                                                                                        "
        + "                                                                                                                                                                                      "
        + "NON EMPTY                                                                                                                                                                             "
        + "{                                                                                                                                                                                     "
        + "     {[Product].[Product Family].Members}                                                                                                                                             "
        + "                                                                                                                                                                                      "
        + "} ON ROWS                                                                                                                                                                             "
        + "FROM [Sales]</Statement>                                                                                                                                                              "
        + "   </Command>                                                                                                                                                                         "
        + "   <Properties xsi:type=\"ns1:clsXMLAProx.Execute.Properties\">                                                                                                                       "
        + "    <PropertyList xsi:type=\"ns1:clsXMLAProx.Execute.PropertyList\">                                                                                                                  "
        + "     <ns1:DataSourceInfo xsi:type=\"xsd:string\">Local Analysis Server</ns1:DataSourceInfo>                                                                                           "
        + "     <ns1:Catalog xsi:type=\"xsd:string\">FoodMart 2000</ns1:Catalog>                                                                                                                 "
        + "     <ns1:Format xsi:type=\"xsd:string\">Native</ns1:Format>                                                                                                                          "
        + "     <ns1:Content xsi:type=\"xsd:string\">Data</ns1:Content>                                                                                                                          "
        + "     <ns1:AxisFormat xsi:type=\"xsd:string\">TupleFormat</ns1:AxisFormat>                                                                                                             "
        + "    </PropertyList>                                                                                                                                                                   "
        + "   </Properties>                                                                                                                                                                      "
        + "  </ns1:Execute>                                                                                                                                                                      "
        + " </soapenv:Body>                                                                                                                                                                      "
        + "</soapenv:Envelope>                                                                                                                                                                   ";

     try {
        url = new URL("http://localhost/xmla/msxisapi.dll");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        Iterator it = defineExecuteMimeHeaders().entrySet().iterator();
        while (it.hasNext()) {
           Map.Entry me = (Map.Entry) it.next();
           urlConnection.setRequestProperty(me.getKey().toString(),
                                            me.getValue().toString());
        }
        byte[] b = request.getBytes("UTF8");
        S.out("writing:\n" + request );
        writeToStream(b, urlConnection.getOutputStream());
        b = readFromStream(urlConnection.getInputStream());
        String response = new String(b, "UTF8");
        S.out("response = \n" + response);


     } catch(MalformedURLException e){
        e.printStackTrace();
        return;
     } catch (IOException e) {
        e.printStackTrace();
     }


 }

  private byte[] readFromStream(InputStream urlInputStream) throws IOException {
     // ovo je kod skoro uzet iz PipeThreada - objasnjenja su tamo
     BufferedInputStream in = null;
     ByteArrayOutputStream bout = null;
     try {
        in = new BufferedInputStream(urlInputStream);
        bout = new ByteArrayOutputStream();
        int counter = 0;
        byte b = 0;
        int len = 0;
        while (len != -1) {
           len = in.available();
           if (len == 0) {
              b = (byte) in.read();
              if (b != -1) {
                 bout.write(b);
                 counter = 0;
              }
              else {
                 //bout.write(b);
                 if (counter > 100) {
                    throw new EOFException();
                 }
                 counter++;
              }
           }
           else {
              len = in.available();
              byte[] bp = new byte[len];
              in.read(bp);
              bout.write(bp);
              counter = 0;
           }
        }
     }
     catch (EOFException e) {
        // to je u redu, gotovi smo
     }
     finally {
        try {
           if (in != null) {
              in.close();
           }
           if (urlInputStream != null) {
              urlInputStream.close();
           }
        }
        catch (IOException e) {
        }
     }
     return bout.toByteArray();
   }

  private void writeToStream(byte[] data, OutputStream urlOutputStream) throws IOException {
     BufferedOutputStream os = null;
     try {
        os = new BufferedOutputStream(urlOutputStream);
        os.write(data);
        os.flush();
     }
     finally {
        try {
           if (os != null) {
              os.close();
           }
           if (urlOutputStream != null) {
              urlOutputStream.close();
           }
        }
        catch (IOException e) {
        }
     }
   }
   public static void main(String[] args) {
      TestXMLA testxmla = new TestXMLA();
//      testxmla.testDiscover();
      testxmla.testDiscover2();
//      testxmla.testExecute();
   }
}
