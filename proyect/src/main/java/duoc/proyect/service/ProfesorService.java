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

    public List<Profesor> getAllProfesors(){return profesorRepository.getAllProfesores();}

    public Profesor getProfesorById(int id){return profesorRepository.getProfesorById(id);}

    public String addProfesor(Profesor Profesor){return profesorRepository.addProfesor(Profesor);}

    public String deleteProfesor(int id){return profesorRepository.deleteProfesor(id);}

    public String updateProfesor(Profesor a){return profesorRepository.updateProfesor(a);}
}