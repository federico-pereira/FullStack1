package duoc.proyect.Test;

import duoc.proyect.model.Contenido;
import duoc.proyect.repository.ContenidoRepository;
import duoc.proyect.service.ContenidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContenidoTest {

    @Mock
    private ContenidoRepository contenidoRepository;

    @InjectMocks
    private ContenidoService contenidoService;

    private Contenido contenidoDemo;

    @BeforeEach
    void setUp() {
        contenidoDemo = new Contenido();
        contenidoDemo.setId(1);
        contenidoDemo.setTitulo("Introducción a Spring");
        contenidoDemo.setDescripcion("Contenido introductorio sobre Spring Framework");
    }

    @Nested
    class GetTests {

        @Test
        void testGetContenidos_conContenido() {
            when(contenidoRepository.findAll()).thenReturn(List.of(contenidoDemo));
            ResponseEntity<List<Contenido>> response = contenidoService.getContenidos();
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertFalse(response.getBody().isEmpty());
            verify(contenidoRepository, times(1)).findAll();
        }

        @Test
        void testGetContenidos_sinContenido() {
            when(contenidoRepository.findAll()).thenReturn(List.of());
            ResponseEntity<List<Contenido>> response = contenidoService.getContenidos();
            // Se espera que si no hay contenido el service retorne 404 y body null.
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            assertNull(response.getBody());
            verify(contenidoRepository, times(1)).findAll();
        }

        @Test
        void testGetContenidoById_existente() {
            when(contenidoRepository.findById(contenidoDemo.getId())).thenReturn(Optional.of(contenidoDemo));
            ResponseEntity<Object> response = contenidoService.getContenidoById(contenidoDemo.getId());
            assertEquals(HttpStatus.OK, response.getStatusCode());
            Contenido contenidoEncontrado = (Contenido) response.getBody();
            assertEquals("Introducción a Spring", contenidoEncontrado.getTitulo());
            assertEquals("Contenido introductorio sobre Spring Framework", contenidoEncontrado.getDescripcion());
            verify(contenidoRepository, times(1)).findById(contenidoDemo.getId());
        }

        @Test
        void testGetContenidoById_inexistente() {
            when(contenidoRepository.findById(999)).thenReturn(Optional.empty());
            ResponseEntity<Object> response = contenidoService.getContenidoById(999);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(contenidoRepository, times(1)).findById(999);
        }
    }

    @Nested
    class PostTests {

        @Test
        void testAddContenido_conflict() {
            when(contenidoRepository.existsById(contenidoDemo.getId())).thenReturn(true);
            ResponseEntity<String> response = contenidoService.addContenido(contenidoDemo);
            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            verify(contenidoRepository, never()).save(any(Contenido.class));
        }

        @Test
        void testAddContenido_creado() {
            when(contenidoRepository.existsById(contenidoDemo.getId())).thenReturn(false);
            when(contenidoRepository.save(any(Contenido.class))).thenReturn(contenidoDemo);
            ResponseEntity<String> response = contenidoService.addContenido(contenidoDemo);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals("Contenido agregado", response.getBody());  // Verificar el mensaje correcto
            verify(contenidoRepository, times(1)).existsById(contenidoDemo.getId());
            verify(contenidoRepository, times(1)).save(contenidoDemo);
        }

    }

    @Nested
    class PutTests {

        @Test
        void testUpdateContenido_existente() {
            Contenido contenidoActualizado = new Contenido();
            contenidoActualizado.setTitulo("Spring Avanzado");
            contenidoActualizado.setDescripcion("Contenido avanzado sobre Spring Framework");

            when(contenidoRepository.findById(contenidoDemo.getId())).thenReturn(Optional.of(contenidoDemo));
            // Simula el guardar devolviendo el objeto modificado.
            when(contenidoRepository.save(any(Contenido.class))).thenAnswer(invocation -> invocation.getArgument(0));

            ResponseEntity<String> response = contenidoService.updateContenido(contenidoDemo.getId(), contenidoActualizado);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.getBody().contains("Contenido actualizado: "));
            assertTrue(response.getBody().contains("Spring Avanzado"));
            verify(contenidoRepository, times(1)).findById(contenidoDemo.getId());
            verify(contenidoRepository, times(1)).save(contenidoDemo);
        }

        @Test
        void testUpdateContenido_inexistente() {
            Contenido contenidoActualizado = new Contenido();
            contenidoActualizado.setTitulo("Spring Avanzado");
            contenidoActualizado.setDescripcion("Contenido avanzado sobre Spring Framework");

            when(contenidoRepository.findById(999)).thenReturn(Optional.empty());
            ResponseEntity<String> response = contenidoService.updateContenido(999, contenidoActualizado);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            verify(contenidoRepository, times(1)).findById(999);
            verify(contenidoRepository, never()).save(any(Contenido.class));
        }
    }

    @Nested
    class DeleteTests {

        @Test
        void testDeleteContenido_existente() {
            when(contenidoRepository.existsById(contenidoDemo.getId())).thenReturn(true);
            ResponseEntity<String> response = contenidoService.deleteContenido(contenidoDemo.getId());
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            verify(contenidoRepository, times(1)).existsById(contenidoDemo.getId());
            verify(contenidoRepository, times(1)).deleteById(contenidoDemo.getId());
        }

        @Test
        void testDeleteContenido_inexistente() {
            when(contenidoRepository.existsById(999)).thenReturn(false);
            ResponseEntity<String> response = contenidoService.deleteContenido(999);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Contenido con id 999 no encontrado", response.getBody());
            verify(contenidoRepository, times(1)).existsById(999);
            verify(contenidoRepository, never()).deleteById(anyInt());
        }
    }
}