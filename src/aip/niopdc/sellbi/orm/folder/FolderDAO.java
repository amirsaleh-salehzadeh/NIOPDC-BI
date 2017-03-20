package aip.niopdc.sellbi.orm.folder;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aip.common.AIPBaseDeleteParam;
import aip.common.AIPDefaultParam;
import aip.common.AIPException;
import aip.common.folder.FolderAndReportDTO;
import aip.common.folder.FolderDTO;
import aip.common.folder.FolderENT;
import aip.common.folder.FolderInterface;
import aip.niopdc.sellbi.orm.BaseHibernateDAO;
import aip.util.AIPUtil;
import aip.util.NVL;

public class FolderDAO extends BaseHibernateDAO implements FolderInterface{

	public void deleteFolder(AIPBaseDeleteParam param) {
		if(param.getId()>0)
			super.delete(FolderENT.class, param.getId());
		else if(!"".equals(NVL.getString(param.getIds()))){
			super.deleteSelectedIds(FolderENT.class, param.getIds());
		}else if(!"".equals(NVL.getString(param.getCriteria()))){
			super.deleteByCondition(FolderENT.class, param.getCriteria());
		}
	}

	public FolderENT getForlderENT(AIPDefaultParam param) {
		return (FolderENT)get(FolderENT.class, param.getId());
	}

	public void makePrivate(AIPDefaultParam param) throws AIPException{
		FolderENT ent = (FolderENT)get(FolderENT.class, param.getId());
		Session session = getSession();
		Transaction tx = null;
		try{
			tx=session.beginTransaction();

			Query q = getSession().createQuery("from FolderENT where hierarchy like '"+ent.getHierarchy()+"%'");
			List<FolderENT> list = (ArrayList<FolderENT>)q.list();
			for(FolderENT child:list){
				child.setIsPublic(false);
				child = (FolderENT)super.saveNoTran(child, session);
			}

			ent.setIsPublic(false);
			ent = (FolderENT)super.saveNoTran(ent, session);
	    	tx.commit();
		}catch (HibernateException e) {
			e.printStackTrace();
			if(tx!=null && tx.isActive() )
				tx.rollback();
			session.clear();
			session.close();
			throw getAIPException(AIPEX_SAVE,e);
		}
		
	}

	public void makePublic(AIPDefaultParam param) {
		FolderENT ent = (FolderENT)get(FolderENT.class, param.getId());
		ent.setIsPublic(true);
		ent = (FolderENT)super.save(ent);
	}

	public FolderENT saveFolder(FolderENT ent) throws AIPException {
		Session session = getSession();
		Transaction tx = null;
		try{
			String h = "";
			if(NVL.getInt(ent.getParentId()) != 0){
				Query q = session.createQuery("select hierarchy from FolderENT where id=?");
				q.setInteger(0, ent.getParentId());
				h = (String)q.uniqueResult();
			}

			tx=session.beginTransaction();
			ent = (FolderENT)super.saveNoTran(ent,session);
			
			ent.setHierarchy("".equals(h) ? "."+ent.getId()+"." : h+ent.getId()+".");
	    	tx.commit();
		}catch (HibernateException e) {
			e.printStackTrace();
			if(tx!=null && tx.isActive() )
				tx.rollback();
			session.clear();
			session.close();
			throw getAIPException(AIPEX_SAVE,e);
		}

		return ent;
	}

	public ArrayList<FolderDTO> getFolderTree(AIPDefaultParam param){
		ArrayList<FolderDTO> ents = new ArrayList<FolderDTO>();
		Query q = getSession().getNamedQuery("spBIGetFolders");
		q.setString(0, NVL.getString(param.getCriteria()));
		q.setString(1, NVL.getString(param.getWebUser().getRemoteUser()));
		ents = (ArrayList<FolderDTO>)q.list();
		ents = (ArrayList<FolderDTO>)AIPUtil.exchangeList2Tree(ents, "Id", "ParentId", "Childrens");
		return ents;
	}

	public ArrayList<FolderAndReportDTO> getFolderAndReportTree(AIPDefaultParam param){
		ArrayList<FolderAndReportDTO> list = new ArrayList<FolderAndReportDTO>();
		Query q = getSession().getNamedQuery("spBIGetFolderAndReports");
		q.setString(0, NVL.getString(param.getCriteria()));
		q.setString(1, NVL.getString(param.getWebUser().getRemoteUser()));
		list = (ArrayList<FolderAndReportDTO>)q.list();
		list = (ArrayList<FolderAndReportDTO>)AIPUtil.exchangeList2Tree(list, "UniqueId", "ParentUniqueId", "Childrens");
		return list;
	}
	
	public ArrayList<FolderAndReportDTO> getFolderAndReportTreeForSelectedNode(AIPDefaultParam param){
		ArrayList<FolderAndReportDTO> list = new ArrayList<FolderAndReportDTO>();
		Query q = getSession().getNamedQuery("spBIGetFolderAndReportsForOneLevel");
		q.setString(0, NVL.getString(param.getCriteria()));
		q.setString(1, NVL.getString(param.getWebUser().getRemoteUser()));
		q.setInteger(2, NVL.getInt(param.getId()));
		list = (ArrayList<FolderAndReportDTO>)q.list();
		list = (ArrayList<FolderAndReportDTO>)AIPUtil.exchangeList2Tree(list, "UniqueId", "ParentUniqueId", "Childrens");
		return list;
	}
}
