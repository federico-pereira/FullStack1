package duoc.proyect.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class Usuario {
    private int id;
    private String rut;
    private String nombre;
    private String apellidos;
    private String correo;

}
