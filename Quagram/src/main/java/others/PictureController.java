package others;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class PictureController {
	private String path = "/Users/marcelhagmann/Desktop/DownloadFromInternet/";
	
	public String saveImageToFilesystem(String imageURL) {
		InputStream inputStream = null;
        OutputStream outputStream = null;
        
        String imageName = createImageNameFromURL(imageURL);
        
        File file = new File(path + imageName);
 
        // only download file if it doesnt exist
        if (!file.exists()) {
        		try {
                URL url = new URL(imageURL);
                inputStream = url.openStream();
                outputStream = new FileOutputStream(path + imageName);
     
                byte[] buffer = new byte[2048];
                int length;
     
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
     
            } catch (MalformedURLException e) {
                System.out.println("MalformedURLException :- " + e.getMessage());
     
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFoundException :- " + e.getMessage());
     
            } catch (IOException e) {
                System.out.println("IOException :- " + e.getMessage());
            } finally {
                try {
     
                    inputStream.close();
                    outputStream.close();
                    return imageName;
                } catch (IOException e) {
                    System.out.println("Finally IOException :- " + e.getMessage());
                }
            }
        		return imageName;
        } else {
        		System.out.println("Didnt Load Image! :)");
        		return imageName;
        }
	}
	
	public static String createImageNameFromURL(String imageURL) {
		String[] urlComponents = imageURL.split("/");
		String imageName = urlComponents[urlComponents.length - 1];
		return imageName;
	}
}