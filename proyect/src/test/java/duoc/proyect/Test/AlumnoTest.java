package duoc.proyect.service;

import duoc.proyect.model.Alumno;
import duoc.proyect.repository.AlumnoRepository;
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
public class AlumnoServiceTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoService alumnoService;

    private Alumno alumnoDemo;

    @BeforeEach
    void setUp() {
        //voy a crear un objeto de tipo Alumno para poder probar de manera que sea mas facil
        alumnoDemo = new Alumno();
        alumnoDemo.setId(1);
        alumnoDemo.setRut("11111111-1");
        alumnoDemo.setName("Juan");
        alumnoDemo.setLastName("Pérez");
        alumnoDemo.setMail("juan@mail.com");
    }
    //cree test con contenido y otro sin, porque creo que es mas facil entender y mas completo
    @Nested
    class GetTests {
        @Test
        void testGetAlumnos_conContenido() {
            when(alumnoRepository.findAll()).thenReturn(List.of(alumnoDemo)); // list.of crea lista inmutables, es mas rapido
            ResponseEntity<List<Alumno>> response = alumnoService.getAlumnos(); // responseEntity es un objeto que controla las respuestas http (erorr 404)
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertFalse(response.getBody().isEmpty());
            verify(alumnoRepository, times(1)).findAll();
        }

        @Test
        void testGetAlumnos_sinContenido() {
            when(alumnoRepository.findAll()).thenReturn(List.of());
            ResponseEntity<List<Alumno>> response = alumnoService.getAlumnos();
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(alumnoRepository, times(1)).findAll();
        }

        @Test
        void testGetAlumnoById_existente() {
            when(alumnoRepository.findById(alumnoDemo.getId())).thenReturn(Optional.of(alumnoDemo));
            ResponseEntity<Object> response = alumnoService.getAlumnoById(alumnoDemo.getId()); //ResponseEntity<Object> es una respuesta completa de la "funcion"
            assertEquals(HttpStatus.OK, response.getStatusCode());
            Alumno alumnoEncontrado = (Alumno) response.getBody();
            assertEquals("Juan", alumnoEncontrado.getName());
            assertEquals("Pérez", alumnoEncontrado.getLastName());
            verify(alumnoRepository, times(1)).findById(alumnoDemo.getId());
        }

        @Test
        void testGetAlumnoById_inexistente() {
            when(alumnoRepository.findById(999)).thenReturn(Optional.empty());
            ResponseEntity<Object> response = alumnoService.getAlumnoById(999);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(alumnoRepository, times(1)).findById(999);
        }
    }

    @Nested
    class PostTests {
        @Test
        void testAddAlumno_conflict() {
            when(alumnoRepository.existsById(alumnoDemo.getId())).thenReturn(true);
            ResponseEntity<Object> response = alumnoService.addAlumno(alumnoDemo);
            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            verify(alumnoRepository, never()).save(any(Alumno.class));
        }

        @Test
        void testAddAlumno_creado() {
            when(alumnoRepository.existsById(alumnoDemo.getId())).thenReturn(false);
            when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumnoDemo);
            ResponseEntity<Object> response = alumnoService.addAlumno(alumnoDemo);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            Alumno alumnoGuardado = (Alumno) response.getBody();
            assertEquals("Juan", alumnoGuardado.getName());
            verify(alumnoRepository, times(1)).existsById(alumnoDemo.getId());
            verify(alumnoRepository, times(1)).save(alumnoDemo);
        }
    }

    @Nested
    class PutTests {
        @Test
        void testUpdateAlumno_existente() {
            Alumno alumnoActualizado = new Alumno();
            alumnoActualizado.setName("Carlos");
            alumnoActualizado.setLastName("Ramírez");
            alumnoActualizado.setMail("carlos@mail.com");
            // Puedes actualizar otros atributos si se desea

            when(alumnoRepository.findById(alumnoDemo.getId())).thenReturn(Optional.of(alumnoDemo));
            when(alumnoRepository.save(any(Alumno.class))).thenAnswer(invocation -> invocation.getArgument(0));

            ResponseEntity<String> response = alumnoService.updateAlumno(alumnoDemo.getId(), alumnoActualizado);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.getBody().contains("Alumno actualizado: "));
            assertTrue(response.getBody().contains("Carlos"));
            verify(alumnoRepository, times(1)).findById(alumnoDemo.getId());
            verify(alumnoRepository, times(1)).save(alumnoDemo);
        }

        @Test
        void testUpdateAlumno_inexistente() {
            Alumno alumnoActualizado = new Alumno();
            alumnoActualizado.setName("Carlos");
            alumnoActualizado.setLastName("Ramírez");
            alumnoActualizado.setMail("carlos@mail.com");

            when(alumnoRepository.findById(999)).thenReturn(Optional.empty());
            ResponseEntity<String> response = alumnoService.updateAlumno(999, alumnoActualizado);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            verify(alumnoRepository, times(1)).findById(999);
            verify(alumnoRepository, never()).save(any(Alumno.class));
        }
    }

    @Nested
    class DeleteTests {
        @Test
        void testDeleteAlumno_existente() {
            when(alumnoRepository.existsById(alumnoDemo.getId())).thenReturn(true);
            ResponseEntity<Object> response = alumnoService.deleteAlumno(alumnoDemo.getId());
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            verify(alumnoRepository, times(1)).existsById(alumnoDemo.getId());
            verify(alumnoRepository, times(1)).deleteById(alumnoDemo.getId());
        }

        @Test
        void testDeleteAlumno_inexistente() {
            when(alumnoRepository.existsById(999)).thenReturn(false);
            ResponseEntity<Object> response = alumnoService.deleteAlumno(999);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Alumno con id 999 no encontrado", response.getBody());
            verify(alumnoRepository, times(1)).existsById(999);
            verify(alumnoRepository, never()).deleteById(anyInt());
        }
    }
}