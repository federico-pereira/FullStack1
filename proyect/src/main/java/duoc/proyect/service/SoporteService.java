package duoc.proyect.service;

import duoc.proyect.model.Soporte;
import duoc.proyect.model.TicketSoporte;
import duoc.proyect.repository.SoporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SoporteService {

    @Autowired
    SoporteRepository soporteRepository;

    @Autowired
    TicketSoporteService ticketSoporteService;

    public ResponseEntity<List<Soporte>> getSoportes() {
        List<Soporte> soportes = soporteRepository.findAll();
        if (soportes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(soportes, HttpStatus.OK);
    }

    public ResponseEntity<Object> getSoporteById(int id) {
        Optional<Soporte> soporte = soporteRepository.findById(id);
        if (soporte.isPresent()) {
            return new ResponseEntity<>(soporte.get(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Soporte con id " + id + " no encontrado");
    }

    public ResponseEntity<Object> addSoporte(Soporte soporte) {
        soporteRepository.save(soporte);
        return new ResponseEntity<>(soporte, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateSoporte(int id, Soporte soporte) {
        if (soporteRepository.existsById(id)) {
            soporte.setId(id);
            soporteRepository.save(soporte);
            return ResponseEntity.status(HttpStatus.OK).body("Soporte actualizado: " + soporte);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Soporte con id " + id + " no encontrado");
    }

    public ResponseEntity<Object> deleteSoporte(int id) {
        if (soporteRepository.existsById(id)) {
            soporteRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Soporte eliminado: " + id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Soporte con id " + id + " no encontrado");
    }

    //Tickets

    public ResponseEntity<List<TicketSoporte>> getTicketsSoportes(int idSoporte) {
        Optional<Soporte> soporteOpt = soporteRepository.findById(idSoporte);
        if (soporteOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<TicketSoporte> lista = soporteOpt.get().getTicketsAsignados();
        if (lista.isEmpty() || lista == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    public ResponseEntity<String> addTicketSoporte(int idSoporte, int idTicket) {
        if (!soporteRepository.existsById(idSoporte)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Soporte no encontrado");
        }
        if (ticketSoporteService.getTicketByID(idTicket) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket no encontrado");
        }
        Soporte soporte = soporteRepository.findById(idSoporte).get();
        TicketSoporte ticketSoporte = (TicketSoporte) ticketSoporteService.getTicketByID(idTicket).getBody();
        soporte.getTicketsAsignados().add(ticketSoporte);
        soporteRepository.save(soporte);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ticket agregado con id: " + idTicket + " de Soporte: " + soporte);
    }

    public ResponseEntity<String> deleteTicketSoporte(int idSoporte, int idTicket) {
        if (!soporteRepository.existsById(idSoporte)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Soporte no encontrado");
        }
        if (ticketSoporteService.getTicketByID(idTicket) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket no encontrado");
        }
        Soporte soporte = soporteRepository.findById(idSoporte).get();
        TicketSoporte ticketSoporte = (TicketSoporte) ticketSoporteService.getTicketByID(idTicket).getBody();
        soporte.getTicketsAsignados().remove(ticketSoporte);
        soporteRepository.save(soporte);
        return ResponseEntity.status(HttpStatus.OK).body("Ticket eleminado con id: " + idTicket + " de Soporte: " + soporte);
    }
}