package duoc.proyect.service;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Contenido;
import duoc.proyect.model.Curso;
import duoc.proyect.repositoy.AlumnoRepository;
import duoc.proyect.repositoy.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    AlumnoRepository alumnoRepository;

    @Autowired
    ContenidoService contenidoService;

    //Cursos

    public String getCursos(){
        String output = "";
        for(Curso curso : cursoRepository.findAll()){
            output += curso.toString() + "\n";
        }
        if (output.isEmpty()){
            return "No hay cursos";
        }else {
            return output;
        }
    }

    public String addCurso(Curso curso){
        if (!cursoRepository.existsById(curso.getId())){
            cursoRepository.save(curso);
            return "Curso guardado";
        }
        return "El curso ya existe";
    }

    public String getCursoById(int id) {
        for(Curso curso : cursoRepository.findAll()){
            if (curso.getId() == id){
                return curso.toString();
            }
        }
        return "Curso no encontrado";
    }

    public String deleteCurso(int id) {
        if(cursoRepository.existsById(id)){
            cursoRepository.deleteById(id);
            return "Curso eliminado";
        }else {
            return "Curso no encontrado";
        }
    }

    public String updateCurso(Curso curso) {
        cursoRepository.save(curso);
        return "Curso actualizado" + curso.toString();
    }


    // Alumnos

    public String getAlumnos(int idCurso) {
        if(cursoRepository.existsById(idCurso)){
            for(Curso curso : cursoRepository.findAll()){
                if (curso.getId() == idCurso){
                    return curso.getListaCurso().toString();
                }
            }
        }
        return "Alumno no encontrado";
    }

    public String addAlumno(int idCurso,Alumno alumno) {
        if (alumnoRepository.findById(alumno.getId()).isPresent()){
            if (cursoRepository.existsById(idCurso)){
                for(Curso curso : cursoRepository.findAll()){
                    if (curso.getId() == idCurso){
                        curso.addAlumno(alumno);
                        return "Alumno" + alumno.toString()+"\nagregado al curso: " +curso.getName() +"\n id: "  + curso.getId();
                    }
                }
            }else {
                return "Curso no encontrado";
            }
        }
        return "Alumno no encontrado en el sistema";
    }

    public String deleteAlumno(int id, int idCurso) {
        if(cursoRepository.existsById(idCurso)){
            for(Curso curso : cursoRepository.findAll()){
                if (curso.getId() == idCurso){
                    for (Alumno alumno : curso.getListaCurso()){
                        if (alumno.getId() == id){
                            curso.removeAlumno(alumno);
                            return "Alumno eliminado del curso: " +curso.getName() +"\n id: "  + curso.getId();
                        }
                    }
                    return "Alumno no encontrado";
                }
            }
        };
        return "Curso no encontrado";
    }

    // Contenido

    public String getContenidos(int idCurso) {
        if(cursoRepository.existsById(idCurso)){
            for(Curso curso : cursoRepository.findAll()){
                if (curso.getId() == idCurso){
                    return curso.getListaContenido().toString();
                }
            }
        }
        return "Contenido no encontrado";
    }

    public String addContenido(int idCurso, Contenido contenido) {
        if (cursoRepository.existsById(idCurso)){
            for(Curso curso : cursoRepository.findAll()){
                if (curso.getId() == idCurso){
                    curso.addContenido(contenido);
                    return "Contenido agregado al curso: " +curso.getName() +"\n id: "  + curso.getId();
                }
            }
        }
        return "Contenido no encontrado";
    }

    public String deleteContenido(int id, int idCurso) {
        if(cursoRepository.existsById(idCurso)){
            for(Curso curso : cursoRepository.findAll()){
                if (curso.getId() == idCurso){
                    for (Contenido contenido : curso.getListaContenido()){
                        if (contenido.getId() == id){
                            curso.removeContenido(contenido);
                            return "Contenido con id: "+contenido.getId() +"\neliminado del curso: " +curso.getName()+"\n id: "  + curso.getId();
                        }
                    }
                    return "Contenido no encontrado";
                }
            }
        }
        return "Curso no encontrado en el sistema";
    }

}
