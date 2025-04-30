package duoc.proyect.service;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Curso;
import duoc.proyect.repositoy.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.List;

@Service
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    AlumnoService alumnoService;

    @Autowired
    CursoService cursoService;
    //Cursos

    public String saveCurso(Curso curso) {
        return cursoRepository.addCurso(curso);
    }

    public List<Curso> getAllCursos() {
        return cursoRepository.getAllCursos();
    }

    public Curso getCursoById(int id) {
        return cursoRepository.getCursoById(id);
    }

    public String deleteCurso(int id) {
        return cursoRepository.deleteCurso(id);
    }

    public String updateCurso(Curso curso) {
        return cursoRepository.updateCurso(curso);
    }


    // Alumnos

    public String getAlumnos(int idCurso) {
        return cursoRepository.getAlumnos(idCurso);
    }

    public String addAlumno(int idCurso,Alumno alumno) {
        if (alumnoService.getAllAlumnos().contains(alumno)) {
            return cursoRepository.addAlumno(idCurso,alumno);
        }else{
            return "Alumno no encontrado";
        }
    }

    public String deleteAlumno(int id, int idCurso) {
        return cursoRepository.deleteAlumno(idCurso,id);
    }

    // Contenido

    public String getContenido(int idCurso) {
        return cursoRepository.getContenidos(idCurso);
    }
}
