package duoc.proyect.Test;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.TicketSoporte;
import duoc.proyect.repository.TicketSoporteRepository;
import duoc.proyect.service.TicketSoporteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketSoporteTest {

    @InjectMocks
    private TicketSoporteService ticketService;

    @Mock
    private TicketSoporteRepository ticketRepository;

    private TicketSoporte crearTicketValido(int id) {
        Alumno reclamante = new Alumno();
        reclamante.setRut("12345678-9");

        TicketSoporte ticket = new TicketSoporte();
        ticket.setId(id);
        ticket.setReclamante(reclamante);
        ticket.setTema("Problema con la plataforma");
        ticket.setDescripcion("No puedo acceder a mis cursos");
        ticket.setFechaCreacion(LocalDateTime.now());
        return ticket;
    }

    @Test
    @DisplayName("Obtener todos los tickets - Caso con datos")
    void testGetTickets_conDatos() {
        // Preparar datos
        TicketSoporte ticket1 = crearTicketValido(1);
        TicketSoporte ticket2 = crearTicketValido(2);

        // Configurar mock
        when(ticketRepository.findAll()).thenReturn(List.of(ticket1, ticket2));

        // Ejecutar
        ResponseEntity<List<TicketSoporte>> respuesta = ticketService.getTickets();

        // Verificar
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(2, respuesta.getBody().size());
    }

    @Test
    @DisplayName("Obtener todos los tickets - Caso sin datos")
    void testGetTickets_sinDatos() {
        // Configurar mock
        when(ticketRepository.findAll()).thenReturn(Collections.emptyList());

        // Ejecutar
        ResponseEntity<List<TicketSoporte>> respuesta = ticketService.getTickets();

        // Verificar
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        assertNull(respuesta.getBody());
    }

    @Test
    @DisplayName("Agregar ticket válido")
    void testAddTicket_valido() {
        // Preparar datos
        TicketSoporte nuevoTicket = crearTicketValido(1);

        // Configurar mock
        when(ticketRepository.existsById(1)).thenReturn(false);
        when(ticketRepository.save(nuevoTicket)).thenReturn(nuevoTicket);

        // Ejecutar
        ResponseEntity<TicketSoporte> respuesta = ticketService.addTicket(nuevoTicket);

        // Verificar
        assertNotNull(respuesta.getBody());
        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
    }

    @Test
    @DisplayName("Agregar ticket con ID existente")
    void testAddTicket_idExistente() {
        // Preparar datos
        TicketSoporte ticketExistente = crearTicketValido(1);

        // Configurar mock
        when(ticketRepository.existsById(1)).thenReturn(true);

        // Ejecutar
        ResponseEntity<TicketSoporte> respuesta = ticketService.addTicket(ticketExistente);

        // Verificar
        assertNull(respuesta.getBody());
        assertEquals(HttpStatus.CONFLICT, respuesta.getStatusCode());
        verify(ticketRepository, never()).save(any());
    }

    @Test
    @DisplayName("Obtener ticket por ID existente")
    void testGetTicketByID_existente() {
        // Preparar datos
        TicketSoporte ticket = crearTicketValido(1);

        // Configurar mock
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));

        // Ejecutar
        ResponseEntity<TicketSoporte> respuesta = ticketService.getTicketSoporteById(1);

        // Verificar
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(ticket, respuesta.getBody());
    }

    @Test
    @DisplayName("Obtener ticket por ID inexistente")
    void testGetTicketByID_inexistente() {
        // Configurar mock
        when(ticketRepository.findById(99)).thenReturn(Optional.empty());

        // Ejecutar
        ResponseEntity<TicketSoporte> respuesta = ticketService.getTicketSoporteById(99);

        // Verificar
        assertNull(respuesta.getBody());
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    @DisplayName("Eliminar ticket existente")
    void testDeleteTicket_existente() {
        // Configurar mock
        when(ticketRepository.existsById(1)).thenReturn(true);

        // Ejecutar
        ResponseEntity<Void> respuesta = ticketService.deleteTicket(1);

        // Verificar
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        verify(ticketRepository).deleteById(1);
    }

    @Test
    @DisplayName("Eliminar ticket inexistente")
    void testDeleteTicket_inexistente() {
        // Configurar mock
        when(ticketRepository.existsById(99)).thenReturn(false);

        // Ejecutar
        ResponseEntity<Void> respuesta = ticketService.deleteTicket(99);

        // Verificar
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        verify(ticketRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Actualizar ticket existente")
    void testUpdateTicket_existente() {
        // Preparar datos
        TicketSoporte ticketActualizado = crearTicketValido(1);
        ticketActualizado.setTema("Nuevo tema");

        // Configurar mock
        when(ticketRepository.existsById(1)).thenReturn(true);
        when(ticketRepository.save(ticketActualizado)).thenReturn(ticketActualizado);

        // Ejecutar
        ResponseEntity<TicketSoporte> respuesta = ticketService.updateTicket(1, ticketActualizado);

        // Verificar
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        verify(ticketRepository).save(ticketActualizado);
    }

    @Test
    @DisplayName("Actualizar ticket inexistente")
    void testUpdateTicket_inexistente() {
        // Preparar datos
        TicketSoporte ticket = crearTicketValido(99);

        // Configurar mock
        when(ticketRepository.existsById(99)).thenReturn(false);

        // Ejecutar
        ResponseEntity<TicketSoporte> respuesta = ticketService.updateTicket(99, ticket);

        // Verificar
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        verify(ticketRepository, never()).save(any());
    }

    @Test
    @DisplayName("Buscar tickets por reclamante - Con resultados")
    void testGetTicketsByReclamante_conResultados() {
        // Preparar datos
        String rut = "12345678-9";
        TicketSoporte ticket1 = crearTicketValido(1);
        TicketSoporte ticket2 = crearTicketValido(2);

        // Configurar mock
        when(ticketRepository.findByReclamanteRut(rut)).thenReturn(List.of(ticket1, ticket2));

        // Ejecutar
        ResponseEntity<List<TicketSoporte>> respuesta = ticketService.getTicketsByReclamante(rut);

        // Verificar
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(2, respuesta.getBody().size());
    }

    @Test
    @DisplayName("Buscar tickets por reclamante - Sin resultados")
    void testGetTicketsByReclamante_sinResultados() {
        // Preparar datos
        String rut = "00000000-0";

        // Configurar mock
        when(ticketRepository.findByReclamanteRut(rut)).thenReturn(Collections.emptyList());

        // Ejecutar
        ResponseEntity<List<TicketSoporte>> respuesta = ticketService.getTicketsByReclamante(rut);

        // Verificar
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        assertNull(respuesta.getBody());
    }

}