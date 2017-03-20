package aip.niopdc.sellbi.struts.form.visual;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import aip.common.visualreport.VisualReportDTO;
import aip.common.visualreport.VisualReportLST;


public class VisualReport1Form extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VisualReportLST visualReportLST = new VisualReportLST();
	private VisualReportDTO visualReportDTO = new VisualReportDTO();
	private Integer defaultAddType = 1;
	
	private String selectedFilters;
	private String selectedRows;
	private String selectedColumns;
	private String selectedMeasures;
	

//	private String[] filterArr;
//	private String[] rowsArr;
//	private String[] columnArr;
//	private String[] measureArr;
//	

	private String errorMessage=null;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}





	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
	}

	public VisualReportLST getVisualReportLST() {
		return visualReportLST;
	}

	public void setVisualReportLST(VisualReportLST visualReportLST) {
		this.visualReportLST = visualReportLST;
	}

	public VisualReportDTO getVisualReportDTO() {
		return visualReportDTO;
	}

	public void setVisualReportDTO(VisualReportDTO visualReportDTO) {
		this.visualReportDTO = visualReportDTO;
	}

	public Integer getDefaultAddType() {
		return defaultAddType;
	}

	public void setDefaultAddType(Integer defaultAddType) {
		this.defaultAddType = defaultAddType;
	}

	public String getSelectedFilters() {
		return selectedFilters;
	}

	public void setSelectedFilters(String selectedFilters) {
		this.selectedFilters = selectedFilters;
	}

	public String getSelectedRows() {
		return selectedRows;
	}

	public void setSelectedRows(String selectedRows) {
		this.selectedRows = selectedRows;
	}

	public String getSelectedColumns() {
		return selectedColumns;
	}

	public void setSelectedColumns(String selectedColumns) {
		this.selectedColumns = selectedColumns;
	}

	public String getSelectedMeasures() {
		return selectedMeasures;
	}

	public void setSelectedMeasures(String selectedMeasures) {
		this.selectedMeasures = selectedMeasures;
	}



}
