/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package aip.niopdc.sellbi.struts.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.symbol.xmlaprocessor.request.Request;

import aip.common.AIPDefaultParam;
import aip.common.AIPWebUser;
import aip.common.folder.FolderAndReportDTO;
import aip.common.folder.FolderDTO;
import aip.common.folder.FolderInterface;
import aip.niopdc.sellbi.struts.SellRemoteManager;
import aip.util.NVL;

public class NavigationAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		//response.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=utf-8");
		
		int id=NVL.getInt( request.getParameter("id") );
		
		

			try {
				PrintWriter out = response.getWriter();
				AIPDefaultParam param = new AIPDefaultParam();
				AIPWebUser webUser = new AIPWebUser();
				param.setWebUser(webUser);
				
//				ArrayList<FolderAndReportDTO> list = getDAO().getFolderAndReportTree(param);
				if(request.getParameter("foldeId")!=null){
					param.setId(NVL.getInt(request.getParameter("folderId")));
				}
				ArrayList<FolderAndReportDTO> list = getDAO().getFolderAndReportTreeForSelectedNode(param);
//				ArrayList<ReportDTO> list = getDAO().getFolderTree(param);
				recursiveViewAppendNode(out, list);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return null;
	}

	private void recursiveViewAppendNode(PrintWriter out,ArrayList<FolderAndReportDTO> list) {
		
		FolderAndReportDTO dto ;
		
		for (int i=list.size()-1;i>=0;i--) {
			dto = list.get(i);
			String oid = NVL.getString( dto.getId() );
			String text = dto.getCaption();	
			
			
			if(dto.getIsFolder()){
				out.print("<ul><li class=\".folder-close\"><span class=\"toggler\" onclick=\"navigation.do?folderId='"+oid+"'></span><span class=\"text\" onclick=\"selectedNodeChange('");
				out.print(oid);
				out.print("');\" id=\"");
				out.print(oid);
				out.print("\"><a href=\"aipbifolder.jsp?folderId="+oid+"\">");
//				out.print("\">");
				out.print(text);
				out.print("</a></span>");
				out.print("<ul style=\"display: block;\">");
			}else{
				out.print("<ul><li class=\"leaf-last\"><span class=\"text\" onclick=\"selectedNodeChange('");
				out.print(oid);
				out.print("');\" id=\"");
				out.print(oid);
				out.print("\"><a href=\"aipbimdx.jsp?reportId="+oid+"\">");
				out.print(text);
				out.print("</a></span></li>");
			}
			
			recursiveViewAppendNode(out, dto.getChildrens());
			
			
			if(dto.getIsFolder()){
				out.print("</ul></li>");
				out.print("</ul>");
			}else{
				out.print("</ul>");
			}
		}
	}
	FolderInterface getDAO() {
		return SellRemoteManager.getFolderInterface();
	}
}