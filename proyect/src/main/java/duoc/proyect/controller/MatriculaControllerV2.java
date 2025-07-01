package duoc.proyect.controller;

import duoc.proyect.assembler.MatriculaModelAssembler;
import duoc.proyect.model.Matricula;
import duoc.proyect.service.MatriculaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/matriculas")
@Tag(name = "Matriculas V2", description = "Controlador con HATEOAS para Matriculas")
public class MatriculaControllerV2 {

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private MatriculaModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<Matricula>> getAllMatriculas() {
        ResponseEntity<List<Matricula>> response = matriculaService.getMatriculas();
        List<Matricula> matriculas = response.getBody();

        if (matriculas == null || matriculas.isEmpty()) {
            return CollectionModel.empty();
        }

        List<EntityModel<Matricula>> matriculaModels = matriculas.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(matriculaModels,
                linkTo(methodOn(MatriculaControllerV2.class).getAllMatriculas()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Matricula> getMatriculaById(@PathVariable int id) {
        Matricula matricula = (Matricula) matriculaService.getMatriculaById(id).getBody();
        return assembler.toModel(matricula);
    }
}
