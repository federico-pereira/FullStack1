package duoc.proyect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titulo;
    private String descripcion;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "evaluacion_curso",
            joinColumns = @JoinColumn(name = "evaluacion_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<Curso> cursos  = new ArrayList<>();
}
