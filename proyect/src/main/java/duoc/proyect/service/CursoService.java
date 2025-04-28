package duoc.proyect.service;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Curso;
import duoc.proyect.repositoy.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;

    public String saveCurso(Alumno alumno) {
        return cursoRepository.addAlumno(alumno);
    }

    public String getAllCursos() {
        return cursoRepository.getAlumnos();
    }

    public String getCursoById(int id) {
        return cursoRepository.getAlumnos();
    }
}
