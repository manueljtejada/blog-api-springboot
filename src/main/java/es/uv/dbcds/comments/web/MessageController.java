package es.uv.dbcds.comments.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.uv.dbcds.comments.domain.Comment;
import es.uv.dbcds.comments.domain.Message;
import es.uv.dbcds.comments.service.MessagesService;

@RestController
public class MessageController {
	@Autowired
	private MessagesService messagesService;

	// Get all messages
	@GetMapping("/messages")
	public List<Message> getAllMessages() {
		return messagesService.getMessages();
	}

	// Get message by ID
	@GetMapping("messages/{id}")
	public Message getMessageById(@PathVariable("id") int id) {
		return messagesService.getMessageById(id);
	}

	// Get all comments from a message
	@GetMapping("messages/{id}/comments")
	public List<Comment> getMessageComments(@PathVariable("id") int id) {
		return messagesService.getMessageComments(id);
	}

	// Get a comment by ID
	@GetMapping("messages/{messageId}/comments/{commentId}")
	public Comment getCommentById(@PathVariable("messageId") int messageId, @PathVariable("commentId") int commentId) {
		return messagesService.getCommentById(messageId, commentId);
	}

	// Add a new message
	@PostMapping("/messages")
	@ResponseStatus(HttpStatus.CREATED)
	public Message addMessage(@RequestBody Message message) {
		return messagesService.addMessage(message);
	}

	// Add a new comment to a message
	@PostMapping("messages/{id}/comments")
	public void addComment(@PathVariable("id") int id, @RequestBody Comment comment) {
		messagesService.addComment(id, comment);
	}

	// Delete a message by ID
	@DeleteMapping("/messages/{id}")
	public void deleteMessage(@PathVariable("id") int id) {
		messagesService.deleteMessage(id);
	}

	// Delete a comment by ID
	@DeleteMapping("/messages/{messageId}/comments/{commentId}")
	public void deleteComment(@PathVariable("messageId") int messageId, @PathVariable("commentId") int commentId) {
		messagesService.deleteComment(messageId, commentId);
	}
	
	// Update a message
	@PutMapping("/messages/{id}")
	public void updateMessage(@PathVariable("id") int id, @RequestBody Message newMessage) {
		messagesService.updateMessage(id, newMessage);
	}
	
	// Update a comment
	@PutMapping("/messages/{messageId}/comments/{commentId}")
	public void updateComment(@PathVariable("messageId") int messageId, @PathVariable("commentId") int commentId,
			@RequestBody Comment newComment) {
		messagesService.updateComment(messageId, commentId, newComment);
	}
}
