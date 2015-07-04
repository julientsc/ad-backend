package model.user;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.auth.Token;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Users {

	private final static Logger LOGGER = Logger.getLogger(Users.class.getName());
	private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private final static String PATH = "./users.json";

	private static Users INSTANCE = null;
	private HashMap<Long, User> users = null;
	private long currentUserId = 0;

	public static Users getInstance() {
		if (INSTANCE == null) {
			try {
				INSTANCE = load(PATH);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				INSTANCE = new Users();
				INSTANCE.save();
			}
		}
		return INSTANCE;
	}

	private static Users load(String path) throws IOException {
		BufferedReader br;
		br = new BufferedReader(new FileReader(path));
		Users users = GSON.fromJson(br, Users.class);
		br.close();
		return users;
	}

	private Users() {
		this.users = new HashMap<Long, User>();
		this.currentUserId = 0;
	}
	
	public Set<Long> getUserIds() {
		return users.keySet();
	}

	public User addUser() {
		long id = currentUserId++;
		User user = new User(id);
		users.put(id, user);
		save();
		return user;
	}

	public boolean removeUser(Token token, long id) {
		if(token.getUserId() != id)
			return false;
		
		if (users.containsKey(id) && users.get(id).isActive()) {
			users.get(id).setActive(false);
			save();
			return true;
		}
		return false;
	}

	public User getUser(long id) {
		LOGGER.log(Level.INFO, "getUser " + id);

		if (users.containsKey(id) && users.get(id).isActive())
			return users.get(id);
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
}
