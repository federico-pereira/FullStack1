package duoc.proyect.controller;

import duoc.proyect.model.Soporte;
import duoc.proyect.model.TicketSoporte;
import duoc.proyect.service.SoporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/soportes")
public class    SoporteController {

    @Autowired
    private SoporteService soporteService;

    @GetMapping
    public ResponseEntity<List<Soporte>> getSoportes() {
        return soporteService.getSoportes();
    }

    @PostMapping
    public ResponseEntity<Object> createSoporte(@RequestBody Soporte soporte) {
        return soporteService.addSoporte(soporte);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSoporteById(@PathVariable int id) {
        return soporteService.getSoporteById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSoporteById(@PathVariable int id) {
        return  soporteService.deleteSoporte(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSoporte(@PathVariable int id, @RequestBody Soporte soporte) {
        return soporteService.updateSoporte(id, soporte);
    }

    //Tickets

    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<TicketSoporte>> getTicketsBySoporteId(@PathVariable int id) {
        return soporteService.getTicketsSoportes(id);
    }

    //CORREGIR GENERACION AUTOMATICA DE ID PARA LA REQUEST
    @PostMapping("/{idSoporte}/tickets")
    public ResponseEntity<String> addTicket(@PathVariable int idSoporte, @RequestBody TicketSoporte ticketSoporte) {
        return soporteService.addTicketSoporte(idSoporte,ticketSoporte.getId());
    }

    @DeleteMapping("/{idSoporte}/tickets/{idTicket}")
    public ResponseEntity<String> deleteTicket(@PathVariable int idSoporte, @PathVariable int idTicket) {
        return soporteService.deleteTicketSoporte(idSoporte,idTicket);
    }
}
