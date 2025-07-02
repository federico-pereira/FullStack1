package duoc.proyect.repository;

import duoc.proyect.model.DetalleEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleEvaluacionRepository extends JpaRepository<DetalleEvaluacion, Integer> {
    List<DetalleEvaluacion> findByAlumnoId(int alumnoId);
    List<DetalleEvaluacion> findByEvaluacionId(int evaluacionId);
}