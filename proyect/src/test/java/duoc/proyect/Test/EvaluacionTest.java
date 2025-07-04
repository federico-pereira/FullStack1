package duoc.proyect.Test;

import duoc.proyect.model.Curso;
import duoc.proyect.model.Evaluacion;
import duoc.proyect.repository.CursoRepository;
import duoc.proyect.repository.EvaluacionRepository;
import duoc.proyect.service.EvaluacionService;
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
public class EvaluacionTest {

    @Mock
    private EvaluacionRepository evaluacionRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private EvaluacionService evaluacionService;

    private Evaluacion evaluacion;
    private Curso curso;

    @BeforeEach
    void setUp() {
        evaluacion = new Evaluacion();
        evaluacion.setId(1);

        curso = new Curso();
        curso.setId(1);
    }

    // Tests para métodos de Evaluacion

    @Test
    void getEvaluaciones_ConDatos_ReturnsOk() {
        List<Evaluacion> evaluaciones = new ArrayList<>();
        evaluaciones.add(evaluacion);

        when(evaluacionRepository.findAll()).thenReturn(evaluaciones);

        ResponseEntity<List<Evaluacion>> response = evaluacionService.getEvaluaciones();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getEvaluaciones_SinDatos_ReturnsNoContent() {
        when(evaluacionRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Evaluacion>> response = evaluacionService.getEvaluaciones();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void addEvaluacion_Nueva_ReturnsCreated() {
        when(evaluacionRepository.existsById(1)).thenReturn(false);
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(evaluacion);

        ResponseEntity<Object> response = evaluacionService.addEvaluacion(evaluacion);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Evaluacion creada"));
    }

    @Test
    void addEvaluacion_Existente_ReturnsConflict() {
        when(evaluacionRepository.existsById(1)).thenReturn(true);

        ResponseEntity<Object> response = evaluacionService.addEvaluacion(evaluacion);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Evaluacion ya existente", response.getBody());
    }

    @Test
    void getEvaluacionById_Existente_ReturnsOk() {
        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(evaluacion));

        ResponseEntity<Object> response = evaluacionService.getEvaluacionById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(evaluacion, response.getBody());
    }

    @Test
    void getEvaluacionById_NoExistente_ReturnsNotFound() {
        when(evaluacionRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = evaluacionService.getEvaluacionById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Evaluacion no encontrada", response.getBody());
    }

    @Test
    void deleteEvaluacion_Existente_ReturnsNoContent() {
        when(evaluacionRepository.existsById(1)).thenReturn(true);

        ResponseEntity<Object> response = evaluacionService.deleteEvaluacion(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Evaluacion eliminada", response.getBody());
        verify(evaluacionRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteEvaluacion_NoExistente_ReturnsConflict() {
        when(evaluacionRepository.existsById(1)).thenReturn(false);

        ResponseEntity<Object> response = evaluacionService.deleteEvaluacion(1);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Evaluacion no encontrada", response.getBody());
        verify(evaluacionRepository, never()).deleteById(1);
    }

    @Test
    void updateEvaluacion_Existente_ReturnsOk() {
        when(evaluacionRepository.existsById(1)).thenReturn(true);
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(evaluacion);

        ResponseEntity<Object> response = evaluacionService.updateEvaluacion(evaluacion, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Evaluacion actualizada"));
    }

    @Test
    void updateEvaluacion_NoExistente_ReturnsNotFound() {
        when(evaluacionRepository.existsById(1)).thenReturn(false);

        ResponseEntity<Object> response = evaluacionService.updateEvaluacion(evaluacion, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Evaluacion no existente en el sistema", response.getBody());
    }

    // Tests para métodos de Cursos

    @Test
    void getCursos_ConCursos_ReturnsOk() {
        List<Curso> cursos = new ArrayList<>();
        cursos.add(curso);
        evaluacion.setCursos(cursos);

        when(evaluacionRepository.existsById(1)).thenReturn(true);
        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(evaluacion));

        ResponseEntity<List<Curso>> response = evaluacionService.getCursos(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getCursos_SinCursos_ReturnsNotFound() {
        when(evaluacionRepository.existsById(1)).thenReturn(true);
        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(evaluacion));

        ResponseEntity<List<Curso>> response = evaluacionService.getCursos(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getCursos_EvaluacionNoExiste_ReturnsNotFound() {
        when(evaluacionRepository.existsById(1)).thenReturn(false);

        ResponseEntity<List<Curso>> response = evaluacionService.getCursos(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void addCurso_TodoValido_ReturnsOk() {
        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(evaluacion));
        when(cursoRepository.findById(1)).thenReturn(Optional.of(curso));
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(evaluacion);

        ResponseEntity<Object> response = evaluacionService.addCurso(1, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Curso añadido"));
        assertTrue(evaluacion.getCursos().contains(curso));
    }

    @Test
    void addCurso_EvaluacionNoExiste_ReturnsNotFound() {
        when(evaluacionRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = evaluacionService.addCurso(1, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Evaluación no encontrada", response.getBody());
    }

    @Test
    void addCurso_CursoNoExiste_ReturnsNotFound() {
        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(evaluacion));
        when(cursoRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = evaluacionService.addCurso(1, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Curso no encontrado", response.getBody());
    }

    @Test
    void addCurso_CursoYaAsociado_ReturnsConflict() {
        evaluacion.getCursos().add(curso);

        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(evaluacion));
        when(cursoRepository.findById(1)).thenReturn(Optional.of(curso));

        ResponseEntity<Object> response = evaluacionService.addCurso(1, 1);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Curso ya existente en la evaluación", response.getBody());
    }

    @Test
    void deleteCurso_TodoValido_ReturnsOk() {
        evaluacion.getCursos().add(curso);

        when(evaluacionRepository.existsById(1)).thenReturn(true);
        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(evaluacion));
        when(cursoRepository.findById(1)).thenReturn(Optional.of(curso));
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(evaluacion);

        ResponseEntity<Object> response = evaluacionService.deleteCurso(1, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Curso eliminado", response.getBody());
        assertFalse(evaluacion.getCursos().contains(curso));
    }

    @Test
    void deleteCurso_EvaluacionNoExiste_ReturnsNotFound() {
        when(evaluacionRepository.existsById(1)).thenReturn(false);

        ResponseEntity<Object> response = evaluacionService.deleteCurso(1, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Evaluacion no existe en el sistema", response.getBody());
    }

    @Test
    void deleteCurso_CursoNoExisteEnEvaluacion_ReturnsNotFound() {
        when(evaluacionRepository.existsById(1)).thenReturn(true);
        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(evaluacion));
        when(cursoRepository.findById(1)).thenReturn(Optional.of(curso));

        ResponseEntity<Object> response = evaluacionService.deleteCurso(1, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Curso no existe en la evaluacion", response.getBody());
    }
}