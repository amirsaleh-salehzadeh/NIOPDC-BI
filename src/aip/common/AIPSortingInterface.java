package aip.common;

public interface AIPSortingInterface {
	public Boolean getIsDescending();
	public void setIsDescending(Boolean isDescending);
	public String getSortedByField();
	public void setSortedByField(String sortedByField);
	public String  resetSortedByField(AIPSortingInterface prm);
}
