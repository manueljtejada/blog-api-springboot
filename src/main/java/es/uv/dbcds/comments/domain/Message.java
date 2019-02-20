package es.uv.dbcds.comments.domain;

import java.util.List;

public class Message {
	private int id;
	private String title;
	private String body;
	private List<Comment> comments;

	private Message() {

	}

	public Message(int id, String title, String body, List<Comment> comments) {
		super();
		this.id = id;
		this.title = title;
		this.body = body;
		this.comments = comments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
