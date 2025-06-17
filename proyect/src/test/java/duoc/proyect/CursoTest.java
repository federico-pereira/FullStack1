package duoc.proyect.service;

import duoc.proyect.model.Curso;
import duoc.proyect.repository.CursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CursoServiceTest {

    private CursoRepository cursoRepository;
    private CursoService cursoService;

    @BeforeEach
    void setUp() {
        cursoRepository = mock(CursoRepository.class);
        cursoService = new CursoService(cursoRepository);
    }

    @Test
    void testBuscarTodos() {
        Curso curso = new Curso();
        curso.setId(1);
        curso.setNombre("Programación");

        when(cursoRepository.findAll()).thenReturn(List.of(curso));

        List<Curso> cursos = cursoService.buscarTodos();

        assertEquals(1, cursos.size());
        assertEquals("Programación", cursos.get(0).getNombre());
    }
}
