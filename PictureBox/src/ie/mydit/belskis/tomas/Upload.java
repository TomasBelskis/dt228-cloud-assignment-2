package ie.mydit.belskis.tomas;

import java.io.IOException;




import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

@SuppressWarnings("serial")
public class Upload extends HttpServlet {
	
		/**
		* 
		*/
		private static final long serialVersionUID = 1L;
		
		private BlobstoreService blobstoreService = 
				BlobstoreServiceFactory.getBlobstoreService();
		
		

		public void doPost(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {
					 @SuppressWarnings("deprecation")
					 Map<String, BlobKey> blobs = 
					 blobstoreService.getUploadedBlobs(req); 
					 BlobKey blobKey = blobs.get("myFile");
					 String title = req.getParameter("foo");
					 
					 // uploads image into blobstore REFERENCE: https://cloud.google.com/appengine/docs/java/blobstore/
					 
					 
					 if (blobKey == null) {
						 
						 	res.sendRedirect("/");
						 	
					 } else {
						 
						 System.out.println("Uploaded a file with blobKey: "+blobKey.getKeyString());
						 try {
							Thread.sleep(0);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 res.sendRedirect("/serve?title="+title+"&blob-key=" + blobKey.getKeyString());
						 
					}
		}
}


