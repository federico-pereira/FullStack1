package duoc.proyect.controller;

import duoc.proyect.assemblers.ProfesorModelAssembler;
import duoc.proyect.model.Profesor;
import duoc.proyect.service.ProfesorService;
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

@RestController
@RequestMapping("/api/v2/profesores")
public class ProfesorControllerV2 {

    private final ProfesorService profesorService;
    private final ProfesorModelAssembler assembler;

    @Autowired
    public ProfesorControllerV2(ProfesorService profesorService, ProfesorModelAssembler assembler) {
        this.profesorService = profesorService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Profesor>>> getAllProfesores() {
        ResponseEntity<List<Profesor>> response = profesorService.getProfesores();

        if (response.getStatusCode() == NO_CONTENT) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Profesor>> models = response.getBody().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Profesor>> collection = CollectionModel.of(models)
                .add(linkTo(methodOn(this.getClass()).getAllProfesores()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Profesor>> getProfesorById(@PathVariable int id) {
        ResponseEntity<Object> response = profesorService.getProfesorById(id);

        if (response.getStatusCode() == NOT_FOUND) {
            throw new ResponseStatusException(NOT_FOUND, "Profesor no encontrado");
        }

        Profesor profesor = (Profesor) response.getBody();
        return ResponseEntity.ok(assembler.toModel(profesor));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Profesor>> createProfesor(@RequestBody Profesor profesor) {
        ResponseEntity<Profesor> response = profesorService.addProfesor(profesor);
        Profesor creado = response.getBody();
        EntityModel<Profesor> entityModel = assembler.toModel(creado);
        return ResponseEntity.status(CREATED).body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Profesor>> updateProfesor(@PathVariable int id, @RequestBody Profesor profesor) {
        ResponseEntity<Profesor> response = profesorService.updateProfesor(profesor, id);

        if (response.getStatusCode() == NOT_FOUND) {
            throw new ResponseStatusException(NOT_FOUND, "Profesor no encontrado");
        }

        EntityModel<Profesor> entityModel = assembler.toModel(response.getBody());
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfesor(@PathVariable int id) {
        ResponseEntity<String> response = profesorService.deleteProfesor(id);

        if (response.getStatusCode() == NOT_FOUND) {
            throw new ResponseStatusException(NOT_FOUND, "Profesor no encontrado");
        }

        return ResponseEntity.noContent().build();
    }
}
