package duoc.proyect.controller;

import duoc.proyect.assembler.CursoModelAssembler;
import duoc.proyect.model.Curso;
import duoc.proyect.service.CursoService;
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
@RequestMapping("/api/v2/cursos")
public class CursoControllerV2 {

    private final CursoService cursoService;
    private final CursoModelAssembler assembler;

    @Autowired
    public CursoControllerV2(CursoService cursoService, CursoModelAssembler assembler) {
        this.cursoService = cursoService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Curso>>> getAllCursos() {
        ResponseEntity<List<Curso>> response = cursoService.getCursos();

        if (response.getStatusCode() == NO_CONTENT) {
            return noContent().build();
        }

        List<EntityModel<Curso>> cursos = response.getBody().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Curso>> collection = CollectionModel.of(cursos)
                .add(linkTo(methodOn(this.getClass()).getAllCursos()).withSelfRel());

        return ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Curso>> getCursoById(@PathVariable int id) {
        ResponseEntity<Object> response = cursoService.getCursoById(id);

        if (response.getStatusCode() == NO_CONTENT) {
            throw new ResponseStatusException(NOT_FOUND, "Curso no encontrado");
        }

        Curso curso = (Curso) response.getBody();
        return ok(assembler.toModel(curso));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Curso>> createCurso(@RequestBody Curso curso) {
        if (curso == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Datos del curso no proporcionados");
        }

        if (curso.getName() == null || curso.getName().trim().isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "El nombre del curso es obligatorio");
        }

        Curso creado = cursoService.addCurso(curso);

        if (creado == null) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Error al crear el curso");
        }

        EntityModel<Curso> entityModel = assembler.toModel(creado);
        return status(CREATED).body(entityModel);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Curso>> updateCurso(@PathVariable int id, @RequestBody Curso curso) {
        if (curso == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Datos del curso no proporcionados");
        }

        if (curso.getName() == null || curso.getName().trim().isEmpty()) {
            throw new ResponseStatusException(BAD_REQUEST, "El nombre del curso es obligatorio");
        }

        ResponseEntity<String> response = cursoService.updateCurso(curso, id);

        if (response.getStatusCode() == NO_CONTENT) {
            throw new ResponseStatusException(NOT_FOUND, "Curso no encontrado");
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ResponseStatusException(response.getStatusCode(), "Error al actualizar el curso");
        }

        Curso actualizado = (Curso) cursoService.getCursoById(id).getBody();
        EntityModel<Curso> entityModel = assembler.toModel(actualizado);
        return ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable int id) {
        ResponseEntity<String> response = cursoService.deleteCurso(id);
        if (response.getStatusCode() == NO_CONTENT) {
            throw new ResponseStatusException(NOT_FOUND, "Curso no encontrado");
        }
        return ok().build();
    }
}