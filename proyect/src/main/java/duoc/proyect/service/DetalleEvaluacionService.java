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
    private DetalleEvaluacionRepository detalleRepository;

    public ResponseEntity<List<DetalleEvaluacion>> getAllDetalles() {
        List<DetalleEvaluacion> detalles = detalleRepository.findAll();
        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detalles);
    }

    public ResponseEntity<DetalleEvaluacion> getDetalleById(int id) {
        Optional<DetalleEvaluacion> detalle = detalleRepository.findById(id);
        return detalle.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<DetalleEvaluacion> addDetalle(DetalleEvaluacion detalle) {
        if (detalleRepository.existsById(detalle.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        DetalleEvaluacion savedDetalle = detalleRepository.save(detalle);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDetalle);
    }

    public ResponseEntity<DetalleEvaluacion> updateDetalle(int id, DetalleEvaluacion detalle) {
        if (!detalleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        detalle.setId(id);
        DetalleEvaluacion updatedDetalle = detalleRepository.save(detalle);
        return ResponseEntity.ok(updatedDetalle);
    }

    public ResponseEntity<Void> deleteDetalle(int id) {
        if (!detalleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        detalleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    public ResponseEntity<List<DetalleEvaluacion>> getDetallesByAlumno(int alumnoId) {
        List<DetalleEvaluacion> detalles = detalleRepository.findByAlumnoId(alumnoId);
        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detalles);
    }

    public ResponseEntity<List<DetalleEvaluacion>> getDetallesByEvaluacion(int evaluacionId) {
        List<DetalleEvaluacion> detalles = detalleRepository.findByEvaluacionId(evaluacionId);
        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detalles);
    }
}