package duoc.proyect.controller;

import duoc.proyect.model.CuponDescuento;
import duoc.proyect.service.CuponDescuentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cupones")
@Tag(name = "Cupones de Descuento",
        description = "Operaciones CRUD para administrar cupones de descuento")
public class CuponDescuentoController {

    private final CuponDescuentoService cuponDescuentoService;

    @Autowired
    public CuponDescuentoController(CuponDescuentoService cuponDescuentoService) {
        this.cuponDescuentoService = cuponDescuentoService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los cupones",
            description = "Retorna una lista de todos los cupones de descuento registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cupones obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CuponDescuento.class))),
            @ApiResponse(responseCode = "204", description = "No hay cupones registrados")
    })
    public ResponseEntity<List<CuponDescuento>> getCupones() {
        return cuponDescuentoService.getCuponesDescuento();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un cupón por ID",
            description = "Retorna un cupón específico basado en su ID único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupón encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CuponDescuento.class))),
            @ApiResponse(responseCode = "404", description = "Cupón no encontrado")
    })
    @Parameter(name = "id", description = "ID del cupón a buscar", required = true, example = "1")
    public ResponseEntity<CuponDescuento> getCuponById(
            @PathVariable
            @Parameter(description = "ID del cupón", example = "1")
            int id) {
        return cuponDescuentoService.getCuponDescuentoById(id);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo cupón",
            description = "Registra un nuevo cupón de descuento en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cupón creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CuponDescuento.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto: ID de cupón ya existe"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<CuponDescuento> addCupon(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del cupón a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CuponDescuento.class))
            )
            @RequestBody CuponDescuento cupon) {
        return cuponDescuentoService.addCuponDescuento(cupon);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cupón",
            description = "Elimina un cupón específico basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cupón eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cupón no encontrado")
    })
    @Parameter(name = "id", description = "ID del cupón a eliminar", required = true, example = "1")
    public ResponseEntity<Void> deleteCupon(
            @PathVariable
            @Parameter(description = "ID del cupón", example = "1")
            int id) {
        return cuponDescuentoService.deleteCuponDescuento(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cupón",
            description = "Actualiza los datos de un cupón existente basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupón actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CuponDescuento.class))),
            @ApiResponse(responseCode = "404", description = "Cupón no encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @Parameter(name = "id", description = "ID del cupón a actualizar", required = true, example = "1")
    public ResponseEntity<CuponDescuento> updateCupon(
            @PathVariable
            @Parameter(description = "ID del cupón", example = "1")
            int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del cupón",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CuponDescuento.class))
            )
            @RequestBody CuponDescuento cupon) {
        return cuponDescuentoService.updateCuponDescuento(id, cupon);
    }
}