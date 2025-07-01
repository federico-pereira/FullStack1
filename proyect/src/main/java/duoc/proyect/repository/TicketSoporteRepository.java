package duoc.proyect.repository;

import duoc.proyect.model.TicketSoporte;

import duoc.proyect.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketSoporteRepository extends JpaRepository<TicketSoporte, Integer> {
    // Usa el nombre correcto de la propiedad (reclamante) y su campo (rut)
    @Query("SELECT t FROM TicketSoporte t WHERE t.reclamante.rut = :rut")
    List<TicketSoporte> findByReclamanteRut(@Param("rut") String rut);
}
