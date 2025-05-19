package duoc.proyect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String matricula;
    @OneToOne
    private Alumno alumno;
    @OneToOne
    private CuponDescuento cuponDescuento;

    // Valor base siempre fijo
    public static final float VALOR = 450_000f;

    // No lo persistimos directamente
    @Transient
    public float getValor() {
        if (cuponDescuento != null) {
            return VALOR - (VALOR * cuponDescuento.getDescuento() / 100f);
        }
        return 0f;
    }
}
