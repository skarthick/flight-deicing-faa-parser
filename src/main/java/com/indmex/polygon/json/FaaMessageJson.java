package com.indmex.polygon.json;

public class FaaMessageJson {
	
	String track;
	String flightId;
	String aircrafType;
	String destination;
	String origin;
	double latitude;
	double longitude;
	double altitude;
	public String getTrack() {
		return track;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public String getFlightId() {
		return flightId;
	}
	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}
	public String getAircrafType() {
		return aircrafType;
	}
	public void setAircrafType(String aircrafType) {
		this.aircrafType = aircrafType;
	}
	 
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	@Override
	public String toString() {
		return "FaaMessageJson [track=" + track + ", flightId=" + flightId
				+ ", aircrafType=" + aircrafType + ", destination="
				+ destination + ", origin=" + origin + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", altitude=" + altitude + "]";
	}
	 
	

}
