package model.campaign;

import java.util.logging.Logger;

import model.common.Item;
import model.user.User;


public class Campaign extends Item<Long> {
	
	@SuppressWarnings("unused")
	private final static Logger LOGGER = Logger.getLogger(User.class.getName());

	public Campaign(long id) {
		super(id);
	}

}
