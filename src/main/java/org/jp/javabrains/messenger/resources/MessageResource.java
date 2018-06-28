package org.jp.javabrains.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.jp.javabrains.messenger.service.Messageservice;
import org.jp.javabrains.messenger.model.Message;
import org.jp.javabrains.messenger.resources.beans.MessageFilterBean;

@Path("/messages")
public class MessageResource {
	Messageservice messageservice = new Messageservice(); 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {
        if(filterBean.getYear()>0) {
        	return messageservice.getAllMessagesForYear(filterBean.getYear());
        }
        
        if (filterBean.getStart() > 0 && filterBean.getSize() > 0) {
        	return messageservice.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
        }
		return messageservice.getAllMessages();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMessage(@Context UriInfo uriInfo,Message message) {
		
		Message newmessage = messageservice.addMessage(message);
		String mid = String.valueOf(newmessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(mid).build();
		return Response.created(uri)
				.entity(newmessage)
				.build();
	}
	
	@PUT
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Message updateMessage(@PathParam("messageId") long id,Message message) {
		message.setId(id);
		messageservice.updateMessage(message);
		return message;
	}
	
	@DELETE
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public void deleteMessage(@PathParam("messageId") long id) {
		messageservice.removeMessage(id);
	}
	
	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam("messageId") long messageId,@Context UriInfo uriInfo) {
		Message message = messageservice.getMessage(messageId); 
		
		String uri = getUriForSelf(uriInfo, message);
		String uri_profile = getUriForProfile(uriInfo, message);
		
		message.addLink(uri, "self");
		message.addLink(uri_profile,"profile");
		message.addLink(getUriForComment(uriInfo, message), "comments");
		return message;
	}

	private String getUriForComment(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class,"getComments")
				.path(CommentResource.class)
				.resolveTemplate("messageId",message.getId())
				.build()
				.toString();
		return uri;
				
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
		.path(MessageResource.class)
		.path(Long.toString(message.getId()))
		.build()
		.toString();
		return uri;
	}
	
	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(message.getAuthor())
				.build()
				.toString();
		return uri;
	}
	
	
	@Path("/{messageId}/comments")
	public CommentResource getComments() {
		return new CommentResource();
	}
}
