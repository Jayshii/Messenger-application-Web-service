package org.jp.javabrains.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jp.javabrains.messenger.service.Messageservice;
import org.jp.javabrains.messenger.service.Profileservice;
import org.jp.javabrains.messenger.model.Message;
import org.jp.javabrains.messenger.model.Profile;;


@Path("/profiles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource {
	private Profileservice profileservice = new Profileservice(); 
	
	
	@GET
	public List<Profile> getProfiles() {
		return profileservice.getAllProfiles();
	}

	@POST
    public Profile addProfile(Profile profile) {
		 return profileservice.addProfile(profile);
	}
	
	@GET
	@Path("/{profileName}")
	public Profile getProfile(@PathParam("profileName") String profileName) {
		return profileservice.getProfile(profileName);
	}
	
	@PUT
	@Path("/{profileName}")
	public Profile updateProfile(@PathParam("profileName") String profileName,Profile profile) {
		profile.setProfileName(profileName);
		return profileservice.updateProfile(profile);
	}
	
	@DELETE
	@Path("/{profileName}")
	public void deleteProfile(@PathParam("profileName") String profileName) {
		profileservice.removeProfile(profileName);
	}
}
