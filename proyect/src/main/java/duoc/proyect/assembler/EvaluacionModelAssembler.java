package duoc.proyect.Assembler;

import duoc.proyect.controller.EvaluacionControllerV2;
import duoc.proyect.model.Evaluacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EvaluacionModelAssembler implements RepresentationModelAssembler<Evaluacion, EntityModel<Evaluacion>> {

    @Override
    public EntityModel<Evaluacion> toModel(Evaluacion evaluacion) {
        return EntityModel.of(evaluacion,
                linkTo(methodOn(EvaluacionControllerV2.class).getEvaluacionById(evaluacion.getId())).withSelfRel(),
                linkTo(methodOn(EvaluacionControllerV2.class).getEvaluaciones()).withRel("evaluaciones"),
                linkTo(methodOn(EvaluacionControllerV2.class).getCursos(evaluacion.getId())).withRel("cursos"));
    }
}
