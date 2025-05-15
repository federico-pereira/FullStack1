package duoc.proyect.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")      // mapea PK→FK con USUARIO(id)
@Data
public class Profesor extends Usuario {
    // hereda id, rut, name, lastName, mail de Usuario
}