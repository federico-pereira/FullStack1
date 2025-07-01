package duoc.proyect.Assembler;

import duoc.proyect.controller.CursoControllerV2;
import duoc.proyect.model.Curso;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CursoModelAssembler implements RepresentationModelAssembler<Curso, EntityModel<Curso>> {

    @Override
    public EntityModel<Curso> toModel(Curso curso) {
        return EntityModel.of(curso,
                linkTo(methodOn(CursoControllerV2.class).getCursoById(curso.getId())).withSelfRel(),
                linkTo(methodOn(CursoControllerV2.class).getAllCursos()).withRel("cursos"));
    }
}
