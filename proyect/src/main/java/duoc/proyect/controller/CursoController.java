package duoc.proyect.controller;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Contenido;
import duoc.proyect.model.Curso;
import duoc.proyect.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {

    @Autowired
    CursoService cursoService;

    // Cursos

    @GetMapping
    public ResponseEntity<List<Curso>> getCursos() {
        return cursoService.getCursos();
    }

    @PostMapping
    public ResponseEntity<Object> addCurso(@RequestBody Curso curso) {
        return cursoService.addCurso(curso);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> getCursoById(@PathVariable int id) {
        return cursoService.getCursoById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCurso(@PathVariable int id) {
        return cursoService.deleteCurso(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCurso(@RequestBody Curso curso, @PathVariable int id) {
        return cursoService.updateCurso(curso,id);
    }

    //Alumo

    @GetMapping("/{idCurso}/alumnos")
    public ResponseEntity<List<Alumno>> getAlumnos(@PathVariable int idCurso) {
        return cursoService.getAlumnos(idCurso);
    }

    @DeleteMapping("/{idCurso}/alumnos/{id}")
    public ResponseEntity<String> deleteAlumno(@PathVariable int idCurso, @PathVariable int id) {
        return cursoService.deleteAlumno(idCurso,id);
    }

    @PostMapping("/{idCurso}/alumnos")
    public ResponseEntity<String> addAlumno(@PathVariable int idCurso, @RequestBody Alumno alumno) {
        return cursoService.addAlumno(idCurso,alumno);
    }

    //Contenido

    @GetMapping("/{idCruso}/contenidos")
    public ResponseEntity<List<Contenido>> getContenidos(@PathVariable int idCurso) {return cursoService.getContenidos(idCurso);}

    @DeleteMapping("/{idCruso}/contenidos/{id}")
    public ResponseEntity<String> deleteContenido(@PathVariable int idCurso, @PathVariable int id) {
        return cursoService.deleteContenido(idCurso,id);
    }

    @PostMapping("/{idCruso}")
    public ResponseEntity<String> addContenido(@PathVariable int idCurso, @RequestBody Contenido contenido) {
        return cursoService.addContenido(idCurso,contenido);
    }
}
