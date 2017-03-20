package aip.tag.tabbed;

public class AIPTab {
	String name;
	String value;
	boolean selected;
	String onclick;
	
	public AIPTab(String name, String value, boolean selected,
			String onclick) {
		super();
		this.name = name;
		this.value = value;
		this.selected = selected;
		this.onclick = onclick;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public AIPTab(String name, String value, boolean selected) {
		super();
		this.name = name;
		this.value = value;
		this.selected = selected;
	}
	public AIPTab(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	} 
	

}
