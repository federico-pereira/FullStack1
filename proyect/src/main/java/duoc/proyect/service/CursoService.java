package duoc.proyect.service;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Contenido;
import duoc.proyect.model.Curso;
import duoc.proyect.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    AlumnoService alumnoService;

    @Autowired
    ContenidoService contenidoService;

    // Cursos

    public ResponseEntity<List<Curso>> getCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        if (cursos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(cursos);
    }

    public ResponseEntity<Object> addCurso(Curso curso) {
        if (cursoRepository.existsById(curso.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        cursoRepository.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    public ResponseEntity<Object> getCursoById(int id) {
        if (cursoRepository.existsById(id)) {
            Curso curso = cursoRepository.findById(id).get();
            return ResponseEntity.status(HttpStatus.OK).body(curso);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Curso no encontrado");
    }

    public ResponseEntity<String> deleteCurso(int id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
            return ResponseEntity.ok("Curso eliminado con id: " + id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Curso no encontrado");
    }

    public ResponseEntity<String> updateCurso(Curso curso, int id) {
        // hacemos un setId a curso para coordinar las id (curso se crea con id automatica)
        curso.setId(id);
        if (cursoRepository.existsById(id)) {
            cursoRepository.save(curso);
            return ResponseEntity.ok("Curso actualizado con id: " + curso);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Curso no encontrado");
    }

    // Alumnos

    public ResponseEntity<List<Alumno>> getAlumnos(int idCurso) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Alumno> lista = cursoOpt.get().getListaCurso();
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(lista);
    }

    // CORREGIR FUNCION, NECESITA AGREGAR POR ID ALUMNO (DE LA FORMA ACTUAL LAS ID SON DISTINTAS)

    public ResponseEntity<String> addAlumno(int idCurso, int idAlumno) {
        if (alumnoService.getAlumnoById(idAlumno) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Alumno no encontrado en el sistema");
        }
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Curso no encontrado");
        }

        Curso curso = cursoOpt.get();
        Alumno alumno = (Alumno) alumnoService.getAlumnoById(idAlumno).getBody();
        List<Alumno> lista = curso.getListaCurso();
        if (lista.contains(alumno)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        lista.add(alumno);
        curso.setListaCurso(lista);
        cursoRepository.save(curso);  // persistir la asociación

        return ResponseEntity.ok("Alumno " + alumno.toString() + " agregado al curso: " + curso.getName() + " id: " + curso.getId());
    }

    public ResponseEntity<String> deleteAlumno(int idAlumno, int idCurso) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
        }
        Curso curso = cursoOpt.get();
        Alumno alumno = (Alumno) alumnoService.getAlumnoById(idAlumno).getBody();

        if (!curso.getListaCurso().contains(alumno)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno no encontrado en el curso");
        }
        List<Alumno> lista = curso.getListaCurso();
        lista.remove(alumno);
        curso.setListaCurso(lista);
        cursoRepository.save(curso);
        return ResponseEntity.ok("Alumno eliminado del curso: " + curso.getName() + " id: " + curso.getId());
    }

    // Contenido

    public ResponseEntity<List<Contenido>> getContenidos(int idCurso) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        List<Contenido> lista = cursoOpt.get().getListaContenido();
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(lista);
    }

    public ResponseEntity<String> addContenido(int idCurso, int idContenido) {
        // Verificar si el contenido existe en el sistema
        if (contenidoService.getContenidoById(idContenido)==null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Contenido no encontrado en el sistema");
        }

        // Buscar el curso
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Curso no encontrado");
        }
        Curso curso = cursoOpt.get();

        // Agregar el contenido al curso
        Contenido contenido = (Contenido) contenidoService.getContenidoById(idContenido).getBody();
        List<Contenido> lista = curso.getListaContenido();
        lista.add(contenido);
        curso.setListaContenido(lista);

        // Persistir la relación
        cursoRepository.save(curso);

        return ResponseEntity.ok("Contenido con id: " + contenido.getId() + " agregado al curso: " + curso.getName() + " id: " + curso.getId());
    }


    public ResponseEntity<String> deleteContenido(int idContenido, int idCurso) {
        // Buscar el curso
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Curso no encontrado en el sistema");
        }
        Curso curso = cursoOpt.get();
        Contenido contenidoTemp = (Contenido) contenidoService.getContenidoById(idContenido).getBody();
        if (!curso.getListaContenido().contains(contenidoTemp)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Contenido no encontrado en la lista de contenidos del curso");
        }

        // Eliminar el contenido del curso
        List<Contenido> lista = curso.getListaContenido();
        lista.remove(contenidoTemp);
        curso.setListaContenido(lista);

        // Persistir los cambios
        cursoRepository.save(curso);

        return ResponseEntity.ok("Contenido con id: " + idContenido + " eliminado del curso: " + curso.getName() + " id: " + curso.getId());
    }

    public ResponseEntity<String> updateCurso(int id, int id1) {
        return null;
    }
}
