package duoc.proyect.controller;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Contenido;
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

    @GetMapping
    public String getCursos() {
        return cursoService.getCursos();
    }

    @PostMapping
    public String addCurso(@RequestBody Curso curso) {
        return cursoService.addCurso(curso);
    }

    @GetMapping("/{id}")
    public String getCursoById(@PathVariable int id) {
        return cursoService.getCursoById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteCurso(@PathVariable int id) {
        return cursoService.deleteCurso(id);
    }

    @PutMapping
    public String updateCurso(@RequestBody Curso curso) {
        return cursoService.updateCurso(curso);
    }

    //Alumo

    @GetMapping("/{idCurso}/alumnos")
    public String getAlumnos(@PathVariable int idCurso) {
        return cursoService.getAlumnos(idCurso);
    }

    @DeleteMapping("/{idCurso}/alumnos/{id}")
    public String deleteAlumno(@PathVariable int idCurso, @PathVariable int id) {
        return cursoService.deleteAlumno(idCurso,id);
    }

    @PostMapping("/{idCurso}/alumnos")
    public String addAlumno(@PathVariable int idCurso, @RequestBody Alumno alumno) {
        return cursoService.addAlumno(idCurso,alumno);
    }

    //Contenido

    @GetMapping("/{idCruso}/contenidos")
    public String getContenidos(@PathVariable int idCurso) {return cursoService.getContenidos(idCurso);}

    @DeleteMapping("/{idCruso}/contenidos/{id}")
    public String deleteContenido(@PathVariable int idCurso, @PathVariable int id) {
        return cursoService.deleteContenido(idCurso,id);
    }

    @PostMapping("/{idCruso}")
    public String addContenido(@PathVariable int idCurso, @RequestBody Contenido contenido) {
        return cursoService.addContenido(idCurso,contenido);
    }
}
