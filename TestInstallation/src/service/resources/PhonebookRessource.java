package service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/phonebook")
public class PhonebookRessource {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getAbout() {
		return "<h1>Phonebook<h1><p>Jersey Intro Web Service is running</p>";
	}
	
}
