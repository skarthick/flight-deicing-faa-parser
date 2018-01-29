package com.indmex.polygon.json;

public class FlightInPoly {
	
	private String flightID;
	private String aircraftType;
	private long inTime;
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
	public long getInTime() {
		return inTime;
	}
	public void setInTime(long inTime) {
		this.inTime = inTime;
	}
	@Override
	public String toString() {
		return "FlightInPoly [flightID=" + flightID + ", aircraftType=" + aircraftType + ", inTime=" + inTime + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flightID == null) ? 0 : flightID.hashCode());
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
		FlightInPoly other = (FlightInPoly) obj;
		if (flightID == null) {
			if (other.flightID != null)
				return false;
		} else if (!flightID.equals(other.flightID))
			return false;
		return true;
	}
	
	

}
