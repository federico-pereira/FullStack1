package duoc.proyect.repositoy;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Curso;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CursoRepository {

    private List<Alumno> alumnos = new ArrayList<>();
    private List<Curso> cursos = new ArrayList<>();

    public CursoRepository() {
        alumnos.add(new Alumno(1,"testAlumno1","test1@gmail.com"));
        alumnos.add(new Alumno(2,"testAlumno2","test2@gmail.com"));
        alumnos.add(new Alumno(3,"testAlumno3","test3@gmail.com"));

        cursos.add(new Curso(1,"testCurso1",alumnos));
        cursos.add(new Curso(2,"testCurso2",alumnos));
        cursos.add(new Curso(3,"testCurso3",alumnos));

    }

    public String getCursos() {
        String output = "";
        for (Curso curso : cursos) {
            output += "ID Curso: "+curso.getId() + "\n";
            output += "Nombre Curso: "+curso.getName() + "\n";
            output += "Lista Curso: "+curso.getListaCurso() + "\n";
        }
        if(output.isEmpty()){
            return "No se encontraron cursos";
        }else {
            return output;
        }
    }

    public String getCursoById(int id) {
        for (Curso curso : cursos) {
            if (curso.getId() == id) {
                String output = "ID Curso: "+curso.getId() + "\n";
                output += "Nombre Curso: "+curso.getName() + "\n";
                output += "Lista Curso: "+curso.getListaCurso() + "\n";
                return output;
            }
        }
        return "No se encontro el curso";
    }

    public String addCurso(Curso curso) {
        cursos.add(curso);
        return "Curso agregado";
    }

    public String updateCurso(Curso curso) {
        for (Curso c : cursos) {
            if (c.getId() == curso.getId()) {
                c.setId(curso.getId());
                c.setName(curso.getName());
                c.setListaCurso(curso.getListaCurso());
                break;
            }
        }
        return "Curso actualizado";
    }

    public String deleteCurso(int id) {
        for (Curso c : cursos) {
            if (c.getId() == id) {}
            cursos.remove(c);
            return "curso eliminado";
        }
        return "curso no encontrado";
    }

    public String getAlumnos() {
        String output = "";
        for (Alumno a : alumnos) {
            output += "ID Alumno: "+a.getId() + "\n";
            output += "Nombre Alumno: "+a.getName() + "\n";
            output += "Email Alumno: "+a.getEmail() + "\n";
        }
        if(output.isEmpty()){
            return "No se encontraron cursos";
        }else{
            return output;
        }
    }

    public String addAlumno(Alumno a) {
        alumnos.add(a);
        return "Alumno agregado";
    }

    public String updateAlumno(Alumno a) {
        for (Alumno a1 : alumnos) {
            if (a1.getId() == a.getId()) {
                a1.setId(a.getId());
                a1.setName(a.getName());
                a1.setEmail(a.getEmail());
                break;
            }
        }
        return "Alumno actualizado";
    }

    public String deleteAlumno(int id) {
        for (Alumno a : alumnos) {
            if (a.getId() == id) {}
            alumnos.remove(a);
            return "Alumno eliminado";
        }
        return "No se enconto al alumno";

    }
}
