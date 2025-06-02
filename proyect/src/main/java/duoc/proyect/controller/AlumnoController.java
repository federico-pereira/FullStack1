package duoc.proyect.controller;

import duoc.proyect.model.Alumno;
import duoc.proyect.service.AlumnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alumnos")
@Tag(name="Alumnos", description = "Operaciones relacionadas con los alumnos")
public class AlumnoController {

    @Autowired
    AlumnoService alumnoService;

    @GetMapping
    @Operation(summary = "Obtener todos los alumnos", description = "Devuelve una lista de todos los alumnos registrados")
    @ApiResponse(responseCode = "200", description = "Se obtuvieron los alumnos")
    public ResponseEntity<List<Alumno>> getAlumnos() {
        return alumnoService.getAlumnos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un alumno por su id", description = "Devuelve un alumno por su id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se obtuvo el alumno"),
                    @ApiResponse(responseCode = "404", description = "Alumno no encontrado")
            }
    )
    @Parameter(description = "Id del alumno a buscar", required = true)
    public ResponseEntity<Object> getAlumno(@PathVariable int id) {
        return alumnoService.getAlumnoById(id);
    }

    @PostMapping
    public ResponseEntity<String> addAlumno(@RequestBody Alumno alumno) {
        return alumnoService.addAlumno(alumno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAlumno(@PathVariable int id) {
        return alumnoService.deleteAlumno(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAlumno(@RequestBody Alumno alumno, @PathVariable int id) {
        return alumnoService.updateAlumno(id,alumno);
    }
}
