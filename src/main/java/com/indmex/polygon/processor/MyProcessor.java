package com.indmex.polygon.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.indmex.polygon.json.FaaMessageJson;

public class MyProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		
		String flightMessage = exchange.getIn().getBody(String.class);
		String[] messageArray = flightMessage.split(",");
		FaaMessageJson faaMessageJson = new FaaMessageJson();
		for(String message : messageArray)
		{
			//System.out.println("Actual message : " + message);
			if(message.startsWith("PTS|UID#"))
			{
				faaMessageJson.setTrack(message.substring(8)); 
			}
			else if(message.startsWith("AID#") && message.length() > 4)
			{
				faaMessageJson.setFlightId(message.substring(4)); 
				
			}
			else if(message.startsWith("Atype#")&& message.length() > 6)
			{
				faaMessageJson.setAircrafType(message.substring(6));
				
			}else if(message.startsWith("Dest#") && message.length() > 5 )
			{
				faaMessageJson.setDestination( message.substring(5));
				
			}
			else if(message.startsWith("Org#") && message.length() > 4)
			{
				faaMessageJson.setOrigin( message.substring(4));
				
			}
			else if(message.startsWith("Lat#") && message.length() > 4)
			{
				faaMessageJson.setLatitude(Double.valueOf(message.substring(4)));
				
			}else if(message.startsWith("Long#") && message.length() > 5)
			{
				faaMessageJson.setLongitude(Double.valueOf(message.substring(5)));
				
			}
			else if(message.startsWith("Alt#") && message.length() > 4)
			{
				if(message.substring(4).equalsIgnoreCase("GND"))
				{
					faaMessageJson.setAltitude(0);
				}else{
				faaMessageJson.setAltitude(Double.valueOf(message.substring(4)));
				}
				
			}
		}
		//System.out.println("Message : " + faaMessageJson.toString());
		exchange.getIn().setBody(faaMessageJson);
	}

}
