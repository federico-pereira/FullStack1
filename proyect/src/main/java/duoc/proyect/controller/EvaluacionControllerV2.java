package duoc.proyect.controller;

import duoc.proyect.assembler.EvaluacionModelAssembler;
import duoc.proyect.model.Evaluacion;
import duoc.proyect.service.EvaluacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/evaluaciones")
@Tag(name = "Evaluaciones V2", description = "Controlador con HATEOAS para Evaluaciones")
public class EvaluacionControllerV2 {

    @Autowired
    private EvaluacionService evaluacionService;

    @Autowired
    private EvaluacionModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<Evaluacion>> getAllEvaluaciones() {
        ResponseEntity<List<Evaluacion>> response = evaluacionService.getEvaluaciones();
        List<Evaluacion> evaluaciones = response.getBody();

        if (evaluaciones == null || evaluaciones.isEmpty()) {
            return CollectionModel.empty();
        }

        List<EntityModel<Evaluacion>> evaluacionModels = evaluaciones.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(evaluacionModels,
                linkTo(methodOn(EvaluacionControllerV2.class).getAllEvaluaciones()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Evaluacion> getEvaluacionById(@PathVariable int id) {
        Evaluacion evaluacion = (Evaluacion) evaluacionService.getEvaluacionById(id).getBody();
        return assembler.toModel(evaluacion);
    }
}