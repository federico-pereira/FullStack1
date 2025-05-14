package duoc.proyect.controller;

import duoc.proyect.model.Profesor;
import duoc.proyect.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profesores")
public class ProfesorController {

    @Autowired
    ProfesorService profesorService;

    @GetMapping
    public String getProfesors() {
        return profesorService.getProfesores();
    }

    @GetMapping("/{id}")
    public String getProfesor(@PathVariable int id) {
        return profesorService.getProfesorById(id);
    }

    @PostMapping
    public String createProfesor(@RequestBody Profesor Profesor) {
        return profesorService.addProfesor(Profesor);
    }

    @DeleteMapping("/{id}")
    public String deleteProfesor(@PathVariable int id) {
        return profesorService.deleteProfesor(id);
    }

    @PutMapping("/{id}")
    public String updateProfesor(@RequestBody Profesor profesor,@PathVariable int id) {
        return profesorService.updateProfesor(profesor,id);
    }
}
