package aip.util;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import aip.util.test.Employee;
import aip.util.test.Person;

//import com.thoughtworks.xstream.XStream;
import com.tonbeller.jpivot.olap.model.OlapException;
import com.tonbeller.jpivot.xmla.XMLA_OlapItem;
import com.tonbeller.jpivot.xmla.XMLA_SOAP;

public class AIPJSFUtil {
	public static void main(String[] args){
		
		System.out.println("AIPJSFUtil.main():start");
		getInstance().invoke("aip.util.AIPJSFUtil", "testString", System.out);
		System.out.println();
		getInstance().invoke("aip.util.AIPJSFUtil", "testPerson", System.out);
		System.out.println();
		getInstance().invoke("aip.util.AIPJSFUtil", "testEmployee", System.out);
		System.out.println();
//		getInstance().invoke("aip.util.AIPJSFUtil", "testEmployeeParam", System.out,"<aip.util.AIPJSFUtil_-Person>  <name>zzzzz</name>  <family>zzzzzzzzz</family>  <age>100</age>  <outer-class/></aip.util.AIPJSFUtil_-Person>");
		System.out.println("\nAIPJSFUtil.main():end");
		
		
	}
	public static AIPJSFUtil getInstance(){
		return new AIPJSFUtil(); 
	}
	

	public void invoke(String className,String methodName,OutputStream out){
		invoke(className, methodName, out,null);
	}
	public void invoke(String className,String methodName,OutputStream out,String xmlparam){
		
//		try {
//			XStream xstream = new XStream();
//			Object param=null;
//			if(xmlparam!=null){
//				StringReader sr=new StringReader(xmlparam);
//				param = xstream.fromXML(sr);
//			}
//			
//			Object obj = Class.forName(className).newInstance();
//			Object result =null;
//			if(param==null){
//				result = obj.getClass().getMethod(methodName,null ).invoke(obj, null);
//			}else{
//				result = obj.getClass().getMethod(methodName,param.getClass() ).invoke(obj, param);
//			}
//			
//			//XMLEncoder encolder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("c:/outfilename.xml")));
//			XMLEncoder encolder = new XMLEncoder(out);
////			PrintWriter out2 = new PrintWriter(out);
////			XMLEncoderFactory f=new XMLEncoderFactory();
////			XMLEncoder encolder=(XMLEncoder) f.getDefaultEncoder( );
//			encolder.writeObject(result);
//			encolder.flush();
////			String xml = xstream.toXML(result);
////			out.write(xml.getBytes());
//			
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}
	}
	
	public String testString(){
		return "this is a test.";
	}
	public Person testPerson(){
		return new Person();
	}
	public Person testEmployee(){
		return new Employee();
	}
	public Person testEmployeeParam(Person param){
		Employee e= new Employee();
		e.setManager(param);
		return e;
	}
	
	
	
	
	
}
