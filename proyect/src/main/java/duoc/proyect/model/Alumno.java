package duoc.proyect.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("ALUMNO")
public class Alumno extends Usuario {
    @ManyToMany(mappedBy = "listaCurso")
    private List<Curso> cursos = new ArrayList<>();
}
