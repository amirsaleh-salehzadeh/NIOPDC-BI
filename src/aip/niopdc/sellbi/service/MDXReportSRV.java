package aip.niopdc.sellbi.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import aip.common.JDBCConectionManager;
import aip.common.mdxreport.DimensionTreeLST;
import aip.common.mdxreport.MDXReportDTO;
import aip.common.mdxreport.MDXReportLST;
import aip.util.AIPUtil;
import aip.util.NVL;
import aip.xmla.AIPOlap;


public class MDXReportSRV extends JDBCConectionManager{

 
	public MDXReportLST getMDXReportDTOs(){
		MDXReportLST lst = new MDXReportLST();
		List<MDXReportDTO> list = new ArrayList<MDXReportDTO>();

		try{
			Statement stmt = JDBCConectionManager.getAipCN().createStatement();
			ResultSet rs = stmt.executeQuery("select * from mrsmdxreport");
			MDXReportDTO dto = null;
			while(rs.next()){
				dto = new MDXReportDTO();
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
		lst.setMdxReportDTOs(list);
		return lst;
	}
	
	public void deleteMDXReport(String selectedIds){
		Integer ids[] = AIPUtil.splitString2Integer(selectedIds, ",");
		
		try {
			Statement stmt = JDBCConectionManager.getAipCN().createStatement();	
			for(int i=0;i<ids.length;i++)
				stmt.executeUpdate("delete from mrsmdxreport where ID="+ids[i]);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MDXReportDTO getMDXReportDTO(int reportId){
		MDXReportDTO dto = new MDXReportDTO();
		try{
			Statement stmt = JDBCConectionManager.getAipCN().createStatement();
			ResultSet rs = stmt.executeQuery("select * from mrsmdxreport where ID="+reportId);
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

		return dto;
	}

	public MDXReportDTO saveMDXReport(MDXReportDTO dto){
		try{
			if(NVL.getInt(dto.getId())==0){
				PreparedStatement stmt = getAipCN().prepareStatement("insert into mrsmdxreport (QueryName,Query) VALUE (?,?)", Statement.RETURN_GENERATED_KEYS);
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
				//stmt.executeUpdate("insert into mrsmdxreport (QueryName,Query) VALUE ("+dto.getQueryName()+","+dto.getQuery()+")");
			}else{
				//Statement stmt = JDBCConectionManager.getAipCN().createStatement();
				//stmt.executeUpdate("update mrsmdxreport SET(QueryName="+dto.getQueryName()+",Query="+dto.getQuery()+ ") where ID="+dto.getId());

				PreparedStatement stmt = getAipCN().prepareStatement("update mrsmdxreport SET QueryName=?,Query=? " +
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

		return getMDXReportDTO(dto.getId());
	}
	public DimensionTreeLST getDimensionTreeLST(String uniqueName){
		DimensionTreeLST lst = new DimensionTreeLST();
		//lst.setDimensionTree(AIPOlap.getDimensionMemberTree());
		lst.setTree(AIPOlap.getDimensionTreeElement());
		
		return lst;
	}
	 

}
