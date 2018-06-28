package org.jp.javabrains.messenger.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.jp.javabrains.messenger.model.Message;
import org.jp.javabrains.messenger.database.*;
import org.jp.javabrains.messenger.exception.DataNotFoundException;

public class Messageservice {
    
	private Map<Long,Message> messages = DatabaseClass.getMessages();
	
	public Messageservice() {
		messages.put(1L,new Message(1,"Hello World","Jersey"));
	}
	public List<Message> getAllMessages(){
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear (int year){
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal =Calendar.getInstance();
		for(Message message:messages.values()) {
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR)==year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}
	
	public List<Message> getAllMessagesPaginated(int start,int size){
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		if(start+size>list.size()) return new ArrayList<Message>();
		return list.subList(start,start+size);
	}
	public Message getMessage(long id) {
     Message message = messages.get(id);
     if(message == null) {
    	 throw new DataNotFoundException("message not found" + id);
     }
     return message;
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size()+1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		if(message.getId()<=0) {
			return null;}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message removeMessage(long id) {
		return messages.remove(id);
	}
}
