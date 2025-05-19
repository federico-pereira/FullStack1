package duoc.proyect.controller;

import duoc.proyect.model.CuponDescuento;
import duoc.proyect.service.CuponDescuentoSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cupones")
public class CuponDescuentoController {

    @Autowired
    CuponDescuentoSevice cuponDescuentoSevice;

    @GetMapping
    public ResponseEntity<List<CuponDescuento>> getCupones() {
        return cuponDescuentoSevice.getCuponesDescuento();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCuponById(@PathVariable int id) {
        return cuponDescuentoSevice.getCuponDescuentoById(id);
    }

    @PostMapping
    public ResponseEntity<Object> addCupon(@RequestBody CuponDescuento cupon) {
        return cuponDescuentoSevice.addCuponDescuento(cupon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCuponById(@PathVariable int id) {
        return cuponDescuentoSevice.deleteCuponDescuento(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCupon(@PathVariable int id, @RequestBody CuponDescuento cupon) {
        return cuponDescuentoSevice.updateCuponDescuento(id, cupon);
    }
}
