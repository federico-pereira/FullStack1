package duoc.proyect.Test;

import duoc.proyect.model.Curso;
import duoc.proyect.repository.CursoRepository;
import duoc.proyect.service.CursoService;
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
public class CursoTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoService cursoService;

    private Curso cursoDemo;

    @BeforeEach
    void setUp() {
        cursoDemo = new Curso();
        cursoDemo.setId(1);
        cursoDemo.setName("Programación Java");
    }

    @Nested
    class GetTests {
        @Test
        void testGetCursos_conContenido() {
            when(cursoRepository.findAll()).thenReturn(List.of(cursoDemo));
            ResponseEntity<List<Curso>> response = cursoService.getCursos();
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertFalse(response.getBody().isEmpty());
            verify(cursoRepository, times(1)).findAll();
        }

        @Test
        void testGetCursos_sinContenido() {
            when(cursoRepository.findAll()).thenReturn(List.of());
            ResponseEntity<List<Curso>> response = cursoService.getCursos();
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(cursoRepository, times(1)).findAll();
        }

        @Test
        void testGetCursoById_existente() {
            when(cursoRepository.findById(cursoDemo.getId())).thenReturn(Optional.of(cursoDemo));
            ResponseEntity<Object> response = cursoService.getCursoById(cursoDemo.getId());
            assertEquals(HttpStatus.OK, response.getStatusCode());
            Curso cursoEncontrado = (Curso) response.getBody();
            assertEquals("Programación Java", cursoEncontrado.getName());
            verify(cursoRepository, times(1)).findById(cursoDemo.getId());
        }

        @Test
        void testGetCursoById_inexistente() {
            when(cursoRepository.findById(999)).thenReturn(Optional.empty());
            ResponseEntity<Object> response = cursoService.getCursoById(999);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(cursoRepository, times(1)).findById(999);
        }
    }

    @Nested
    class PostTests {
        @Test
        void testAddCurso_conflict() {
            when(cursoRepository.existsById(cursoDemo.getId())).thenReturn(true);
            ResponseEntity<Object> response = cursoService.addCurso(cursoDemo);
            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            verify(cursoRepository, never()).save(any(Curso.class));
        }

        @Test
        void testAddCurso_creado() {
            when(cursoRepository.existsById(cursoDemo.getId())).thenReturn(false);
            when(cursoRepository.save(any(Curso.class))).thenReturn(cursoDemo);
            ResponseEntity<Object> response = cursoService.addCurso(cursoDemo);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            Curso cursoGuardado = (Curso) response.getBody();
            assertEquals("Programación Java", cursoGuardado.getName());
            verify(cursoRepository, times(1)).existsById(cursoDemo.getId());
            verify(cursoRepository, times(1)).save(cursoDemo);
        }
    }

    @Nested
    class PutTests {
        @Test
        void testUpdateCurso_existente() {
            Curso cursoActualizado = new Curso();
            cursoActualizado.setName("Programación Avanzada Java");

            when(cursoRepository.findById(cursoDemo.getId())).thenReturn(Optional.of(cursoDemo));
            when(cursoRepository.save(any(Curso.class))).thenAnswer(invocation -> invocation.getArgument(0));

            ResponseEntity<String> response = cursoService.updateCurso(cursoDemo.getId(), cursoActualizado.getId());
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.getBody().contains("Curso actualizado: "));
            assertTrue(response.getBody().contains("Programación Avanzada Java"));
            verify(cursoRepository, times(1)).findById(cursoDemo.getId());
            verify(cursoRepository, times(1)).save(cursoDemo);
        }

        @Test
        void testUpdateCurso_inexistente() {
            Curso cursoActualizado = new Curso();
            cursoActualizado.setName("Programación Avanzada Java");

            when(cursoRepository.findById(999)).thenReturn(Optional.empty());
            ResponseEntity<String> response = cursoService.updateCurso(999, cursoActualizado.getId());
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            verify(cursoRepository, times(1)).findById(999);
            verify(cursoRepository, never()).save(any(Curso.class));
        }
    }

    @Nested
    class DeleteTests {
        @Test
        void testDeleteCurso_existente() {
            when(cursoRepository.existsById(cursoDemo.getId())).thenReturn(true);
            ResponseEntity<String> response = cursoService.deleteCurso(cursoDemo.getId());
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            verify(cursoRepository, times(1)).existsById(cursoDemo.getId());
            verify(cursoRepository, times(1)).deleteById(cursoDemo.getId());
        }

        @Test
        void testDeleteCurso_inexistente() {
            when(cursoRepository.existsById(999)).thenReturn(false);
            ResponseEntity<String> response = cursoService.deleteCurso(999);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Curso con id 999 no encontrado", response.getBody());
            verify(cursoRepository, times(1)).existsById(999);
            verify(cursoRepository, never()).deleteById(anyInt());
        }
    }
}