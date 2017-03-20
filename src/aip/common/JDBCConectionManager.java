package aip.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import aip.common.mdxreport.MDXReportDTO;
import aip.util.NVL;


public class JDBCConectionManager {
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws HeadlessException 
	 */

	
	static Connection _cnAip = null;

	public static void main(String[] args) {
		ArrayList<MDXReportDTO> list = new ArrayList<MDXReportDTO>();
		try {
			getAipCN();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	}
	public static synchronized Connection getAipCN() throws java.sql.SQLException {
			if (_cnAip == null) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				_cnAip = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/niopdc_bi?characterEncoding=utf8", "root", "root");
			}
			return _cnAip;
		}
	
	public static synchronized void closeAipCN() {
			if (_cnAip == null) {
				try {
					_cnAip.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			_cnAip = null;
		}

	

}
