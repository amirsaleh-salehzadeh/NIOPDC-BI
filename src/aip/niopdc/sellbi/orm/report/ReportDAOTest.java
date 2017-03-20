package aip.niopdc.sellbi.orm.report;

//import static org.junit.Assert.fail;

//import org.junit.Test;

import aip.common.AIPDefaultParam;
import aip.common.AIPException;
import aip.common.report.ReportCriteriaENT;
import aip.common.report.ReportDetailENT;
import aip.common.report.ReportENT;
import aip.common.report.ReportForSaveParam;
import aip.common.report.ReportLSTParam;
import aip.util.AIPUtil;
import aip.util.DateCnv;

public class ReportDAOTest {
	ReportDAO dao = new ReportDAO();

	public static void main(String[] args) {
		ReportDAOTest t = new ReportDAOTest();
		//System.out.println(ChartTypeEnum.VerticalLine.name());
		System.out.println(DateCnv.nowJalali());
		
		t.testSaveReport();
		//t.testGetAllReports();
		//t.testGetMDXReports();
		//t.testGetVisualReports();
		//t.testGetReportENT();
	}




//	@Test
	public void testDeleteReport() {

	}

//	@Test
	public void testGetAllReports() {
		ReportLSTParam param = new ReportLSTParam();
		param.setPageSize(10);
		AIPUtil.printObject(dao.getAllReports(param));
	}

//	@Test
	public void testGetMDXReports() {
		ReportLSTParam param = new ReportLSTParam();
		param.setPageSize(10);
		AIPUtil.printObject(dao.getMDXReports(param));
	}

//	@Test
	public void testGetReportENT() {
		AIPDefaultParam param = new AIPDefaultParam();
		param.setId(3);
		AIPUtil.printObject(dao.getReportENT(param));
	}

//	@Test
	public void testGetVisualReports() {
		ReportLSTParam param = new ReportLSTParam();
		param.setPageSize(10);
		AIPUtil.printObject(dao.getVisualReports(param));
	}

//	@Test
	public void testMakePrivate() {
//		fail("Not yet implemented");
	}

//	@Test
	public void testMakePublic() {
//		fail("Not yet implemented");
	}

//	@Test
	public void testSaveReport() {
		ReportENT ent = new ReportENT();
		ent.setReportName("1111111تست1");
		//ent.setId(3);
		ReportDetailENT d = new ReportDetailENT();
		//d.setReportId(3);
		d.setType("Row");
		d.setDimension("dimension");
		d.setSelectedMembers("selectedMembers");
		d.setOrderNo(1);
		ent.getReportDetailENTs().add(d);
		
		ReportCriteriaENT criteriaENT = new ReportCriteriaENT();
		criteriaENT.setCriteriaMdx("CriteriaMdx");
		criteriaENT.setDefaultCaption("DefaultCaption");
		criteriaENT.setDefaultValue("DefaultValue");
		criteriaENT.setName("name");
		criteriaENT.setOrder(1);
		
		ent.getCriterias().add(criteriaENT);
		
		ReportForSaveParam param = new ReportForSaveParam();
		param.setReportENT(ent);

		try {
			AIPUtil.printObject(dao.saveReport(param));
		} catch (AIPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
