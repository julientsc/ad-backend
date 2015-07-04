package rest;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.auth.Token;
import model.auth.Tokens;
import model.user.User;
import model.user.Users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import consumed.LoginItem;
import consumed.TokenItem;
import exposed.RestItem;
import exposed.RestList;

@Path("/tokens")
public class AuthWS {
	
	@SuppressWarnings("unused")
	private final static Logger LOGGER = Logger.getLogger(Token.class.getName());
	private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		RestList<String> list = new RestList<String>();
		Tokens tokens = Tokens.getInstance();
		for (String id : tokens.getTokenIds()) {
			if(tokens.getToken(id) != null) {
				RestItem<String> restItem = new RestItem<String>(id, String.valueOf(id));
				list.getItems().add(restItem);
			}
		}
		String output = GSON.toJson(list);
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response detail(@PathParam("id") String id) {
		Token token = Tokens.getInstance().getToken(id);
		if(token != null) {
			String output = GSON.toJson(token);
			return Response.status(200).entity(output).build();
		}
		else			
			return Response.status(404).build();
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(String json) {
		LoginItem authItem = GSON.fromJson(json, LoginItem.class);
		
		User user = Users.getInstance().getUser(authItem.getUser());
		if(user == null)
			return Response.status(404).build();
		
		Token token = Tokens.getInstance().addToken(user);

		String output = GSON.toJson(token);
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logout(String json) {
		System.out.println(json);
		TokenItem authItem = GSON.fromJson(json, TokenItem.class);
		
		if(Tokens.getInstance().removeToken(authItem.getToken()))
			return Response.status(200).build();
		else			
			return Response.status(404).build();
	}
}
