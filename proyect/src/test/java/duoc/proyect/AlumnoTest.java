package duoc.proyect.service;

import duoc.proyect.model.Alumno;
import duoc.proyect.repository.AlumnoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlumnoServiceTest {

    private AlumnoRepository alumnoRepository;
    private AlumnoService alumnoService;

    @BeforeEach
    void setUp() {
        alumnoRepository = mock(AlumnoRepository.class);
        alumnoService = new AlumnoService(alumnoRepository);
    }

    @Test
    void testBuscarTodos() {
        Alumno alumno = new Alumno();
        alumno.setId(1);
        alumno.setRut("12345678-9");

        when(alumnoRepository.findAll()).thenReturn(List.of(alumno));

        List<Alumno> alumnos = alumnoService.buscarTodos();

        assertEquals(1, alumnos.size());
        assertEquals("12345678-9", alumnos.get(0).getRut());
    }
}
