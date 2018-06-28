package org.jp.javabrains.messenger.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jp.javabrains.messenger.database.DatabaseClass;
import org.jp.javabrains.messenger.model.Comment;
import org.jp.javabrains.messenger.model.ErrorMessage;
import org.jp.javabrains.messenger.model.Message;

public class Commentservice {

	private Map<Long,Message> messages = DatabaseClass.getMessages();
	
	public List<Comment> getAllComments(long messageId) {
		Map<Long,Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageId,long commentId) {
		
		ErrorMessage errormessage = new ErrorMessage("Cant you undersatnd, not found", 404, "google.com");
		Response response =  Response.status(Status.NOT_FOUND)
				.entity(errormessage)
				.build();
		Message message = messages.get(messageId);
		if(message == null) {
			throw new WebApplicationException(response);
		}
		Map<Long,Comment> comments = messages.get(messageId).getComments();
		Comment comment = comments.get(commentId);
		if(comment == null) {
			throw new NotFoundException(response);
		}
		return comment;
	}
	
	public Comment addComment(long messageId,Comment comment) {
		Map<Long,Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size()+1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId,Comment comment) {
		Map<Long,Comment> comments = messages.get(messageId).getComments();
		if(comment.getId()<=0) {
			return null;}
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId,long commentId) {
		Map<Long,Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
}
