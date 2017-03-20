package aip.niopdc.sellbi.ejb.report;

import javax.ejb.Stateless;

import aip.common.AIPBaseDeleteParam;
import aip.common.AIPDefaultParam;
import aip.common.AIPException;
import aip.common.report.ReportENT;
import aip.common.report.ReportForSaveParam;
import aip.common.report.ReportInterface;
import aip.common.report.ReportLST;
import aip.common.report.ReportLSTParam;
import aip.niopdc.sellbi.orm.report.ReportDAO;


@Stateless
public class ReportBean implements ReportBeanLocal, ReportBeanRemote {

	public void deleteReport(AIPBaseDeleteParam param) {
		ReportInterface dao = new ReportDAO();
		dao.deleteReport(param);
	}

	public ReportLST getAllReports(ReportLSTParam param) {
		ReportInterface dao = new ReportDAO();
		return dao.getAllReports(param);
	}

	public ReportLST getMDXReports(ReportLSTParam param) {
		ReportInterface dao = new ReportDAO();
		return dao.getMDXReports(param);
	}

	public ReportENT getReportENT(AIPDefaultParam param) {
		ReportInterface dao = new ReportDAO();
		return dao.getReportENT(param);
	}

	public ReportLST getVisualReports(ReportLSTParam param) {
		ReportInterface dao = new ReportDAO();
		return dao.getVisualReports(param);
	}

	public void makePrivate(AIPDefaultParam param) {
		ReportInterface dao = new ReportDAO();
		dao.makePrivate(param);
	}

	public void makePublic(AIPDefaultParam param) {
		ReportInterface dao = new ReportDAO();
		dao.makePublic(param);
	}

	public ReportENT saveReport(ReportForSaveParam param) throws AIPException {
		ReportInterface dao = new ReportDAO();
		return dao.saveReport(param);
	}

	public String createVisualQuery(String[] rows,String[] columns,String[] filters,String[] measures){
		ReportInterface dao = new ReportDAO();
		return dao.createVisualQuery(rows, columns, filters, measures);
	}
}
