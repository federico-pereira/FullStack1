package duoc.proyect.controller;

import duoc.proyect.Assembler.AlumnoModelAssembler;
import duoc.proyect.model.Alumno;
import duoc.proyect.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/alumnos")
public class AlumnoControllerV2 {

    private final AlumnoService alumnoService;
    private final AlumnoModelAssembler assembler;

    @Autowired
    public AlumnoControllerV2(AlumnoService alumnoService,
                              AlumnoModelAssembler assembler) {
        this.alumnoService = alumnoService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Alumno>>> getAllAlumnos() {
        ResponseEntity<List<Alumno>> response = alumnoService.getAlumnos();

        List<Alumno> alumnos = response.getBody();

        if (alumnos == null || alumnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Alumno>> models = alumnos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(models,
                        linkTo(methodOn(AlumnoControllerV2.class).getAllAlumnos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Alumno>> getAlumnoById(@PathVariable int id) {
        ResponseEntity<Alumno> response = alumnoService.getAlumnoById(id);
        Alumno alumno = response.getBody();

        if (alumno == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alumno no encontrado");
        }

        return ResponseEntity.ok(assembler.toModel(alumno));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Alumno>> addAlumno(@RequestBody Alumno alumno) {
        ResponseEntity<Object> response = alumnoService.addAlumno(alumno);

        if (response.getStatusCode() == HttpStatus.CONFLICT) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // o incluir mensaje si quieres
        }

        Alumno creado = (Alumno) response.getBody();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Alumno>> updateAlumno(@PathVariable int id,
                                                            @RequestBody Alumno alumno) {
        ResponseEntity<Alumno> response = alumnoService.updateAlumno(id, alumno);

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(assembler.toModel(Objects.requireNonNull(response.getBody())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable int id) {
        return alumnoService.deleteAlumno(id);
    }
}
