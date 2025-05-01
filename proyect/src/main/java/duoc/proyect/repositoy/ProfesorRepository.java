package duoc.proyect.repositoy;

import duoc.proyect.model.Profesor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProfesorRepository {

    private List<Profesor> profesores = new ArrayList<Profesor>();

    public ProfesorRepository() {
        profesores.add(new Profesor(1, "testNombre1", "testEmail1"));
        profesores.add(new Profesor(2, "testNombre2", "testEmail2"));
        profesores.add(new Profesor(3, "testNombre3", "testEmail3"));
    }

    public List<Profesor> getAllProfesores() {
        if (profesores.isEmpty()) {
            return null;
        }else{
            return profesores;
        }
    }

    public Profesor getProfesorById(int id) {
        for (Profesor Profesor : profesores) {
            if (Profesor.getId() == id) {
                return Profesor;
            }
        }
        return null;
    }

    public String addProfesor(Profesor Profesor) {
        profesores.add(Profesor);
        return "Profesor agregado \n" + Profesor.toString();
    }

    public String updateProfesor(Profesor Profesor) {
        for (Profesor Profesor2 : profesores) {
            if (Profesor2.getId() == Profesor2.getId()) {
                Profesor2.setName(Profesor2.getName());
                Profesor2.setEmail(Profesor2.getEmail());
                return "Profesor actualizado \n" + Profesor2.toString();
            }
        }
        return "Profesor no encontrado";
    }

    public String deleteProfesor(int id) {
        for (Profesor Profesor : profesores) {
            if (Profesor.getId() == id) {
                profesores.remove(Profesor);
                return "Profesor eliminado \n" + Profesor.toString();
            }
        }
        return "Profesor no encontrado";
    }
}
