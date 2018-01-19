package com.php.Quagram.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.php.Quagram.service.ErrorService;

import others.IFileService;

@Path("/picture")
public class PictureDownloadResource implements IFileService {
	ErrorService errorService = new ErrorService();
	public static final String UPLOAD_FILE_SERVER = "D:/Demo/upload/";
	
	@GET
	@Path("/{pictureID}")
	@Produces({"image/jpg"})
	public Response downloadImageFile(@PathParam("pictureID") String pictureID) {
		errorService.isPictureIDValid(pictureID);
		
		String path = getURLPathForPictureID(pictureID);
		File file = new File(path);
		
		ResponseBuilder responseBuilder = Response.ok((Object) file);
		responseBuilder.header("Content-Disposition", "attachment; filename=\"" + pictureID + "\"");
		return responseBuilder.build();
	}
	
	public String getURLPathForPictureID(String pictureID) {
		String path = "/Users/marcelhagmann/Desktop/DownloadFromInternet/" + pictureID;
		return path;
	}
	
///////////Ab hier brauchen wir eigl nichts mehr -> nur zur Vervollständigung////////
	
	//http://localhost:8080/Quagram/webapi/picture/upload
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadImageFile(@FormDataParam("uploadFile") InputStream fileInputStream, @FormDataParam("uploadFile") FormDataContentDisposition dispo) {
		
		String fileName = null;
		String uploadFilePath = null;
		
		try {
			fileName = dispo.getFileName();
			uploadFilePath = writeToFileServer(fileInputStream, fileName);
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		finally {
			//release resources, if any
		}
		return Response.ok("File uploaded successfully at " + uploadFilePath).build();
	}
	
	/**
	 * write input stream to file server
	 * @param inputStream
	 * @param fileName
	 * @throws IOException
	 */
	private String writeToFileServer(InputStream inputStream, String fileName) throws IOException {
		
		OutputStream outputStream = null;
		String qualifiedUploadFilePath = UPLOAD_FILE_SERVER + fileName;
		
		try {
			outputStream = new FileOutputStream(new File(qualifiedUploadFilePath));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		finally {
			//release resource, if any
			outputStream.close();
		}
		return qualifiedUploadFilePath;
	}
	
	
///////////Ab hier war alles nur zum Testen///////////////////////////////	
	
	/*final Application application = new ResourceConfig()
		    .packages("org.glassfish.jersey.examples.multipart")
		    .register(MultiPartFeature.class);*/
	
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
	
	/*
	@GET
	@Path("/{pictureID}")
	@Produces("image/jpg")
	public Response getPicture(@PathParam("pictureID") String pictureID) {
		System.out.println("joasodjaosjdoasjd");
		File file = new File();
		
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=PictureID.jpg");
		
		return response.build();
	}*/
}
