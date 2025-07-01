package duoc.proyect.controller;

import duoc.proyect.assembler.DetalleEvaluacionModelAssembler;
import duoc.proyect.model.DetalleEvaluacion;
import duoc.proyect.service.DetalleEvaluacionService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/detalle-evaluaciones")
public class DetalleEvaluacionControllerV2 {

    private final DetalleEvaluacionService service;
    private final DetalleEvaluacionModelAssembler assembler;

    public DetalleEvaluacionControllerV2(DetalleEvaluacionService service, DetalleEvaluacionModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        ResponseEntity<List<DetalleEvaluacion>> response = service.getDetalleEvaluaciones();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<EntityModel<DetalleEvaluacion>> models = response.getBody().stream()
                    .map(assembler::toModel)
                    .toList();
            return ResponseEntity.ok(
                    CollectionModel.of(models,
                            linkTo(methodOn(DetalleEvaluacionControllerV2.class).getAll()).withSelfRel())
            );
        }
        return ResponseEntity.status(response.getStatusCode()).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        ResponseEntity<Object> response = service.getDetalleEvaluacionById(id);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() instanceof DetalleEvaluacion detalle) {
            return ResponseEntity.ok(assembler.toModel(detalle));
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody DetalleEvaluacion detalleEvaluacion) {
        return service.addDetalleEvaluacion(detalleEvaluacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody DetalleEvaluacion detalleEvaluacion) {
        return service.updateDetalleEvaluacion(id, detalleEvaluacion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return service.deleteDetalleEvaluacion(id);
    }
}
