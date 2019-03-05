package es.uv.dbcds.comments.domain;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Message {
	@Min(0)
	private int id;

	@Size(min=1, max=50)
	private String title;

	@NotNull
	private String body;

	private List<Comment> comments;

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
