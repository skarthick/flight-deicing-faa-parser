package com.indmex.polygon.json;

import java.util.List;

public class PolygonJson {
	private List<Childpolygon> childpolygon = null;
	private String polygonName;
	private boolean blocked;
	private List<FlightInPoly> flightInPoly = null;
	public List<Childpolygon> getChildpolygon() {
		return childpolygon;
	}
	public void setChildpolygon(List<Childpolygon> childpolygon) {
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
	
	

}
