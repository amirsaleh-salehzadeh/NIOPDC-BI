package aip.common.report;

import java.util.ArrayList;

import aip.common.AIPWebUser;
import aip.common.security.group.GroupLST;

public class ReportComboLST {
   private ArrayList<GroupLST>  groupLST = new ArrayList<GroupLST>();

public ArrayList<GroupLST> getGroupLST() {
	return groupLST;
}

public void setGroupLST(ArrayList<GroupLST> groupLST) {
	this.groupLST = groupLST;
}
}
