package duoc.proyect.service;

import duoc.proyect.model.Profesor;
import duoc.proyect.repositoy.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorService {

    @Autowired
    ProfesorRepository profesorRepository;

    public String getProfesores(){
        String output = "";
        for(Profesor profesor : profesorRepository.findAll()){
            output += profesor.toString() + "\n";
        }
        if(output.isEmpty()){
            return "No hay profesores.";
        }else {
            return output;
        }
    }

    public String addProfesor(Profesor profesor){
        profesorRepository.save(profesor);
        return "Profesor agregado";
    }

    public String getProfesorById(int id){
        for (Profesor profesor : profesorRepository.findAll()) {
            if (profesor.getId() == id) {
                return profesor.toString();
            }
        };
        return "Profesor no encontrado";
    }

    public String deleteProfesor(int id){
        if(profesorRepository.existsById(id)){
            profesorRepository.deleteById(id);
            return "Profesor eliminado";
        }else {
            return "Profesor no encontrado";
        }
    }

    public String updateProfesor(Profesor newProfesor, int id){
        if(profesorRepository.existsById(id)){
            for(Profesor profesor : profesorRepository.findAll()){
                if(profesor.getId() == id){
                    profesor.setName(newProfesor.getName());
                    profesor.setEmail(newProfesor.getEmail());
                    profesorRepository.save(profesor);
                }
            }
            return "Profesor actualizado "+newProfesor.toString();
        }else{
            return "Profesor no encontrado";
        }
    }
}