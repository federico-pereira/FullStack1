package duoc.proyect;

import duoc.proyect.model.Alumno;
import duoc.proyect.repository.AlumnoRepository;
import duoc.proyect.service.AlumnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static  org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
public class AlumnoTest {

    @Autowired
    AlumnoRepository alumnoRepository;

    @MockitoBean
    AlumnoService alumnoService;

    @Test
    void findAllTest(){

        List<Alumno> alumnos = alumnoRepository.findAll();

        assertNotNull(alumnos);
        for(Alumno alumno:alumnos){
            assertNotNull(alumno.getId());
            assertNotNull(alumno.getRut());
        }
        assertEquals(3, alumnos.size());
    }
}