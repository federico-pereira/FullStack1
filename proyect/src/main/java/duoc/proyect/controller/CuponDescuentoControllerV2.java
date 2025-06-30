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
    public CollectionModel<EntityModel<CuponDescuento>> getCupones() {
        List<EntityModel<CuponDescuento>> models = cuponService.getCuponesDescuento().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(models)
                .add(linkTo(methodOn(this.getClass()).getCupones()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CuponDescuento>> getCuponById(@PathVariable int id) {
        return cuponService.getCuponDescuentoById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<CuponDescuento> addCupon(@RequestBody CuponDescuento cupon) {
        CuponDescuento nuevoCupon = cuponService.addCuponDescuento(cupon);
        return assembler.toModel(nuevoCupon);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCuponById(@PathVariable int id) {
        cuponService.getCuponDescuentoById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cupón no encontrado"));

        cuponService.deleteCuponDescuento(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CuponDescuento>> updateCupon(
            @PathVariable int id,
            @RequestBody CuponDescuento cupon) {

        cuponService.getCuponDescuentoById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cupón no encontrado"));

        cupon.setId(id); // Asegurar que el ID coincida
        CuponDescuento actualizado = cuponService.updateCuponDescuento(id, cupon);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @GetMapping("/por-descuento/{descuento}")
    public ResponseEntity<CollectionModel<EntityModel<CuponDescuento>>> getCuponesByDescuento(
            @PathVariable int descuento) {

        List<CuponDescuento> cupones = cuponService.findByDescuento(descuento);

        if (cupones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<CuponDescuento>> models = cupones.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<CuponDescuento>> collection = CollectionModel.of(models)
                .add(linkTo(methodOn(this.getClass()).getCuponesByDescuento(descuento)).withSelfRel());

        return ResponseEntity.ok(collection);
    }
}