package aip.tag.tabbed;

import java.util.ArrayList;
import java.util.List;


public class AIPTabs {
	
	String formId;
	String selectedInputId;
	List<AIPTab> tabs=new ArrayList<AIPTab>();
	String selectedName;

	public String getSelectedName() {
		return selectedName;
	}

	public void setSelectedName(String selectedName) {
		this.selectedName = selectedName;
		for (AIPTab tab : tabs) {
			if(selectedName.equalsIgnoreCase(tab.getName())){
				tab.setSelected(true);
			}
		}
	}


	public String getSelectedInputId() {
		return selectedInputId;
	}

	public void setSelectedInputId(String selectedInputId) {
		this.selectedInputId = selectedInputId;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public List<AIPTab> getTabs() {
		return tabs;
	}

	public void setTabs(List<AIPTab> tabs) {
		this.tabs = tabs;
	}

	public AIPTabs() {
		super();
	}

	public boolean add(AIPTab o) {
		return tabs.add(o);
	}

	public AIPTab get(int index) {
		return tabs.get(index);
	}

	public int size() {
		return tabs.size();
	}

	public AIPTabs(String formId,String selectedInputId) {
		super();
		this.selectedInputId = selectedInputId;
		this.formId = formId;
	}
	
	
	public String getOnClick(int index){
		AIPTab tab = tabs.get(index);
		
		if(tab!=null && tab.getOnclick()!=null && !"".equals(tab.getOnclick())){
			return tab.getOnclick();
		}else{
			return "changeSelectedTab('"+getFormId()+"','"+getSelectedInputId()+"','"+tab.getName()+"')";
		}
	}
	

}
