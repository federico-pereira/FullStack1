package duoc.proyect.Test;

import duoc.proyect.model.CuponDescuento;
import duoc.proyect.repository.CuponDescuentoRepository;
import duoc.proyect.service.CuponDescuentoService;
import jakarta.validation.ConstraintViolationException;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CuponDescuentoTest {
    
    @InjectMocks
    private CuponDescuentoService cuponDescuentoService;

    @Mock
    private CuponDescuentoRepository cuponDescuentoRepository;

    @Test
    @DisplayName("Obtener todos los cupones - Caso con datos existentes")
    void testGetCuponesDescuento_valido() {
        // 1. Preparar datos de prueba
        CuponDescuento cupon1 = new CuponDescuento();
        cupon1.setId(1);
        cupon1.setDescuento(15);

        CuponDescuento cupon2 = new CuponDescuento();
        cupon2.setId(2);
        cupon2.setDescuento(20);

        List<CuponDescuento> cuponesMock = List.of(cupon1, cupon2);

        // 2. Configurar comportamiento mock
        when(cuponDescuentoRepository.findAll()).thenReturn(cuponesMock);

        // 3. Ejecutar
        ResponseEntity<List<CuponDescuento>> resultado = cuponDescuentoService.getCuponesDescuento();

        // 4. Verificaciones
        assertNotNull(resultado, "La lista no debe ser nula");
        assertEquals(2, resultado.getBody().size(), "Debe retornar 2 cupones");
    }

    @Test
    @DisplayName("Obtener todos los cupones - Caso sin datos")
    void testGetCuponesDescuento_vacio() {
        // 1. Configurar comportamiento mock para lista vacía
        when(cuponDescuentoRepository.findAll()).thenReturn(Collections.emptyList());

        // 2. Ejecutar
        ResponseEntity<List<CuponDescuento>> resultado = cuponDescuentoService.getCuponesDescuento();

        // 3. Verificaciones
        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode(), "Debe ser estado 204 No Content");
        assertNull(resultado.getBody(), "El cuerpo debe ser nulo");
        assertTrue(resultado.getHeaders().isEmpty(), "Los headers no deben contener elementos");
    }

    @Test
    @DisplayName("Obtener cupon por id - Caso valido")
    void testGetCuponDescuentoById_valido() {
        CuponDescuento sampleCupon = new CuponDescuento();
        sampleCupon.setId(1);
        sampleCupon.setDescuento(10);

        when(cuponDescuentoRepository.findById(1)).thenReturn(Optional.of(sampleCupon));

        ResponseEntity resultado = cuponDescuentoService.getCuponDescuentoById(1);

        assertNotNull(resultado);
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
    }

    @Test
    @DisplayName("Obtener cupon por id - Caso invalido")
    void testGetCuponDescuentoById_invalido() {

        when(cuponDescuentoRepository.findById(99)).thenReturn(Optional.empty());

        ResponseEntity resultado = cuponDescuentoService.getCuponDescuentoById(99);

        assertNotNull(resultado);
        assertEquals(HttpStatus.NOT_FOUND, resultado.getStatusCode());
    }

    @Test
    @DisplayName("Agregar un cupon - Caso válido")
    void testAddCuponDescuento_valido() {
        // 1. Preparar datos de prueba
        CuponDescuento nuevoCupon = new CuponDescuento();
        nuevoCupon.setDescuento(15);

        // 2. Configurar comportamiento mock
        when(cuponDescuentoRepository.save(nuevoCupon)).thenReturn(nuevoCupon);

        // 3. Ejecutar
        ResponseEntity resultado = cuponDescuentoService.addCuponDescuento(nuevoCupon);

        // 4. Verificaciones
        assertNotNull(resultado);
        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
    }

    @Test
    @DisplayName("Agregar cupón con ID existente - Debe dar conflicto")
    void testAddCuponDescuento_idExistente() {
        // 1. Preparar cupón con ID existente
        CuponDescuento cuponExistente = new CuponDescuento();
        cuponExistente.setId(1);
        cuponExistente.setDescuento(15);

        // 2. Configurar comportamiento mock
        when(cuponDescuentoRepository.existsById(1)).thenReturn(true);

        // 3. Ejecutar método a probar
        ResponseEntity<CuponDescuento> response = cuponDescuentoService.addCuponDescuento(cuponExistente);

        // 4. Verificaciones
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode(), "Debe ser estado 409 Conflict");
        assertNotNull(response.getBody(), "El cuerpo debe contener el cupón conflictivo");
        assertEquals(1, response.getBody().getId(), "Debe tener el mismo ID");

        // 5. Verificar que NO se llamó a save()
        verify(cuponDescuentoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Actualizar cupón - Caso exitoso")
    void testUpdateCuponDescuento_exitoso() {
        // 1. Preparar datos
        int id = 1;
        CuponDescuento cuponExistente = new CuponDescuento();
        cuponExistente.setId(id);
        cuponExistente.setDescuento(10);

        CuponDescuento updateData = new CuponDescuento();
        updateData.setDescuento(20); // Nuevo valor

        // 2. Configurar mock
        when(cuponDescuentoRepository.existsById(id)).thenReturn(true);
        when(cuponDescuentoRepository.save(any())).thenAnswer(inv -> {
            CuponDescuento c = inv.getArgument(0);
            return c; // Retorna el mismo objeto actualizado
        });

        // 3. Ejecutar
        ResponseEntity<CuponDescuento> response = cuponDescuentoService.updateCuponDescuento(id, updateData);

        // 4. Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Debe ser estado 200 OK");
        assertNotNull(response.getBody(), "El cuerpo no debe ser nulo");
        assertEquals(id, response.getBody().getId(), "El ID debe mantenerse");
        assertEquals(20, response.getBody().getDescuento(), "El descuento debe actualizarse");
    }

    @Test
    @DisplayName("Actualizar cupón - No encontrado")
    void testUpdateCuponDescuento_noEncontrado() {
        // 1. Preparar datos
        int id = 99; // ID inexistente
        CuponDescuento updateData = new CuponDescuento();
        updateData.setDescuento(30);

        // 2. Configurar mock
        when(cuponDescuentoRepository.existsById(id)).thenReturn(false);

        // 3. Ejecutar
        ResponseEntity<CuponDescuento> response = cuponDescuentoService.updateCuponDescuento(id, updateData);

        // 4. Verificaciones
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Debe ser estado 404 Not Found");
        assertNull(response.getBody(), "El cuerpo debe ser nulo");
    }

    @Test
    @DisplayName("Buscar cupones por descuento - Con resultados")
    void testFindByDescuento_conResultados() {
        // 1. Preparar datos
        int descuento = 15;
        CuponDescuento cupon1 = new CuponDescuento();
        cupon1.setId(1);
        cupon1.setDescuento(descuento);

        CuponDescuento cupon2 = new CuponDescuento();
        cupon2.setId(2);
        cupon2.setDescuento(descuento);

        // 2. Configurar mock
        when(cuponDescuentoRepository.findByDescuento(descuento)).thenReturn(List.of(cupon1, cupon2));

        // 3. Ejecutar
        ResponseEntity<List<CuponDescuento>> response = cuponDescuentoService.findByDescuento(descuento);

        // 4. Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Debe ser estado 200 OK");
        assertNotNull(response.getBody(), "El cuerpo no debe ser nulo");
        assertEquals(2, response.getBody().size(), "Debe tener 2 cupones");
        assertTrue(response.getBody().stream().allMatch(c -> c.getDescuento() == descuento),
                "Todos los cupones deben tener el descuento buscado");
    }

    @Test
    @DisplayName("Buscar cupones por descuento - Sin resultados")
    void testFindByDescuento_sinResultados() {
        // 1. Preparar datos
        int descuento = 99; // Descuento inexistente

        // 2. Configurar mock
        when(cuponDescuentoRepository.findByDescuento(descuento)).thenReturn(Collections.emptyList());

        // 3. Ejecutar
        ResponseEntity<List<CuponDescuento>> response = cuponDescuentoService.findByDescuento(descuento);

        // 4. Verificaciones
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode(), "Debe ser estado 204 No Content");
        assertNull(response.getBody(), "El cuerpo debe ser nulo");
    }

    @Test
    @DisplayName("Eliminar cupón - Caso exitoso")
    void testDeleteCuponDescuento_exitoso() {
        // 1. Preparar datos
        int id = 1;

        // 2. Configurar mock
        when(cuponDescuentoRepository.existsById(id)).thenReturn(true);

        // 3. Ejecutar
        ResponseEntity<Void> response = cuponDescuentoService.deleteCuponDescuento(id);

        // 4. Verificaciones
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode(), "Debe ser estado 204 No Content");
        assertNull(response.getBody(), "El cuerpo debe ser nulo");
    }

    @Test
    @DisplayName("Eliminar cupón - No encontrado")
    void testDeleteCuponDescuento_noEncontrado() {
        // 1. Preparar datos
        int id = 99; // ID inexistente

        // 2. Configurar mock
        when(cuponDescuentoRepository.existsById(id)).thenReturn(false);

        // 3. Ejecutar
        ResponseEntity<Void> response = cuponDescuentoService.deleteCuponDescuento(id);

        // 4. Verificaciones
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Debe ser estado 404 Not Found");
        assertNull(response.getBody(), "El cuerpo debe ser nulo");
    }
}