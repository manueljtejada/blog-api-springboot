package es.uv.dbcds.comments.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RootController {
  @GetMapping
  ResourceSupport index() {
    ResourceSupport rootResource = new ResourceSupport();
    rootResource.add(linkTo(methodOn(MessageController.class).getMessages()).withRel("messages"));
    rootResource.add(linkTo(methodOn(CommentController.class).getComments()).withRel("comments"));

    return rootResource;
  }
}