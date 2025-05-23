package duoc.proyect.service;

import duoc.proyect.model.TicketSoporte;
import duoc.proyect.repositoy.TicketSoporteRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketSoporteService {

    @Autowired
    TicketSoporteRepository ticketSoporteRepository;

    public ResponseEntity<List<TicketSoporte>> getTickets() {
        List<TicketSoporte> tickets = ticketSoporteRepository.findAll();
        if (tickets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(tickets);
    }

    public ResponseEntity<String> addTicket(TicketSoporte ticket) {
        if (ticketSoporteRepository.existsById(ticket.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ticket ya existe");
        }
        ticketSoporteRepository.save(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ticket agregado correctamente");
    }

    public ResponseEntity<Object> getTicketByID(int id) {
        Optional<TicketSoporte> ticket = ticketSoporteRepository.findById(id);
        if (ticket.isPresent()) {
            return ResponseEntity.ok(ticket.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Ticket con id "+ id + " no encontrado");
    }

    public ResponseEntity<String> deleteTicket(int id) {
        if (ticketSoporteRepository.existsById(id)) {
            ticketSoporteRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Ticket con id "+ id + " eliminado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Ticket con id "+ id + " no encontrado");
    }

    public ResponseEntity<String> updateTicket(int id, TicketSoporte newTicket) {
        if (ticketSoporteRepository.existsById(id)) {
            newTicket.setId(id);
            ticketSoporteRepository.save(newTicket);
            return ResponseEntity.ok("Ticket actualizado correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket con id "+ id + " no encontrado");
    }
}
