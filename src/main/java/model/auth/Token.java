package model.auth;

import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.user.User;

public class Token {

	@SuppressWarnings("unused")
	private final static Logger LOGGER = Logger.getLogger(Token.class.getName());
	private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static long TOKEN_TIME = 1000 * 60 * 10; // 10 minutes

	private Date creationDate = null;
	private Date expirationDate = null;

	private String tokenValue;

	private long userId;

	public Token(User user) {
		this.creationDate = new Date();
		this.expirationDate = new Date(this.creationDate.getTime() + TOKEN_TIME);

		this.userId = user.getId();

		this.tokenValue = "u" + user.getId() + "t"
				+ new Date().getTime() + "r" + new Random().nextInt(10000);
	}

	public void renew() {
		expirationDate = new Date(this.creationDate.getTime() + TOKEN_TIME);
	}

	public boolean isValid() {
		return new Date().getTime() < expirationDate.getTime();
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public long getUserId() {
		return userId;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setInValid() {
		expirationDate = new Date();
	}

	@Override
	public String toString() {
		return GSON.toJson(this);
	}

}
