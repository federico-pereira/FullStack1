package duoc.proyect.service;

import duoc.proyect.model.Alumno;
import duoc.proyect.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {

    @Autowired
    AlumnoRepository alumnoRepository;

    public ResponseEntity<List<Alumno>> getAlumnos() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        if (alumnos.isEmpty()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(alumnos);
    }

    public ResponseEntity<Object> addAlumno(Alumno alumno) {
        if (alumnoRepository.existsById(alumno.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        alumnoRepository.save(alumno);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(alumno);
    }

    public ResponseEntity<Object> getAlumnoById(int id) {
        Optional<Alumno> alumnoOpt = alumnoRepository.findById(id);
        if (alumnoOpt.isPresent()) {
            return ResponseEntity.ok(alumnoOpt.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> deleteAlumno(int id) {
        if (alumnoRepository.existsById(id)) {
            alumnoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Alumno con id " + id + " no encontrado");
    }

    public ResponseEntity<String> updateAlumno(int id, Alumno newAlumno) {
        Optional<Alumno> alumnoOpt = alumnoRepository.findById(id);
        if (alumnoOpt.isPresent()) {
            Alumno alumno = alumnoOpt.get();
            alumno.setName(newAlumno.getName());
            alumno.setMail(newAlumno.getMail());
            // Puedes agregar más campos a actualizar si quieres
            alumnoRepository.save(alumno);
            return ResponseEntity.ok("Alumno actualizado: " + alumno.toString());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
