package es.uv.dbcds.comments.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//import es.uv.dbcds.comments.domain.Comment;
import es.uv.dbcds.comments.domain.Comment;
import es.uv.dbcds.comments.domain.Message;
import es.uv.dbcds.comments.service.MessagesService;
//import es.uv.dbcds.comments.service.MessageNotFoundException;
//import es.uv.dbcds.comments.service.MessagesService;

@RestController
@CrossOrigin
public class MessageController {
	@Autowired
	private MessagesService service;

	@Autowired
	private MessageResourceAssembler assembler;

	@Autowired
	private CommentResourceAssembler cAssembler;

	// Get all messages
	@GetMapping("/messages")
	Resources<Resource<Message>> getMessages() {

		List<Resource<Message>> messages = service.getMessages().stream()
		        .map(assembler::toResource)
		        .collect(Collectors.toList());

		        return new Resources<>(messages,
		        linkTo(methodOn(MessageController.class).getMessages()).withSelfRel());
	}

	// Add new message
	@PostMapping("/messages")
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<?> newMessage(@RequestBody @Valid Message newMessage) throws URISyntaxException {

		Resource<Message> resource = assembler.toResource(service.addMessage(newMessage));

		return ResponseEntity
			.created(new URI(resource.getId().expand().getHref()))
			.body(resource);
	}

	// Get message by ID
	@GetMapping("/messages/{id}")
	Resource<Message> getMessageById(@PathVariable int id) {

		Message message = service.getMessageById(id);

		return assembler.toResource(message);
	}

	// Delete a message by ID
	@DeleteMapping("/messages/{id}")
	ResponseEntity<?> deleteMessage(@PathVariable int id) {

		service.deleteMessage(id);

		return ResponseEntity.noContent().build();
	}


	// Update a message
	@PutMapping("/messages/{id}")
	public ResponseEntity<?> updateMessage(@PathVariable int id, @RequestBody Message newMessage) throws URISyntaxException {
		Message message = service.updateMessage(id, newMessage);

		Resource<Message> resource = assembler.toResource(message);

		return ResponseEntity
				.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}

	// Like a comment
	@PutMapping("/messages/{id}/like")
	public ResponseEntity<?> likeMessage(@PathVariable int id) throws URISyntaxException {
		Message message = service.likeMessage(id);

		Resource<Message> resource = assembler.toResource(message);

		return ResponseEntity
				.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}
}
