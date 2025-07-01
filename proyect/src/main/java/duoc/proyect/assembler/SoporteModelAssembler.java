package duoc.proyect.Assembler;

import duoc.proyect.controller.SoporteControllerV2;
import duoc.proyect.model.Soporte;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SoporteModelAssembler implements RepresentationModelAssembler<Soporte, EntityModel<Soporte>> {

    @Override
    public EntityModel<Soporte> toModel(Soporte soporte) {
        return EntityModel.of(soporte,
                linkTo(methodOn(SoporteControllerV2.class).getSoporteById(soporte.getId())).withSelfRel(),
                linkTo(methodOn(SoporteControllerV2.class).getAllSoportes()).withRel("soportes"));
    }
}