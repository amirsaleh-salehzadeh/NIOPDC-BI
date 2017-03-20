package aip.jpivot;

public class AIPPivotParam {
	
	String formAction;
	String queryName;
	String mdxQuery;
	boolean refresh;
	String loader;

	String mdxQueryNC;

	public String getLoader() {
		return loader;
	}

	public void setLoader(String loader) {
		this.loader = loader;
	}

	public boolean isRefresh() {
		return refresh;
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public String getMdxQuery() {
		return mdxQuery;
	}

	public void setMdxQuery(String mdxQuery) {
		this.mdxQuery = mdxQuery;
	}

	public String getFormAction() {
		return formAction;
	}

	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}

	public String getMdxQueryNC() {
		if(    (mdxQueryNC==null || "".equals(mdxQueryNC)) 
			&& (mdxQuery!=null && !"".equals(mdxQuery) )   
			){
			return mdxQuery; 
		}
		return mdxQueryNC;
	}

	public void setMdxQueryNC(String mdxQueryNC) {
		this.mdxQueryNC = mdxQueryNC;
	}

}
