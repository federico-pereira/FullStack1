package duoc.proyect.repositoy;

import duoc.proyect.model.TicketSoporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketSoporteRepository extends JpaRepository<TicketSoporte, Integer> {
}
