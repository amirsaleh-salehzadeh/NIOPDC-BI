/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package aip.niopdc.sellbi.struts.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import rex.graphics.dimensiontree.elements.DimensionElement;
import rex.graphics.dimensiontree.elements.HierarchyElement;
import rex.graphics.dimensiontree.elements.LevelElement;
import rex.graphics.dimensiontree.elements.MemberElement;
import rex.graphics.filtertree.elements.FilterTreeMemberElement;

import aip.tag.tree.AIPTree;
import aip.tag.tree.TreeNodeTypeEnum;
import aip.util.NVL;
import aip.util.UTF8;
import aip.xmla.AIPOlap;

/** 
 * MyEclipse Struts
 * Creation date: 09-21-2009
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class DimensionsAction extends Action {
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
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
//		response.setCharacterEncoding("utf8");
		response.setHeader("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">", "");		response.setContentType("text/html");
		
		String cubeName="حواله";

		try {
			String reqCode = NVL.getEmptyString(request.getParameter("reqCode"),"dimensions");
			String id=UTF8.cnvUTF8( request.getParameter("id") );
			
			System.out.println("DimensionsAction.execute():reqCode="+reqCode);

			PrintWriter out = response.getWriter();
			String text="", url,oid="";
		
			//String nodeType = NVL.getString(request.getParameter("nodeType"), "DEFAULT");
			if("dimensions".equalsIgnoreCase(reqCode)){
				java.util.List<DimensionElement> lstDimensions=null;
				lstDimensions=AIPOlap.getDimensions(cubeName);

				for (DimensionElement dim : lstDimensions) {
					oid = dim.getDimensionUniqueName();
					text = dim.getCaption();
					url = "dimensions.do?reqCode=dimension&id="+oid;
					//AIPTree.appendTreeNode(out, text, url );
					AIPTree.appendTreeNode(out, text, url,"dbclickCreateQuery(reportQuery,this);",oid);
				}			
			}else if("dimension".equalsIgnoreCase(reqCode)){
				java.util.List<HierarchyElement> lstHierarchies=null;
				lstHierarchies=AIPOlap.getHierarchys(cubeName, id);

				for (HierarchyElement hierarchy : lstHierarchies) {
					oid = hierarchy.getHierarchyUniqueName();
					text = hierarchy.getCaption();
					url = "dimensions.do?reqCode=hierarchy&id="+oid+"&dim="+id;
					AIPTree.appendTreeNode(out, text, url,"dbclickCreateQuery(reportQuery,this);",oid);
				}			
			}else if("hierarchy".equalsIgnoreCase(reqCode)){
				String dim=UTF8.cnvUTF8(request.getParameter("dim"));
				java.util.List<LevelElement> lstLevels=null;
				lstLevels=AIPOlap.getLevels(cubeName,dim, id);

				for (LevelElement level : lstLevels) {
					oid = level.getUniqueName();
					text = level.getCaption();
					url = "dimensions.do?reqCode=level&id="+oid+"&dim="+dim+"&hierarchy="+id;
					//AIPTree.appendTreeNode(out, text, url);
					AIPTree.appendTreeNodeLeaf(out, text, "dbclickCreateQuery(reportQuery,this);",id);
				}			
//			}else if("level".equalsIgnoreCase(reqCode)){
//				String dim=UTF8.cnvUTF8(request.getParameter("dim"));
//				String hierarchy=UTF8.cnvUTF8(request.getParameter("hierarchy"));
//				java.util.List<FilterTreeMemberElement> lstMembers=null;
//				lstMembers=AIPOlap.getMembers(cubeName,dim,hierarchy, id);
//
//				for (FilterTreeMemberElement member : lstMembers) {
//					oid = member.getUniqueName();
//					text = member.getCaption();
//					url = "dimensions.do?reqCode=member&id="+oid+"&dim="+dim+"&hierarchy="+hierarchy+"&level="+id;
//					AIPTree.appendTreeNode(out, text, url);
//					break;
//				}			
//			}else if("member".equalsIgnoreCase(reqCode)){
//				String dim=UTF8.cnvUTF8(request.getParameter("dim"));
//				String hierarchy=UTF8.cnvUTF8(request.getParameter("hierarchy"));
//				String level=UTF8.cnvUTF8(request.getParameter("level"));
//				java.util.List<FilterTreeMemberElement> lstMembers=null;
//				lstMembers=AIPOlap.getMemberChildren(cubeName,dim,hierarchy,level ,id);
//
//				for (FilterTreeMemberElement member : lstMembers) {
//					oid = member.getUniqueName();
//					text = member.getCaption();
//					url = "dimensions.do?reqCode=member&id="+oid+"&dim="+dim+"&hierarchy="+hierarchy+"&level="+level+"&parent="+id;
//					if(member.getChildrenCount()>0){
//						AIPTree.appendTreeNode(out, text, url);
//					}else{
//						AIPTree.appendTreeNodeLeaf(out, text,"",id);
//					}
//				}			
//			}else{
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}