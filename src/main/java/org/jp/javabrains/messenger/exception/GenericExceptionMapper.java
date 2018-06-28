package org.jp.javabrains.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jp.javabrains.messenger.model.ErrorMessage;


public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

	@Override
	public Response toResponse(Throwable ex) {
		// TODO Auto-generated method stub
		ErrorMessage errormessage = new ErrorMessage(ex.getMessage(), 500, "google.com");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errormessage)
				.build();
	}

}