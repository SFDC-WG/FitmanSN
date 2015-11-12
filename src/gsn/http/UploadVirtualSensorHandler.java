package gsn.http;

import gsn.Mappings;
import gsn.beans.VSensorConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadVirtualSensorHandler implements RequestHandler {
	
	private String servetpath;
	
	

	public String getServetpath() {
		return servetpath;
	}

	public void setServetpath(String servetpath) {
		this.servetpath = servetpath;
	}

	@Override
	public boolean isValid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return true;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setStatus( HttpServletResponse.SC_OK );
		//if (ServletFileUpload.isMultipartContent(request)) {
			System.out.println("MultipartServelet");
			ServletFileUpload uploadHandler = new ServletFileUpload();
			InputStream stream = null;

			try {

				FileItemIterator itr = uploadHandler.getItemIterator(request);
				String name="";
				while (itr.hasNext()) {
					FileItemStream item = itr.next();
					name = item.getFieldName(); // form field name

					if (item.isFormField()) {

						String fieldName = item.getFieldName();

						System.out.println("FIELDNAME:" + fieldName);

					} else {

						name = item.getName();

						System.out.println("NAME:" + name);
					}

					System.out.println(">>>" + item.toString());
					stream = item.openStream();
					System.out.println("System path::"+getServetpath());
					
					
					//response.getWriter().write(writeToFile(stream, getServetpath()+"/../virtual-sensors/"+name));
					writeToFile(stream, getServetpath()+"/../virtual-sensors/"+name);
					

				}
				Iterator < VSensorConfig > vsIterator =Mappings.getAllVSensorConfigs();
	    		while (vsIterator.hasNext())
	    		{
	    			VSensorConfig sensorConfig = vsIterator.next( ); 
	    			if( !sensorConfig.getName().equals(name))
	    				vsIterator =Mappings.getAllVSensorConfigs();    		
	    		}
			} catch (FileUploadException ex) {
				ex.printStackTrace();
			}

		//}
	}

	private String writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			return "[DONE]";
		} catch (IOException e) {

			e.printStackTrace();
			return "[FAILED]";
		}

	}

}
