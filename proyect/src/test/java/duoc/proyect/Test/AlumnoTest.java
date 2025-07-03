package duoc.proyect.Test;

import duoc.proyect.model.Alumno;
import duoc.proyect.repository.AlumnoRepository;
import duoc.proyect.service.AlumnoService;
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
public class AlumnoTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoService alumnoService;

    @Test
    @DisplayName("Obtener todos los alumnos - Caso con datos")
    void testGetAlumnos_conDatos() {
        Alumno alumno1 = new Alumno();
        alumno1.setId(1);
        alumno1.setName("Juan");

        Alumno alumno2 = new Alumno();
        alumno2.setId(2);
        alumno2.setName("Ana");

        when(alumnoRepository.findAll()).thenReturn(List.of(alumno1, alumno2));

        ResponseEntity<List<Alumno>> response = alumnoService.getAlumnos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @DisplayName("Obtener todos los alumnos - Lista vacía")
    void testGetAlumnos_vacio() {
        when(alumnoRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Alumno>> response = alumnoService.getAlumnos();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Obtener alumno por ID - Existe")
    void testGetAlumnoById_existe() {
        Alumno alumno = new Alumno();
        alumno.setId(1);
        alumno.setName("Pedro");

        when(alumnoRepository.findById(1)).thenReturn(Optional.of(alumno));

        ResponseEntity<Alumno> response = alumnoService.getAlumnoById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Pedro", response.getBody().getName());
    }

    @Test
    @DisplayName("Obtener alumno por ID - No existe")
    void testGetAlumnoById_noExiste() {
        when(alumnoRepository.findById(99)).thenReturn(Optional.empty());

        ResponseEntity<Alumno> response = alumnoService.getAlumnoById(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Agregar alumno - Caso válido")
    void testAddAlumno_valido() {
        Alumno alumno = new Alumno();
        alumno.setName("Laura");
        alumno.setRut("11.111.111-1");

        when(alumnoRepository.existsByRut(alumno.getRut())).thenReturn(false);
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumno);

        ResponseEntity<Object> response = alumnoService.addAlumno(alumno);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof Alumno);
        verify(alumnoRepository).save(any(Alumno.class));
    }

    @Test
    @DisplayName("Agregar alumno - Rut duplicado")
    void testAddAlumno_duplicado() {
        Alumno alumno = new Alumno();
        alumno.setName("Laura");
        alumno.setRut("11.111.111-1");

        when(alumnoRepository.existsByRut(alumno.getRut())).thenReturn(true);

        ResponseEntity<Object> response = alumnoService.addAlumno(alumno);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Ya existe un alumno"));
        verify(alumnoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Actualizar alumno - Existe")
    void testUpdateAlumno_existe() {
        int id = 1;
        Alumno existente = new Alumno();
        existente.setId(id);
        existente.setName("Old Name");

        Alumno datos = new Alumno();
        datos.setName("New Name");
        datos.setMail("new@mail.com");
        datos.setRut("11.111.111-1");

        when(alumnoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(alumnoRepository.save(any(Alumno.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Alumno> response = alumnoService.updateAlumno(id, datos);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("New Name", response.getBody().getName());
        assertEquals("new@mail.com", response.getBody().getMail());
        verify(alumnoRepository).save(any(Alumno.class));
    }

    @Test
    @DisplayName("Actualizar alumno - No existe")
    void testUpdateAlumno_noExiste() {
        int id = 99;
        Alumno datos = new Alumno();
        datos.setName("Nuevo Nombre");

        when(alumnoRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Alumno> response = alumnoService.updateAlumno(id, datos);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(alumnoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Eliminar alumno - Existe")
    void testDeleteAlumno_existe() {
        int id = 1;

        when(alumnoRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = alumnoService.deleteAlumno(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(alumnoRepository).deleteById(id);
    }

    @Test
    @DisplayName("Eliminar alumno - No existe")
    void testDeleteAlumno_noExiste() {
        int id = 99;

        when(alumnoRepository.existsById(id)).thenReturn(false);

        ResponseEntity<Void> response = alumnoService.deleteAlumno(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(alumnoRepository, never()).deleteById(anyInt());
    }
}
