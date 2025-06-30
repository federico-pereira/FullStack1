package duoc.proyect.repository;

import duoc.proyect.model.CuponDescuento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuponDescuentoRepository extends JpaRepository<CuponDescuento, Integer> {
    List<CuponDescuento> findByDescuento(int descuento);
}