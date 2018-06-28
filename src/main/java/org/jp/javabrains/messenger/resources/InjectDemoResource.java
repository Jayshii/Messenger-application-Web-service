package org.jp.javabrains.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("injectdemo")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

	@GET
	@Path("annotations")
	public String getParamsusingAnnotations(@MatrixParam("param") String matrixParam,
			                                 @HeaderParam("customHeaderValue") String headerParam,
			                                 @CookieParam("name") String cookie) {
		return matrixParam+"Hello"+headerParam+cookie;
		
	}
	
	
	@GET
	@Path("context")
	public String getparamsUsingContext(@Context UriInfo uriInfo,@Context HttpHeaders httpHeaders) {
		String s1 = uriInfo.getAbsolutePath().toString();
		String s2 = uriInfo.getPathParameters().toString();
		String s3 = httpHeaders.getCookies().toString();
		return "String1"+ s1 + "Stringg2"+ s2 + "string3" + s3 ;
	}
	
}
