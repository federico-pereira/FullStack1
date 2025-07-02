package duoc.proyect.controller;

import duoc.proyect.assemblers.DetalleEvaluacionModelAssembler;
import duoc.proyect.model.DetalleEvaluacion;
import duoc.proyect.service.DetalleEvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("api/v2/detallesEvaluaciones")
public class DetalleEvaluacionControllerV2 {

    @Autowired
    private DetalleEvaluacionService detalleService;

    @Autowired
    private DetalleEvaluacionModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DetalleEvaluacion>>> getAllDetalles() {
        List<DetalleEvaluacion> detalles = detalleService.getAllDetalles().getBody();

        if (detalles == null || detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<DetalleEvaluacion>> detalleModels = detalles.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<DetalleEvaluacion>> collection = CollectionModel.of(detalleModels,
                linkTo(methodOn(this.getClass()).getAllDetalles()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DetalleEvaluacion>> getDetalleById(@PathVariable int id) {
        ResponseEntity<DetalleEvaluacion> response = detalleService.getDetalleById(id);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(assembler.toModel(response.getBody()));
        }
        return ResponseEntity.status(response.getStatusCode()).build();
    }

    @PostMapping
    public ResponseEntity<EntityModel<DetalleEvaluacion>> createDetalle(@RequestBody DetalleEvaluacion detalle) {
        ResponseEntity<DetalleEvaluacion> response = detalleService.addDetalle(detalle);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            EntityModel<DetalleEvaluacion> resource = assembler.toModel(response.getBody());
            return ResponseEntity
                    .created(linkTo(methodOn(this.getClass()).getDetalleById(detalle.getId())).toUri())
                    .body(resource);
        }
        return ResponseEntity.status(response.getStatusCode()).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<DetalleEvaluacion>> updateDetalle(
            @PathVariable int id,
            @RequestBody DetalleEvaluacion detalle) {
        ResponseEntity<DetalleEvaluacion> response = detalleService.updateDetalle(id, detalle);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(assembler.toModel(response.getBody()));
        }
        return ResponseEntity.status(response.getStatusCode()).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDetalle(@PathVariable int id) {
        ResponseEntity<Void> response = detalleService.deleteDetalle(id);
        return ResponseEntity.status(response.getStatusCode()).build();
    }


    @GetMapping("/porAlumno/{alumnoId}")
    public ResponseEntity<CollectionModel<EntityModel<DetalleEvaluacion>>> getDetallesByAlumno(@PathVariable int alumnoId) {
        List<DetalleEvaluacion> detalles = detalleService.getDetallesByAlumno(alumnoId).getBody();

        if (detalles == null || detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<DetalleEvaluacion>> detalleModels = detalles.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<DetalleEvaluacion>> collection = CollectionModel.of(detalleModels,
                linkTo(methodOn(this.getClass()).getDetallesByAlumno(alumnoId)).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/porEvaluacion/{evaluacionId}")
    public ResponseEntity<CollectionModel<EntityModel<DetalleEvaluacion>>> getDetallesByEvaluacion(@PathVariable int evaluacionId) {
        List<DetalleEvaluacion> detalles = detalleService.getDetallesByEvaluacion(evaluacionId).getBody();

        if (detalles == null || detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<DetalleEvaluacion>> detalleModels = detalles.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<DetalleEvaluacion>> collection = CollectionModel.of(detalleModels,
                linkTo(methodOn(this.getClass()).getDetallesByEvaluacion(evaluacionId)).withSelfRel());

        return ResponseEntity.ok(collection);
    }
}