package duoc.proyect.service;

import duoc.proyect.model.Contenido;
import duoc.proyect.repository.ContenidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContenidoService {

    @Autowired
    private ContenidoRepository contenidoRepository;

    public ResponseEntity<List<Contenido>> getContenidos() {
        List<Contenido> contenidos = contenidoRepository.findAll();
        if (contenidos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(contenidos);
    }

    public ResponseEntity<Contenido> addContenido(Contenido contenido) {
        Contenido saved = contenidoRepository.save(contenido);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    public ResponseEntity<Contenido> getContenidoById(int id) {
        Optional<Contenido> contenidoOpt = contenidoRepository.findById(id);
        if (contenidoOpt.isPresent()) {
            return ResponseEntity.ok(contenidoOpt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public ResponseEntity<Contenido> updateContenido(int id, Contenido contenido) {
        Optional<Contenido> contenidoOpt = contenidoRepository.findById(id);
        if (contenidoOpt.isPresent()) {
            contenido.setId(id);
            Contenido updated = contenidoRepository.save(contenido);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public ResponseEntity<String> deleteContenido(int id) {
        if (contenidoRepository.existsById(id)) {
            contenidoRepository.deleteById(id);
            return ResponseEntity.ok("Contenido eliminado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contenido no encontrado");
    }
}
