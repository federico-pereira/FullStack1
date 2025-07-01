package duoc.proyect.assembler;

import duoc.proyect.controller.DetalleEvaluacionControllerV2;
import duoc.proyect.model.DetalleEvaluacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DetalleEvaluacionModelAssembler implements RepresentationModelAssembler<DetalleEvaluacion, EntityModel<DetalleEvaluacion>> {

    @Override
    public EntityModel<DetalleEvaluacion> toModel(DetalleEvaluacion detalle) {
        return EntityModel.of(detalle,
                linkTo(methodOn(DetalleEvaluacionControllerV2.class).getById(detalle.getId())).withSelfRel(),
                linkTo(methodOn(DetalleEvaluacionControllerV2.class).getAll()).withRel("detalle-evaluaciones"));
    }
}
