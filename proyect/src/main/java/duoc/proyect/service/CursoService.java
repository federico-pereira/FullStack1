package duoc.proyect.service;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Contenido;
import duoc.proyect.model.Curso;
import duoc.proyect.repositoy.AlumnoRepository;
import duoc.proyect.repositoy.ContenidoRepository;
import duoc.proyect.repositoy.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    AlumnoRepository alumnoRepository;

    @Autowired
    ContenidoRepository contenidoRepository;

    // Cursos

    public ResponseEntity<List<Curso>> getCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        if (cursos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(cursos);
    }

    public ResponseEntity<Object> addCurso(Curso curso) {
        if (!cursoRepository.existsById(curso.getId())) {
            cursoRepository.save(curso);
            return ResponseEntity.status(HttpStatus.CREATED).body("Curso creado\n"+curso);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Curso ya existente");
    }

    public ResponseEntity<Curso> getCursoById(int id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        if (curso.isPresent()) {
            return ResponseEntity.ok(curso.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public ResponseEntity<String> deleteCurso(int id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
            return ResponseEntity.ok("Curso eliminado con id: " + id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
    }

    public ResponseEntity<String> updateCurso(Curso curso,int id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.save(curso);
            return ResponseEntity.ok("Curso actualizado con id: " + curso);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
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

    public ResponseEntity<String> addAlumno(int idCurso, Alumno alumno) {
        if (!alumnoRepository.existsById(alumno.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno no encontrado en el sistema");
        }
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
        }
        Curso curso = cursoOpt.get();
        curso.addAlumno(alumno);
        cursoRepository.save(curso);  // persistir la asociación
        return ResponseEntity.ok("Alumno " + alumno.toString() + " agregado al curso: " + curso.getName() + " id: " + curso.getId());
    }

    public ResponseEntity<String> deleteAlumno(int idAlumno, int idCurso) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
        }
        Curso curso = cursoOpt.get();
        Optional<Alumno> alumnoOpt = curso.getListaCurso().stream()
                .filter(a -> a.getId() == idAlumno)
                .findFirst();

        if (alumnoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno no encontrado en el curso");
        }

        curso.removeAlumno(alumnoOpt.get());
        cursoRepository.save(curso);
        return ResponseEntity.ok("Alumno eliminado del curso: " + curso.getName() + " id: " + curso.getId());
    }

    // Contenido

    public ResponseEntity<List<Contenido>> getContenidos(int idCurso) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Contenido> lista = cursoOpt.get().getListaContenido();
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(lista);
    }

    public ResponseEntity<String> addContenido(int idCurso, Contenido contenido) {
        // Verificar si el contenido existe en el sistema
        if (!contenidoRepository.existsById(contenido.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contenido no encontrado en el sistema");
        }

        // Buscar el curso
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado");
        }
        Curso curso = cursoOpt.get();

        // Agregar el contenido al curso
        curso.addContenido(contenido);

        // Persistir la relación
        cursoRepository.save(curso);

        return ResponseEntity.ok("Contenido con id: " + contenido.getId() + " agregado al curso: " + curso.getName() + " id: " + curso.getId());
    }


    public ResponseEntity<String> deleteContenido(int idContenido, int idCurso) {
        // Buscar el curso
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (cursoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado en el sistema");
        }
        Curso curso = cursoOpt.get();

        // Buscar el contenido en la lista del curso
        Optional<Contenido> contenidoOpt = curso.getListaContenido().stream()
                .filter(c -> c.getId() == idContenido)
                .findFirst();

        if (contenidoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contenido no encontrado en el curso");
        }

        // Eliminar el contenido del curso
        curso.removeContenido(contenidoOpt.get());

        // Persistir los cambios
        cursoRepository.save(curso);

        return ResponseEntity.ok("Contenido con id: " + idContenido + " eliminado del curso: " + curso.getName() + " id: " + curso.getId());
    }

}
