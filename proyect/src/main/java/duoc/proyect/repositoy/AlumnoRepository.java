package duoc.proyect.repositoy;

import duoc.proyect.model.Alumno;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AlumnoRepository {

    private List<Alumno> alumnos = new ArrayList<Alumno>();

    public AlumnoRepository() {
        alumnos.add(new Alumno(1, "testNombre1", "testEmail1"));
        alumnos.add(new Alumno(2, "testNombre2", "testEmail2"));
        alumnos.add(new Alumno(3, "testNombre3", "testEmail3"));
    }

    public List<Alumno> getAllAlumnos() {
        if (alumnos.isEmpty()) {
            return null;
        }else{
            return alumnos;
        }
    }

    public Alumno getAlumnoById(int id) {
        for (Alumno alumno : alumnos) {
            if (alumno.getId() == id) {
                return alumno;
            }
        }
        return null;
    }

    public String addAlumno(Alumno alumno) {
        alumnos.add(alumno);
        return "alumno agregado \n" + alumno.toString();
    }

    public String updateAlumno(Alumno alumno) {
        for (Alumno alumno2 : alumnos) {
            if (alumno2.getId() == alumno2.getId()) {
                alumno2.setName(alumno2.getName());
                alumno2.setEmail(alumno2.getEmail());
                return "alumno actualizado \n" + alumno2.toString();
            }
        }
        return "Alumno no encontrado";
    }

    public String deleteAlumno(int id) {
        for (Alumno alumno : alumnos) {
            if (alumno.getId() == id) {
                alumnos.remove(alumno);
                return "alumno eliminado \n" + alumno.toString();
            }
        }
        return "Alumno no encontrado";
    }
}
