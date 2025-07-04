package duoc.proyect.Test;

import duoc.proyect.model.Soporte;
import duoc.proyect.model.TicketSoporte;
import duoc.proyect.repository.SoporteRepository;
import duoc.proyect.service.SoporteService;
import duoc.proyect.service.TicketSoporteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SoporteTest {

    @Mock
    private SoporteRepository soporteRepository;

    @Mock
    private TicketSoporteService ticketSoporteService;

    @InjectMocks
    private SoporteService soporteService;

    private Soporte soporte;
    private TicketSoporte ticket;

    @BeforeEach
    void setUp() {
        soporte = new Soporte();
        soporte.setId(1);
        soporte.setTicketsAsignados(new ArrayList<>());

        ticket = new TicketSoporte();
        ticket.setId(1);
    }

    @Test
    void getSoportes_ConDatos_ReturnsOk() {
        List<Soporte> soportes = new ArrayList<>();
        soportes.add(soporte);

        when(soporteRepository.findAll()).thenReturn(soportes);

        ResponseEntity<List<Soporte>> response = soporteService.getSoportes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getSoportes_SinDatos_ReturnsNoContent() {
        when(soporteRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Soporte>> response = soporteService.getSoportes();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getSoporteById_Existente_ReturnsOk() {
        when(soporteRepository.findById(1)).thenReturn(Optional.of(soporte));

        ResponseEntity<Object> response = soporteService.getSoporteById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(soporte, response.getBody());
    }

    @Test
    void getSoporteById_NoExistente_ReturnsNotFound() {
        when(soporteRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = soporteService.getSoporteById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("no encontrado"));
    }

    @Test
    void addSoporte_ReturnsCreated() {
        when(soporteRepository.save(any(Soporte.class))).thenReturn(soporte);

        ResponseEntity<Object> response = soporteService.addSoporte(soporte);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(soporte, response.getBody());
    }

    @Test
    void updateSoporte_Existente_ReturnsOk() {
        when(soporteRepository.existsById(1)).thenReturn(true);
        when(soporteRepository.save(any(Soporte.class))).thenReturn(soporte);

        ResponseEntity<Object> response = soporteService.updateSoporte(1, soporte);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("actualizado"));
    }

    @Test
    void updateSoporte_NoExistente_ReturnsNotFound() {
        when(soporteRepository.existsById(1)).thenReturn(false);

        ResponseEntity<Object> response = soporteService.updateSoporte(1, soporte);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("no encontrado"));
    }

    @Test
    void deleteSoporte_Existente_ReturnsOk() {
        when(soporteRepository.existsById(1)).thenReturn(true);

        ResponseEntity<Object> response = soporteService.deleteSoporte(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("eliminado"));
        verify(soporteRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteSoporte_NoExistente_ReturnsNotFound() {
        when(soporteRepository.existsById(1)).thenReturn(false);

        ResponseEntity<Object> response = soporteService.deleteSoporte(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("no encontrado"));
        verify(soporteRepository, never()).deleteById(anyInt());
    }

    @Test
    void getTicketsSoportes_ConTickets_ReturnsOk() {
        soporte.getTicketsAsignados().add(ticket);
        when(soporteRepository.findById(1)).thenReturn(Optional.of(soporte));

        ResponseEntity<List<TicketSoporte>> response = soporteService.getTicketsSoportes(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getTicketsSoportes_SinTickets_ReturnsNoContent() {
        when(soporteRepository.findById(1)).thenReturn(Optional.of(soporte));

        ResponseEntity<List<TicketSoporte>> response = soporteService.getTicketsSoportes(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getTicketsSoportes_SoporteNoExiste_ReturnsNotFound() {
        when(soporteRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<List<TicketSoporte>> response = soporteService.getTicketsSoportes(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void addTicketSoporte_TodoValido_ReturnsCreated() {
        when(soporteRepository.existsById(1)).thenReturn(true);
        when(soporteRepository.findById(1)).thenReturn(Optional.of(soporte));
        when(ticketSoporteService.getTicketSoporteById(1)).thenReturn(ResponseEntity.ok(ticket));

        ResponseEntity<String> response = soporteService.addTicketSoporte(1, 1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Ticket agregado"));
        assertTrue(soporte.getTicketsAsignados().contains(ticket));
    }

    @Test
    void addTicketSoporte_SoporteNoExiste_ReturnsNotFound() {
        when(soporteRepository.existsById(1)).thenReturn(false);

        ResponseEntity<String> response = soporteService.addTicketSoporte(1, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Soporte no encontrado", response.getBody());
    }

    @Test
    void addTicketSoporte_TicketNoExiste_ReturnsNotFound() {
        when(soporteRepository.existsById(1)).thenReturn(true);
        when(ticketSoporteService.getTicketSoporteById(1)).thenReturn(null);

        ResponseEntity<String> response = soporteService.addTicketSoporte(1, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Ticket no encontrado", response.getBody());
    }

    @Test
    void deleteTicketSoporte_TodoValido_ReturnsOk() {
        soporte.getTicketsAsignados().add(ticket);

        when(soporteRepository.existsById(1)).thenReturn(true);
        when(soporteRepository.findById(1)).thenReturn(Optional.of(soporte));
        when(ticketSoporteService.getTicketSoporteById(1)).thenReturn(ResponseEntity.ok(ticket));

        ResponseEntity<String> response = soporteService.deleteTicketSoporte(1, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Ticket eleminado"));
        assertFalse(soporte.getTicketsAsignados().contains(ticket));
    }

    @Test
    void deleteTicketSoporte_SoporteNoExiste_ReturnsNotFound() {
        when(soporteRepository.existsById(1)).thenReturn(false);

        ResponseEntity<String> response = soporteService.deleteTicketSoporte(1, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Soporte no encontrado", response.getBody());
    }

    @Test
    void deleteTicketSoporte_TicketNoExiste_ReturnsNotFound() {
        when(soporteRepository.existsById(1)).thenReturn(true);
        when(ticketSoporteService.getTicketSoporteById(1)).thenReturn(null);

        ResponseEntity<String> response = soporteService.deleteTicketSoporte(1, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Ticket no encontrado", response.getBody());
    }
}
