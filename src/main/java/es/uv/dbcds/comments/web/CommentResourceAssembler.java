package es.uv.dbcds.comments.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import es.uv.dbcds.comments.domain.Comment;

@Component
public class CommentResourceAssembler implements ResourceAssembler<Comment, Resource<Comment>> {

	@Override
	public Resource<Comment> toResource(Comment comment) {
		return new Resource<>(comment,
			linkTo(methodOn(CommentController.class).getCommentById(comment.getParent().getId(), comment.getId())).withSelfRel(),
			linkTo(methodOn(CommentController.class).getMessageComments(comment.getParent().getId())).withSelfRel());
	}

}
