package duoc.proyect.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Alumno {
    private int Id;
    private String Name;
    private String Email;
}
