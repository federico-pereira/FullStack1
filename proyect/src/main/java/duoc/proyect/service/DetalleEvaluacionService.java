package duoc.proyect.service;

import duoc.proyect.model.DetalleEvaluacion;
import duoc.proyect.repository.DetalleEvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleEvaluacionService {

    @Autowired
    DetalleEvaluacionRepository detalleEvaluacionRepository;

    EvaluacionService evaluacionService;

    AlumnoService alumnoService;

    public ResponseEntity<List<DetalleEvaluacion>> getDetalleEvaluaciones() {
        List<DetalleEvaluacion> detalleEvaluaciones = detalleEvaluacionRepository.findAll();
        if (detalleEvaluaciones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(detalleEvaluaciones, HttpStatus.OK);
    }

    public ResponseEntity<Object> getDetalleEvaluacionById(Integer id) {
        Optional<DetalleEvaluacion> detalleEvaluacion = detalleEvaluacionRepository.findById(id);
        if (detalleEvaluacion.isPresent()) {
            return new ResponseEntity<>(detalleEvaluacion.get(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Detalle Evaluacion no encontrada");
    }

    public ResponseEntity<Object> addDetalleEvaluacion(DetalleEvaluacion detalleEvaluacion) {
        if (detalleEvaluacionRepository.existsById(detalleEvaluacion.getId())) {
            return new ResponseEntity<>("Detalle evaluacion ya existe en el sistema",HttpStatus.CONFLICT);
        }
        detalleEvaluacionRepository.save(detalleEvaluacion);
        return new ResponseEntity<>("Detalle evaluacion creado",HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deleteDetalleEvaluacion(Integer id) {
        Optional<DetalleEvaluacion> detalleEvaluacion = detalleEvaluacionRepository.findById(id);
        if (detalleEvaluacion.isPresent()) {
            detalleEvaluacionRepository.deleteById(id);
            return new ResponseEntity<>("Detalle evaluacion eliminado",HttpStatus.OK);
        }
        return new ResponseEntity<>("Detalle evaluacion no se encuentra en el sistema",HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> updateDetalleEvaluacion(int id,DetalleEvaluacion detalleEvaluacion) {
        if (detalleEvaluacionRepository.existsById(id)) {
            detalleEvaluacion.setId(id);
            detalleEvaluacionRepository.save(detalleEvaluacion);
            return new ResponseEntity<>("Detalle evaluacion actualizado",HttpStatus.OK);
        }
        return new ResponseEntity<>("Detalle evaluacion no se encuentra en el sistema",HttpStatus.NO_CONTENT);
    }
}
