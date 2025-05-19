package duoc.proyect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketSoporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_reclamante")
    private Usuario reclamante;

    private String tema;
    private String descripcion;

    @CreationTimestamp
    @Column(name = "creado",nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

}
