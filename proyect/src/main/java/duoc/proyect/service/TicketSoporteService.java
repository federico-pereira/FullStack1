package duoc.proyect.service;

import duoc.proyect.model.CuponDescuento;
import duoc.proyect.model.TicketSoporte;
import duoc.proyect.repository.TicketSoporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketSoporteService {

    @Autowired
    TicketSoporteRepository ticketSoporteRepository;

    public ResponseEntity<List<TicketSoporte>> getTickets() {
        List<TicketSoporte> tickets = ticketSoporteRepository.findAll();
        return tickets.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(tickets);
    }

    public ResponseEntity<TicketSoporte> addTicket(TicketSoporte ticket) {
        if (ticketSoporteRepository.existsById(ticket.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        TicketSoporte savedTicket = ticketSoporteRepository.save(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicket);
    }

    public ResponseEntity<TicketSoporte> getTicketSoporteById(int id) {
        Optional<TicketSoporte> ticketSoporte = ticketSoporteRepository.findById(id);
        if (ticketSoporte.isPresent()) {
            return ResponseEntity.ok(ticketSoporte.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> deleteTicket(int id) {
        if (ticketSoporteRepository.existsById(id)) {
            ticketSoporteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<TicketSoporte> updateTicket(int id, TicketSoporte newTicket) {
        if (ticketSoporteRepository.existsById(id)) {
            newTicket.setId(id);
            TicketSoporte updatedTicket = ticketSoporteRepository.save(newTicket);
            return ResponseEntity.ok(updatedTicket);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<TicketSoporte>> getTicketsByReclamante(String rut) {
        List<TicketSoporte> tickets = ticketSoporteRepository.findByReclamanteRut(rut);

        if (tickets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tickets);
    }
}
