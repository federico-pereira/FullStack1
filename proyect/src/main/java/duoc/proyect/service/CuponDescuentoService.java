package duoc.proyect.service;

import duoc.proyect.model.CuponDescuento;
import duoc.proyect.repository.CuponDescuentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuponDescuentoService {

    @Autowired
    CuponDescuentoRepository cuponDescuentoRepository;

    public List<CuponDescuento> getCuponesDescuento() {
        return cuponDescuentoRepository.findAll();
    }

    public Optional<CuponDescuento> getCuponDescuentoById(int id) {
        return cuponDescuentoRepository.findById(id);
    }

    public CuponDescuento addCuponDescuento(CuponDescuento cuponDescuento) {
        return cuponDescuentoRepository.save(cuponDescuento);
    }

    public void deleteCuponDescuento(int id) {
        cuponDescuentoRepository.deleteById(id);
    }

    public CuponDescuento updateCuponDescuento(int id, CuponDescuento updateCuponDescuento) {
        updateCuponDescuento.setId(id);
        return cuponDescuentoRepository.save(updateCuponDescuento);
    }

    public List<CuponDescuento> findByDescuento(int descuento) {
        return cuponDescuentoRepository.findByDescuento(descuento);
    }


}