package duoc.proyect.controller;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Curso;
import duoc.proyect.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CursoController {

    @Autowired
    CursoService cursoService;

    @GetMapping("/cursos")
    public String getCurso() {
        return cursoService.getAllCursos();
    }

    @PostMapping("/cursos")
    public String addCurso(@RequestBody Alumno alumno) {
        return cursoService.saveCurso(alumno);
    }

    @GetMapping("/cursos/{id}")
    public String getCursos(@PathVariable int id) {
        return cursoService.getAllCursos();
    }
}
