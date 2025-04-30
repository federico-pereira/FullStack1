package duoc.proyect.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contenido {
    private String id;
    private String nombre;
    private String descripcion;
    private String archivo;
}
