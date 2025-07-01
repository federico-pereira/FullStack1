package duoc.proyect.controller;

import duoc.proyect.Assembler.ProfesorModelAssembler;
import duoc.proyect.model.Profesor;
import duoc.proyect.service.ProfesorService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/profesores")
public class ProfesorControllerV2 {

    private final ProfesorService profesorService;
    private final ProfesorModelAssembler assembler;

    public ProfesorControllerV2(ProfesorService profesorService, ProfesorModelAssembler assembler) {
        this.profesorService = profesorService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<?> getProfesores() {
        ResponseEntity<List<Profesor>> response = profesorService.getProfesores();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<EntityModel<Profesor>> profesores = response.getBody().stream()
                    .map(assembler::toModel)
                    .toList();
            return ResponseEntity.ok(
                    CollectionModel.of(profesores, linkTo(methodOn(ProfesorControllerV2.class).getProfesores()).withSelfRel())
            );
        }
        return ResponseEntity.status(response.getStatusCode()).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfesorById(@PathVariable int id) {
        ResponseEntity<Object> response = profesorService.getProfesorById(id);
        if (response.getStatusCode().is2xxSuccessful()) {
            Optional<Profesor> profesorOpt = (Optional<Profesor>) response.getBody();
            return profesorOpt.map(assembler::toModel)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<?> addProfesor(@RequestBody Profesor profesor) {
        return profesorService.addProfesor(profesor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfesor(@PathVariable int id) {
        return profesorService.deleteProfesor(id);
    }

    // Nota: la actualización en el servicio tiene problemas, ver comentario abajo
}
