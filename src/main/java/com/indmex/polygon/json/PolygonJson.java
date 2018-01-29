package com.indmex.polygon.json;

import java.util.ArrayList;
import java.util.List;

public class PolygonJson {
	private List<PolygonJson> childpolygon = null;
	private String polygonName;
	private int polygonId;
	private transient String geoJson;
	private transient List<Point> coOrdinates = new ArrayList<Point>();
	private boolean blocked;
	private List<FlightInPoly> flightInPoly = null;
	
	
	
	public List<Point> getCoOrdinates() {
		return coOrdinates;
	}
	public void setCoOrdinates(List<Point> coOrdinates) {
		this.coOrdinates = coOrdinates;
	}
	public int getPolygonId() {
		return polygonId;
	}
	public void setPolygonId(int polygonId) {
		this.polygonId = polygonId;
	}
	public String getGeoJson() {
		return geoJson;
	}
	public void setGeoJson(String geoJson) {
		this.geoJson = geoJson;
	}
	public List<PolygonJson> getChildpolygon() {
		return childpolygon;
	}
	public void setChildpolygon(List<PolygonJson> childpolygon) {
		this.childpolygon = childpolygon;
	}
	public String getPolygonName() {
		return polygonName;
	}
	public void setPolygonName(String polygonName) {
		this.polygonName = polygonName;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public List<FlightInPoly> getFlightInPoly() {
		return flightInPoly;
	}
	public void setFlightInPoly(List<FlightInPoly> flightInPoly) {
		this.flightInPoly = flightInPoly;
	}
	@Override
	public String toString() {
		return "PolygonJson [childpolygon=" + childpolygon + ", polygonName=" + polygonName + ", blocked=" + blocked
				+ ", flightInPoly=" + flightInPoly + "]";
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
		PolygonJson other = (PolygonJson) obj;
		if (polygonId != other.polygonId)
			return false;
		return true;
	}
	
	
	
	

}
