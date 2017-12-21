package com.php.Quagram.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

@Path("/picture")
public class MultiPartResource {
	
	final Application application = new ResourceConfig()
		    .packages("org.glassfish.jersey.examples.multipart")
		    .register(MultiPartFeature.class);
	/*
	@GET
	@Path("{pictureID}")
	@Produces("multipart/mixed")
	public MultiPart getPicture(@PathParam("pictureID") String pictureID) {
		MultiPart multiPartEntity = new MultiPart()
		        .bodyPart(new BodyPart().entity("hello"))
		        .bodyPart(new BodyPart(new JaxbBean("xml"), MediaType.APPLICATION_XML_TYPE))
		        .bodyPart(new BodyPart(new JaxbBean("json"), MediaType.APPLICATION_JSON_TYPE));
	}*/
}
