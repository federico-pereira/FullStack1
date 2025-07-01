package duoc.proyect.controller;

import duoc.proyect.model.TicketSoporte;
import duoc.proyect.service.TicketSoporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@Tag(name = "Ticket de Soporte", description = "API para gestión de tickets de soporte técnico")
public class TicketSoporteController {

    @Autowired
    private TicketSoporteService ticketService;

    @Operation(summary = "Obtener todos los tickets", description = "Retorna lista completa de tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tickets encontrados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TicketSoporte.class))),
            @ApiResponse(responseCode = "204", description = "No hay tickets registrados")
    })
    @GetMapping
    public ResponseEntity<?> getAllTickets() {
        return ticketService.getTickets();
    }

    @Operation(summary = "Crear nuevo ticket", description = "Registra un nuevo ticket de soporte")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket creado exitosamente"),
            @ApiResponse(responseCode = "409", description = "El ticket ya existe")
    })
    @PostMapping
    public ResponseEntity<TicketSoporte> createTicket(
            @Parameter(description = "Datos del ticket a crear", required = true,
                    content = @Content(schema = @Schema(implementation = TicketSoporte.class)))
            @RequestBody TicketSoporte ticket) {
        return ticketService.addTicket(ticket);
    }

    @Operation(summary = "Obtener ticket por ID", description = "Busca un ticket por su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TicketSoporte.class))),
            @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TicketSoporte> getTicketById(
            @Parameter(description = "ID del ticket", required = true, example = "1")
            @PathVariable int id) {
        return ticketService.getTicketSoporteById(id);
    }

    @Operation(summary = "Eliminar ticket", description = "Elimina un ticket por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ticket eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(
            @Parameter(description = "ID del ticket a eliminar", required = true, example = "1")
            @PathVariable int id) {
        return ticketService.deleteTicket(id);
    }

    @Operation(summary = "Actualizar ticket", description = "Actualiza los datos de un ticket existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TicketSoporte> updateTicket(
            @Parameter(description = "ID del ticket a actualizar", required = true, example = "1")
            @PathVariable int id,
            @Parameter(description = "Datos actualizados del ticket", required = true,
                    content = @Content(schema = @Schema(implementation = TicketSoporte.class)))
            @RequestBody TicketSoporte updatedTicket) {
        return ticketService.updateTicket(id, updatedTicket);
    }
}