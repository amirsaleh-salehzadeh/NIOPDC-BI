package aip.common.security.group;

import java.util.ArrayList;

import aip.common.AIPDefaultPagingLST;
import aip.common.AIPPagingParamInterface;

public class GroupLST extends  AIPDefaultPagingLST{
	    private String filter ;
	    private ArrayList<GroupDTO> dtos=new ArrayList<GroupDTO>();
	
		public ArrayList<GroupDTO> getDtos() {
			return dtos;
		}
		public void setDtos(ArrayList<GroupDTO> dtos) {
			this.dtos = dtos;
		}
		public String getFilter() {
			return filter;
		}
		public void setFilter(String filter) {
			this.filter = filter;
		}
		
}

