package duoc.proyect.service;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Curso;
import duoc.proyect.repositoy.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;

@Service
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;

    //Cursos

    public String saveCurso(Curso curso) {
        return cursoRepository.addCurso(curso);
    }

    public String getAllCursos() {
        return cursoRepository.getCursos();
    }

    public String getCursoById(int id) {
        return cursoRepository.getCursoById(id);
    }

    public String deleteCurso(int id) {
        return cursoRepository.deleteCurso(id);
    }

    public String updateCurso(Curso curso) {
        return cursoRepository.updateCurso(curso);
    }

    // Alumnos

    public String addAlumno(Alumno alumno) {
        return cursoRepository.addAlumno(alumno);
    }

    public String deleteAlumno(int id) {
        return cursoRepository.deleteAlumno(id);
    }

}
