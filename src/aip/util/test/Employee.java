package aip.util.test;

public class Employee extends Person{
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