package duoc.proyect.assembler;

import duoc.proyect.controller.ContenidoControllerV2;
import duoc.proyect.model.Contenido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ContenidoModelAssembler implements RepresentationModelAssembler<Contenido, EntityModel<Contenido>> {

    @Override
    public EntityModel<Contenido> toModel(Contenido contenido) {
        return EntityModel.of(contenido,
                linkTo(methodOn(ContenidoControllerV2.class).getContenidoById(contenido.getId())).withSelfRel(),
                linkTo(methodOn(ContenidoControllerV2.class).getAllContenidos()).withRel("contenidos")
        );
    }
}
