package aip.niopdc.sellbi.orm.bireport;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import aip.common.bireport.bimdx.MDXFunctions;
import aip.common.bireport.bimdx.MdxFunctionsInterface;
import aip.niopdc.sellbi.orm.BaseHibernateDAO;


public class MDXFunctionsDAO extends BaseHibernateDAO implements MdxFunctionsInterface{

	public ArrayList<MDXFunctions> getMDXFunctions(String subFunction) {
		ArrayList<MDXFunctions> functions = new ArrayList<MDXFunctions>();
		Query q = null;
		try{
			if("".equalsIgnoreCase(subFunction)||subFunction == null){
				q = getSession().createSQLQuery("select * from mdxfunction");
				functions = (ArrayList<MDXFunctions>)q.list();
			} else {
				q = getSession().getNamedQuery("sqlGetMdxFunctions")
				.setString(0, subFunction);
				functions = (ArrayList<MDXFunctions>)q.list();
			}
		} catch (HibernateException e) {
				e.printStackTrace();
		}
		return functions;
	}

//	public static void main(String[] args) {
//		MDXFunctionsDAO functionsDAO = new MDXFunctionsDAO();
//		List functions = functionsDAO.getAllSubFunctions();
//		//nonMDXFunction
//		for(int i = 0 ; i < functions.size() ; i ++ ){
//			System.out.println("************   "+functions.get(i) );
//		}
//	}

	public List getAllSubFunctions() {
		List allSubFunctions = null;
		Query q = getSession().createSQLQuery("select DISTINCT(parentName) from mdxfunction");
		allSubFunctions = q.list();
		return allSubFunctions;
	}

	
}
