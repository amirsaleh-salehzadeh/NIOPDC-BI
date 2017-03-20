package aip.niopdc.sellbi.ejb.folder;

import java.util.ArrayList;

import javax.ejb.Stateless;

import aip.common.AIPBaseDeleteParam;
import aip.common.AIPDefaultParam;
import aip.common.AIPException;
import aip.common.folder.FolderAndReportDTO;
import aip.common.folder.FolderDTO;
import aip.common.folder.FolderENT;
import aip.common.folder.FolderInterface;
import aip.niopdc.sellbi.orm.folder.FolderDAO;


@Stateless
public class FolderBean implements FolderBeanLocal, FolderBeanRemote {

	public void deleteFolder(AIPBaseDeleteParam param) {
		FolderInterface dao = new FolderDAO();
		dao.deleteFolder(param);
	}

	public FolderENT getForlderENT(AIPDefaultParam param) {
		FolderInterface dao = new FolderDAO();
		return dao.getForlderENT(param);
	}

	public void makePrivate(AIPDefaultParam param) throws AIPException {
		FolderInterface dao = new FolderDAO();
		dao.makePrivate(param);
	}

	public void makePublic(AIPDefaultParam param) {
		FolderInterface dao = new FolderDAO();
		dao.makePublic(param);
	}

	public FolderENT saveFolder(FolderENT ent) throws AIPException {
		FolderInterface dao = new FolderDAO();
		return dao.saveFolder(ent);
	}

	public ArrayList<FolderAndReportDTO> getFolderAndReportTree(AIPDefaultParam param) {
		FolderInterface dao = new FolderDAO();
		return dao.getFolderAndReportTree(param);
	}

	public ArrayList<FolderDTO> getFolderTree(AIPDefaultParam param) {
		FolderInterface dao = new FolderDAO();
		return dao.getFolderTree(param);
	}



}
