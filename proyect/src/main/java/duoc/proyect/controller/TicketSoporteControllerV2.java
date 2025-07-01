package duoc.proyect.controller;

import duoc.proyect.assemblers.TicketSoporteModelAssembler;
import duoc.proyect.model.TicketSoporte;
import duoc.proyect.service.TicketSoporteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/tickets")
@Tag(name = "tickets V2", description = "Controlador de tickets con HATEOAS")
public class TicketSoporteControllerV2 {

    private final TicketSoporteService ticketService;
    private final TicketSoporteModelAssembler assembler;

    @Autowired
    public TicketSoporteControllerV2(TicketSoporteService ticketService,
                                     TicketSoporteModelAssembler assembler) {
        this.ticketService = ticketService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TicketSoporte>>> getAllTickets() {
        List<EntityModel<TicketSoporte>> tickets = ticketService.getTickets().getBody().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<TicketSoporte>> collection = CollectionModel.of(tickets,
                linkTo(methodOn(TicketSoporteControllerV2.class).getAllTickets()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TicketSoporte>> getTicketById(@PathVariable int id) {
        ResponseEntity<TicketSoporte> response = ticketService.getTicketSoporteById(id);

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(response.getBody()));
    }

    @PostMapping
    public ResponseEntity<EntityModel<TicketSoporte>> createTicket(@RequestBody TicketSoporte ticket) {
        ResponseEntity<TicketSoporte> response = ticketService.addTicket(ticket);

        if(response.getStatusCode() == HttpStatus.CREATED) {
            EntityModel<TicketSoporte> entityModel = assembler.toModel(ticket);
            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        }
        return ResponseEntity.status(response.getStatusCode()).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<TicketSoporte>> updateTicket(
            @PathVariable int id,
            @RequestBody TicketSoporte updatedTicket) {

        ResponseEntity<TicketSoporte> response = ticketService.updateTicket(id, updatedTicket);

        if(response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(assembler.toModel(updatedTicket));
        }
        return ResponseEntity.status(response.getStatusCode()).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable int id) {
        ResponseEntity<Void> response = ticketService.deleteTicket(id);
        return ResponseEntity.status(response.getStatusCode()).build();
    }

    @GetMapping("/porReclamante/{rutReclamante}")
    public ResponseEntity<CollectionModel<EntityModel<TicketSoporte>>> getTicketsByReclamante(
            @PathVariable String rutReclamante) {

        ResponseEntity<List<TicketSoporte>> response = ticketService.getTicketsByReclamante(rutReclamante);

        if(response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<TicketSoporte>> models = response.getBody().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<TicketSoporte>> collection = CollectionModel.of(models)
                .add(linkTo(methodOn(this.getClass()).getTicketsByReclamante(rutReclamante)).withSelfRel());

        return ResponseEntity.ok(collection);
    }
}