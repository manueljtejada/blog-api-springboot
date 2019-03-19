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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.uv.dbcds.comments.domain.Comment;
import es.uv.dbcds.comments.service.MessagesService;

@RestController
public class CommentController {

	@Autowired
	MessagesService service;

  @Autowired
	CommentResourceAssembler assembler;

  // Get all comments
  @GetMapping("comments")
  public Resources<Resource<Comment>> getComments() {
    List<Resource<Comment>> comments = service.getComments()
      .stream().map(assembler::toResource).collect(Collectors.toList());

    return new Resources<>(comments,
            linkTo(methodOn(CommentController.class).getComments()).withSelfRel());
  }

	// Get all comments from a message
	@GetMapping("messages/{id}/comments")
	public Resources<Resource<Comment>> getMessageComments(@PathVariable("id") int id) {

		List<Resource<Comment>> comments = service.getMessageById(id).getComments()
				.stream().map(assembler::toResource).collect(Collectors.toList());

		return new Resources<>(comments,
		        linkTo(methodOn(CommentController.class).getMessageComments(id)).withSelfRel());
	}

	@PostMapping("messages/{id}/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> addComment(@PathVariable("id") int id, @RequestBody @Valid Comment newComment) throws URISyntaxException {

		Resource<Comment> resource = assembler.toResource(service.addComment(id, newComment));

		return ResponseEntity
				.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}

	@GetMapping("messages/{messageId}/comments/{commentId}")
	public Resource<Comment> getCommentById(@PathVariable("messageId") int messageId, @PathVariable("commentId") int commentId) {
		Comment comment = service.getCommentById(messageId, commentId);

		return assembler.toResource(comment);
	}

	// Delete a comment by ID
	@DeleteMapping("/messages/{messageId}/comments/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable("messageId") int messageId, @PathVariable("commentId") int commentId) {
		service.deleteComment(messageId, commentId);
		return ResponseEntity.noContent().build();
	}

	// Update a comment
	@PutMapping("/messages/{messageId}/comments/{commentId}")
	public ResponseEntity<?> updateComment(@PathVariable("messageId") int messageId, @PathVariable("commentId") int commentId,
			@RequestBody Comment newComment) throws URISyntaxException {
		Comment commentToUpdate = service.updateComment(messageId, commentId, newComment);

		Resource<Comment> resource = assembler.toResource(commentToUpdate);

		return ResponseEntity
				.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}
}
