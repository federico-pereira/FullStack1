package duoc.proyect.service;

import duoc.proyect.model.Curso;
import duoc.proyect.model.Evaluacion;
import duoc.proyect.repositoy.CursoRepository;
import duoc.proyect.repositoy.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionService {

    @Autowired
    EvaluacionRepository evaluacionRepository;

    @Autowired
    CursoRepository cursoRepository;

    //Evaluaciones

    public ResponseEntity<List<Evaluacion>> getEvaluaciones() {
        List<Evaluacion> evaluaciones = evaluacionRepository.findAll();
        if (evaluaciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(evaluaciones);
    }

    public ResponseEntity<Object> addEvaluacion(Evaluacion evaluacion) {
        if (!evaluacionRepository.existsById(evaluacion.getId())) {
            evaluacionRepository.save(evaluacion);
            return ResponseEntity.status(HttpStatus.CREATED).body("Evaluacion creada\n"+evaluacion);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Evaluacion ya existente");
    }

    public ResponseEntity<Object> getEvaluacionById(int idEvaluacion) {
        Optional<Evaluacion> evaluacion = evaluacionRepository.findById(idEvaluacion);
        return evaluacion.<ResponseEntity<Object>>map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evaluacion no encontrada"));
    }

    public ResponseEntity<Object> deleteEvaluacion(int idEvaluacion) {
        if (evaluacionRepository.existsById(idEvaluacion)) {
            evaluacionRepository.deleteById(idEvaluacion);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Evaluacion eliminada");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Evaluacion no encontrada");
    }

    public ResponseEntity<Object> updateEvaluacion(Evaluacion evaluacion,int idEvaluacion) {
        evaluacion.setId(idEvaluacion);
        if (evaluacionRepository.existsById(idEvaluacion)) {
            evaluacionRepository.save(evaluacion);
            return ResponseEntity.ok("Evaluacion actualizada\n"+evaluacion);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evaluacion no existente en el sistema");

    }

    //Cursos

    public ResponseEntity<List<Curso>> getCursos(int idEvaluacion) {
        if (evaluacionRepository.existsById(idEvaluacion)) {
            return ResponseEntity.ok(evaluacionRepository.findById(idEvaluacion).get().getCursos());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public ResponseEntity<Object> addCurso(int idEvaluacion, int idCurso) {
        if (evaluacionRepository.existsById(idEvaluacion)) {
            Evaluacion evaluacion = evaluacionRepository.findById(idEvaluacion).get();
            Curso curso = cursoRepository.findById(idCurso).get();
            if (curso == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
            }
            if (evaluacion.getCursos().contains(curso)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Curso ya existente en la evaluacion");
            }
            List<Curso> listaCursos = evaluacion.getCursos();
            listaCursos.add(curso);
            evaluacion.setCursos(listaCursos);
            evaluacionRepository.save(evaluacion);
            return ResponseEntity.status(HttpStatus.OK).body("Evaluacion creada\n"+evaluacion);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evaluacion no encontrada");
    }

    public ResponseEntity<Object> deleteCurso(int idEvaluacion,int idCurso) {
        if (!evaluacionRepository.existsById(idEvaluacion)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evaluacion no existe en el sistema");
        }
        if (!evaluacionRepository.findById(idEvaluacion).get().getCursos().contains(cursoRepository.findById(idCurso).get())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no existe en la evaluacion");
        }
        evaluacionRepository.findById(idEvaluacion).get().getCursos().remove(cursoRepository.findById(idCurso).get());
        return ResponseEntity.ok("Curso eliminado");
    }
}
