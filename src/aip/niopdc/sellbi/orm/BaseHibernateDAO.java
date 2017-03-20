package aip.niopdc.sellbi.orm;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;

import aip.common.AIPException;
import aip.util.AIPUtil;
import aip.util.NVL;

/**
 * Data access object (DAO) for domain model
 * 
 * @author MyEclipse Persistence Tools
 */
public class BaseHibernateDAO implements IBaseHibernateDAO {

	public Session getSession() {
		return HibernateSessionFactory.getSession();
	}

	public Object saveNoTran(Object ent, Session session)
			throws HibernateException {

		// it may be throw exception when ent is new then you should check
		// Identity field that should be null NOT ZERO

		// because : when receiving data from web it create new ENT and it does
		// not match with cached ENT in hibernate and it cause exception already
		// exist object
		Object o = new Object();  
		o = session.merge(ent);

		// because : save only insert and we need update when id is exists
		session.saveOrUpdate(o);

		// because : receiving null for id in web after adding merge clause
		// ent=session.get(ent.getClass(),session.getIdentifier(ent));
		// session.refresh(ent);
		return o;

	}

	public Object save(Object ent, String identifierFied,
			Class[] ObjectsMustRefresh) {
		Transaction tx = null;
		Session session = getSession();
		;
		try {
			tx = session.beginTransaction();

			ent = saveNoTran(ent, session);

			tx.commit();

			refreshRelatedObjects(ent, identifierFied, ObjectsMustRefresh,
					session);

			// because : receiving null for id in web after adding merge clause
			// ent=session.get(ent.getClass(),session.getIdentifier(ent));
		} catch (HibernateException e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			session.close();
			throw e;
		}
		return ent;
	}

	// it don't work correctly instead of these use method refreshRelatedObjects
	// public Object saveNoTran(Object ent,String identifierFied, Class[]
	// ObjectsMustRefresh,Session session) {
	// if(true)throw new IllegalStateException("خانم زیبایی لطفا بجای استفاده از
	// این متد از متدهای 1.saveNoTran(,)normal و refreshRelatedObjects(,,,)
	// استفاده کنید");
	// //because: id=0 will make problem for hibernate : (instead of insert call
	// update comand) then id=0 should change to id=null
	// Integer id=null;
	// if(identifierFied!=null && !"".equals(identifierFied)){
	// try {
	// id=(Integer)AIPUtil.invoke(ent, identifierFied);
	// id=NVL.getInteger(id);
	// AIPUtil.invoke(ent, identifierFied,id);
	//				
	// System.out.println("BaseHibernateDAO.save()::::::::::::::::::");
	// AIPUtil.printObject(ent);
	// } catch (Exception e) {
	// System.out.println("BaseHibernateDAO.save():identifierFied:exception:"+e.getMessage());
	// }
	// }
	//
	// //because : when eceiving data from web it create new ENT and it does not
	// match with cached ENT in hibernate and it cause exception already exist
	// object
	// // ent=session.merge(ent);
	// //
	// // //because : save only insert and we need update when id is exists
	// // session.saveOrUpdate(ent);
	// // Serializable id=session.getIdentifier(ent);
	// // System.out.println("BaseHibernateDAO.save():id="+id);
	//			
	// ent=saveNoTran(ent,session);
	//
	//
	// if(ObjectsMustRefresh!=null && (session.contains(ent) || id!=null) ){
	// if(id==null)id=(Integer)session.getIdentifier(ent);
	// for(int i=0;i<ObjectsMustRefresh.length;i++){
	// session.refresh(session.load(ObjectsMustRefresh[i], id ));
	// }
	// }
	//			
	// return ent;
	// }
	public Object save(Object ent) {
		return save(ent, null, null);
	}

	public Object saveWithEx(Object ent) throws AIPException {
		try {
			return save(ent, null, null);
		} catch (Exception ex) {
			throw getAIPException(AIPEX_SAVE, ex);
		}
	}

	public void refreshRelatedObjects(Object ent, String identifierFied,
			Class[] ObjectsMustRefresh, Session session) {
		try {

			Integer id = null;
			if (identifierFied != null && !"".equals(identifierFied)) {
				try {
					id = (Integer) AIPUtil.invoke(ent, identifierFied);
					id = NVL.getInteger(id);
					AIPUtil.invoke(ent, identifierFied, id);

					System.out
							.println("BaseHibernateDAO.save()::::::::::::::::::");
					AIPUtil.printObject(ent);
				} catch (Exception e) {
					System.out
							.println("BaseHibernateDAO.save():identifierFied:exception:"
									+ e.getMessage());
				}
			}

			session.refresh(ent);

			if (ObjectsMustRefresh != null
					&& (session.contains(ent) || id != null)) {
				if (id == null)
					id = (Integer) session.getIdentifier(ent);
				for (int i = 0; i < ObjectsMustRefresh.length; i++) {
					session.refresh(session.load(ObjectsMustRefresh[i], id));
				}
			}

		} catch (HibernateException ex) {
			Transaction tx = session.getTransaction();
			if (tx != null && tx.isActive())
				tx.rollback();
			// session.flush();
			session.close();
			throw ex;
		}
	}

	public Object get(Class clazz, Serializable id) {
		return getSession().get(clazz, id);
	}

	public void deleteNoTran(Object ent, Session session) {
		try {
			session.delete(ent);
		} catch (HibernateException ex) {
			Transaction tx = session.getTransaction();
			if (tx != null && tx.isActive())
				tx.rollback();
			// session.flush();
			session.close();
			throw ex;
		}
	}

	public void deleteGetBeforeNoTran(Class clazz, Serializable id,
			Session session) {
		Object ent = session.get(clazz, id);
		deleteNoTran(ent, session);
	}

	public void delete(Object ent) {
		Transaction tx = null;
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			session.delete(ent);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null && tx.isActive())
				tx.rollback();
			// session.flush();
			session.close();
			throw ex;
		}
		// return ent;
	}

	public void delete(Class clazz, Serializable id) {
		Object ent = this.get(clazz, id);
		delete(ent);
	}

	/*
	 * public void deleteNoTran(Class clazz,Serializable id,Session session) {
	 * Object ent=this.get(clazz, id); deleteNoTran(ent,session); }
	 */
	public Object edit(Object ent) {
		Transaction tx = null;
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			session.update(ent);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			// session.flush();
			session.close();
			throw e;
		}
		return ent;
	}

	public void deleteByCondition(final Class clazz, String whereClause) {
		Transaction tx = null;
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			String queryString = "delete " + clazz.getName() + " where "
					+ whereClause;
			Query queryObject = session.createQuery(queryString);
			queryObject.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			// e.printStackTrace();
			if (tx != null && tx.isActive())
				tx.rollback();
			// session.flush();
			session.close();
			throw e;
		}
	}

	public void deleteByConditionNoTran(final Class clazz, String whereClause,
			Session session) {
		try {

			String queryString = "delete " + clazz.getName() + " where "
					+ whereClause;
			Query queryObject = session.createQuery(queryString);
			queryObject.executeUpdate();

		} catch (HibernateException e) {
			// e.printStackTrace();
			Transaction tx = session.getTransaction();
			if (tx != null && tx.isActive())
				tx.rollback();
			throw e;
		}
	}

	public void deleteByCondition(final Class clazz, String propertyName,
			Object value) {
		// Transaction tx = null;
		// Session session = getSession();
		// try {
		// tx = session.beginTransaction();
		// String queryString = "delete " + clazz.getName() + " as object where
		// "+propertyName+"= " + value;
		// Query queryObject = session.createQuery(queryString);
		// queryObject.executeUpdate();
		// tx.commit();
		// } catch (HibernateException e) {
		// //e.printStackTrace();
		// if (tx != null && tx.isActive())
		// tx.rollback();
		// //session.flush();
		// session.close();
		// throw e;
		// }
		deleteByCondition(clazz, propertyName + "= " + value);
	}

	public List findAll(final Class clazz) throws HibernateException {
		return getSession().createCriteria(clazz).list();

	}

	/**
	 * 
	 * adl its created for getting new no of any field of any table first usage
	 * was in LawBaseInfoDAO.getNewLawNo , ...
	 */
	public Integer getNewNo(String table, String field) {
		return getNewNo(table, field, null);
	}

	public Integer getNewNo(String table, String field, String clause) {
		if (clause != null && !"".equals(clause)) {
			clause = " where " + clause;
		} else {
			clause = "";
		}
		Query q = getSession().createSQLQuery(
				"select max(" + field + ") from " + table + clause);
		Integer max = (Integer) q.uniqueResult();
		if (max == null) {
			max = 1;
		} else {
			max += 1;
		}
		return max;
	}

	public void deleteSelectedIds(Class clazz, String selectedIds) {
		String[] ids = AIPUtil.splitSelectedIds(selectedIds, ",");

		Transaction tx = null;
		Session session = getSession();
		try {

			tx = session.beginTransaction();
			for (int i = 0; i < ids.length; i++) {
				deleteGetBeforeNoTran(clazz, new Integer(ids[i]), session);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			// session.flush();
			session.close();
			throw e;
		}
	}

	/**
	 * ‌Business Related General Methods
	 */
	// //from LawExecutorDAO
	// public int getLawId(int isLaw,int lawNo){
	// Query q = getSession().getNamedQuery("sqlGetLawRegulationId");
	// q.setInteger(0, lawNo);
	// q.setInteger(1, isLaw);
	// AIPGeneralDTO o=(AIPGeneralDTO) q.uniqueResult();
	// if(o!=null){
	// return o.getId().intValue();
	// }
	// return 0;
	// }
	// public String getLawCaption(int lawId){
	// Query q = getSession().getNamedQuery("sqlGetLawRegulationCaption");
	// q.setInteger(0, lawId);
	// Object o=q.uniqueResult();
	// if(o!=null){
	// return o.toString();
	// }
	// return "";
	// }
	// from CommonDAO
	public String getLawCaption(int lawId) {
		SQLQuery sqlQuery = null;
		String sql = "";
		sql = "SELECT `Caption` FROM `lwlaw` WHERE `ID` = " + lawId;
		String caption = "";
		sqlQuery = getSession().createSQLQuery(sql);
		caption = (String) sqlQuery.uniqueResult();
		return caption;
	}

	public int getLawId(int isLaw, int lawNo) {
		try {

			Query q = null;
			if (isLaw == 1) {
				// Query q = getSession().getNamedQuery("sqlGetLawId");
				q = getSession()
						.createQuery(
								"select l.id from LawENT l where l.isLaw=? and l.lawNo=?");
				q.setInteger(0, isLaw);
				q.setInteger(1, lawNo);
			} else {
				q = getSession()
						.createQuery(
								"select l.id from LawENT l where l.isReg=? and l.ruleNo=?");
				q.setInteger(0, 1);
				q.setInteger(1, lawNo);
			}
			Integer id = (Integer) q.uniqueResult();
			System.out
					.println(">>>>>>>>>>>>>>>>>BaseHibernateDAO.getLawId():id="
							+ id);
			if (id != null) {
				return NVL.getInt(id);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public int getPhaseId(int lawId, int phaseNo) {
		// Query q = getSession().getNamedQuery("sqlGetPhaseId");
		// it don't work because lwlaw and lwphase maped to class and couldn't
		// use
		Query q = getSession().createQuery(
				"select p.id from PhaseENT p where p.lawId=? and p.no=?");
		q.setInteger(0, lawId);
		q.setInteger(1, phaseNo);
		Integer id = (Integer) q.uniqueResult();
		if (id != null) {
			return NVL.getInt(id);
		}
		return 0;
	}

	public int getIsLaw(int lawId) {
		SQLQuery sqlQuery = null;
		String sql = "";
		sql = "SELECT `isLaw` FROM `lwlaw` WHERE `ID` = " + lawId;
		sqlQuery = getSession().createSQLQuery(sql);
		return NVL.getInt(sqlQuery.uniqueResult());
	}

	public static final int AIPEX_UNKNOWN = AIPException.AIPEX_UNKNOWN;
	public static final int AIPEX_DELETE = AIPException.AIPEX_DELETE;
	public static final int AIPEX_SAVE = AIPException.AIPEX_SAVE;
	public static final int AIPEX_SAVE_DUPLICATE = AIPException.AIPEX_SAVE_DUPLICATE;

	public AIPException getAIPException(Exception ex) {
		return getAIPException(AIPEX_UNKNOWN, ex);
	}

	public AIPException getAIPException(int operationType, Exception ex) {
		String msg = ex.getMessage();
		switch (operationType) {
		case AIPEX_DELETE:
			msg = "اشکال در حذف اطلاعات.";
			break;
		case AIPEX_SAVE:
			msg = "اشکال در ثبت اطلاعات.";
			break;
		case AIPEX_SAVE_DUPLICATE:
			msg = "رکورد مورد نظر قبلا ثبت شده است.";
			
		}
		AIPException e = getAIPException(msg, ex);
		e.setType(operationType);
		return e;

	}

	public AIPException getAIPException(String defaultMessage, Exception ex) {
		getSession().close();

		if (ex == null) {
			return new AIPException(defaultMessage);
		}

		ex.printStackTrace();

		if (ex.getMessage().indexOf(
				"Batch update returned unexpected row count from update") > -1) {
			defaultMessage = "رکوردی مورد نظر یافت نشد.";
		} else if (ex.getMessage().indexOf("a foreign key constraint fails") > -1) {
			defaultMessage = defaultMessage + "\n"
					+ "بدلیل ارتباط با سایر اطلاعات";
		} else if (ex.getCause().getMessage().indexOf("Duplicate entry") > -1) {
			defaultMessage = defaultMessage + "\n"
					+ "اطلاعات تکراری است.";
		} else if (ex instanceof ConstraintViolationException) {
			ConstraintViolationException cvex = (ConstraintViolationException) ex;
			defaultMessage = defaultMessage
					+ "\n"
					+ " بدلیل ارتباط با سایر اطلاعات یا محدودیتهای تغییر اطلاعاتی";
		}
		// else if(ex.getMessage().startsWith(""){}
		return new AIPException(defaultMessage, ex);
	}




}