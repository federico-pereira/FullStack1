package duoc.proyect.controller;

import duoc.proyect.model.Alumno;
import duoc.proyect.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alumnos")
public class AlumnoController {

    @Autowired
    AlumnoService alumnoService;

    @GetMapping
    public ResponseEntity<List<Alumno>> getAlumnos() {
        return alumnoService.getAlumnos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAlumno(@PathVariable int id) {
        return alumnoService.getAlumnoById(id);
    }

    @PostMapping
    public ResponseEntity<String> createAlumno(@RequestBody Alumno alumno) {
        return alumnoService.addAlumno(alumno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAlumno(@PathVariable int id) {
        return alumnoService.deleteAlumno(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAlumno(@RequestBody Alumno alumno, @PathVariable int id) {
        return alumnoService.updateAlumno(id,alumno);
    }
}
