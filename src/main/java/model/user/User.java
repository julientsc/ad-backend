package model.user;

import java.util.logging.Logger;

import model.common.ItemContent;

public class User extends ItemContent<Long> {

	@SuppressWarnings("unused")
	private final static Logger LOGGER = Logger.getLogger(User.class.getName());
	
	public User(long id) {
		super(id);
	}

}
