package model.common;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Item<T> {

	private final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	private T id;
	private boolean isActive = true;

	private Date creationDate = null;
	private Date lastUpdateDate = null;
	
	public Item(T id) {
		this.id = id;
		this.isActive = true;

		this.creationDate = new Date();
		this.lastUpdateDate = new Date();
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public T getId() {
		return id;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return GSON.toJson(this);
	}
}
