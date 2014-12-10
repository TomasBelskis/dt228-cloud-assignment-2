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
public class PictureBoxServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		
		UserService userService = UserServiceFactory.getUserService();
		Principal myPrincipal = req.getUserPrincipal();
		String uploadURL="index.jsp";
		String viewImagesURL=null;
		String emailAddress = null;
		String thisURL = req.getRequestURI();
		String loginURL = userService.createLoginURL(thisURL);
		String logoutURL = userService.createLogoutURL(thisURL);
		String displayURL = "/display";
		// string array to hold the member emails
		String[] members = {"john@gmail.com","p@mail.com","cod@dit.ie"};
		resp.setContentType("text/html");
		
		PrintWriter writer = resp.getWriter();
		if(myPrincipal == null) {
			
		writer .println("<p>You are Not Logged In time</p>");
		writer.println("<p>You can <a href=\""+loginURL+"\">sign in here</a>.</p>");
		} // end if not logged in
		if(myPrincipal !=null) {
			Boolean isAdmin = userService.isUserAdmin();
			emailAddress = myPrincipal.getName();
			//we check if the user is an admin
			if(isAdmin){
			
			writer.println("<p>You are Logged in as ADMIN(email):"+emailAddress+"</p>");
			writer.println("<p>You can upload images : <a href=\"" + uploadURL +"\">Upload Images</a>.</p>" );
			writer.println("<p>You can view images: <a href=\"" + displayURL +"\">View Images</a>.</p>" );
			writer.println("<p>You can <a href=\"" + logoutURL +"\">sign out</a>.</p>");
			}// end if admin
			//check if the user is a member
			else if(emailAddress.equalsIgnoreCase(members[0].toString())||emailAddress.equalsIgnoreCase(members[1].toString())||emailAddress.equalsIgnoreCase(members[2].toString())){
			writer.println("<p>You are Logged in as MEMBER(email):"+emailAddress+"</p>");
			writer.println("<p>You can upload images : <a href=\"" + uploadURL +"\">Upload Images</a>.</p>" );
			writer.println("<p>You can view images: <a href=\"" + displayURL +"\">View Images</a>.</p>" );
			writer.println("<p>You can <a href=\"" + logoutURL +"\">sign out</a>.</p>");
			}//end else if member
			//else the user is a guest 
			else
			{
			writer .println("<p>You are Not A Member or Admin!</p>");
			writer.println("<p>You can <a href=\"" + logoutURL +"\">Login as a Member or Admin</a>.</p>");
			}//end else for guest
		} // end if logged in
		
		//REFERENCE FOR DATASTORE API: https://cloud.google.com/appengine/docs/java/datastore/entities
		// display images stored in datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		writer.println("Public Images");
		Query q = new Query("Images");
		PreparedQuery pq = datastore.prepare(q);
		for(Entity result : pq.asIterable()){
			 String titleQ = (String) result.getProperty("imageTitle");
			 String urlQ = (String)result.getProperty("imageURL");
			 resp.getWriter().println("<p><a href=\"" + urlQ +"\"><img height=\"200\" width=\"250\" src=\""+urlQ+"\"></a></p>");
			 resp.getWriter().println("<br>ImageTitle: \""+titleQ+"");	
		 }
	}//end doGet
}//end of class
	
