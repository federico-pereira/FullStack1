package duoc.proyect.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("PROFESOR")
public class Profesor extends Usuario {

}
