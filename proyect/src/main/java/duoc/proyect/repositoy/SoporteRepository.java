package duoc.proyect.repositoy;

import duoc.proyect.model.Soporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoporteRepository extends JpaRepository<Soporte, Integer> {
}
