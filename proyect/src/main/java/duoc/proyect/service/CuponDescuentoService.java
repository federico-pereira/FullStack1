package duoc.proyect.service;

import duoc.proyect.model.CuponDescuento;
import duoc.proyect.repository.CuponDescuentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuponDescuentoService {

    @Autowired
    private CuponDescuentoRepository cuponDescuentoRepository;

    public ResponseEntity<List<CuponDescuento>> getCuponesDescuento() {
        List<CuponDescuento> cupones = cuponDescuentoRepository.findAll();
        if (cupones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cupones);
    }

    public ResponseEntity<CuponDescuento> getCuponDescuentoById(int id) {
        Optional<CuponDescuento> cupon = cuponDescuentoRepository.findById(id);
        if (cupon.isPresent()) {
            return ResponseEntity.ok(cupon.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<CuponDescuento> addCuponDescuento(CuponDescuento cuponDescuento) {

        // Si se proporciona ID, verificar si ya existe
        if (cuponDescuentoRepository.existsById(cuponDescuento.getId())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(cuponDescuento);
        }


        // Guardar el cupón
        CuponDescuento savedCupon = cuponDescuentoRepository.save(cuponDescuento);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedCupon);
    }

    public ResponseEntity<Void> deleteCuponDescuento(int id) {
        if (!cuponDescuentoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cuponDescuentoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<CuponDescuento> updateCuponDescuento(int id, CuponDescuento updateCuponDescuento) {
        if (!cuponDescuentoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updateCuponDescuento.setId(id);
        CuponDescuento updated = cuponDescuentoRepository.save(updateCuponDescuento);
        return ResponseEntity.ok(updated);
    }

    public ResponseEntity<List<CuponDescuento>> findByDescuento(int descuento) {
        List<CuponDescuento> cupones = cuponDescuentoRepository.findByDescuento(descuento);
        if (cupones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cupones);
    }
}