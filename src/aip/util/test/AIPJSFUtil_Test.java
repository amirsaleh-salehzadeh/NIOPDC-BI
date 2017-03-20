package aip.util;

import java.beans.XMLEncoder;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import com.thoughtworks.xstream.XStream;

public class AIPJSFUtil {
	public static void main(String[] args){
		
		System.out.println("AIPJSFUtil.main():start");
		getInstance().invoke("aip.util.AIPJSFUtil", "testString", System.out);
		System.out.println();
		getInstance().invoke("aip.util.AIPJSFUtil", "testPerson", System.out);
		System.out.println();
		getInstance().invoke("aip.util.AIPJSFUtil", "testEmployee", System.out);
		System.out.println("\nAIPJSFUtil.main():end");
		
	}
	public static AIPJSFUtil getInstance(){
		return new AIPJSFUtil(); 
	}
	

	public void invoke(String className,String methodName,OutputStream out){
		
		try {
			Object obj = Class.forName(className).newInstance();
			Object result = obj.getClass().getMethod(methodName,null ).invoke(obj, null);
			
//			XMLEncoder encolder = new XMLEncoder(out);
//			encolder.writeObject(result);
			XStream xstream = new XStream();
			String xml = xstream.toXML(result);
			out.write(xml.getBytes());
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	class Person {
		String name;
		String family;
		int age;
		public Person(){
			name="ali";
			family="alizadeh";
			age=18;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getFamily() {
			return family;
		}
		public void setFamily(String family) {
			this.family = family;
		}
	}
	class Employee extends Person{
		String employeeNo;
		Person manager;
		public Employee(){
			employeeNo="1000";
			manager=new Person();
			manager.setName("hassan");
			manager.setName("hassanzadeh");
		}
		public String getEmployeeNo() {
			return employeeNo;
		}
		public void setEmployeeNo(String employeeNo) {
			this.employeeNo = employeeNo;
		}
		public Person getManager() {
			return manager;
		}
		public void setManager(Person manager) {
			this.manager = manager;
		}
		
	}
	
	
}
