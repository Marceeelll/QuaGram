package service;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("*") 
public class PhonebookApp extends ResourceConfig {
	public PhonebookApp() {
		packages("service.resources");
	}
}