package com.indmex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.Gson;
import com.indmex.dao.UtilDao;
import com.indmex.polygon.json.FaaMessageJson;
import com.indmex.polygon.json.FlightInPoly;
import com.indmex.polygon.json.Point;
import com.indmex.polygon.json.PolygonJson;

public class ServiceUtil {
	
	private UtilDao utilDao;
	private CopyOnWriteArrayList<PolygonJson> polygonJsonList = new CopyOnWriteArrayList<PolygonJson>();
	private String polygonJsonNoFlightData = null;
	private Gson gson;
	
	private ServiceUtil(){
		utilDao = new UtilDao();
		gson = new Gson();
		loadPolyData();
	}
	
	private void loadPolyData(){
		utilDao.loadPolygonData(polygonJsonList);
		List<PolygonJson> polygonNoFlightDataList = new ArrayList<PolygonJson>();
		polygonNoFlightDataList.addAll(polygonJsonList);
		polygonJsonNoFlightData = gson.toJson(polygonNoFlightDataList);
	}
	
	
	private String getPolygonMessageAsJson(FaaMessageJson faaMessageJson){
		if(faaMessageJson != null){
			pointInPolygon(faaMessageJson, polygonJsonList);
			String result = gson.toJson(polygonJsonList);
			return result;
		}
		
		return polygonJsonNoFlightData;
	}

	
	
	public void pointInPolygon(FaaMessageJson faaMessageJson, List<PolygonJson> polyJsonsList) {
		double longitude = faaMessageJson.getLongitude();
		double latitude = faaMessageJson.getLatitude();
		
		for (PolygonJson polygonJson : polyJsonsList) {
			int cnt = polygonJson.getCoOrdinates().size();
			Boolean isInside = false;
			for (int i = 0, j = cnt - 1; i < cnt; j = i++) {
				Point ipoint = polygonJson.getCoOrdinates().get(i);
				Point jpoint = polygonJson.getCoOrdinates().get(j);
				if (((ipoint.getPointLongitude() > longitude) != (jpoint.getPointLongitude() > longitude))
						&& (latitude < (jpoint.getPointLatitude() - ipoint.getPointLatitude())
								* (longitude - ipoint.getPointLongitude())
								/ (jpoint.getPointLongitude() - ipoint.getPointLongitude())
								+ ipoint.getPointLatitude())) {
					isInside = true;
					FlightInPoly flightInPoly = new FlightInPoly();
					flightInPoly.setAircraftType(faaMessageJson.getAircrafType());
					flightInPoly.setFlightID(faaMessageJson.getFlightId());
					flightInPoly.setInTime(System.currentTimeMillis());
					if(!polygonJson.getFlightInPoly().contains(flightInPoly)){
						polygonJson.getFlightInPoly().add(flightInPoly);
					}
					
				}

			}
			if(!isInside){
				removeFlight(polygonJson, faaMessageJson.getFlightId());
			}
			if (polygonJson.getChildpolygon().size() > 0) {
				pointInPolygon(faaMessageJson, polygonJson.getChildpolygon());
			}

		}

	}
	
	
	private void removeFlight(PolygonJson polygonJson,String flightId){
		List<FlightInPoly> flightInPolyList = polygonJson.getFlightInPoly();
		FlightInPoly tempFlightInPoly = new FlightInPoly();
		tempFlightInPoly.setFlightID(flightId);
		int pos = flightInPolyList.indexOf(tempFlightInPoly);
		if(pos != -1){
			flightInPolyList.remove(pos);
		}
	}
	
	private static ServiceUtil serviceUtil;
	private static ServiceUtil getInstance(){
		if(serviceUtil == null){
			serviceUtil = new ServiceUtil();
		}
		return serviceUtil;
	}
	
	

	public static String getPolygonMessage(FaaMessageJson faaMessageJson) {
		try {
			ServiceUtil serviceUtilInstance = getInstance();
			return serviceUtilInstance.getPolygonMessageAsJson(faaMessageJson);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

	

}
