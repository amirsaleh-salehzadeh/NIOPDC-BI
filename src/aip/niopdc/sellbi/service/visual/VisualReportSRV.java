package aip.niopdc.sellbi.service.visual;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import aip.common.JDBCConectionManager;
import aip.common.visualreport.VisualReportDTO;
import aip.common.visualreport.VisualReportDetailDTO;
import aip.common.visualreport.VisualReportLST;
import aip.util.AIPUtil;
import aip.util.NVL;
import aip.xmla.AIPOlap;


public class VisualReportSRV extends JDBCConectionManager{
	
	public VisualReportLST getVisualReportDTOs(){
		VisualReportLST lst = new VisualReportLST();
		List<VisualReportDTO> list = new ArrayList<VisualReportDTO>();

		try{
			Statement stmt = JDBCConectionManager.getAipCN().createStatement();
			ResultSet rs = stmt.executeQuery("select * from mrsvisualreport");
			VisualReportDTO dto = null;
			while(rs.next()){
				dto = new VisualReportDTO();
				dto.setId(NVL.getInt(rs.getInt("ID")));
				dto.setQueryName(NVL.getString(rs.getString("QueryName")));
				list.add(dto);
			}
			System.out.println("mdxList...........list.size="+list.size());			 				 
		}catch(SQLException ex) {
			ex.printStackTrace();
			System.err.print("SQLException: ");
			System.err.println(ex.getMessage());
		}
		lst.setVisualReportDTOs(list);
		return lst;
	}
	
	public void deleteVisualReport(String selectedIds){
		Integer ids[] = AIPUtil.splitString2Integer(selectedIds, ",");
		
		try {
			Statement stmt = JDBCConectionManager.getAipCN().createStatement();	
			for(int i=0;i<ids.length;i++)
				stmt.executeUpdate("delete from mrsvisualreport where ID="+ids[i]);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteVisualReport(int id){
		try {
			Statement stmt = JDBCConectionManager.getAipCN().createStatement();	
			stmt.executeUpdate("delete from mrsvisualreport where ID="+id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public VisualReportDTO getVisualReportDTO(int reportId){
		VisualReportDTO dto = new VisualReportDTO();
		try{
			Statement stmt = JDBCConectionManager.getAipCN().createStatement();
			ResultSet rs = stmt.executeQuery("select * from mrsvisualreport where ID="+reportId);
			if(rs.next()){
				dto.setId(NVL.getInt(rs.getInt("ID")));
				dto.setQueryName(NVL.getString(rs.getString("QueryName")));
				dto.setQuery(NVL.getString(rs.getString("Query")));
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
			System.err.print("SQLException: ");
			System.err.println(ex.getMessage());
		}
		dto.setChildren(getVisualReportDetails(dto.getId()));
		return dto;
	}

	public VisualReportDTO saveVisualReportDTO(VisualReportDTO dto){
		try{
			if(NVL.getInt(dto.getId())==0){
				PreparedStatement stmt = getAipCN().prepareStatement("insert into mrsvisualreport (QueryName,Query) VALUE (?,?)", Statement.RETURN_GENERATED_KEYS);
				stmt.setObject(1, NVL.getStringNull(dto.getQueryName()));
				stmt.setObject(2, NVL.getStringNull(dto.getQuery()));
				stmt.executeUpdate();
				ResultSet rs = stmt.getGeneratedKeys();
				int id = 0;
				if(rs != null && rs.next()){
					id = rs.getInt(1);
					rs.close();
				}
				dto.setId(id);
				stmt.clearParameters();
				stmt.close();
				//stmt.executeUpdate("insert into mrsvisualreport (QueryName,Query) VALUE ("+dto.getQueryName()+","+dto.getQuery()+")");
			}else{
				//Statement stmt = JDBCConectionManager.getAipCN().createStatement();
				//stmt.executeUpdate("update mrsvisualreport SET(QueryName="+dto.getQueryName()+",Query="+dto.getQuery()+ ") where ID="+dto.getId());

				PreparedStatement stmt = getAipCN().prepareStatement("update mrsvisualreport SET QueryName=?,Query=? " +
																		   " where ID=?");
				stmt.setObject(1, NVL.getStringNull(dto.getQueryName()));
				stmt.setObject(2, NVL.getStringNull(dto.getQuery()));
				stmt.setInt(3, NVL.getInteger(dto.getId()));
				stmt.executeUpdate();
				stmt.clearParameters();
				stmt.close();

			}
		}catch(SQLException ex) {
			System.err.print("SQLException: ");
			ex.printStackTrace();
		}

		return getVisualReportDTO(dto.getId());
	}
	 

	public String createVisualQuery(String measureUniqueName,String rowUniqueName,String columnUniqueName,String filterUniqueName) {
		String q = "";
		String rowStr = "";
		String columnStr = "";
		String measureStr = "";
		String filterStr = "";
		if(/*!"".equals(measureUniqueName) || */!"".equals(rowUniqueName) || !"".equals(columnUniqueName) /*|| !"".equals(filterUniqueName)*/){
			
			if(!"".equals(rowUniqueName)){
				String rowArr[] = AIPUtil.splitString(rowUniqueName, ",");
				rowStr = " { ";
				for(int i=0;i<rowArr.length;i++){
					if(rowArr[i]!=",")
						rowStr+=rowArr[i]+",";
					
				}
				rowStr = rowStr.substring(0, rowStr.length()-1);
				rowStr+=" } on rows ";
			}
			if(!"".equals(columnUniqueName)){
				String columnArr[] = AIPUtil.splitString(columnUniqueName, ",");
				columnStr = " { ";
				for(int i=0;i<columnArr.length;i++){
					columnStr+=columnArr[i]+",";
					
				}
				columnStr = columnStr.substring(0, columnStr.length()-1);
				columnStr+=" } on columns ";
			}
			
			if(!"".equals(measureUniqueName)){
				String measureArr[] = AIPUtil.splitString(measureUniqueName, ",");
				measureStr = " { ";
				for(int i=0;i<measureArr.length;i++){
					measureStr+=measureArr[i]+",";
					
				}
				measureStr = measureStr.substring(0, measureStr.length()-1);
				measureStr+=" } on pages ";
			}


			if(!"".equals(filterUniqueName)){
				String filterArr[] = AIPUtil.splitString(filterUniqueName, ",");
				filterStr = " { ";
				for(int i=0;i<filterArr.length;i++){
					filterStr+=filterArr[i]+",";
					
				}
				filterStr = filterStr.substring(0, filterStr.length()-1);
				filterStr+=" } ";
			}
			
			q = "select "+rowStr+( !"".equals(rowStr) && !"".equals(columnStr) ? "," : "")+columnStr+
				((!"".equals(rowStr) || !"".equals(columnStr)) && !"".equals(measureStr) ? "," : "")+measureStr+
				" from ["+AIPOlap.getCubeName()+"] "+
				(!"".equals(filterStr) ? " where " : "")+filterStr;

		}
		
		return q;
	}
	public String createVisualQuery(String[] rows,String[] columns,String[] filters,String[] measures){
		String rowIds = getUniqueIds4Query(rows);
 		String columnIds = getUniqueIds4Query(columns);
		try {
			System.out.println("********888888888***="+URLEncoder.encode( columnIds.trim() ,"iso8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

 		String filterIds = getUniqueIds4Query(filters);
 		String measureIds = getUniqueIds4Query(measures);
 		String query = createVisualQuery(measureIds, rowIds,columnIds, filterIds);
 		return query;
	}
	public VisualReportDTO saveVisualReport(VisualReportDTO dto,String[] rows,String[] columns,String[] filters,String[] measures){
		VisualReportDetailDTO detaiDTO = new VisualReportDetailDTO();
		detaiDTO.setVisualReportId(dto.getId());
		

		try{
			JDBCConectionManager.getAipCN().setAutoCommit(false);
			
	 		/*
	 		 * save VisualReportDTO
	 		 */
			String rowIds = getUniqueIds4Query(rows);
	 		String columnIds = getUniqueIds4Query(columns);
	 		String filterIds = getUniqueIds4Query(filters);
	 		String measureIds = getUniqueIds4Query(measures);
	 		String query = createVisualQuery(measureIds, rowIds,columnIds, filterIds);
	 		query = query.replaceAll("%26","&");
	 		dto.setQuery(query);
	 		dto = saveVisualReportDTO(dto);

	 		
			PreparedStatement stmt = JDBCConectionManager.getAipCN().prepareStatement("delete from mrsvisualreportdetail " +
																							" where F_MRSVisualReportID=?");
			stmt.setInt(1, dto.getId());
			stmt.executeUpdate();
			stmt.clearParameters();

			/*
	 		 * get rowIds & dim & save in db
	 		 */
	 		//List<String> rowsDim=new ArrayList<String>();
	 		if(rows != null && rows.length>0){
	 			saveVisualReportDetail(dto.getId(),rows,"Row");
	 		}
	 		
	 		/*
	 		 * get columnIds & dim & save in db 
	 		 */
	 		if(columns != null && columns.length>0){
	 			saveVisualReportDetail(dto.getId(),columns,"Column");	 		
	 		}
	
	 		/*
	 		 * get filterIds & dim & save in db
	 		 */
	 		if(filters != null && filters.length>0){
	 			saveVisualReportDetail(dto.getId(),filters,"Filter");
	 		}
	
	 		/*
	 		 * get measureIds & dim & save in db
	 		 */
	 		if(measures != null && measures.length>0){
	 			saveVisualReportDetail(dto.getId(),measures,"Measure");
	 		}
	

			JDBCConectionManager.getAipCN().commit();
			JDBCConectionManager.getAipCN().setAutoCommit(true);
		}catch(SQLException se){
			try {
				JDBCConectionManager.getAipCN().rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			se.printStackTrace();
		}
		dto.setChildren(getVisualReportDetails(dto.getId()));
		return dto;
	}

	private List<VisualReportDetailDTO> getVisualReportDetails(Integer id) {
		List<VisualReportDetailDTO> list = new ArrayList<VisualReportDetailDTO>();
		try {
			Statement stmt = JDBCConectionManager.getAipCN().createStatement();
			ResultSet rs = stmt.executeQuery("select * from mrsvisualreportdetail m where m.F_MRSVisualReportID="+NVL.getInt(id));
			
			VisualReportDetailDTO dto = null;
			while(rs.next()){
				dto = new VisualReportDetailDTO();
				dto.setId(NVL.getInteger(rs.getInt("ID")));
				dto.setVisualReportId(NVL.getInteger(rs.getInt("F_MRSVisualReportID")));
				dto.setOrderNo(NVL.getInteger(rs.getInt("OrderNo")));
				dto.setType(NVL.getStringNull(rs.getString("Type")));
				dto.setDimension(NVL.getStringNull(rs.getString("Dimension")));
				dto.setSelectedMembers(NVL.getStringNull(rs.getString("SelectedMembers")));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	private void saveVisualReportDetail(int visualReportId,String[] rows, String type) throws SQLException {
		PreparedStatement updateStmt = JDBCConectionManager.getAipCN().prepareStatement("insert into mrsvisualreportdetail " +
				" SET F_MRSVisualReportID = ? " +
				" ,OrderNo=?,Type=?,Dimension=?,SelectedMembers=?");
		String ids="";
		for(String st :rows){
			//st = UTF8.cnvUTF8(st);
			if(st.indexOf('{')>0 && st.indexOf("}")>0){
				String dim = st.substring(st.indexOf('{')+1, st.indexOf('}'));
				//rowsDim.add(dim);
	 			
				ids+=st.substring(st.indexOf('}')+1);
				ids = ids.replaceAll("%26","&");
				
				ids = removeDuplicated(ids);
				
	 			
				updateStmt.setInt(1, visualReportId);
				updateStmt.setObject(2, null);
				updateStmt.setString(3, type);
				updateStmt.setString(4, dim);
				updateStmt.setObject(5, NVL.getStringNull(ids));
				updateStmt.executeUpdate();
				
				updateStmt.clearParameters();
				
			}
 		}
		updateStmt.close();
	}
	private String removeDuplicated(String ids) {
		String[] arr = AIPUtil.splitString(ids,  ",");
		List<String> list = new ArrayList<String>();
		for(String s:arr){
			if(!list.contains(s))
				list.add(s);
		}
		String st = (list.size()>0 ? ","+AIPUtil.joinSelectedIds(list.toArray(), ",")+"," : "");
		return st;
	}

	private String getUniqueIds4Query(String[] arr){
		String ids="";
		if(arr != null && arr.length>0){
			for(String st :arr){
				//st = UTF8.cnvUTF8(st);
				ids+=st.substring(st.indexOf('}')+1);
			}
		}
		return ids;
	}
	
	public String getSelectedNames(String selectedUniqueName){
		String name = AIPOlap.getMemberName(AIPOlap.getCubeName(), selectedUniqueName);
		return name;
	}
}
