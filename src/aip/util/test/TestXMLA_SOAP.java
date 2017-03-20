package aip.util.test;

import java.util.List;
import java.util.Map;

import aip.util.AIPUtil;

import com.tonbeller.jpivot.olap.model.OlapException;
import com.tonbeller.jpivot.xmla.XMLA_OlapItem;
import com.tonbeller.jpivot.xmla.XMLA_SOAP;

public class TestXMLA_SOAP {

	public static void main(String[] args){
		
//		System.out.println("AIPJSFUtil.main():start");
//		getInstance().invoke("aip.util.AIPJSFUtil", "testString", System.out);
//		System.out.println();
//		getInstance().invoke("aip.util.AIPJSFUtil", "testPerson", System.out);
//		System.out.println();
//		getInstance().invoke("aip.util.AIPJSFUtil", "testEmployee", System.out);
//		System.out.println();
////		getInstance().invoke("aip.util.AIPJSFUtil", "testEmployeeParam", System.out,"<aip.util.AIPJSFUtil_-Person>  <name>zzzzz</name>  <family>zzzzzzzzz</family>  <age>100</age>  <outer-class/></aip.util.AIPJSFUtil_-Person>");
//		System.out.println("\nAIPJSFUtil.main():end");
		
		
		try {
//			XMLA_SOAP xs=new XMLA_SOAP("http://localhost:81/olap/msmdpump.dll","","");
			XMLA_SOAP xs=new XMLA_SOAP("http://taheri/olap/msmdpump.dll","","");
			
//			List<XMLA_OlapItem> cats = xs.discoverCat();
//			for (XMLA_OlapItem object : cats) {
//				AIPUtil.printObject(object);
//			}
			
//			List<XMLA_OlapItem> cubes = xs.discoverCube("Waremart 2005");
//			List<XMLA_OlapItem> cubes = xs.discoverCube("NIOPDC_MRS_Ver2");
//			for (XMLA_OlapItem object : cubes) {
//				AIPUtil.printObject(object);
//			}
//			
//			List<XMLA_OlapItem> dims = xs.discoverDim("Waremart 2005","Sales");
//			List<XMLA_OlapItem> dims = xs.discoverDim("NIOPDC_MRS_Ver2","حواله");
//			for (XMLA_OlapItem object : dims) {
//				AIPUtil.printObject(object);
//			}
			
//			List<XMLA_OlapItem> DSProps = xs.discoverDSProps();
//			for (XMLA_OlapItem object : DSProps) {
//				AIPUtil.printObject(object);
//			}
//			
//			List<XMLA_OlapItem> hierarchies = xs.discoverHier("Waremart 2005","Sales","Product");
//			for (XMLA_OlapItem object : hierarchies) {
//				AIPUtil.printObject(object);
//			}
//			
/* ? com.tonbeller.jpivot.olap.model.OlapException: No metadata schema levels for catalog: Waremart 2005 and cube: Sales
 * 
 			List<XMLA_OlapItem> levels = xs.discoverLev("Waremart 2005","Sales","Product","ByCategory");
			for (XMLA_OlapItem object : levels) {
				AIPUtil.printObject(object);
			}
*/			
//			List<XMLA_OlapItem> dims = xs.discoverMem("Waremart 2005","Sales","Product","ByCategory","Family");
////			List<XMLA_OlapItem> dims = xs.discoverMem("Waremart 2005","Sales","Product","ByCategory","Brand");
//			for (XMLA_OlapItem object : dims) {
//				AIPUtil.printObject(object);
//			}
//			
//			List<XMLA_OlapItem> mems = xs.discoverMemTree("Waremart 2005","Sales","[Product].[ByCategory].[Family].&[1]",1);
//			List<XMLA_OlapItem> mems = xs.discoverMemTree("Waremart 2005","Sales","[Product].[ByCategory].[Category].&[20]",1);
//			List<XMLA_OlapItem> mems = xs.discoverMemTree("NIOPDC_MRS_Ver3","حواله","[زمان].[سال-فصل-ماه].[فصل].&[1]",1);
//			List<XMLA_OlapItem> mems = xs.discoverMemTree("NIOPDC_MRS_Ver3","حواله","[زمان].[YQM].[YearNo].&[1387]",1);
//			List<XMLA_OlapItem> mems = xs.discoverMemTree("NIOPDC_MRS_Ver3","حواله","[فرآورده].[گروه - فرآورده- نرخ].[All]",1);
//			List<XMLA_OlapItem> mems = xs.discoverMemTree("NIOPDC_MRS_Ver3","حواله","[فرآورده].[گروه - فرآورده- نرخ].[گروه فرآورده].&[1.0001]",1);
			List<XMLA_OlapItem> mems = xs.discoverMemTree("NIOPDC_MRS_Ver3","حواله","[فرآورده].[گروه - فرآورده- نرخ].[فرآورده].&[1.0001]",1);
//			List<XMLA_OlapItem> mems = xs.discoverMemTree("NIOPDC_MRS_Ver3","حواله","[زمان].[سال-فصل-ماه].[سال].[1387].&[1].&[2]",1);
			
			for (XMLA_OlapItem object : mems) {
				AIPUtil.printObject(object);
			}
			
//			List<XMLA_OlapItem> props = xs.discoverProp("Waremart 2005","Sales","Product","ByCategory","SubCategory");
//			for (XMLA_OlapItem object : props) {
//				AIPUtil.printObject(object);
//			}
//			
/*
 * ? com.tonbeller.jpivot.olap.model.OlapException: Soap Fault code=XMLAnalysisError.0xc10f0014 fault string=An error occurred while parsing the 'RequestType' element at line 1, column 371 ('urn:schemas-microsoft-com:xml-analysis' namespace) under Envelope/Body/Discover/RequestType. fault actor=null
			List<XMLA_OlapItem> sapVars = xs.discoverSapVar("Waremart 2005","Sales");
			for (XMLA_OlapItem object : sapVars) {
				AIPUtil.printObject(object);
			}
 */			
//			Map datasources = xs.discoverDS();
//			
//			for (Object object : datasources.values()) {
//				AIPUtil.printObject(object);
//			}
			
			
		} catch (OlapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
