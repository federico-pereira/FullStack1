package duoc.proyect;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.CuponDescuento;
import duoc.proyect.model.Profesor;
import duoc.proyect.model.TicketSoporte;
import duoc.proyect.repository.AlumnoRepository;
import duoc.proyect.repository.CuponDescuentoRepository;
import duoc.proyect.repository.ProfesorRepository;
import duoc.proyect.repository.TicketSoporteRepository;
import duoc.proyect.service.AlumnoService;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CuponDescuentoRepository cuponDescuentoRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private TicketSoporteRepository ticketSoporteRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;
    private AlumnoService alumnoService;

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

        //Generar tipos de Alumnos
        List<Alumno> alumnos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Alumno alumno = new Alumno();
            alumno.setMail(faker.internet().emailAddress());
            alumno.setRut(faker.number().numberBetween(7000000, 25000000) + "-" + faker.options().option("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "K"));
            alumno.setName(faker.name().firstName());
            alumno.setLastName(faker.name().lastName());
            alumnos.add(alumnoRepository.save(alumno));
        }

        //Generar tipos de Profesores
        for (int i = 0; i < 3; i++) {
            Profesor profesor = new Profesor();
            profesor.setMail(faker.internet().emailAddress());
            profesor.setRut(faker.number().numberBetween(7000000, 25000000) + "-" + faker.options().option("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "K"));
            profesor.setName(faker.name().firstName());
            profesor.setLastName(faker.name().lastName());
            profesorRepository.save(profesor);
        }

        //Generar tipos de TicketSoporte
        for (int i = 0; i < 3; i++) {
            TicketSoporte ticket = new TicketSoporte();
            Alumno alumnoAleatorio = alumnos.get(faker.number().numberBetween(0, alumnos.size() - 1));
            ticket.setReclamante(alumnoAleatorio);
            ticket.setTema(faker.internet().emailSubject());
            ticket.setDescripcion(faker.internet().emailSubject());
            ticketSoporteRepository.save(ticket);
        }
    }
}
