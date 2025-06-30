package duoc.proyect.controller;

import duoc.proyect.model.CuponDescuento;
import duoc.proyect.service.CuponDescuentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cupones")
public class CuponDescuentoController {

    private final CuponDescuentoService cuponDescuentoService; // Nombre corregido

    @Autowired
    public CuponDescuentoController(CuponDescuentoService cuponDescuentoService) {
        this.cuponDescuentoService = cuponDescuentoService;
    }

    @GetMapping
    public List<CuponDescuento> getCupones() {
        return cuponDescuentoService.getCuponesDescuento(); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuponDescuento> getCuponById(@PathVariable int id) {
        Optional<CuponDescuento> cupon = cuponDescuentoService.getCuponDescuentoById(id);
        return cupon.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cupón no encontrado"));
    }

    @PostMapping
    public ResponseEntity<CuponDescuento> addCupon(@RequestBody CuponDescuento cupon) {
        CuponDescuento nuevoCupon = cuponDescuentoService.addCuponDescuento(cupon);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCupon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuponById(@PathVariable int id) {
        return cuponDescuentoService.getCuponDescuentoById(id)
                .map(cupon -> {
                    cuponDescuentoService.deleteCuponDescuento(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cupón no encontrado"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuponDescuento> updateCupon(
            @PathVariable int id,
            @RequestBody CuponDescuento cupon) {

        return cuponDescuentoService.getCuponDescuentoById(id)
                .map(existing -> {
                    cupon.setId(id);
                    CuponDescuento actualizado = cuponDescuentoService.updateCuponDescuento(id, cupon);
                    return ResponseEntity.ok(actualizado);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cupón no encontrado"));
    }
}