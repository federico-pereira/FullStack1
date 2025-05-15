package duoc.proyect.service;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Alumno;
import duoc.proyect.repositoy.AlumnoRepository;
import duoc.proyect.repositoy.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoService {

    @Autowired
    AlumnoRepository alumnoRepository;

    public String getAlumnos(){
        String output = "";
        for(Alumno alumno : alumnoRepository.findAll()){
            output += alumno.toString() + "\n";
        }
        if(output.isEmpty()){
            return "No hay Alumnoes.";
        }else {
            return output;
        }
    }

    public String addAlumno(Alumno alumno){
        alumnoRepository.save(alumno);
        return "Alumno agregado";
    }

    public String getAlumnoById(int id){
        for (Alumno alumno : alumnoRepository.findAll()) {
            if (alumno.getId() == id) {
                return alumno.toString();
            }
        }
        return "Alumno no encontrado";
    }

    public String deleteAlumno(int id){
        if(alumnoRepository.existsById(id)){
            alumnoRepository.deleteById(id);
            return "Alumno eliminado";
        }else {
            return "Alumno no encontrado";
        }
    }

    public String updateAlumno(Alumno newAlumno, int id){
        if(alumnoRepository.existsById(id)){
            for(Alumno alumno : alumnoRepository.findAll()){
                if(alumno.getId() == id){
                    alumno.setName(newAlumno.getName());
                    alumno.setMail(newAlumno.getMail());
                    alumnoRepository.save(alumno);
                }
            }
            return "Alumno actualizado "+newAlumno.toString();
        }else{
            return "Alumno no encontrado";
        }
    }
}
