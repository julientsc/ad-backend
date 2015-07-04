package rest;

import java.util.logging.Logger;

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

@Path("/users")
public class UserWS {
	
	@SuppressWarnings("unused")
	private final static Logger LOGGER = Logger.getLogger(Token.class.getName());
	private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		RestList<Long> list = new RestList<Long>();
		Users users = Users.getInstance();
		for (Long id : users.getUserIds()) {
			if(users.getUser(id) != null) {
				RestItem<Long> restItem = new RestItem<Long>(id, String.valueOf(id));
				list.getItems().add(restItem);
			}
		}
		String output = GSON.toJson(list);
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response detail(@PathParam("id") Long id) {
		User user = Users.getInstance().getUser(id);
		if(user != null) {
			String output = GSON.toJson(user);
			return Response.status(200).entity(output).build();
		}
		else			
			return Response.status(404).build();
	}
	
	@GET
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response add() {
		User user = Users.getInstance().addUser();
		String output = GSON.toJson(user);
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/remove/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response remove(@PathParam("id") Long id, String json) {

		TokenItem authItem = GSON.fromJson(json, TokenItem.class);
		
		Token token = Tokens.getInstance().getToken(authItem.getToken());
				
		if( token != null && Users.getInstance().removeUser(token, id))
			return Response.status(200).build();
		else			
			return Response.status(404).build();
	}
}
