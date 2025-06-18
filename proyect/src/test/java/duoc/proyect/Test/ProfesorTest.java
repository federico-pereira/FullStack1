package duoc.proyect.Test;

import duoc.proyect.model.Profesor;
import duoc.proyect.repository.ProfesorRepository;
import duoc.proyect.service.ProfesorService;
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
public class ProfesorTest {

    @Mock
    private ProfesorRepository profesorRepository;

    @InjectMocks
    private ProfesorService profesorService;

    private Profesor profesorDemo;

    @BeforeEach
    void setUp() {
        profesorDemo = new Profesor();
        profesorDemo.setId(1);
        profesorDemo.setRut("22222222-2");
        profesorDemo.setName("María");
        profesorDemo.setLastName("García");
        profesorDemo.setMail("maria@mail.com");
    }

    @Nested
    class GetTests {
        @Test
        void testGetProfesores_conContenido() {
            when(profesorRepository.findAll()).thenReturn(List.of(profesorDemo));
            ResponseEntity<List<Profesor>> response = profesorService.getProfesores();
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertFalse(response.getBody().isEmpty());
            verify(profesorRepository, times(1)).findAll();
        }

        @Test
        void testGetProfesores_sinContenido() {
            when(profesorRepository.findAll()).thenReturn(List.of());
            ResponseEntity<List<Profesor>> response = profesorService.getProfesores();
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(profesorRepository, times(1)).findAll();
        }

        @Test
        void testGetProfesorById_existente() {
            when(profesorRepository.findById(profesorDemo.getId())).thenReturn(Optional.of(profesorDemo));
            ResponseEntity<Object> response = profesorService.getProfesorById(profesorDemo.getId());
            assertEquals(HttpStatus.OK, response.getStatusCode());
            Profesor profesorEncontrado = (Profesor) response.getBody();
            assertEquals("María", profesorEncontrado.getName());
            assertEquals("García", profesorEncontrado.getLastName());
            verify(profesorRepository, times(1)).findById(profesorDemo.getId());
        }

        @Test
        void testGetProfesorById_inexistente() {
            when(profesorRepository.findById(999)).thenReturn(Optional.empty());
            ResponseEntity<Object> response = profesorService.getProfesorById(999);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(profesorRepository, times(1)).findById(999);
        }
    }

    @Nested
    class PostTests {
        @Test
        void testAddProfesor_conflict() {
            when(profesorRepository.existsById(profesorDemo.getId())).thenReturn(true);
            ResponseEntity<String> response = profesorService.addProfesor(profesorDemo);
            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            verify(profesorRepository, never()).save(any(Profesor.class));
        }

        @Test
        void testAddProfesor_creado() {
                when(profesorRepository.existsById(profesorDemo.getId())).thenReturn(false);
                when(profesorRepository.save(any(Profesor.class))).thenReturn(profesorDemo);
                ResponseEntity<String> response = profesorService.addProfesor(profesorDemo);
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                verify(profesorRepository, times(1)).existsById(profesorDemo.getId());
                verify(profesorRepository, times(1)).save(profesorDemo);
            }
    }

    @Nested
    class PutTests {
        @Test
        void testUpdateProfesor_existente() {
            Profesor profesorActualizado = new Profesor();
            profesorActualizado.setName("Luisa");
            profesorActualizado.setLastName("Fernández");
            profesorActualizado.setMail("luisa@mail.com");


            when(profesorRepository.findById(profesorDemo.getId())).thenReturn(Optional.of(profesorDemo));
            when(profesorRepository.save(any(Profesor.class))).thenAnswer(invocation -> invocation.getArgument(0));

            ResponseEntity<String> response = profesorService.updateProfesor(profesorActualizado.getId(), profesorDemo.getId());
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.getBody().contains("Profesor actualizado: "));
            assertTrue(response.getBody().contains("Luisa"));
            verify(profesorRepository, times(1)).findById(profesorDemo.getId());
            verify(profesorRepository, times(1)).save(profesorDemo);
        }

        @Test
        void testUpdateProfesor_inexistente() {
            Profesor profesorActualizado = new Profesor();
            profesorActualizado.setName("Luisa");
            profesorActualizado.setLastName("Fernández");
            profesorActualizado.setMail("luisa@mail.com");

            when(profesorRepository.findById(999)).thenReturn(Optional.empty());
            ResponseEntity<String> response = profesorService.updateProfesor(999, profesorActualizado.getId());
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            verify(profesorRepository, times(1)).findById(999);
            verify(profesorRepository, never()).save(any(Profesor.class));
        }
    }

    @Nested
    class DeleteTests {
        @Test
        void testDeleteProfesor_existente() {
            when(profesorRepository.existsById(profesorDemo.getId())).thenReturn(true);
            ResponseEntity<String> response = profesorService.deleteProfesor(profesorDemo.getId());
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            verify(profesorRepository, times(1)).existsById(profesorDemo.getId());
            verify(profesorRepository, times(1)).deleteById(profesorDemo.getId());
        }

        @Test
        void testDeleteProfesor_inexistente() {
            when(profesorRepository.existsById(999)).thenReturn(false);
            ResponseEntity<String> response = profesorService.deleteProfesor(999);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Profesor con id 999 no encontrado", response.getBody());
            verify(profesorRepository, times(1)).existsById(999);
            verify(profesorRepository, never()).deleteById(anyInt());
        }
    }
}