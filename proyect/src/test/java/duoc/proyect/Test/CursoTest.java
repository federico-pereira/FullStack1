package duoc.proyect.Test;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Contenido;
import duoc.proyect.model.Curso;
import duoc.proyect.repository.CursoRepository;
import duoc.proyect.service.AlumnoService;
import duoc.proyect.service.ContenidoService;
import duoc.proyect.service.CursoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CursoTest {

    @InjectMocks
    private CursoService cursoService;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private AlumnoService alumnoService;

    @Mock
    private ContenidoService contenidoService;

    @Test
    @DisplayName("Obtener todos los cursos - Con datos")
    void testGetCursos_conDatos() {
        Curso curso1 = new Curso();
        curso1.setId(1);
        curso1.setName("Java");

        when(cursoRepository.findAll()).thenReturn(List.of(curso1));

        ResponseEntity<List<Curso>> response = cursoService.getCursos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("Obtener todos los cursos - Sin datos")
    void testGetCursos_vacio() {
        when(cursoRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Curso>> response = cursoService.getCursos();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Agregar curso - Nuevo")
    void testAddCurso_valido() {
        Curso curso = new Curso();
        curso.setName("Spring Boot");

        when(cursoRepository.existsByName("Spring Boot")).thenReturn(false);
        when(cursoRepository.save(curso)).thenReturn(curso);

        Curso result = cursoService.addCurso(curso);

        assertEquals("Spring Boot", result.getName());
        verify(cursoRepository).save(curso);
    }

    @Test
    @DisplayName("Agregar curso - Nombre duplicado")
    void testAddCurso_duplicado() {
        Curso curso = new Curso();
        curso.setName("Spring Boot");

        when(cursoRepository.existsByName("Spring Boot")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> cursoService.addCurso(curso));
        verify(cursoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Obtener curso por ID - Existe")
    void testGetCursoById_existe() {
        Curso curso = new Curso();
        curso.setId(1);
        curso.setName("Java");

        when(cursoRepository.existsById(1)).thenReturn(true);
        when(cursoRepository.findById(1)).thenReturn(Optional.of(curso));

        ResponseEntity<Object> response = cursoService.getCursoById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Java", ((Curso)response.getBody()).getName());
    }

    @Test
    @DisplayName("Eliminar curso - Existe")
    void testDeleteCurso_existe() {
        when(cursoRepository.existsById(1)).thenReturn(true);

        ResponseEntity<String> response = cursoService.deleteCurso(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Curso eliminado"));
    }

    @Test
    @DisplayName("Actualizar curso - Existe")
    void testUpdateCurso_existe() {
        Curso curso = new Curso();
        curso.setId(1);
        curso.setName("Updated");

        when(cursoRepository.existsById(1)).thenReturn(true);
        when(cursoRepository.save(curso)).thenReturn(curso);

        ResponseEntity<String> response = cursoService.updateCurso(curso, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Agregar alumno a curso - Válido")
    void testAddAlumno_valido() {
        int idCurso = 1;
        int idAlumno = 10;

        Alumno alumno = new Alumno();
        alumno.setId(idAlumno);
        Curso curso = new Curso();
        curso.setId(idCurso);
        curso.setListaCurso(new ArrayList<>());

        when(alumnoService.getAlumnoById(idAlumno)).thenReturn(ResponseEntity.ok(alumno));
        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));
        when(cursoRepository.save(any())).thenReturn(curso);

        ResponseEntity<String> response = cursoService.addAlumno(idCurso, idAlumno);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("agregado al curso"));
    }

    @Test
    @DisplayName("Eliminar alumno del curso - Válido")
    void testDeleteAlumno_valido() {
        int idCurso = 1;
        int idAlumno = 10;

        Alumno alumno = new Alumno();
        alumno.setId(idAlumno);
        Curso curso = new Curso();
        curso.setId(idCurso);
        curso.setListaCurso(new ArrayList<>(List.of(alumno)));

        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));
        when(alumnoService.getAlumnoById(idAlumno)).thenReturn(ResponseEntity.ok(alumno));

        ResponseEntity<String> response = cursoService.deleteAlumno(idAlumno, idCurso);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Alumno eliminado"));
    }
}
