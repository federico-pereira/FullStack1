package duoc.proyect.assembler;

import duoc.proyect.controller.ProfesorControllerV2;
import duoc.proyect.model.Profesor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProfesorModelAssembler implements RepresentationModelAssembler<Profesor, EntityModel<Profesor>> {

    @Override
    public EntityModel<Profesor> toModel(Profesor profesor) {
        return EntityModel.of(profesor,
                linkTo(methodOn(ProfesorControllerV2.class).getProfesorById(profesor.getId())).withSelfRel(),
                linkTo(methodOn(ProfesorControllerV2.class).getAllProfesores()).withRel("profesores")
        );
    }
}
