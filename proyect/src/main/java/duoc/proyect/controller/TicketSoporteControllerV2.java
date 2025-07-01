package duoc.proyect.controller;

import duoc.proyect.Assembler.TicketSoporteModelAssembler;
import duoc.proyect.model.TicketSoporte;
import duoc.proyect.service.TicketSoporteService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/tickets")
public class TicketSoporteControllerV2 {

    private final TicketSoporteService service;
    private final TicketSoporteModelAssembler assembler;

    public TicketSoporteControllerV2(TicketSoporteService service, TicketSoporteModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<?> getTickets() {
        ResponseEntity<List<TicketSoporte>> response = service.getTickets();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<EntityModel<TicketSoporte>> tickets = response.getBody().stream()
                    .map(assembler::toModel)
                    .toList();
            return ResponseEntity.ok(
                    CollectionModel.of(tickets,
                            linkTo(methodOn(TicketSoporteControllerV2.class).getTickets()).withSelfRel())
            );
        }
        return ResponseEntity.status(response.getStatusCode()).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable int id) {
        ResponseEntity<Object> response = service.getTicketByID(id);
        if (response.getStatusCode().is2xxSuccessful()) {
            TicketSoporte ticket = (TicketSoporte) response.getBody();
            return ResponseEntity.ok(assembler.toModel(ticket));
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<?> addTicket(@RequestBody TicketSoporte ticket) {
        return service.addTicket(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicket(@PathVariable int id, @RequestBody TicketSoporte ticket) {
        return service.updateTicket(id, ticket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable int id) {
        return service.deleteTicket(id);
    }
}
