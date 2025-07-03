package duoc.proyect.service;

import duoc.proyect.model.Profesor;
import duoc.proyect.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesorService {

    @Autowired
    ProfesorRepository profesorRepository;

    public ResponseEntity<List<Profesor>> getProfesores() {
        List<Profesor> profesores = profesorRepository.findAll();
        if (profesores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(profesores);
    }

    public ResponseEntity<Profesor> addProfesor(Profesor profesor) {
        Profesor saved = profesorRepository.save(profesor);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    public ResponseEntity<Object> getProfesorById(int id) {
        Optional<Profesor> profesor = profesorRepository.findById(id);
        if (profesor.isPresent()) {
            return ResponseEntity.ok(profesor.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<String> deleteProfesor(int id) {
        if (profesorRepository.existsById(id)) {
            profesorRepository.deleteById(id);
            return ResponseEntity.ok("Profesor eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profesor no encontrado");
        }
    }

    public ResponseEntity<Profesor> updateProfesor(Profesor newProfesor, int id) {
        Optional<Profesor> profesorOpt = profesorRepository.findById(id);
        if (profesorOpt.isPresent()) {
            Profesor profesor = profesorOpt.get();
            profesor.setName(newProfesor.getName());
            profesor.setMail(newProfesor.getMail());
            Profesor updated = profesorRepository.save(profesor);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
