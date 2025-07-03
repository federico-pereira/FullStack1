package duoc.proyect.controller;

import duoc.proyect.assembler.ContenidoModelAssembler;
import duoc.proyect.model.Contenido;
import duoc.proyect.service.ContenidoService;
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
@RequestMapping("/api/v2/contenidos")
public class ContenidoControllerV2 {

    private final ContenidoService contenidoService;
    private final ContenidoModelAssembler assembler;

    @Autowired
    public ContenidoControllerV2(ContenidoService contenidoService, ContenidoModelAssembler assembler) {
        this.contenidoService = contenidoService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Contenido>>> getAllContenidos() {
        ResponseEntity<List<Contenido>> response = contenidoService.getContenidos();

        if (response.getStatusCode() == NO_CONTENT) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Contenido>> models = response.getBody().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Contenido>> collection = CollectionModel.of(models)
                .add(linkTo(methodOn(this.getClass()).getAllContenidos()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Contenido>> getContenidoById(@PathVariable int id) {
        ResponseEntity<Contenido> response = contenidoService.getContenidoById(id);

        if (response.getStatusCode() == NOT_FOUND) {
            throw new ResponseStatusException(NOT_FOUND, "Contenido no encontrado");
        }

        return ResponseEntity.ok(assembler.toModel(response.getBody()));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Contenido>> createContenido(@RequestBody Contenido contenido) {
        ResponseEntity<Contenido> response = contenidoService.addContenido(contenido);
        EntityModel<Contenido> entityModel = assembler.toModel(response.getBody());
        return ResponseEntity.status(CREATED).body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Contenido>> updateContenido(@PathVariable int id, @RequestBody Contenido contenido) {
        ResponseEntity<Contenido> response = contenidoService.updateContenido(id, contenido);

        if (response.getStatusCode() == NOT_FOUND) {
            throw new ResponseStatusException(NOT_FOUND, "Contenido no encontrado");
        }

        EntityModel<Contenido> entityModel = assembler.toModel(response.getBody());
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContenido(@PathVariable int id) {
        ResponseEntity<String> response = contenidoService.deleteContenido(id);

        if (response.getStatusCode() == NOT_FOUND) {
            throw new ResponseStatusException(NOT_FOUND, "Contenido no encontrado");
        }

        return ResponseEntity.noContent().build();
    }
}
