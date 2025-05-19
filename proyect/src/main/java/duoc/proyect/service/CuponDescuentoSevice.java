package duoc.proyect.service;

import duoc.proyect.model.CuponDescuento;
import duoc.proyect.repositoy.CuponDescuentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuponDescuentoSevice {

    @Autowired
    CuponDescuentoRepository cuponDescuentoRepository;

    public ResponseEntity<List<CuponDescuento>> getCuponesDescuento() {
        List<CuponDescuento> listaCupones = cuponDescuentoRepository.findAll();
        if (listaCupones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listaCupones, HttpStatus.OK);
    }

    public ResponseEntity<Object> getCuponDescuentoById(int id) {
        Optional<CuponDescuento> cuponDescuento = cuponDescuentoRepository.findById(id);
        if (cuponDescuento.isPresent()) {
            return new ResponseEntity<>(cuponDescuento.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> addCuponDescuento(CuponDescuento cuponDescuento) {
        cuponDescuentoRepository.save(cuponDescuento);
        return new ResponseEntity<>("Cupon creado "+cuponDescuento, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deleteCuponDescuento(int id) {
        Optional<CuponDescuento> cuponDescuento = cuponDescuentoRepository.findById(id);
        if (cuponDescuento.isPresent()) {
            cuponDescuentoRepository.deleteById(id);
            return new ResponseEntity<>("Cupon eliminado "+cuponDescuento, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> updateCuponDescuento(int id, CuponDescuento updateCuponDescuento) {
        if (cuponDescuentoRepository.findById(id).isPresent()) {
            updateCuponDescuento.setId(id);
            cuponDescuentoRepository.save(updateCuponDescuento);
            return new ResponseEntity<>("Cupon actualizado "+updateCuponDescuento, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
