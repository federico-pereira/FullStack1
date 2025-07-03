package duoc.proyect.controller;

import duoc.proyect.assemblers.AlumnoModelAssembler;
import duoc.proyect.model.Alumno;
import duoc.proyect.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/v2/alumnos")
public class AlumnoControllerV2 {

    private final AlumnoService alumnoService;
    private final AlumnoModelAssembler assembler;

    @Autowired
    public AlumnoControllerV2(AlumnoService alumnoService, AlumnoModelAssembler assembler) {
        this.alumnoService = alumnoService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Alumno>>> getAllAlumnos() {
        ResponseEntity<List<Alumno>> response = alumnoService.getAlumnos();

        if (response.getStatusCode() == NO_CONTENT) {
            return noContent().build();
        }

        List<EntityModel<Alumno>> models = response.getBody().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Alumno>> collection = CollectionModel.of(models)
                .add(linkTo(methodOn(this.getClass()).getAllAlumnos()).withSelfRel());

        return ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Alumno>> getAlumnoById(@PathVariable int id) {
        ResponseEntity<Alumno> response = alumnoService.getAlumnoById(id);

        if (response.getStatusCode() == NOT_FOUND) {
            throw new ResponseStatusException(NOT_FOUND, "Alumno no encontrado");
        }

        return ok(assembler.toModel(response.getBody()));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Alumno>> createAlumno(@RequestBody Alumno alumno) {
        ResponseEntity<Object> response = alumnoService.addAlumno(alumno);

        if (response.getStatusCode() == CONFLICT) {
            throw new ResponseStatusException(CONFLICT, response.getBody().toString());
        }

        Alumno creado = (Alumno) response.getBody();
        EntityModel<Alumno> entityModel = assembler.toModel(creado);
        return status(CREATED).body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Alumno>> updateAlumno(@PathVariable int id, @RequestBody Alumno alumno) {
        ResponseEntity<Alumno> response = alumnoService.updateAlumno(id, alumno);

        if (response.getStatusCode() == NOT_FOUND) {
            throw new ResponseStatusException(NOT_FOUND, "Alumno no encontrado");
        }

        EntityModel<Alumno> entityModel = assembler.toModel(response.getBody());
        return ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable int id) {
        ResponseEntity<Void> response = alumnoService.deleteAlumno(id);

        if (response.getStatusCode() == NOT_FOUND) {
            throw new ResponseStatusException(NOT_FOUND, "Alumno no encontrado");
        }

        return noContent().build();
    }
}
