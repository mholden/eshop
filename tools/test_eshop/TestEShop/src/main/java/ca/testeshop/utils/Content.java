package ca.testeshop.utils;

public class Content {
	public String id;
	public byte[] data;

	public Content() {

	}
	
	public Content(String id) throws Exception {
		this.id = id;
		this.data = Content.class.getResourceAsStream(id).readAllBytes();
	}
	
	public Content(String id, byte[] data) throws Exception {
		this.id = id;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String toString() {
		return String.format("id: %s", id);
	}
}
