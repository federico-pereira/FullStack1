package duoc.proyect.service;

import duoc.proyect.model.Profesor;
import duoc.proyect.repository.ProfesorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfesorServiceTest {

    private ProfesorRepository profesorRepository;
    private ProfesorService profesorService;

    @BeforeEach
    void setUp() {
        profesorRepository = mock(ProfesorRepository.class);
        profesorService = new ProfesorService(profesorRepository);
    }

    @Test
    void testBuscarTodos() {
        Profesor profesor = new Profesor();
        profesor.setId(1L);
        profesor.setNombre("Claudia");

        when(profesorRepository.findAll()).thenReturn(List.of(profesor));

        List<Profesor> profesores = profesorService.buscarTodos();

        assertEquals(1, profesores.size());
        assertEquals("Claudia", profesores.get(0).getNombre());
    }
}
