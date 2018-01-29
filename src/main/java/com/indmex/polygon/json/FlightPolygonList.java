package com.indmex.polygon.json;

public class FlightPolygonList {
	
	private int parentPolygonId;
	private int polygonId;
	private boolean isChild;
	public int getParentPolygonId() {
		return parentPolygonId;
	}
	public void setParentPolygonId(int parentPolygonId) {
		this.parentPolygonId = parentPolygonId;
	}
	public int getPolygonId() {
		return polygonId;
	}
	public void setPolygonId(int polygonId) {
		this.polygonId = polygonId;
	}
	public boolean isChild() {
		return isChild;
	}
	public void setChild(boolean isChild) {
		this.isChild = isChild;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + polygonId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightPolygonList other = (FlightPolygonList) obj;
		if (polygonId != other.polygonId)
			return false;
		return true;
	}
	
	
	

}
