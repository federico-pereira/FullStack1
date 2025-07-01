package duoc.proyect.controller;

import duoc.proyect.assembler.CuponDescuentoModelAssembler;
import duoc.proyect.model.CuponDescuento;
import duoc.proyect.service.CuponDescuentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/v2/cupones")
public class CuponDescuentoControllerV2 {

    private final CuponDescuentoService cuponService;
    private final CuponDescuentoModelAssembler assembler;

    @Autowired
    public CuponDescuentoControllerV2(CuponDescuentoService cuponService,
                                      CuponDescuentoModelAssembler assembler) {
        this.cuponService = cuponService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CuponDescuento>>> getCupones() {
        ResponseEntity<List<CuponDescuento>> response = cuponService.getCuponesDescuento();

        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return noContent().build();
        }

        List<EntityModel<CuponDescuento>> models = response.getBody().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<CuponDescuento>> collection = CollectionModel.of(models)
                .add(linkTo(methodOn(this.getClass()).getCupones()).withSelfRel());

        return ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CuponDescuento>> getCuponById(@PathVariable int id) {
        ResponseEntity<CuponDescuento> response = cuponService.getCuponDescuentoById(id);

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return notFound().build();
        }

        return ok(assembler.toModel(response.getBody()));
    }

    @PostMapping
    public ResponseEntity<EntityModel<CuponDescuento>> addCupon(@RequestBody CuponDescuento cupon) {
        ResponseEntity<CuponDescuento> response = cuponService.addCuponDescuento(cupon);

        if (response.getStatusCode() == HttpStatus.CONFLICT) {
            throw new ResponseStatusException(CONFLICT, "Ya existe un cupón con este ID");
        }

        EntityModel<CuponDescuento> entityModel = assembler.toModel(response.getBody());
        return status(CREATED).body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuponById(@PathVariable int id) {
        ResponseEntity<Void> response = cuponService.deleteCuponDescuento(id);

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new ResponseStatusException(NOT_FOUND, "Cupón no encontrado");
        }

        return noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CuponDescuento>> updateCupon(
            @PathVariable int id,
            @RequestBody CuponDescuento cupon) {

        ResponseEntity<CuponDescuento> response = cuponService.updateCuponDescuento(id, cupon);

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new ResponseStatusException(NOT_FOUND, "Cupón no encontrado");
        }

        EntityModel<CuponDescuento> entityModel = assembler.toModel(response.getBody());
        return ok(entityModel);
    }

    @GetMapping("/porDescuento/{descuento}")
    public ResponseEntity<CollectionModel<EntityModel<CuponDescuento>>> getCuponesByDescuento(
            @PathVariable int descuento) {

        ResponseEntity<List<CuponDescuento>> response = cuponService.findByDescuento(descuento);

        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return noContent().build();
        }

        List<EntityModel<CuponDescuento>> models = response.getBody().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<CuponDescuento>> collection = CollectionModel.of(models)
                .add(linkTo(methodOn(this.getClass()).getCuponesByDescuento(descuento)).withSelfRel());

        return ok(collection);
    }
}