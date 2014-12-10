package ie.mydit.belskis.tomas;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class DisplayImages extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		//REFERENCE FOR DATASTORE API: https://cloud.google.com/appengine/docs/java/datastore/entities
		Query q = new Query("Images");
		PreparedQuery pq = datastore.prepare(q);
		String indexURL = "/picturebox";
		String deleteURL = "/delete";
		resp.getWriter().println("<form action=\""+indexURL+"\" method=\"get\"><input type=\"submit\" value=\"Home\"></form>");
		resp.setContentType("text/html");
	
		//We display images with the button delete, that allows us to delete images
		//we iterate through result set pq and store values into result entity
		//use result entity to retrieve and store required data
		for(Entity result : pq.asIterable()){
			 String titleQ = (String) result.getProperty("imageTitle");
			 String urlQ = (String)result.getProperty("imageURL");
			 
			 //displays the image thumbnail size of 200 by 250 and makes the image 
			 //clickable that redirects to full image
			 resp.getWriter().println("<p><a href=\"" + urlQ +"\"><img height=\"200\" width=\"250\" src=\""+urlQ+"\"></a></p>");
			 resp.getWriter().println("<br>ImageTitle: \""+titleQ+"");
			 
			 //creates a button that forwards images url to delete image servlet that deletes the image
			 resp.getWriter().println("<form action=\""+deleteURL+"\" method=\"get\"><input type=\"hidden\" name=\"IMAGE-URL\" value=\"" +urlQ+"\"><input type=\"submit\" value=\"Delete\"></form>");
		 }
	} //doGet
}

