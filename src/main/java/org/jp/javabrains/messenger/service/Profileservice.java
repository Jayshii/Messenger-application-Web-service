package org.jp.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.jp.javabrains.messenger.model.Profile;
import org.jp.javabrains.messenger.database.*;


public class Profileservice {

	private Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	public Profileservice() {
		profiles.put("Jayprakash", new Profile(1L,"Jay","Prakash","Datani"));
	}
	
	public List<Profile> getAllProfiles(){
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size()+1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile) {
		if(profile.getProfileName().isEmpty()) {
			return null;}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
	}
}
