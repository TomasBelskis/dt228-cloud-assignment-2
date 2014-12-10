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
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;

@SuppressWarnings("serial")
public class DeleteImages extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		
		//initialises variables 
		String indexURL = "/picturebox";
		//creates a button to return to index page
		resp.getWriter().println("<form action=\""+indexURL+"\" method=\"get\"><input type=\"submit\" value=\"Home\"></form>");
		resp.setContentType("text/html");
		//retrieves image url from url
		String imageURL = req.getParameter("IMAGE-URL");
		
		//REFERENCE https://cloud.google.com/appengine/docs/java/datastore/queries
		//REFERENCE https://cloud.google.com/appengine/docs/java/datastore/entities
		//Set up a filter to locate the imageURL in datastore
		Filter imageUrlFilter = new FilterPredicate("imageURL",FilterOperator.EQUAL,imageURL);
		//we create a query that uses the filter to find image url
		Query q = new Query("Images").setFilter(imageUrlFilter);
		PreparedQuery pq = datastore.prepare(q);
		// we loop through result set and delete image with the same url from datastore
		for(Entity result : pq.asIterable()){
			datastore.delete(result.getKey());
		}
		
		
		resp.getWriter().println("Image Deleted!");
	} //doGet
}

