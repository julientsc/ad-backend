package model.product;

import java.util.logging.Logger;

import model.common.ItemContent;
import model.user.User;

public class Product extends ItemContent<Long> {
	
	@SuppressWarnings("unused")
	private final static Logger LOGGER = Logger.getLogger(User.class.getName());


	public Product(long id) {
		super(id);
	}

}
