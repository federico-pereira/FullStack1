package duoc.proyect.Test;

import duoc.proyect.model.Profesor;
import duoc.proyect.repository.ProfesorRepository;
import duoc.proyect.service.ProfesorService;
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
public class ProfesorTest {
    @InjectMocks
    private ProfesorService profesorService;

    @Mock
    private ProfesorRepository profesorRepository;

    @Test
    @DisplayName("Obtener todos los profesores - Caso con datos")
    void testGetAll_profesor_conDatos() {
        Profesor obj1 = new Profesor();
        Profesor obj2 = new Profesor();
        when(profesorRepository.findAll()).thenReturn(List.of(obj1, obj2));

        ResponseEntity<List<Profesor>> response = profesorService.getProfesores();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @DisplayName("Obtener todos los profesores - Lista vacía")
    void testGetAll_profesor_vacio() {
        when(profesorRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Profesor>> response = profesorService.getProfesores();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Obtener profesor por ID - Existe")
    void testGetProfesorById_existe() {
        Profesor obj = new Profesor();
        obj.setId(1);
        when(profesorRepository.findById(1)).thenReturn(Optional.of(obj));

        ResponseEntity<?> response = profesorService.getProfesorById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Obtener profesor por ID - No existe")
    void testGetProfesorById_noExiste() {
        when(profesorRepository.findById(99)).thenReturn(Optional.empty());

        ResponseEntity<?> response = profesorService.getProfesorById(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Agregar profesor - Caso válido")
    void testAddProfesor_valido() {
        Profesor obj = new Profesor();
        when(profesorRepository.save(any())).thenReturn(obj);

        ResponseEntity<Profesor> response = profesorService.addProfesor(obj);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Actualizar profesor - Existe")
    void testUpdateProfesor_existe() {
        int id = 1;
        Profesor existing = new Profesor();
        Profesor updated = new Profesor();
        when(profesorRepository.findById(id)).thenReturn(Optional.of(existing));
        when(profesorRepository.save(any())).thenReturn(updated);

        ResponseEntity<Profesor> response = profesorService.updateProfesor(updated, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Actualizar profesor - No existe")
    void testUpdateProfesor_noExiste() {
        Profesor updated = new Profesor();
        when(profesorRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResponseEntity<Profesor> response = profesorService.updateProfesor(updated, 99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Eliminar profesor - Existe")
    void testDeleteProfesor_existe() {
        when(profesorRepository.existsById(1)).thenReturn(true);

        ResponseEntity<String> response = profesorService.deleteProfesor(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Eliminar profesor - No existe")
    void testDeleteProfesor_noExiste() {
        when(profesorRepository.existsById(1)).thenReturn(false);

        ResponseEntity<String> response = profesorService.deleteProfesor(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
