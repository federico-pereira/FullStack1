package duoc.proyect.Test;

import duoc.proyect.model.DetalleEvaluacion;
import duoc.proyect.model.Evaluacion;
import duoc.proyect.model.Alumno;
import duoc.proyect.repository.DetalleEvaluacionRepository;
import duoc.proyect.service.DetalleEvaluacionService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetalleEvaluacionTest {

    @Mock
    private DetalleEvaluacionRepository detalleRepository;

    @InjectMocks
    private DetalleEvaluacionService detalleService;

    // Helper para crear objetos de prueba
    private DetalleEvaluacion crearDetalle(int id, float nota) {
        DetalleEvaluacion detalle = new DetalleEvaluacion();
        detalle.setId(id);
        detalle.setNotaAlumno(nota);

        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setId(100 + id);
        detalle.setEvaluacion(evaluacion);

        Alumno alumno = new Alumno();
        alumno.setId(200 + id);
        detalle.setAlumno(alumno);

        return detalle;
    }

    @Test
    void getAllDetalles_conDatos() {
        // 1. Preparar datos
        DetalleEvaluacion detalle1 = crearDetalle(1, 5.5f);
        DetalleEvaluacion detalle2 = crearDetalle(2, 6.2f);
        List<DetalleEvaluacion> detalles = List.of(detalle1, detalle2);

        // 2. Configurar mock
        when(detalleRepository.findAll()).thenReturn(detalles);

        // 3. Ejecutar
        ResponseEntity<List<DetalleEvaluacion>> response = detalleService.getAllDetalles();

        // 4. Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(5.5f, response.getBody().get(0).getNotaAlumno());
        assertEquals(6.2f, response.getBody().get(1).getNotaAlumno());
    }

    @Test
    void getAllDetalles_sinDatos() {
        // 1. Configurar mock
        when(detalleRepository.findAll()).thenReturn(Collections.emptyList());

        // 2. Ejecutar
        ResponseEntity<List<DetalleEvaluacion>> response = detalleService.getAllDetalles();

        // 3. Verificar
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getDetalleById_existente() {
        // 1. Preparar datos
        DetalleEvaluacion detalle = crearDetalle(1, 4.3f);

        // 2. Configurar mock
        when(detalleRepository.findById(1)).thenReturn(Optional.of(detalle));

        // 3. Ejecutar
        ResponseEntity<DetalleEvaluacion> response = detalleService.getDetalleById(1);

        // 4. Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals(4.3f, response.getBody().getNotaAlumno());
    }

    @Test
    void getDetalleById_inexistente() {
        // 1. Configurar mock
        when(detalleRepository.findById(99)).thenReturn(Optional.empty());

        // 2. Ejecutar
        ResponseEntity<DetalleEvaluacion> response = detalleService.getDetalleById(99);

        // 3. Verificar
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void addDetalle_valido() {
        // 1. Preparar datos
        DetalleEvaluacion nuevoDetalle = crearDetalle(0, 5.0f); // ID 0 para nuevo

        // 2. Configurar mock
        when(detalleRepository.existsById(0)).thenReturn(false);
        when(detalleRepository.save(nuevoDetalle)).thenAnswer(inv -> {
            DetalleEvaluacion d = inv.getArgument(0);
            d.setId(1); // Simular ID generado
            return d;
        });

        // 3. Ejecutar
        ResponseEntity<DetalleEvaluacion> response = detalleService.addDetalle(nuevoDetalle);

        // 4. Verificar
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        verify(detalleRepository).save(nuevoDetalle);
    }

    @Test
    void addDetalle_conflictoIdExistente() {
        // 1. Preparar datos
        DetalleEvaluacion detalleExistente = crearDetalle(1, 6.0f);

        // 2. Configurar mock
        when(detalleRepository.existsById(1)).thenReturn(true);

        // 3. Ejecutar
        ResponseEntity<DetalleEvaluacion> response = detalleService.addDetalle(detalleExistente);

        // 4. Verificar
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        verify(detalleRepository, never()).save(any());
    }

    @Test
    void updateDetalle_exitoso() {
        // 1. Preparar datos
        DetalleEvaluacion detalleExistente = crearDetalle(1, 4.0f);
        DetalleEvaluacion datosActualizados = crearDetalle(1, 6.5f); // Nueva nota

        // 2. Configurar mock
        when(detalleRepository.existsById(1)).thenReturn(true);
        when(detalleRepository.save(datosActualizados)).thenReturn(datosActualizados);

        // 3. Ejecutar
        ResponseEntity<DetalleEvaluacion> response = detalleService.updateDetalle(1, datosActualizados);

        // 4. Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals(6.5f, response.getBody().getNotaAlumno());
    }

    @Test
    void updateDetalle_noEncontrado() {
        // 1. Preparar datos
        DetalleEvaluacion datosActualizados = crearDetalle(99, 5.0f);

        // 2. Configurar mock
        when(detalleRepository.existsById(99)).thenReturn(false);

        // 3. Ejecutar
        ResponseEntity<DetalleEvaluacion> response = detalleService.updateDetalle(99, datosActualizados);

        // 4. Verificar
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(detalleRepository, never()).save(any());
    }

    @Test
    void deleteDetalle_exitoso() {
        // 1. Configurar mock
        when(detalleRepository.existsById(1)).thenReturn(true);

        // 2. Ejecutar
        ResponseEntity<Void> response = detalleService.deleteDetalle(1);

        // 3. Verificar
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(detalleRepository).deleteById(1);
    }

    @Test
    void deleteDetalle_noEncontrado() {
        // 1. Configurar mock
        when(detalleRepository.existsById(99)).thenReturn(false);

        // 2. Ejecutar
        ResponseEntity<Void> response = detalleService.deleteDetalle(99);

        // 3. Verificar
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(detalleRepository, never()).deleteById(anyInt());
    }

    @Test
    void getDetallesByEvaluacion_conResultados() {
        // 1. Preparar datos
        DetalleEvaluacion detalle1 = crearDetalle(1, 5.5f);
        DetalleEvaluacion detalle2 = crearDetalle(2, 6.0f);
        List<DetalleEvaluacion> detalles = List.of(detalle1, detalle2);

        // 2. Configurar mock
        when(detalleRepository.findByEvaluacionId(100)).thenReturn(detalles);

        // 3. Ejecutar
        ResponseEntity<List<DetalleEvaluacion>> response = detalleService.getDetallesByEvaluacion(100);

        // 4. Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getDetallesByEvaluacion_sinResultados() {
        // 1. Configurar mock
        when(detalleRepository.findByEvaluacionId(999)).thenReturn(Collections.emptyList());

        // 2. Ejecutar
        ResponseEntity<List<DetalleEvaluacion>> response = detalleService.getDetallesByEvaluacion(999);

        // 3. Verificar
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getDetallesByAlumno_conResultados() {
        // 1. Preparar datos
        DetalleEvaluacion detalle1 = crearDetalle(1, 4.5f);
        DetalleEvaluacion detalle2 = crearDetalle(2, 5.0f);
        List<DetalleEvaluacion> detalles = List.of(detalle1, detalle2);

        // 2. Configurar mock
        when(detalleRepository.findByAlumnoId(201)).thenReturn(detalles);

        // 3. Ejecutar
        ResponseEntity<List<DetalleEvaluacion>> response = detalleService.getDetallesByAlumno(201);

        // 4. Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getDetallesByAlumno_sinResultados() {
        // 1. Configurar mock
        when(detalleRepository.findByAlumnoId(999)).thenReturn(Collections.emptyList());

        // 2. Ejecutar
        ResponseEntity<List<DetalleEvaluacion>> response = detalleService.getDetallesByAlumno(999);

        // 3. Verificar
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}