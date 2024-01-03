package ca.testeshop.utils;

public class Message {
	private String to;
	private String from;
	private String text;

	public String getText() {
		return text;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
