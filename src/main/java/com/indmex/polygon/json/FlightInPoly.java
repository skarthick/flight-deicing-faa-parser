package com.indmex.polygon.json;

public class FlightInPoly {
	
	private String flightID;
	private String aircraftType;
	private String inTime;
	public String getFlightID() {
		return flightID;
	}
	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}
	public String getAircraftType() {
		return aircraftType;
	}
	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	@Override
	public String toString() {
		return "FlightInPoly [flightID=" + flightID + ", aircraftType=" + aircraftType + ", inTime=" + inTime + "]";
	}
	
	

}
