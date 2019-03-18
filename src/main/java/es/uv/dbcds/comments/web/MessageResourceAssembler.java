package es.uv.dbcds.comments.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URISyntaxException;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import es.uv.dbcds.comments.domain.Message;

@Component
public class MessageResourceAssembler implements ResourceAssembler<Message, Resource<Message>> {

	@Override
	public Resource<Message> toResource(Message message) {
		try {
			return new Resource<>(message,
				linkTo(methodOn(MessageController.class).getMessageById(message.getId())).withSelfRel(),
				linkTo(methodOn(MessageController.class).likeMessage(message.getId())).withRel("like"),
				linkTo(methodOn(MessageController.class).getMessages()).withRel("messages"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
