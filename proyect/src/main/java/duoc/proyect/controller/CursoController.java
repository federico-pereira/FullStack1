package duoc.proyect.controller;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Curso;
import duoc.proyect.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {

    @Autowired
    CursoService cursoService;

    // Cursos

    @GetMapping("/cursos")
    public String getCurso() {
        return cursoService.getAllCursos();
    }

    @PostMapping("/cursos")
    public String addCurso(@RequestBody Curso curso) {
        return cursoService.saveCurso(curso);
    }

    @GetMapping("/cursos/{id}")
    public String getCursos(@PathVariable int id) {
        return cursoService.getCursoById(id);
    }

    @DeleteMapping("/cursos/{id}")
    public String deleteCurso(@PathVariable int id) {
        return cursoService.deleteCurso(id);
    }

    @PatchMapping
    public String updateCurso(@RequestBody Curso curso) {
        return cursoService.updateCurso(curso);
    }

    //Alumo

    /*
    @DeleteMapping
    public String deleteAlumno(@PathVariable int id) {
        return cursoService.deleteAlumno(id);
    }

    @PatchMapping
    public String addAlumno(@RequestBody Alumno alumno) {
        return cursoService.addAlumno(alumno);
    }
     */
}
