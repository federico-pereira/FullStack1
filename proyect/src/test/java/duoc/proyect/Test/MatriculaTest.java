package duoc.proyect.Test;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.CuponDescuento;
import duoc.proyect.model.Matricula;
import duoc.proyect.repository.MatriculaRepository;
import duoc.proyect.service.AlumnoService;
import duoc.proyect.service.CuponDescuentoService;
import duoc.proyect.service.MatriculaService;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatriculaTest {

    @Mock
    private MatriculaRepository matriculaRepository;

    @Mock
    private AlumnoService alumnoService;

    @Mock
    private CuponDescuentoService cuponDescuentoService;

    @InjectMocks
    private MatriculaService matriculaService;

    private Matricula matricula;
    private Alumno alumno;
    private CuponDescuento cupon;

    @BeforeEach
    void setUp() {
        matricula = new Matricula();
        matricula.setId(1);

        alumno = new Alumno();
        alumno.setId(1);

        cupon = new CuponDescuento();
        cupon.setId(1);
    }

    // Tests para métodos CRUD de Matricula

    @Test
    void getMatriculas_ConDatos_ReturnsOk() {
        when(matriculaRepository.findAll()).thenReturn(List.of(matricula));

        ResponseEntity<List<Matricula>> response = matriculaService.getMatriculas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getMatriculas_SinDatos_ReturnsNoContent() {
        when(matriculaRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Matricula>> response = matriculaService.getMatriculas();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getMatriculaById_Existente_ReturnsOk() {
        when(matriculaRepository.existsById(1)).thenReturn(true);
        when(matriculaRepository.findById(1)).thenReturn(Optional.of(matricula));

        ResponseEntity<Object> response = matriculaService.getMatriculaById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(matricula, response.getBody());
    }

    @Test
    void getMatriculaById_NoExistente_ReturnsNoContent() {
        when(matriculaRepository.existsById(1)).thenReturn(false);

        ResponseEntity<Object> response = matriculaService.getMatriculaById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Matricula no encontrada", response.getBody());
    }

    @Test
    void addMatricula_Success_ReturnsCreated() {
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);

        ResponseEntity<Object> response = matriculaService.addMatricula(matricula);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(matricula, response.getBody());
    }

    @Test
    void updateMatricula_Existente_ReturnsOk() {
        when(matriculaRepository.existsById(1)).thenReturn(true);
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);

        ResponseEntity<Object> response = matriculaService.updateMatricula(1, matricula);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Matricula actualizada: " + matricula, response.getBody());
    }

    @Test
    void updateMatricula_NoExistente_ReturnsNoContent() {
        when(matriculaRepository.existsById(1)).thenReturn(false);

        ResponseEntity<Object> response = matriculaService.updateMatricula(1, matricula);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Matricula no encontrada", response.getBody());
    }

    @Test
    void deleteMatricula_Existente_ReturnsOk() {
        when(matriculaRepository.existsById(1)).thenReturn(true);

        ResponseEntity<Object> response = matriculaService.deleteMatricula(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Matricula eliminada", response.getBody());
        verify(matriculaRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteMatricula_NoExistente_ReturnsNoContent() {
        when(matriculaRepository.existsById(1)).thenReturn(false);

        ResponseEntity<Object> response = matriculaService.deleteMatricula(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Matricula no encontrada", response.getBody());
        verify(matriculaRepository, never()).deleteById(1);
    }

    // Tests para métodos de Alumno

    @Test
    void addAlumno_Success_ReturnsOk() {
        // Configurar mocks
        when(matriculaRepository.findById(1)).thenReturn(Optional.of(matricula));
        when(alumnoService.getAlumnoById(1)).thenReturn(ResponseEntity.ok(alumno));
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);

        ResponseEntity<Object> response = matriculaService.addAlumno(1, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(matricula, response.getBody());
        assertEquals(alumno, matricula.getAlumno());
    }

    @Test
    void addAlumno_MatriculaNoExiste_ReturnsNoContent() {
        when(matriculaRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = matriculaService.addAlumno(1, 1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Matrícula con id 1 no encontrada", response.getBody());
    }

    @Test
    void addAlumno_AlumnoYaAsignado_ReturnsConflict() {
        matricula.setAlumno(alumno);
        when(matriculaRepository.findById(1)).thenReturn(Optional.of(matricula));

        ResponseEntity<Object> response = matriculaService.addAlumno(1, 1);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Ya hay un alumno registrado"));
    }

    @Test
    void addAlumno_AlumnoNoExiste_ReturnsNoContent() {
        when(matriculaRepository.findById(1)).thenReturn(Optional.of(matricula));
        when(alumnoService.getAlumnoById(1)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

        ResponseEntity<Object> response = matriculaService.addAlumno(1, 1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Alumno con id 1 no encontrado", response.getBody());
    }

    @Test
    void deleteAlumno_Success_ReturnsOk() {
        matricula.setAlumno(alumno);
        when(matriculaRepository.existsById(1)).thenReturn(true);
        when(matriculaRepository.findById(1)).thenReturn(Optional.of(matricula));
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);

        ResponseEntity<Object> response = matriculaService.deleteAlumno(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(matricula, response.getBody());
        assertNull(matricula.getAlumno());
    }

    @Test
    void deleteAlumno_SinAlumno_ReturnsNoContent() {
        when(matriculaRepository.existsById(1)).thenReturn(true);
        when(matriculaRepository.findById(1)).thenReturn(Optional.of(matricula));

        ResponseEntity<Object> response = matriculaService.deleteAlumno(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Matricula no tiene alumno", response.getBody());
    }

    @Test
    void deleteAlumno_MatriculaNoExiste_ReturnsNoContent() {
        when(matriculaRepository.existsById(1)).thenReturn(false);

        ResponseEntity<Object> response = matriculaService.deleteAlumno(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Alumno no encontrado", response.getBody());
    }

    // Tests para métodos de Cupón

    @Test
    void getCuponMatricula_ConCupon_ReturnsOk() {
        matricula.setCuponDescuento(cupon);
        when(matriculaRepository.existsById(1)).thenReturn(true);
        when(matriculaRepository.findById(1)).thenReturn(Optional.of(matricula));

        ResponseEntity<Object> response = matriculaService.getCuponMatricula(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cupon, response.getBody());
    }

    @Test
    void getCuponMatricula_SinCupon_ReturnsNoContent() {
        when(matriculaRepository.existsById(1)).thenReturn(true);
        when(matriculaRepository.findById(1)).thenReturn(Optional.of(matricula));

        ResponseEntity<Object> response = matriculaService.getCuponMatricula(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Matricula no tiene cupon asignado", response.getBody());
    }

    @Test
    void getCuponMatricula_MatriculaNoExiste_ReturnsNoContent() {
        when(matriculaRepository.existsById(1)).thenReturn(false);

        ResponseEntity<Object> response = matriculaService.getCuponMatricula(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Matricula no encontrada", response.getBody());
    }

    @Test
    void addCuponDescuento_Success_ReturnsOk() {
        when(matriculaRepository.existsById(1)).thenReturn(true);
        when(matriculaRepository.findById(1)).thenReturn(Optional.of(matricula));
        when(cuponDescuentoService.getCuponDescuentoById(1))
                .thenReturn(ResponseEntity.ok(cupon));
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);

        ResponseEntity<Object> response = matriculaService.addCuponDescuento(1, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(matricula, response.getBody());
        assertEquals(cupon, matricula.getCuponDescuento());
    }

    @Test
    void addCuponDescuento_MatriculaNoExiste_ReturnsNoContent() {
        when(matriculaRepository.existsById(1)).thenReturn(false);

        ResponseEntity<Object> response = matriculaService.addCuponDescuento(1, 1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Matricula no encontrada", response.getBody());
    }

    @Test
    void addCuponDescuento_ConCuponExistente_ReturnsNoContent() {
        matricula.setCuponDescuento(cupon);
        when(matriculaRepository.existsById(1)).thenReturn(true);
        when(matriculaRepository.findById(1)).thenReturn(Optional.of(matricula));

        ResponseEntity<Object> response = matriculaService.addCuponDescuento(1, 1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Matricula ta tiene cupon, elimine el existente primero", response.getBody());
    }

    @Test
    void deleteCuponDescuento_Success_ReturnsOk() {
        matricula.setCuponDescuento(cupon);
        when(matriculaRepository.existsById(1)).thenReturn(true);
        when(matriculaRepository.findById(1)).thenReturn(Optional.of(matricula));
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);

        ResponseEntity<Object> response = matriculaService.deleteCuponDescuento(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(matricula, response.getBody());
        assertNull(matricula.getCuponDescuento());
    }

    @Test
    void deleteCuponDescuento_SinCupon_ReturnsNoContent() {
        when(matriculaRepository.existsById(1)).thenReturn(true);
        when(matriculaRepository.findById(1)).thenReturn(Optional.of(matricula));

        ResponseEntity<Object> response = matriculaService.deleteCuponDescuento(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Matricula no tiene cupon", response.getBody());
    }

    @Test
    void deleteCuponDescuento_MatriculaNoExiste_ReturnsNoContent() {
        when(matriculaRepository.existsById(1)).thenReturn(false);

        ResponseEntity<Object> response = matriculaService.deleteCuponDescuento(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Matricula no encontrada", response.getBody());
    }
}