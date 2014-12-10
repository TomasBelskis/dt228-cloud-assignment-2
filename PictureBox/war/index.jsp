<%@ page 
import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%BlobstoreService blobstoreService = 
BlobstoreServiceFactory.getBlobstoreService(); %>

<html>
 	<head>
 		<title>Upload Test</title>
 	</head>
 	<body>
 		<form action="<%= blobstoreService.createUploadUrl("/upload") %>" 
		method="post" enctype="multipart/form-data">
			 <br>Image Title<input type="text" name="foo">
			 <br><input type="file" name="myFile">
			 <br><input type="submit" value="Upload">
		 </form>
 </body>
</html>