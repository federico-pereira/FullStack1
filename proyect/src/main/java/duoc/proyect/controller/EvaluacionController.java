package duoc.proyect.controller;

import duoc.proyect.model.Curso;
import duoc.proyect.model.Evaluacion;
import duoc.proyect.service.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/evaluaciones")
public class EvaluacionController {

    @Autowired
    EvaluacionService evaluacionService;

    //Evaluaciones

    @GetMapping
    public ResponseEntity<List<Evaluacion>> getEvaluaciones() {
        return evaluacionService.getEvaluaciones();
    }

    @PostMapping
    public ResponseEntity<Object> createEvaluacion(@RequestBody Evaluacion evaluacion) {
        return evaluacionService.addEvaluacion(evaluacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEvaluacionById(@PathVariable int id) {
        return evaluacionService.getEvaluacionById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvaluacionById(@PathVariable int id) {
        return evaluacionService.deleteEvaluacion(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEvaluacionById(@PathVariable int id, @RequestBody Evaluacion evaluacion) {
        return evaluacionService.updateEvaluacion(evaluacion, id);
    }

    //Cursos

    @GetMapping("/{id}/cursos")
    public ResponseEntity<List<Curso>> getCursosEvaluaciones(@PathVariable int id) {
        return evaluacionService.getCursos(id);
    }

    @PostMapping("/{id}/cursos/{idCurso}")
    public ResponseEntity<Object> addCurso(@PathVariable int id, @PathVariable int idCurso) {
        return evaluacionService.addCurso(id, idCurso);
    }

    @DeleteMapping("/{idEvaluacion}/cursos/{idCurso}")
    public ResponseEntity<Object> deleteCurso(@PathVariable int idEvaluacion, @PathVariable int idCurso) {
        return evaluacionService.deleteCurso(idEvaluacion, idCurso);
    }
}
