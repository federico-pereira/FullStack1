package duoc.proyect.repositoy;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Curso;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CursoRepository {

    private List<Alumno> alumnos = new ArrayList<>();

    public CursoRepository() {
        alumnos.add(new Alumno(1,"fede1","fede1@gmail.com"));
        alumnos.add(new Alumno(2,"fede2","fede2@gmail.com"));
        alumnos.add(new Alumno(3,"fede3","fede3@gmail.com"));
    }

    public String getAlumnos() {
        String output = "";
        for (Alumno alumno : alumnos) {
            output += "ID Alumno: "+alumno.getId() + "\n";
            output += "Nombre Alumno: "+alumno.getName() + "\n";
            output += "EMAIL Alumno: "+alumno.getEmail() + "\n";
        }
        if(output.isEmpty()){
            return "No se encontraron alumnos de curso";
        }else {
            return output;
        }
    }

    public String addAlumno(Alumno alumno) {
        alumnos.add(alumno);
        return "alumno agregado";
    }

    public String updateAlumno(Alumno alumno) {
        int index = alumnos.indexOf(alumno);
        for (Alumno alum : alumnos) {
            if (alum.getId() == alumno.getId()) {
                index = alumnos.indexOf(alum);
            }
        }
        if (index == -1) {
            return "No se encontro alumno";
        }else {
            alumnos.set(index, alumno);
            return "alumno actualizado";
        }
    }

    public String deleteAlumno(Alumno alumno) {
        int index = alumnos.indexOf(alumno);
        alumnos.remove(index);
        return "alumno eliminado";
    }
}
