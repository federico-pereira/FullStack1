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

    /*

    public String getAlumnos(int idCurso) {
        return cursoRepository.getAlumnos(idCurso);
    }

    public String addAlumno(int idCurso,Alumno alumno) {
        return cursoRepository.addAlumno(idCurso,alumno);
    }

    public String deleteAlumno(int id, int idCurso) {
        return cursoRepository.deleteAlumno(idCurso,id);
    }

     */
}
