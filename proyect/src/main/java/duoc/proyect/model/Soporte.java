package duoc.proyect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString(callSuper=true)
@Entity
@PrimaryKeyJoinColumn(name = "id")      // mapea PK→FK con USUARIO(id)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Soporte extends Usuario {

    @OneToMany
    @JoinColumn(name = "soporte_id", nullable = true)
    private List<TicketSoporte> ticketsAsignados = new ArrayList<>();

    private String departamento;
}
