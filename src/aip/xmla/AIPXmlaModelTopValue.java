package aip.xmla;

public class AIPXmlaModelTopValue {
	String slicer;
	String measureName;
	String dimensionName;
	String totalValue;
	
	String maxMemberName;
	String maxValue;
	String maxPercent;
	
	String minMemberName;
	String minValue;
	String minPercent;
	public String getSlicer() {
		return slicer;
	}
	public void setSlicer(String slicer) {
		this.slicer = slicer;
	}
	public String getMeasureName() {
		return measureName;
	}
	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}
	public String getDimensionName() {
		return dimensionName;
	}
	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}
	public String getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}
	public String getMaxMemberName() {
		return maxMemberName;
	}
	public void setMaxMemberName(String maxMemberName) {
		this.maxMemberName = maxMemberName;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public String getMaxPercent() {
		return maxPercent;
	}
	public void setMaxPercent(String maxPercent) {
		this.maxPercent = maxPercent;
	}
	public String getMinMemberName() {
		return minMemberName;
	}
	public void setMinMemberName(String minMemberName) {
		this.minMemberName = minMemberName;
	}
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getMinPercent() {
		return minPercent;
	}
	public void setMinPercent(String minPercent) {
		this.minPercent = minPercent;
	}
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("slicer="+slicer);
		sb.append(",measureName="+measureName);
		sb.append(",dimensionName="+dimensionName);
		sb.append(",totalValue="+totalValue);
		sb.append(",maxMemberName="+maxMemberName);
		sb.append(",maxValue="+maxValue);
		sb.append(",maxPercent="+maxPercent);
		sb.append(",minMemberName="+minMemberName);
		sb.append(",minValue="+minValue);
		sb.append(",minPercent="+minPercent);

		return sb.toString();
	}

}
