package duoc.proyect;

import duoc.proyect.model.CuponDescuento;
import duoc.proyect.repository.CuponDescuentoRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;

@Profile("test")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CuponDescuentoRepository cuponDescuentoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        //Generar tipos de CuponDescuento
        for (int i = 0; i < 3; i++) {
            CuponDescuento cuponDescuento = new CuponDescuento();
            cuponDescuento.setDescuento(faker.number().numberBetween(0,100));
            cuponDescuentoRepository.save(cuponDescuento);
        }
    }
}
