package com.indmex.polygon.json;

public class Point {
	private  float pointLatitude;
	private  float pointLongitude;
	
	public Point(float pointLatitude,float pointLongitude){
		this.pointLatitude = pointLatitude;
		this.pointLongitude = pointLongitude;
	}
	
	public float getPointLatitude() {
		return pointLatitude;
	}
	public void setPointLatitude(float pointLatitude) {
		this.pointLatitude = pointLatitude;
	}
	public float getPointLongitude() {
		return pointLongitude;
	}
	public void setPointLongitude(float pointLongitude) {
		this.pointLongitude = pointLongitude;
	}
	@Override
	public String toString() {
		return "Point [pointLatitude=" + pointLatitude + ", pointLongitude=" + pointLongitude + "]";
	}
	
	
}
