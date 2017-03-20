package aip.common.mdxreport;

import java.util.ArrayList;
import java.util.List;

import rex.DimensionMember;
import rex.graphics.dimensiontree.elements.DimensionElement;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;


public class DimensionTreeLST {
	private List<DimensionMember> dimensionMembers = new ArrayList<DimensionMember>();
	//private List<DimensionElement> dimensionElementTree = new ArrayList<DimensionElement>();
	//private List<DimensionTreeElement> dimensionTree = new ArrayList<DimensionTreeElement>();
	DimensionTreeElement[] tree = {};

	public List<DimensionMember> getDimensionMembers() {
		return dimensionMembers;
	}

	public void setDimensionMembers(List<DimensionMember> dimensionMembers) {
		this.dimensionMembers = dimensionMembers;
	}



	public DimensionTreeElement[] getTree() {
		return tree;
	}

	public void setTree(DimensionTreeElement[] tree) {
		this.tree = tree;
	}


}
