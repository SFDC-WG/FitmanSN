package gsn.http;

import gsn.Mappings;
import gsn.VSensorLoader;
import gsn.beans.VSensorConfig;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteVirtualSensorHandler implements RequestHandler {
	
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
		
		response.getWriter().write(performDeletion(request.getParameter("VSFileName")));
		
		
	}
	
	private String performDeletion(String filename)
	{
		try{
			Iterator < VSensorConfig > vsIterator = Mappings.getAllVSensorConfigs( );
			  boolean deleted= false;
			 while ( vsIterator.hasNext( ) ) {			  
			      VSensorConfig sensorConfig = vsIterator.next( );			   
			      if( sensorConfig.getName().equals(filename))
			      {
			    	  
			    	   			    	  
			    	  System.out.println("Sensor file found:::"+sensorConfig.getFileName());
			    	  
			    	  File file = new File(sensorConfig.getFileName());
			    	  
			    		if(file.delete()){
			    			System.out.println(file.getName() + " is deleted!");
			    	deleted=true;
			    			
			    			//return("[Deleted]");
			    		}else{
			    			System.out.println("Delete operation is failed.");
			    			deleted=false;	
			    			//return ("Deletion Failed");
			    		}
			    		
			    	  
			      }
			      else
			      {
			    	  //return ("Deletion Failed");
			    	  deleted=false;
			      }
			 }		 
    		vsIterator =Mappings.getAllVSensorConfigs();
    		while (vsIterator.hasNext())
    		{
    			VSensorConfig sensorConfig = vsIterator.next( ); 
    			if( sensorConfig.getName().equals(filename))
    				vsIterator =Mappings.getAllVSensorConfigs();    		
    		}
    		if(deleted)
    			return ("Deleted");
    		else
    			return ("Failed");
 
    	}catch(Exception e){
 
    		e.printStackTrace();
    		return ("Deletion Failed");
    	}
		
	}
	

}
