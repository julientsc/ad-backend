package model.auth;

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

import model.user.User;

public class Tokens {
	
	@SuppressWarnings("unused")
	private final static Logger LOGGER = Logger.getLogger(Tokens.class.getName()); 
	private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private final static String PATH = "./tokens.json";
	
	private static Tokens INSTANCE = null;

	public static Tokens getInstance() {
		if (INSTANCE == null) {
			try {
				INSTANCE = load(PATH);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				INSTANCE = new Tokens();
				INSTANCE.save();
			}
		}
		return INSTANCE;
	}

	private static Tokens load(String path) throws IOException {
		BufferedReader br;
		br = new BufferedReader(new FileReader(path));
		Tokens tokens = GSON.fromJson(br, Tokens.class);
		br.close();
		return tokens;
	}

	private HashMap<String, Token> tokens = null;
	
	private Tokens() {
		this.tokens = new HashMap<String, Token>();
	}
	
	
	public Token addToken(User user) {
		Token token = new Token(user);
		tokens.put(token.getTokenValue(), token);
		save();
		return token;
	}
	
	public boolean removeToken(String token) {
		if(tokens.containsKey(token) && tokens.get(token).isValid()) {
			tokens.get(token).setInValid();
			save();
			return true;
		}
		return false;
	}
	
	public Token getToken(String token) {
		if(tokens.containsKey(token) && tokens.get(token).isValid())
			return tokens.get(token);
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

	public Set<String> getTokenIds() {
		return tokens.keySet();
	}

	
}
