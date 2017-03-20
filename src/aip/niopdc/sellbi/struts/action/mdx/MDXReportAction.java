/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package aip.niopdc.sellbi.struts.action.mdx;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



import aip.common.mdxreport.MDXReportDTO;
import aip.common.mdxreport.MDXReportLST;
import aip.jpivot.AIPPivotParam;
import aip.niopdc.sellbi.service.MDXReportSRV;
import aip.niopdc.sellbi.struts.form.MDXReportForm;
import aip.util.AIPUtil;
import aip.util.NVL;
import aip.util.UTF8;

/** 
 * MyEclipse Struts
 * Creation date: 06-02-2008
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 * @struts.action-forward name="showNavigation" path="/layout/navigation.jsp"
 */
public class MDXReportAction extends Action {
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public static void main(String[] args) {
	    try {
			System.out.println(URLEncoder.encode("\r\n", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		MDXReportForm mdxReportForm = (MDXReportForm)form;
		String reqCode = NVL.getString(request.getParameter("reqCode"));
		System.out.println("reqCode="+reqCode);
		
 		MDXReportSRV srv = new MDXReportSRV(); 
		
		if ("deleteIds".equalsIgnoreCase(reqCode)) {
			srv.deleteMDXReport(NVL.getString(request.getParameter("selectedIds")));
		}else if ("edit".equalsIgnoreCase(reqCode)) {
			int reportId = NVL.getInt(request.getParameter("reportId"));
			mdxReportForm.setMdxReportDTO(srv.getMDXReportDTO(reportId));
			mdxReportForm.setDimensionTreeLST(srv.getDimensionTreeLST(""));
			request.setAttribute("mdxReportForm", mdxReportForm);
			return mapping.findForward("edit");
		}else if ("save".equalsIgnoreCase(reqCode)) {
			MDXReportDTO dto = mdxReportForm.getMdxReportDTO();	
			dto.setQuery(UTF8.cnvUTF8(dto.getQuery()));
			dto.setQueryName(UTF8.cnvUTF8(dto.getQueryName()));
			dto = srv.saveMDXReport(dto);
			mdxReportForm.setMdxReportDTO(dto);
			mdxReportForm.setDimensionTreeLST(srv.getDimensionTreeLST(""));
			request.setAttribute("mdxReportForm", mdxReportForm);
			return mapping.findForward("edit");
		}else if ("new".equalsIgnoreCase(reqCode)) {
			//int reportId = NVL.getInt(request.getParameter("reportId"));
			//mdxReportForm.setMdxReportDTO(srv.getMDXReportDTO(reportId));
			mdxReportForm.setDimensionTreeLST(srv.getDimensionTreeLST(""));
			request.setAttribute("mdxReportForm", mdxReportForm);
			return mapping.findForward("edit");
		}else if("execute".equalsIgnoreCase(reqCode)){
			try {
				AIPPivotParam param = new AIPPivotParam();
				param.setFormAction("pivotview.jsp");
				//param.setMdxQuery(param.getMdxQuery().replaceAll("&", "%26"));
				System.out.println("MDXReportAction.execute():mdxReportForm.getMdxReportDTO().getQuery().trim()="+URLEncoder.encode( mdxReportForm.getMdxReportDTO().getQuery().trim() ,"iso8859-1"));
				
				param.setMdxQuery(UTF8.cnvUTF8(mdxReportForm.getMdxReportDTO().getQuery().trim()));
				
				param.setQueryName(UTF8.cnvUTF8(mdxReportForm.getMdxReportDTO().getQueryName()));
				System.out.println(param.getMdxQuery().replaceAll("\r\n", "%0D%0A"));
/*				param.setMdxQuery("SELECT {[Measures].[تعداد حواله],[Measures].[مبلغ پایه],[Measures].[مبلغ فیش],[Measures].[مبلغ کل],[Measures].[مقدار] "+
					       ",[Measures].[نرخ فرآورده]} on columns, "+
					       "{[مناطق].[منطقه-ناحیه].[همه]} "+
						   "on rows "+
					"from [حواله] "+
					"where  [زمان].[سال-فصل-ماه].[سال].&[1387]");
*/
//				/UTF8.makeObjUTF8(param);
				param.setLoader("mdxreport.do?reqCode=return&reportId="+mdxReportForm.getMdxReportDTO().getId()+"&mdxQuery="+param.getMdxQuery()+"&queryName="+param.getQueryName());
				request.getSession().setAttribute("AIPPivotParam",param);
				
				response.sendRedirect("pivotview.jsp?reqCode=refresh");
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if ("return".equalsIgnoreCase(reqCode)) {
			int reportId = NVL.getInt(request.getParameter("reportId"));
			mdxReportForm.setMdxReportDTO(srv.getMDXReportDTO(reportId));
			mdxReportForm.getMdxReportDTO().setQuery(UTF8.cnvUTF8(request.getParameter("mdxQuery")));
			mdxReportForm.getMdxReportDTO().setQueryName(UTF8.cnvUTF8(request.getParameter("queryName")));
			mdxReportForm.setDimensionTreeLST(srv.getDimensionTreeLST(""));
			request.setAttribute("mdxReportForm", mdxReportForm);
			return mapping.findForward("edit");
		}
		MDXReportLST lst =	srv.getMDXReportDTOs();
		mdxReportForm.setMdxReportLST(lst);

		request.setAttribute("mdxReportForm", mdxReportForm);
		return mapping.findForward("list");
	}
}