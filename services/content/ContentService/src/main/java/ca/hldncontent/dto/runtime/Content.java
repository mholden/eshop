package ca.hldncontent.dto.runtime;

public class Content {
	private String id;
	private byte[] data;

	public Content() {

	}
	
	public Content(String id, byte[] data) {
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
