package duoc.proyect.controller;

import duoc.proyect.model.TicketSoporte;
import duoc.proyect.repositoy.TicketSoporteRepository;
import duoc.proyect.service.TicketSoporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/tickets")
public class TicketSoporteController {

    @Autowired
    TicketSoporteService ticketSoporteService;

    @GetMapping
    public @ResponseBody ResponseEntity<List<TicketSoporte>> getTicketSoportes() {
        return ticketSoporteService.getTickets();
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Object> getTicketSoporteById(@PathVariable int id) {
        return ticketSoporteService.getTicketByID(id);
    }

    @PostMapping
    public ResponseEntity<String> addTicketSoporte(@RequestBody TicketSoporte ticketSoporte) {
        return ticketSoporteService.addTicket(ticketSoporte);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicketSoporte(@PathVariable int id) {
        return ticketSoporteService.deleteTicket(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTicketSoporte(@PathVariable int id, @RequestBody TicketSoporte ticketSoporte) {
        return ticketSoporteService.updateTicket(id, ticketSoporte);
    }

}
