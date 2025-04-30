package duoc.proyect.service;

import duoc.proyect.model.Alumno;
import duoc.proyect.repositoy.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoService {

    @Autowired
    AlumnoRepository alumnoRepository;

    public List<Alumno> getAllAlumnos(){return alumnoRepository.getAllAlumnos();}

    public Alumno getAlumnoById(int id){return alumnoRepository.getAlumnoById(id);}

    public String addAlumno(Alumno alumno){return alumnoRepository.addAlumno(alumno);}

    public String deleteAlumno(int id){return alumnoRepository.deleteAlumno(id);}

    public String updateAlumno(Alumno a){return alumnoRepository.updateAlumno(a);}
}
