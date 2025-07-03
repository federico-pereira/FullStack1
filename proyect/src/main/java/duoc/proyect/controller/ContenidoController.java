package duoc.proyect.controller;

import duoc.proyect.model.Contenido;
import duoc.proyect.service.ContenidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contenidos")
@Tag(name = "Contenido", description = "Operaciones relacionadas con contenidos")
public class ContenidoController {

    @Autowired
    private ContenidoService contenidoService;

    @Operation(
            summary = "Obtener todos los contenidos",
            description = "Devuelve la lista completa de contenidos registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de contenidos"),
            @ApiResponse(responseCode = "204", description = "No hay contenidos registrados")
    })
    @GetMapping
    public ResponseEntity<List<Contenido>> getContenidos() {
        return contenidoService.getContenidos();
    }

    @Operation(
            summary = "Obtener un contenido por ID",
            description = "Devuelve un contenido específico dado su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se encontró el contenido"),
            @ApiResponse(responseCode = "404", description = "Contenido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Contenido> getContenidoById(
            @Parameter(description = "ID del contenido a buscar") @PathVariable int id) {
        return contenidoService.getContenidoById(id);
    }

    @Operation(
            summary = "Crear un contenido",
            description = "Agrega un nuevo contenido al sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contenido creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Contenido> addContenido(@RequestBody Contenido contenido) {
        return contenidoService.addContenido(contenido);
    }

    @Operation(
            summary = "Actualizar un contenido",
            description = "Modifica los datos de un contenido existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contenido actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Contenido no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Contenido> updateContenido(
            @Parameter(description = "ID del contenido a actualizar") @PathVariable int id,
            @RequestBody Contenido contenido) {
        return contenidoService.updateContenido(id, contenido);
    }

    @Operation(
            summary = "Eliminar un contenido",
            description = "Borra un contenido del sistema dado su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contenido eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Contenido no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContenido(
            @Parameter(description = "ID del contenido a eliminar") @PathVariable int id) {
        return contenidoService.deleteContenido(id);
    }
}
