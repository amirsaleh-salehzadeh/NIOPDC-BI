package aip.common.report;

import java.util.ArrayList;

import aip.common.AIPDefaultPagingLST;

public class ReportLST extends  AIPDefaultPagingLST  {
	
    private ArrayList<ReportDTO> dtos = new ArrayList<ReportDTO>();

public ArrayList<ReportDTO> getDtos() {
	return dtos;
}

public void setDtos(ArrayList<ReportDTO> dtos) {
	this.dtos = dtos;
}



}
