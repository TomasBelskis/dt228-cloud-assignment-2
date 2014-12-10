package ie.mydit.belskis.tomas;

import java.io.IOException;




import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;

@SuppressWarnings("serial")
public class Serve extends HttpServlet {
	
		/**
		* 
		*/
		private static final long serialVersionUID = 1L;
		
		private BlobstoreService blobstoreService = 
				BlobstoreServiceFactory.getBlobstoreService();
		
		public void doGet(HttpServletRequest req, HttpServletResponse res)
		
				 throws IOException {
					
					 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
					 BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
					 String imageTitle = req.getParameter("title");
					 ImagesService imagesService = ImagesServiceFactory.getImagesService();
					 String url = imagesService.getServingUrl(blobKey);
					 //REFERENCE FOR DATASTORE API: https://cloud.google.com/appengine/docs/java/datastore/entities
					 //creates images entity to hold image values title and url
					 Entity images = new Entity("Images");
					 images.setProperty("imageTitle", imageTitle);
					 images.setProperty("imageURL", url);
					 //stores the image entity into datastore
					 datastore.put(images);
					 String indexURL = "/picturebox";
					 String displayURL = "/display";
					 
					 res.setContentType("text/html");
					 res.getWriter().println("<form action=\""+indexURL+"\" method=\"get\"><input type=\"submit\" value=\"Home\"></form>");
					 
					//displays the image that has been just uploaded
					res.getWriter().println("<img height=\"200\" width=\"250\" src=\""+url+"\"><br>Image Has been stored!");
					res.getWriter().println("<form action=\""+displayURL+"\" method=\"get\"><input type=\"submit\" value=\"Display Images\"></form>");
				 }
		
			}

