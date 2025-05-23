package duoc.proyect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class DetalleEvaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Relación hacia Evaluación
    @ManyToOne(optional = false)
    @JoinColumn(name = "evaluacion_id", nullable = false)
    private Evaluacion evaluacion;

    // Relación hacia Alumno
    @ManyToOne(optional = false)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    private float notaAlumno;
}
