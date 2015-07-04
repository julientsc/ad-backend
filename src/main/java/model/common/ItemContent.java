package model.common;

public class ItemContent<T> extends Item<T> {

	public ItemContent(T id) {
		super(id);
		this.JSONContent = "";
	}

	private String JSONContent = "";
	
	public String getJSONContent() {
		return JSONContent;
	}

	public void setJSONContent(String jSONContent) {
		JSONContent = jSONContent;
	}
}
