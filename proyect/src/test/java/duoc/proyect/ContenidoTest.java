package duoc.proyect.service;

import duoc.proyect.model.Contenido;
import duoc.proyect.repository.ContenidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContenidoServiceTest {

    private ContenidoRepository contenidoRepository;
    private ContenidoService contenidoService;

    @BeforeEach
    void setUp() {
        contenidoRepository = mock(ContenidoRepository.class);
        contenidoService = new ContenidoService(contenidoRepository);
    }

    @Test
    void testBuscarTodos() {
        Contenido contenido = new Contenido();
        contenido.setId(1);
        contenido.setTitulo("Clase 1");

        when(contenidoRepository.findAll()).thenReturn(List.of(contenido));

        List<Contenido> resultado = contenidoService.buscarTodos();

        assertEquals(1, resultado.size());
        assertEquals("Clase 1", resultado.get(0).getTitulo());
    }
}
