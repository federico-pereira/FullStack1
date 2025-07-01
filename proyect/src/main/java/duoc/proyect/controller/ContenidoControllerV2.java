package duoc.proyect.controller;

import duoc.proyect.Assembler.ContenidoModelAssembler;
import duoc.proyect.model.Contenido;
import duoc.proyect.service.ContenidoService;
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
    @RequestMapping("/api/v2/contenidos")
    public class ContenidoControllerV2 {

        @Autowired
        private ContenidoService contenidoService;

        @Autowired
        private ContenidoModelAssembler assembler;

        @GetMapping
        public ResponseEntity<CollectionModel<EntityModel<Contenido>>> getAllContenidos() {
            ResponseEntity<List<Contenido>> response = contenidoService.getContenidos();
            List<Contenido> contenidos = response.getBody();

            if (contenidos == null || contenidos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<EntityModel<Contenido>> modelos = contenidos.stream()
                    .map(assembler::toModel)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(
                    CollectionModel.of(modelos,
                            linkTo(methodOn(ContenidoControllerV2.class).getAllContenidos()).withSelfRel()));
        }

        @GetMapping("/{id}")
        public ResponseEntity<EntityModel<Contenido>> getContenidoById(@PathVariable int id) {
            ResponseEntity<Object> response = contenidoService.getContenidoById(id);

            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, response.getBody().toString());
            }

            Contenido contenido = (Contenido) response.getBody();
            return ResponseEntity.ok(assembler.toModel(contenido));
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public ResponseEntity<String> addContenido(@RequestBody Contenido contenido) {
            return contenidoService.addContenido(contenido);
        }

        @PutMapping("/{id}")
        public ResponseEntity<String> updateContenido(@PathVariable int id,
                                                      @RequestBody Contenido contenido) {
            return contenidoService.updateContenido(id, contenido);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteContenido(@PathVariable int id) {
            return contenidoService.deleteContenido(id);
        }
    }



