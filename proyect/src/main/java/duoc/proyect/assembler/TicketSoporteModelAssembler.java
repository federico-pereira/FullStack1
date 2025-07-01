package duoc.proyect.Assembler;

import duoc.proyect.controller.TicketSoporteControllerV2;
import duoc.proyect.model.TicketSoporte;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TicketSoporteModelAssembler implements RepresentationModelAssembler<TicketSoporte, EntityModel<TicketSoporte>> {

    @Override
    public EntityModel<TicketSoporte> toModel(TicketSoporte ticket) {
        return EntityModel.of(ticket,
                linkTo(methodOn(TicketSoporteControllerV2.class).getTicketById(ticket.getId())).withSelfRel(),
                linkTo(methodOn(TicketSoporteControllerV2.class).getTickets()).withRel("tickets"));
    }
}
