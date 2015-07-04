package exposed;

public class RestItem<T> {
	T id;
	String label;
	String path;

	public RestItem(T id, String label, String path) {
		super();
		this.id = id;
		this.label = label;
		this.path = path;
	}

	public RestItem(T id, String path) {
		super();
		this.id = id;
		this.path = path;
	}

	public RestItem(T id) {
		super();
		this.id = id;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
