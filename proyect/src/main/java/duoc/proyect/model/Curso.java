package duoc.proyect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "curso_alumno",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "alumno_id")
    )
    private List<Alumno> listaCurso = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "curso_contenido",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "contenido_id")
    )
    private List<Contenido> listaContenido = new ArrayList<>();

    // Métodos para manejar la relación Many-to-Many con Alumno
    public void addAlumno(Alumno alumno) {
        listaCurso.add(alumno);
        alumno.getCursos().add(this);
    }

    public void removeAlumno(Alumno alumno) {
        listaCurso.remove(alumno);
        alumno.getCursos().remove(this);
    }

    // Métodos para manejar la relación Many-to-Many con Contenido
    public void addContenido(Contenido contenido) {
        listaContenido.add(contenido);
        contenido.getCursos().add(this);
    }

    public void removeContenido(Contenido contenido) {
        listaContenido.remove(contenido);
        contenido.getCursos().remove(this);
    }
}

