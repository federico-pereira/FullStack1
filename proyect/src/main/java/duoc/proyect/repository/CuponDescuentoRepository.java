package duoc.proyect.repository;

import duoc.proyect.model.CuponDescuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuponDescuentoRepository extends JpaRepository<CuponDescuento, Integer> {
}
