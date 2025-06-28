package duoc.proyect.controller;

import duoc.proyect.model.Soporte;
import duoc.proyect.model.TicketSoporte;
import duoc.proyect.service.SoporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Soportes", description = "Operaciones relacionadas con los soportes")
@RestController
@RequestMapping("/api/v1/soportes")
public class SoporteController {

    @Autowired
    private SoporteService soporteService;

    @Operation(summary = "Obtener todos los soportes")
    @ApiResponse(responseCode = "200", description = "Lista de soportes obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Soporte>> getSoportes() {
        return soporteService.getSoportes();
    }

    @Operation(summary = "Crear un nuevo soporte")
    @ApiResponse(responseCode = "201", description = "Soporte creado correctamente")
    @PostMapping
    public ResponseEntity<Object> createSoporte(@RequestBody Soporte soporte) {
        return soporteService.addSoporte(soporte);
    }

    @Operation(summary = "Obtener un soporte por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Soporte encontrado"),
            @ApiResponse(responseCode = "404", description = "Soporte no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getSoporteById(@PathVariable int id) {
        return soporteService.getSoporteById(id);
    }

    @Operation(summary = "Eliminar un soporte por ID")
    @ApiResponse(responseCode = "204", description = "Soporte eliminado correctamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSoporteById(@PathVariable int id) {
        return soporteService.deleteSoporte(id);
    }

    @Operation(summary = "Actualizar un soporte")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Soporte actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Soporte no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSoporte(@PathVariable int id, @RequestBody Soporte soporte) {
        return soporteService.updateSoporte(id, soporte);
    }

    @Operation(summary = "Obtener tickets de un soporte específico")
    @ApiResponse(responseCode = "200", description = "Tickets obtenidos correctamente")
    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<TicketSoporte>> getTicketsBySoporteId(@PathVariable int id) {
        return soporteService.getTicketsSoportes(id);
    }

    @Operation(summary = "Agregar un ticket a un soporte")
    @ApiResponse(responseCode = "201", description = "Ticket agregado correctamente")
    @PostMapping("/{idSoporte}/tickets")
    public ResponseEntity<String> addTicket(@PathVariable int idSoporte, @RequestBody TicketSoporte ticketSoporte) {
        return soporteService.addTicketSoporte(idSoporte, ticketSoporte.getId());
    }

    @Operation(summary = "Eliminar un ticket de un soporte")
    @ApiResponse(responseCode = "204", description = "Ticket eliminado correctamente")
    @DeleteMapping("/{idSoporte}/tickets/{idTicket}")
    public ResponseEntity<String> deleteTicket(@PathVariable int idSoporte, @PathVariable int idTicket) {
        return soporteService.deleteTicketSoporte(idSoporte, idTicket);
    }
}
