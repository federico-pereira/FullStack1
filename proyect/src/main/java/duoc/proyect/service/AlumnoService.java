package duoc.proyect.service;

import duoc.proyect.model.Alumno;
import duoc.proyect.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    /**
     * Listar todos los alumnos.
     *  - 200 OK + lista si hay alumnos.
     *  - 204 NO CONTENT si no hay ninguno.
     */
    public ResponseEntity<List<Alumno>> getAlumnos() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        if (alumnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alumnos);
    }

    /**
     * Obtener un alumno por ID.
     *  - 200 OK + alumno si existe.
     *  - 404 NOT FOUND si no existe.
     */
    public ResponseEntity<Alumno> getAlumnoById(int id) {
        return alumnoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Crear un nuevo alumno.
     *  - 201 CREATED + alumno creado si no existía.
     *  - 409 CONFLICT si ya existe uno con ese ID.
     */
    public ResponseEntity<Object> addAlumno(Alumno alumno) {
        // Si viene con un ID pedido por JSON, lo ignoramos para creación
        alumno.setId(null);

        // Comprobar duplicado por rut (añade existsByRut en tu repositorio)
        if (alumno.getRut() != null && alumnoRepository.existsByRut(alumno.getRut())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un alumno con RUT " + alumno.getRut());
        }

        Alumno saved = alumnoRepository.save(alumno);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    /**
     * Actualizar un alumno existente.
     *  - 200 OK + alumno actualizado si existe.
     *  - 404 NOT FOUND si no existe.
     */
    public ResponseEntity<Alumno> updateAlumno(int id, Alumno datos) {
        Optional<Alumno> opt = alumnoRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Alumno alumno = opt.get();
        alumno.setName(datos.getName());
        alumno.setMail(datos.getMail());
        alumno.setRut(datos.getRut());
        // ... actualizar más campos si los hay
        Alumno actualizado = alumnoRepository.save(alumno);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Eliminar un alumno.
     *  - 204 NO CONTENT si se eliminó.
     *  - 404 NOT FOUND si no existía.
     */
    public ResponseEntity<Void> deleteAlumno(int id) {
        if (!alumnoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        alumnoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
