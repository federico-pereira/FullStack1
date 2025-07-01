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
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se obtuvo la lista"),
                    @ApiResponse(responseCode = "204", description = "Se obtuvo la lista vacia")
            }
    )
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
    @Parameter(name ="id",description = "Id del alumno a buscar", required = true)
    public ResponseEntity<Alumno> getAlumno(@PathVariable int id) {
        return alumnoService.getAlumnoById(id);
    }

    @Operation(summary = "Publicar un nuevo alumno", description = "Agrega alumno como objeto")
    @PostMapping
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Alumo creado"),
                    @ApiResponse(responseCode = "409", description = "Alumno ya existe en sistema"),
                    @ApiResponse(responseCode = "404", description = "Mal formato")
            }
    )
    @Parameter(name = "rut", description = "Rut del alumno al agregar", required = true)
    public ResponseEntity<Object> addAlumno(@RequestBody Alumno alumno) {
        return alumnoService.addAlumno(alumno);
    }

    @Operation(summary = "Eliminar un alumno", description = "Devuelve mensaje de respuesta + objeto de alumno")
    @DeleteMapping("/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Alumno eleminado"),
                    @ApiResponse(responseCode = "404", description = "Alumno no existe en sistema")
            }
    )
    @Parameter(name ="id",description = "Id del alumno a buscar", required = true)
    public ResponseEntity<Void> deleteAlumno(@PathVariable int id) {
        return alumnoService.deleteAlumno(id);
    }

    @Operation(summary = "Modificar un alumno", description = "Actualiza un alumno")
    @PutMapping("/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Alumno modificado"),
                    @ApiResponse(responseCode = "404", description = "Alumno no existe en sistema")
            }
    )
    @Parameter(name ="id",description = "Id del alumno a modificar", required = false)
    @Parameter(name = "rut", description = "Rut del alumno a modificar", required = true)
    public ResponseEntity<Alumno> updateAlumno(@RequestBody Alumno alumno, @PathVariable int id) {
        return alumnoService.updateAlumno(id,alumno);
    }
}
