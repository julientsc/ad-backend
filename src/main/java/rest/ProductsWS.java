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
import model.campaign.Campaign;
import model.campaign.Campaigns;
import model.product.Product;
import model.product.Products;
import model.user.User;
import model.user.Users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import consumed.LoginItem;
import consumed.TokenItem;
import exposed.RestItem;
import exposed.RestList;

@Path("/products")
public class ProductsWS {
	
	@SuppressWarnings("unused")
	private final static Logger LOGGER = Logger.getLogger(Token.class.getName());
	private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response list(String json) {
		TokenItem getToken = GSON.fromJson(json, TokenItem.class);
		Token token = Tokens.getInstance().getToken(getToken.getToken());
		if(token == null)
			return Response.status(404).build();
	
		
		RestList<Long> list = new RestList<Long>();
		Products campaigns = Products.getInstance();
		for (Long id : Products.getInstance().getProductsIds(token)) {
			if(campaigns.getProduct(token, id) != null) {
				RestItem<Long> restItem = new RestItem<Long>(id, String.valueOf(id));
				list.getItems().add(restItem);
			}
		}
		String output = GSON.toJson(list);
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response detail(@PathParam("id") Long id, String json) {
		TokenItem getToken = GSON.fromJson(json, TokenItem.class);
		Token token = Tokens.getInstance().getToken(getToken.getToken());
		if(token == null)
			return Response.status(404).build();
		
		Product campaign = Products.getInstance().getProduct(token, id);
		if(campaign != null) {
			String output = GSON.toJson(campaign);
			return Response.status(200).entity(output).build();
		}
		else			
			return Response.status(404).build();
	}
	
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(String json) {
		TokenItem getToken = GSON.fromJson(json, TokenItem.class);
		Token token = Tokens.getInstance().getToken(getToken.getToken());
		if(token == null)
			return Response.status(404).build();
		
		Product user = Products.getInstance().addProduct(token);
		String output = GSON.toJson(user);
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/remove/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response remove(@PathParam("id") Long id, String json) {
		TokenItem getToken = GSON.fromJson(json, TokenItem.class);
		Token token = Tokens.getInstance().getToken(getToken.getToken());
		if(token == null)
			return Response.status(404).build();

		
		if( token != null && Products.getInstance().removeProduct(token, id))
			return Response.status(200).build();
		else			
			return Response.status(404).build();
	}
}
