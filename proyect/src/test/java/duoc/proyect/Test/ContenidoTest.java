package duoc.proyect.Test;

import duoc.proyect.model.Contenido;
import duoc.proyect.repository.ContenidoRepository;
import duoc.proyect.service.ContenidoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContenidoTest {
    @InjectMocks
    private ContenidoService contenidoService;

    @Mock
    private ContenidoRepository contenidoRepository;

    @Test
    @DisplayName("Obtener todos los contenidos - Caso con datos")
    void testGetAll_contenido_conDatos() {
        Contenido obj1 = new Contenido();
        Contenido obj2 = new Contenido();
        when(contenidoRepository.findAll()).thenReturn(List.of(obj1, obj2));

        ResponseEntity<List<Contenido>> response = contenidoService.getContenidos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @DisplayName("Obtener todos los contenidos - Lista vacía")
    void testGetAll_contenido_vacio() {
        when(contenidoRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Contenido>> response = contenidoService.getContenidos();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Obtener contenido por ID - Existe")
    void testGetContenidoById_existe() {
        Contenido obj = new Contenido();
        obj.setId(1);
        when(contenidoRepository.findById(1)).thenReturn(Optional.of(obj));

        ResponseEntity<?> response = contenidoService.getContenidoById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Obtener contenido por ID - No existe")
    void testGetContenidoById_noExiste() {
        when(contenidoRepository.findById(99)).thenReturn(Optional.empty());

        ResponseEntity<?> response = contenidoService.getContenidoById(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Agregar contenido - Caso válido")
    void testAddContenido_valido() {
        Contenido obj = new Contenido();
        when(contenidoRepository.save(any())).thenReturn(obj);

        ResponseEntity<Contenido> response = contenidoService.addContenido(obj);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Actualizar contenido - Existe")
    void testUpdateContenido_existe() {
        int id = 1;
        Contenido existing = new Contenido();
        Contenido updated = new Contenido();
        when(contenidoRepository.findById(id)).thenReturn(Optional.of(existing));
        when(contenidoRepository.save(any())).thenReturn(updated);

        ResponseEntity<Contenido> response = contenidoService.updateContenido(id, updated);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Actualizar contenido - No existe")
    void testUpdateContenido_noExiste() {
        Contenido updated = new Contenido();
        when(contenidoRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResponseEntity<Contenido> response = contenidoService.updateContenido(99, updated);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Eliminar contenido - Existe")
    void testDeleteContenido_existe() {
        when(contenidoRepository.existsById(1)).thenReturn(true);

        ResponseEntity<String> response = contenidoService.deleteContenido(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Eliminar contenido - No existe")
    void testDeleteContenido_noExiste() {
        when(contenidoRepository.existsById(1)).thenReturn(false);

        ResponseEntity<String> response = contenidoService.deleteContenido(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
