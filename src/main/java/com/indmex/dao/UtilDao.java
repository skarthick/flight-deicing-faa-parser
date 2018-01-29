package com.indmex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.google.gson.Gson;
import com.indmex.polygon.json.GeoJson;
import com.indmex.polygon.json.Point;
import com.indmex.polygon.json.PolygonJson;

public  class UtilDao {
	
	Connection connection = null; 
	Gson gson = null;
	
	public UtilDao(){
		
	}
	
	/**
	 * Get Polygon and corresponding child polygon data based on given airport code
	 * @param airportCode
	 * @param polygonJsonList
	 */
	public void loadPolygonData(String airportCode,List<PolygonJson> polygonJsonList){
		PreparedStatement queryStatement = null;
		ResultSet queryResponse = null;
		try {
			queryStatement = connection.prepareStatement("select name,geojson,polygondataid from polygondata where airportcode = ?;");
			queryStatement.setString(1, airportCode);
			queryResponse = queryStatement.executeQuery();
			
			while (queryResponse.next()) {
				PolygonJson polygonJson = getPolygonJson(queryResponse);
				polygonJsonList.add(polygonJson);
				getChildPolygon(polygonJson, airportCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResultSet(queryResponse);
			closeStatement(queryStatement);
		}
	}
	
	/**
	 * Get ChildPolygon based on airport code
	 * @param polygonJson
	 * @param airportCode
	 */
	private void getChildPolygon(PolygonJson polygonJson,String airportCode){
		PreparedStatement queryStatement = null;
		ResultSet queryResponse = null;
		try {
			queryStatement = connection.prepareStatement("select name,geojson,polygondataid from polygondata where airportcode = ? and parentid = ?;");
			queryStatement.setString(1, airportCode);
			queryStatement.setInt(2, polygonJson.getPolygonId());
			queryResponse = queryStatement.executeQuery();
			
			while (queryResponse.next()) {
				PolygonJson childPolygonJson = getPolygonJson(queryResponse);
				polygonJson.getChildpolygon().add(childPolygonJson);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResultSet(queryResponse);
			closeStatement(queryStatement);
		}
	}
	
	/**
	 * Get PolygonJson object from SQL Object
	 * @param queryResponse
	 * @return
	 * @throws Exception
	 */
	private PolygonJson getPolygonJson(ResultSet queryResponse) throws Exception{
		PolygonJson polygonJson = new PolygonJson();
		polygonJson.setPolygonName( queryResponse.getString("name"));
		String geoJson = queryResponse.getString("geojson");
		if(geoJson != null){
			GeoJson geoJsonObj = gson.fromJson(geoJson, GeoJson.class);
			List<List<List<Float>>> coordinatesList  = geoJsonObj.getGeometry().getCoordinates();
			List<List<Float>> coordinates= coordinatesList.get(0);
			for(List<Float> coordinate : coordinates){
				Point point = new Point(coordinate.get(1),coordinate.get(0));
				polygonJson.getCoOrdinates().add(point);
			}
		}
		polygonJson.setGeoJson(queryResponse.getString("geojson"));
		polygonJson.setPolygonId(queryResponse.getInt("polygondataid"));
		return polygonJson;
	}

	
	public void closeResultSet(ResultSet resultSet){
		if(resultSet != null){
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void closeStatement(Statement queryStatement){
		if(queryStatement != null){
			try {
				queryStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}
