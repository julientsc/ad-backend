package model.product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.auth.Token;
import model.auth.Tokens;

public class Products {

	@SuppressWarnings("unused")
	private final static Logger LOGGER = Logger.getLogger(Tokens.class.getName()); 
	private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private final static String PATH = "./products.json";
	
	private static Products INSTANCE = null;
	private HashMap<Long, HashMap<Long, Product>> products = null; // USER_ID // PRODUCT_ID
	private long currentProductId = 0;
	
	public static Products getInstance() {
		if (INSTANCE == null) {
			try {
				INSTANCE = load(PATH);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				INSTANCE = new Products();
				INSTANCE.save();
			}
		}
		return INSTANCE;
	}
	
	private static Products load(String path) throws IOException {
		BufferedReader br;
		br = new BufferedReader(new FileReader(path));
		Products products = GSON.fromJson(br, Products.class);
		br.close();
		return products;
	}
	
	private Products() {
		this.products = new HashMap<Long, HashMap<Long, Product>>();
		this.currentProductId = 0;
	}
	
	
	public Product addProduct(Token token) {
		long id = currentProductId++;
		Product product = new Product(id);
		long userId = token.getUserId();
		
		if (!products.containsKey(userId)) {
			products.put(userId, new HashMap<Long, Product>()); 
		}
		
		products.get(userId).put(id, product);
		save();
		
		return product;		
	}
	
	public boolean removeProduct(Token token, long id) {
		long userId = token.getUserId();
		if (!products.containsKey(userId)) {
			return false;
		}
		
		if(products.get(userId).containsKey(id) && products.get(userId).get(id).isActive()) {
			products.get(userId).get(id).setActive(false);
			save();
			return true;
		}
		return false;
	}
	
	public Product getProduct(Token token, long id) {
		long userId = token.getUserId();
		if (!products.containsKey(userId)) {
			return null;
		}
		
		if(products.get(userId).containsKey(id) && products.get(userId).get(id).isActive())
			return products.get(userId).get(id);
		return null;
	}
	
	public void save() {
		try {
			PrintWriter pw = new PrintWriter(PATH);
			pw.write(GSON.toJson(INSTANCE));
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Set<Long> getProductsIds(Token token) {
		if(!products.containsKey(token.getUserId()))
			products.put(token.getUserId(), new HashMap<Long, Product>());
		return products.get(token.getUserId()).keySet();
	}
	
}
