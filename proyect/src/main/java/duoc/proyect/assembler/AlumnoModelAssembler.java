package duoc.proyect.Assembler;

import duoc.proyect.controller.AlumnoControllerV2;
import duoc.proyect.model.Alumno;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class AlumnoModelAssembler implements RepresentationModelAssembler<Alumno, EntityModel<Alumno>> {
    @Override
    public EntityModel<Alumno> toModel(Alumno alumno) {
        return EntityModel.of(alumno,
                linkTo(methodOn(AlumnoControllerV2.class).getAlumnoById(alumno.getId())).withSelfRel(),
                linkTo(methodOn(AlumnoControllerV2.class).getAllAlumnos()).withRel("todos-alumnos"),
                linkTo(methodOn(AlumnoControllerV2.class).updateAlumno(alumno.getId(), alumno)).withRel("actualizar"),
                linkTo(methodOn(AlumnoControllerV2.class).deleteAlumno(alumno.getId())).withRel("eliminar"),
                linkTo(methodOn(AlumnoControllerV2.class).addAlumno(alumno)).withRel("crear"));
    }
}

