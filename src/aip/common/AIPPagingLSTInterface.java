package aip.common;

public interface AIPPagingLSTInterface {
	
	public void setTotalPages(int totalPages);
	public int getTotalPages();
	
	public void setPageSize(int pageSize);
	public int getPageSize();
	
	public void setCurrentPage(int currentPage);
	public int getCurrentPage();
	
	public void setTotalItems(int totalItems);
	public int getTotalItems();
	
}
