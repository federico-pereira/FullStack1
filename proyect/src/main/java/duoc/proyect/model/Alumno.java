package duoc.proyect.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@ToString(callSuper = true)
@Entity
@PrimaryKeyJoinColumn(name = "id")      // mapea PK→FK con USUARIO(id)
@Data
public class Alumno extends Usuario {
}