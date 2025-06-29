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
@Tag(name = "Contenido", description = "Operanciones relacionadas con contenidos")
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
    public ResponseEntity<List<Contenido>> getContenido() {
        return contenidoService.getContenidos();
    }

    @Operation(
            summary = "Crear un contenido",
            description = "Agrega un nuevo contenido al sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contenido creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Payload inválido")
    })
    @PostMapping
    public ResponseEntity<String> addContenido(
            @RequestBody Contenido contenido
    ) {
        return contenidoService.addContenido(contenido);
    }

    @Operation(
            summary = "Obtener un contenido por ID",
            description = "Devuelve un contenido específico dado su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se encontró el contenido"),
            @ApiResponse(responseCode = "404", description = "Contenido no existe en el sistema")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getContenidoId(
            @Parameter(description = "ID del contenido a buscar")
            @PathVariable int id
    ) {
        return contenidoService.getContenidoById(id);
    }

    @Operation(
            summary = "Actualizar un contenido",
            description = "Modifica los datos de un contenido existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contenido actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Contenido no existe en el sistema"),
            @ApiResponse(responseCode = "400", description = "Payload inválido")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateContenido(
            @Parameter(description = "ID del contenido a actualizar")
            @PathVariable int id,
            @RequestBody Contenido contenido
    ) {
        return contenidoService.updateContenido(id, contenido);
    }

    @Operation(
            summary = "Eliminar un contenido",
            description = "Borra un contenido del sistema dado su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contenido eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Contenido no existe en el sistema")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContenido(
            @Parameter(description = "ID del contenido a eliminar")
            @PathVariable int id
    ) {
        return contenidoService.deleteContenido(id);
    }
}
