package duoc.proyect.controller;

import duoc.proyect.assemblers.SoporteModelAssembler;
import duoc.proyect.model.Soporte;
import duoc.proyect.service.SoporteService;
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
@RequestMapping("/api/v2/soportes")
@Tag(name = "Soportes V2", description = "Controlador de soporte con HATEOAS")
public class SoporteControllerV2 {

    @Autowired
    private SoporteService soporteService;

    @Autowired
    private SoporteModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<Soporte>> getAllSoportes() {
        ResponseEntity<List<Soporte>> response = soporteService.getSoportes();
        List<Soporte> soporteList = response.getBody();

        if (soporteList == null || soporteList.isEmpty()) {
            return CollectionModel.empty();
        }

        List<EntityModel<Soporte>> soportes = soporteList.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(soportes,
                linkTo(methodOn(SoporteControllerV2.class).getAllSoportes()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Soporte> getSoporteById(@PathVariable int id) {
        Soporte soporte = (Soporte) soporteService.getSoporteById(id).getBody();
        return assembler.toModel(soporte);
    }
}