package duoc.proyect;

import duoc.proyect.model.*;
import duoc.proyect.repository.*;
import duoc.proyect.service.AlumnoService;
import jakarta.transaction.Transactional;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

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

    @Autowired
    private ContenidoRepository contenidoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private DetalleEvaluacionRepository detalleEvaluacionRepository;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // 1. Generar datos básicos
        List<CuponDescuento> cupones = generarCupones(faker);
        List<Alumno> alumnos = generarAlumnos(faker);
        List<Profesor> profesores = generarProfesores(faker);
        List<Contenido> contenidos = generarContenidos(faker);

        // 2. Generar cursos (con entidades administradas)
        List<Curso> cursos = generarCursos(faker, random, alumnos, profesores);

        // 3. Generar tickets
        generarTickets(faker, alumnos);

        // 4. Generar evaluaciones (con entidades administradas)
        List<Evaluacion> evaluaciones = generarEvaluaciones(faker, random, cursos);

        // 5. Generar detalles de evaluación
        generarDetallesEvaluacion(faker, evaluaciones, alumnos);
    }

    private List<CuponDescuento> generarCupones(Faker faker) {
        List<CuponDescuento> cupones = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CuponDescuento cupon = new CuponDescuento();
            cupon.setDescuento(faker.number().numberBetween(5, 50));
            cupones.add(cuponDescuentoRepository.save(cupon));
        }
        return cupones;
    }

    private List<Alumno> generarAlumnos(Faker faker) {
        List<Alumno> alumnos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {  // Generar más alumnos
            Alumno alumno = new Alumno();
            alumno.setMail(faker.internet().emailAddress());
            alumno.setRut(faker.number().numberBetween(7000000, 25000000) + "-" +
                    faker.options().option("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "K"));
            alumno.setName(faker.name().firstName());
            alumno.setLastName(faker.name().lastName());
            alumnos.add(alumnoRepository.save(alumno));
        }
        return alumnos;
    }

    private List<Profesor> generarProfesores(Faker faker) {
        List<Profesor> profesores = new ArrayList<>();
        for (int i = 0; i < 5; i++) {  // Generar más profesores
            Profesor profesor = new Profesor();
            profesor.setMail(faker.internet().emailAddress());
            profesor.setRut(faker.number().numberBetween(7000000, 25000000) + "-" +
                    faker.options().option("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "K"));
            profesor.setName(faker.name().firstName());
            profesor.setLastName(faker.name().lastName());
            profesores.add(profesorRepository.save(profesor));
        }
        return profesores;
    }

    private List<Contenido> generarContenidos(Faker faker) {
        List<Contenido> contenidos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Contenido contenido = new Contenido();
            contenido.setDescripcion(faker.lorem().sentence());
            contenido.setTitulo(faker.book().title());
            contenidos.add(contenidoRepository.save(contenido));
        }
        return contenidos;
    }

    private List<Curso> generarCursos(Faker faker, Random random, List<Alumno> alumnos, List<Profesor> profesores) {
        List<Curso> cursos = new ArrayList<>();

        // Precargar todas las entidades como administradas
        List<Alumno> managedAlumnos = alumnoRepository.findAll();
        List<Profesor> managedProfesores = profesorRepository.findAll();

        for (int i = 0; i < 5; i++) {
            Curso curso = new Curso();
            curso.setName(faker.educator().course() + " " + faker.number().digits(3));

            // Seleccionar profesor administrado
            Profesor profesor = managedProfesores.get(faker.number().numberBetween(0, managedProfesores.size()));
            curso.setProfesor(profesor);

            // Seleccionar alumnos administrados
            List<Alumno> listaAlumnos = new ArrayList<>();
            int numAlumnos = faker.number().numberBetween(1, Math.min(5, managedAlumnos.size()));
            Set<Integer> indicesUsados = new HashSet<>();

            for (int j = 0; j < numAlumnos; j++) {
                int idx;
                do {
                    idx = faker.number().numberBetween(0, managedAlumnos.size());
                } while (indicesUsados.contains(idx));

                indicesUsados.add(idx);
                listaAlumnos.add(managedAlumnos.get(idx));
            }

            curso.setListaCurso(listaAlumnos);
            cursos.add(cursoRepository.save(curso));
        }
        return cursos;
    }

    private void generarTickets(Faker faker, List<Alumno> alumnos) {
        for (int i = 0; i < 10; i++) {
            TicketSoporte ticket = new TicketSoporte();
            Alumno alumno = alumnos.get(faker.number().numberBetween(0, alumnos.size()));
            ticket.setReclamante(alumno);
            ticket.setTema(faker.lorem().sentence());
            ticket.setDescripcion(faker.lorem().paragraph());
            ticketSoporteRepository.save(ticket);
        }
    }

    private List<Evaluacion> generarEvaluaciones(Faker faker, Random random, List<Curso> cursos) {
        List<Evaluacion> evaluaciones = new ArrayList<>();

        // Precargar cursos como entidades administradas
        List<Curso> managedCursos = cursoRepository.findAll();

        for (int i = 0; i < 5; i++) {
            Evaluacion evaluacion = new Evaluacion();
            evaluacion.setDescripcion(faker.lorem().sentence());
            evaluacion.setTitulo(faker.book().title());

            // Seleccionar cursos administrados
            List<Curso> cursosEvaluacion = new ArrayList<>();
            int numCursos = faker.number().numberBetween(1, Math.min(3, managedCursos.size()));
            Set<Integer> indicesUsados = new HashSet<>();

            for (int j = 0; j < numCursos; j++) {
                int idx;
                do {
                    idx = faker.number().numberBetween(0, managedCursos.size());
                } while (indicesUsados.contains(idx));

                indicesUsados.add(idx);
                cursosEvaluacion.add(managedCursos.get(idx));
            }

            evaluacion.setCursos(cursosEvaluacion);
            evaluaciones.add(evaluacionRepository.save(evaluacion));
        }
        return evaluaciones;
    }

    private void generarDetallesEvaluacion(Faker faker, List<Evaluacion> evaluaciones, List<Alumno> alumnos) {
        // Precargar entidades como administradas
        List<Evaluacion> managedEvaluaciones = evaluacionRepository.findAll();
        List<Alumno> managedAlumnos = alumnoRepository.findAll();

        for (int i = 0; i < 20; i++) {
            DetalleEvaluacion detalle = new DetalleEvaluacion();
            detalle.setEvaluacion(managedEvaluaciones.get(faker.number().numberBetween(0, managedEvaluaciones.size())));
            detalle.setAlumno(managedAlumnos.get(faker.number().numberBetween(0, managedAlumnos.size())));
            detalle.setNotaAlumno(faker.number().numberBetween(10, 70) / 10f);
            detalleEvaluacionRepository.save(detalle);
        }
    }

}
