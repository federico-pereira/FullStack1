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

    // Relación N-1: un Profesor puede tener muchos Cursos
    @ManyToOne(optional = false)
    @JoinColumn(name = "profesor_id", nullable = false)
    private Profesor profesor;

    // Relación M-M con Alumno
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "curso_alumno",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "alumno_id")
    )
    private List<Alumno> listaCurso = new ArrayList<>();

    // Relación M-M con Contenido
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "curso_contenido",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "contenido_id")
    )
    private List<Contenido> listaContenido = new ArrayList<>();

    // Helpers para sincronizar ambas patas de la relación
    public void addAlumno(Alumno alumno) {
        listaCurso.add(alumno);
        alumno.getCursos().add(this);
    }

    public void removeAlumno(Alumno alumno) {
        listaCurso.remove(alumno);
        alumno.getCursos().remove(this);
    }

    public void addContenido(Contenido contenido) {
        listaContenido.add(contenido);
        contenido.getCursos().add(this);
    }

    public void removeContenido(Contenido contenido) {
        listaContenido.remove(contenido);
        contenido.getCursos().remove(this);
    }
}