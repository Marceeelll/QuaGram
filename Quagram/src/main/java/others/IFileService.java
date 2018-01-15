package others;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

public interface IFileService {
	public Response downloadImageFile(String pictureID);
	public Response uploadImageFile(InputStream fileInputStream, FormDataContentDisposition dispo);
}
