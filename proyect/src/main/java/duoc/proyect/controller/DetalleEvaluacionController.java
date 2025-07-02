package duoc.proyect.controller;

import duoc.proyect.model.DetalleEvaluacion;
import duoc.proyect.service.DetalleEvaluacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalles-evaluacion")
@Tag(name = "Detalle de Evaluación", description = "API para gestión de detalles de evaluación")
public class DetalleEvaluacionController {

    @Autowired
    private DetalleEvaluacionService detalleService;

    @Operation(summary = "Obtener todos los detalles de evaluación")
    @ApiResponse(responseCode = "200", description = "Detalles encontrados",
            content = @Content(schema = @Schema(implementation = DetalleEvaluacion.class)))
    @ApiResponse(responseCode = "204", description = "No hay detalles registrados")
    @GetMapping
    public ResponseEntity<List<DetalleEvaluacion>> getAllDetalles() {
        return detalleService.getAllDetalles();
    }

    @Operation(summary = "Obtener detalle por ID")
    @ApiResponse(responseCode = "200", description = "Detalle encontrado",
            content = @Content(schema = @Schema(implementation = DetalleEvaluacion.class)))
    @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<DetalleEvaluacion> getDetalleById(@PathVariable int id) {
        return detalleService.getDetalleById(id);
    }

    @Operation(summary = "Crear nuevo detalle de evaluación")
    @ApiResponse(responseCode = "201", description = "Detalle creado exitosamente")
    @ApiResponse(responseCode = "409", description = "El detalle ya existe")
    @PostMapping
    public ResponseEntity<DetalleEvaluacion> createDetalle(@RequestBody DetalleEvaluacion detalle) {
        return detalleService.addDetalle(detalle);
    }

    @Operation(summary = "Actualizar detalle de evaluación")
    @ApiResponse(responseCode = "200", description = "Detalle actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<DetalleEvaluacion> updateDetalle(
            @PathVariable int id,
            @RequestBody DetalleEvaluacion detalle) {
        return detalleService.updateDetalle(id, detalle);
    }

    @Operation(summary = "Eliminar detalle de evaluación")
    @ApiResponse(responseCode = "204", description = "Detalle eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalle(@PathVariable int id) {
        return detalleService.deleteDetalle(id);
    }


    @Operation(summary = "Obtener detalles por alumno")
    @ApiResponse(responseCode = "200", description = "Detalles encontrados",
            content = @Content(schema = @Schema(implementation = DetalleEvaluacion.class)))
    @ApiResponse(responseCode = "204", description = "No hay detalles para este alumno")
    @GetMapping("/por-alumno/{alumnoId}")
    public ResponseEntity<List<DetalleEvaluacion>> getDetallesByAlumno(@PathVariable int alumnoId) {
        return detalleService.getDetallesByAlumno(alumnoId);
    }

    @Operation(summary = "Obtener detalles por evaluación")
    @ApiResponse(responseCode = "200", description = "Detalles encontrados",
            content = @Content(schema = @Schema(implementation = DetalleEvaluacion.class)))
    @ApiResponse(responseCode = "204", description = "No hay detalles para esta evaluación")
    @GetMapping("/por-evaluacion/{evaluacionId}")
    public ResponseEntity<List<DetalleEvaluacion>> getDetallesByEvaluacion(@PathVariable int evaluacionId) {
        return detalleService.getDetallesByEvaluacion(evaluacionId);
    }
}