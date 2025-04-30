package duoc.proyect.controller;

import duoc.proyect.model.Alumno;
import duoc.proyect.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alumnos")
public class AlumnoController {

    @Autowired
    AlumnoService alumnoService;

    @GetMapping
    public List<Alumno> getAlumnos() {
        return alumnoService.getAllAlumnos();
    }

    @GetMapping("/{id}")
    public Alumno getAlumno(@PathVariable int id) {
        return alumnoService.getAlumnoById(id);
    }

    @PostMapping
    public String createAlumno(@RequestBody Alumno alumno) {
        return alumnoService.addAlumno(alumno);
    }

    @DeleteMapping("/{id}")
    public String deleteAlumno(@PathVariable int id) {
        return alumnoService.deleteAlumno(id);
    }

    @PatchMapping("/{id}")
    public String updateAlumno(@RequestBody Alumno alumno) {
        return alumnoService.updateAlumno(alumno);
    }
}
