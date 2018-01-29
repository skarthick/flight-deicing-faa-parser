package com.indmex;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indmex.polygon.processor.MyProcessor;
/**
 * Hello world!
 *
 */
public class App 
{
	private String fromUserName;
	private String toUserName;
	private String fromPassword;
	private String toPassword;
	private String fromConnectionUrl;
	private String toConnectionUrl;
	private String toTopicName;
	private String fromTopicName;
	private String currentAirport;
	
	public App(String aPropertiesFileLocation) { 
		loadPropertiesFromFile(aPropertiesFileLocation);
	}
	
	private static final Logger log = LoggerFactory.getLogger(App.class);

	private void loadPropertiesFromFile(String aPropertiesFileLocation) {
		 
		// This constructor simply reads in the properties file and gets the
				// connection information.
				String aFilePath = getClass().getClassLoader().getResource("") + aPropertiesFileLocation;
				// The following line removes "parentDirectory/../" and "file:" from the
				// String.
				String aNormalizedFilePath = aFilePath.replaceAll("[^/]*/\\.\\./", "").replaceAll("file:", "");
				Properties theProp = new Properties();
				try {
					theProp.load(new FileInputStream(aPropertiesFileLocation));
					fromUserName = theProp.getProperty("FROM_TOPIC_USER_NAME");
					fromPassword = theProp.getProperty("FROM_TOPIC_PASSWORD");
					fromConnectionUrl = theProp.getProperty("FROM_TOPIC_CONNECTION_URL");
					fromTopicName = theProp.getProperty("FROM_TOPIC_NAME");
					
					toConnectionUrl = theProp.getProperty("TO_TOPIC_CONNECTION_URL");
					toPassword = theProp.getProperty("TO_TOPIC_PASSWORD");
					toUserName = theProp.getProperty("TO_TOPIC_USER_NAME");
					toTopicName = theProp.getProperty("TO_TOPIC_NAME");
					
					currentAirport =  theProp.getProperty("CURRENT_AIRPORT");
				} catch (NullPointerException e) {
					log.error("The properties file should be located at " + aNormalizedFilePath + ": ", e);
					System.exit(1);
				} catch (ClassCastException e) {
					log.error("The properties file contains non-string values: ", e);
					System.exit(1);
				} catch (IOException e) {
					log.error("IO Exception: ", e);
					System.exit(1);
				}
	}

	public void doProcess()
	{
		try {
			
			// Get camel context
			CamelContext camelContext = new DefaultCamelContext();
			// Get active mq component
			ActiveMQComponent comp = new ActiveMQComponent();
			ActiveMQComponent comp1 = new ActiveMQComponent();
			
			comp.setConnectionFactory(new ActiveMQConnectionFactory(fromUserName, fromPassword, fromConnectionUrl));
			comp1.setConnectionFactory(new ActiveMQConnectionFactory(toUserName, toPassword, toConnectionUrl));

			// adding component
			camelContext.addComponent("activemq", comp);
			camelContext.addComponent("activemq1", comp1);
			
			PropertiesComponent pc = new PropertiesComponent();
			pc.setLocation("./config.properties"); // the path to your properties file
			camelContext.addComponent("properties", pc);
			
			// adding routing logic to route message into diffrent topic
			camelContext.addRoutes(new RouteBuilder() {
				public void configure() {
					String toTopic = toTopicName; 
					
					from("activemq:topic:"+fromTopicName).process(new MyProcessor()).to("activemq1:topic:"+toTopic);
					//from("activemq:topic:"+fromTopicName).process(new MyProcessor()).to("log:?level=INFO&showBody=true");
				}
			});

			camelContext.start();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static void main( String[] args )
    {
    	App app = new App(args[0]);
		app.doProcess();
    }
}
