package es.uv.dbcds.comments.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.uv.dbcds.comments.domain.Comment;
import es.uv.dbcds.comments.domain.Message;

@Service
public class MessagesService {
	private final List<Message> messages = new ArrayList<>();
	private final List<Comment> comments = new ArrayList<>();
	private final List<Comment> comments1 = new ArrayList<>();
	private final List<Comment> comments2 = new ArrayList<>();

	public MessagesService() {
		Message message1 = new Message(1, "Hello, World", "Hello hello hello", comments1, 0);
		Message message2 = new Message(2, "Hola, Mundo", "Hola hola hola", comments2, 0);
		comments1.add(new Comment(1, "Manuel", "matemer@alumni.uv.es", "Bye", message1));
		comments2.add(new Comment(2, "Manu", "sample@uv.es", "fhsa agsf ak", message2));
		comments.addAll(comments1);
		comments.addAll(comments2);
		messages.add(message1);
		messages.add(message2);
	}

	public List<Message> getMessages() {
		return messages;
	}

	public Message getMessageById(int id) {
		return messages.stream().filter(r -> r.getId() == id).findFirst()
				.orElseThrow(() -> new MessageNotFoundException("No message found"));
	}

	public List<Comment> getComments() {
		return comments;
	}

	public Comment getCommentById(int messageId, int commentId) {
		return this.getMessageById(messageId).getComments().stream().filter(c -> c.getId() == commentId).findFirst()
				.orElseThrow(() -> new MessageNotFoundException("No comment found"));
	}

	public List<Comment> getMessageComments(int id) {
		return this.getMessageById(id).getComments();
	}

	public Message addMessage(Message message) {
		messages.add(message);
		return message;
	}

	public Comment addComment(int id, Comment comment) {
		this.getMessageById(id).getComments().add(comment);
		return comment;
	}

	public void deleteMessage(int id) {
		Message message = this.getMessageById(id);
		messages.remove(message);
	}

	public void deleteComment(int messageId, int commentId) {
		Message message = this.getMessageById(messageId);
		Comment comment = this.getCommentById(messageId, commentId);
		message.getComments().remove(comment);
	}

	public Message updateMessage(int id, Message newMessage) {
		Message messageToUpdate = this.getMessageById(id);
		messageToUpdate.setId(newMessage.getId());
		messageToUpdate.setTitle(newMessage.getTitle());
		messageToUpdate.setBody(newMessage.getBody());
		messageToUpdate.setComments(newMessage.getComments());

		return messageToUpdate;
	}

	public Comment updateComment(int messageId, int commentId, Comment newComment) {
		Comment commentToUpdate = this.getCommentById(messageId, commentId);
		commentToUpdate.setId(newComment.getId());
		commentToUpdate.setEmail(newComment.getEmail());
		commentToUpdate.setName(newComment.getName());
		commentToUpdate.setText(newComment.getText());

		return commentToUpdate;
	}

	public Message likeMessage(int id) {
		Message message = this.getMessageById(id);
		int count = message.getLikes();
		message.setLikes(++count);
		return message;
	}
}
