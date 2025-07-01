package duoc.proyect.controller;

import duoc.proyect.Assembler.EvaluacionModelAssembler;
import duoc.proyect.model.Curso;
import duoc.proyect.model.Evaluacion;
import duoc.proyect.service.EvaluacionService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/evaluaciones")
public class EvaluacionControllerV2 {

    private final EvaluacionService service;
    private final EvaluacionModelAssembler assembler;

    public EvaluacionControllerV2(EvaluacionService service, EvaluacionModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<?> getEvaluaciones() {
        ResponseEntity<List<Evaluacion>> response = service.getEvaluaciones();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<EntityModel<Evaluacion>> evaluaciones = response.getBody().stream()
                    .map(assembler::toModel)
                    .toList();
            return ResponseEntity.ok(
                    CollectionModel.of(evaluaciones,
                            linkTo(methodOn(EvaluacionControllerV2.class).getEvaluaciones()).withSelfRel())
            );
        }
        return ResponseEntity.status(response.getStatusCode()).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvaluacionById(@PathVariable int id) {
        ResponseEntity<Object> response = service.getEvaluacionById(id);
        if (response.getStatusCode().is2xxSuccessful()) {
            Evaluacion evaluacion = (Evaluacion) response.getBody();
            return ResponseEntity.ok(assembler.toModel(evaluacion));
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<?> addEvaluacion(@RequestBody Evaluacion evaluacion) {
        return service.addEvaluacion(evaluacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvaluacion(@PathVariable int id, @RequestBody Evaluacion evaluacion) {
        return service.updateEvaluacion(evaluacion, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvaluacion(@PathVariable int id) {
        return service.deleteEvaluacion(id);
    }

    // CURSOS
    @GetMapping("/{id}/cursos")
    public ResponseEntity<?> getCursos(@PathVariable int id) {
        return service.getCursos(id);
    }

    @PostMapping("/{idEvaluacion}/cursos/{idCurso}")
    public ResponseEntity<?> addCurso(@PathVariable int idEvaluacion, @PathVariable int idCurso) {
        return service.addCurso(idEvaluacion, idCurso);
    }

    @DeleteMapping("/{idEvaluacion}/cursos/{idCurso}")
    public ResponseEntity<?> deleteCurso(@PathVariable int idEvaluacion, @PathVariable int idCurso) {
        return service.deleteCurso(idEvaluacion, idCurso);
    }
}
