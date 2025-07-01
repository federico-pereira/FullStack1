package duoc.proyect.controller;

import duoc.proyect.Assembler.CursoModelAssembler;
import duoc.proyect.model.Curso;
import duoc.proyect.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/cursos")
public class CursoControllerV2 {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private CursoModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Curso>>> getAllCursos() {
        ResponseEntity<List<Curso>> response = cursoService.getCursos();
        List<Curso> cursos = response.getBody();

        if (cursos == null || cursos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Curso>> modelos = cursos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(modelos,
                        linkTo(methodOn(CursoControllerV2.class).getAllCursos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Curso>> getCursoById(@PathVariable int id) {
        ResponseEntity<Object> response = cursoService.getCursoById(id);

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, response.getBody().toString());
        }

        Curso curso = (Curso) response.getBody();
        return ResponseEntity.ok(assembler.toModel(curso));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addCurso(@RequestBody Curso curso) {
        return cursoService.addCurso(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCurso(@PathVariable int id,
                                              @RequestBody Curso curso) {
        return cursoService.updateCurso(curso, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCurso(@PathVariable int id) {
        return cursoService.deleteCurso(id);
    }
}