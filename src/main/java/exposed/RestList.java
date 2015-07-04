package exposed;

import java.util.ArrayList;

public class RestList<T> {

	private ArrayList<RestItem<T>> items = new ArrayList<RestItem<T>>();
	
	public ArrayList<RestItem<T>> getItems() {
		return items;
	}

	public void setItems(ArrayList<RestItem<T>> items) {
		this.items = items;
	}
	
}
