package duoc.proyect.controller;

import duoc.proyect.model.Profesor;
import duoc.proyect.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profesores")
public class ProfesorController {

    @Autowired
    ProfesorService profesorService;

    @GetMapping
    public ResponseEntity<List<Profesor>> getProfesors() {
        return profesorService.getProfesores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProfesor(@PathVariable int id) {
        return profesorService.getProfesorById(id);
    }

    @PostMapping
    public ResponseEntity<Profesor> createProfesor(@RequestBody Profesor Profesor) {
        return profesorService.addProfesor(Profesor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfesor(@PathVariable int id) {
        return profesorService.deleteProfesor(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profesor> updateProfesor(@RequestBody Profesor profesor, @PathVariable int id) {
        return profesorService.updateProfesor(profesor, id);
    }
}
