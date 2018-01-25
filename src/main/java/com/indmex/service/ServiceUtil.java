package com.indmex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.gson.Gson;
import com.indmex.dao.UtilDao;
import com.indmex.polygon.json.FlightInPoly;
import com.indmex.polygon.json.Point;
import com.indmex.polygon.json.PolygonJson;

public class ServiceUtil {

	private UtilDao utilDao;
	private CopyOnWriteArrayList<PolygonJson> polygonJsonList = new CopyOnWriteArrayList<PolygonJson>();
	PolygonJson polygonJson;
	private Gson gson;
	
	
	public void pointInPolygon(float latitude, float longitude, List<PolygonJson> polyJsonsList) {
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
					isInside = !isInside;
				}

			}
			if (polygonJson.getChildpolygon().size() > 0) {
				pointInPolygon(latitude, longitude, polygonJson.getChildpolygon());
			}

		}

	}
	

	public String doProcess() {
		try {
			utilDao.loadPolygonData(null, polygonJsonList);
			// Check flight is in polygon or not
			validateFlightInPolygon();
			String result = gson.toJson(polygonJsonList);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Check whether 
	 * @throws Exception
	 */
	private void validateFlightInPolygon() throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(10);
		List<Future<Runnable>> futures = new ArrayList<Future<Runnable>>();
		for (PolygonJson polygonJson : polygonJsonList) {
			Future f = service.submit(new FlightTimeValidator(polygonJson));
			futures.add(f);
		}

		// wait for all tasks to complete before continuing
		for (Future<Runnable> f : futures) {
			f.get();
		}
		service.shutdownNow();
	}

	

	private class FlightTimeValidator implements Runnable {
		PolygonJson polygonJson;
		long currentTime;

		public FlightTimeValidator(PolygonJson polygonJson) {
			this.polygonJson = polygonJson;
		}

		public void run() {
			List<FlightInPoly> flightInPolies = polygonJson.getFlightInPoly();
			doProcess(flightInPolies);
			List<PolygonJson> childPolyList = polygonJson.getChildpolygon();
			for (PolygonJson polygonJson : childPolyList) {
				doProcess(polygonJson.getFlightInPoly());
			}
		}

		private void doProcess(List<FlightInPoly> flightInPolyList) {
			List<Integer> positions = new ArrayList<Integer>();
			int pos = 0;
			for (FlightInPoly flightInPoly : flightInPolyList) {
				long filghtInTime = flightInPoly.getInTime();
				if ((currentTime - filghtInTime) > 5000) {
					positions.add(pos);
				}
				pos++;
			}
			for (Integer i : positions) {
				flightInPolyList.remove(i);
			}

		}

	}

	

}
