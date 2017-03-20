package aip.niopdc.sellbi.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import aip.common.mdxreport.DimensionTreeLST;
import aip.common.mdxreport.MDXReportDTO;
import aip.common.mdxreport.MDXReportLST;


public class MDXReportForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MDXReportLST mdxReportLST = new MDXReportLST();
	private MDXReportDTO mdxReportDTO = new MDXReportDTO();
	private DimensionTreeLST dimensionTreeLST = new DimensionTreeLST();
	
	private String errorMessage=null;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}




	public MDXReportLST getMdxReportLST() {
		return mdxReportLST;
	}

	public void setMdxReportLST(MDXReportLST mdxReportLST) {
		this.mdxReportLST = mdxReportLST;
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

	public MDXReportDTO getMdxReportDTO() {
		return mdxReportDTO;
	}

	public void setMdxReportDTO(MDXReportDTO mdxReportDTO) {
		this.mdxReportDTO = mdxReportDTO;
	}

	public DimensionTreeLST getDimensionTreeLST() {
		return dimensionTreeLST;
	}

	public void setDimensionTreeLST(DimensionTreeLST dimensionTreeLST) {
		this.dimensionTreeLST = dimensionTreeLST;
	}


}
