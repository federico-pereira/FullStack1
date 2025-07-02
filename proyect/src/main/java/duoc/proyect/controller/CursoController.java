package duoc.proyect.controller;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Contenido;
import duoc.proyect.model.Curso;
import duoc.proyect.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cursos")
@Tag(name="Cusos", description = "Operaciones relacionadas con cursos")
public class CursoController {

    @Autowired
    CursoService cursoService;

    // Cursos

    @GetMapping
    @Operation(summary = "Obtener todos los cursos", description = "Devuelve una lista de todos los cursos")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Se obtivieron los cursos"),
                    @ApiResponse(responseCode = "204", description = "Se obtuvo una lista vacia (no hay cursos)")
            }
    )
    @ApiResponse(responseCode = "200", description = "Se obtivieron los cursos")
    public ResponseEntity<List<Curso>> getCursos() {
        return cursoService.getCursos();
    }

    @Operation(
            summary = "Publicar un nuevo curso",
            description = "Agrega un curso como objeto",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del nuevo curso",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "  \"name\": \"Matemáticas avanzadas\",\n" +
                                            "  \"profesor\": {\n" +
                                            "    \"id\": 1\n" +
                                            "  }\n" +
                                            "}"
                            )
                    )
            )
    )    @PostMapping
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Curso creado"),
                    @ApiResponse(responseCode = "409", description = "Curso ya existe en sistema"),
                    @ApiResponse(responseCode = "404", description = "Mal formato")
            }
    )
    public Curso addCurso(@RequestBody Curso curso) {
        return cursoService.addCurso(curso);
    }

    @Operation(summary = "Obtener un curso por su id", description = "Devuelve un objeto de la clase curso")
    @GetMapping("/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se obtuvo el curso"),
                    @ApiResponse(responseCode = "404", description = "Cruso no encontrado")
            }
    )
    public ResponseEntity<Object> getCursoById(@PathVariable int id) {
        return cursoService.getCursoById(id);
    }

    @Operation(summary = "Eliminar un Curso", description = "Devuelve mensaje de respuesta + id de curso")
    @DeleteMapping("/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "Curso eliminado"),
                    @ApiResponse(responseCode = "404",description = "Cruso no encontrado")
            }
    )
    @Parameter(name = "id",description = "Id del curso",required = true)
    public ResponseEntity<String> deleteCurso(@PathVariable int id) {
        return cursoService.deleteCurso(id);
    }

    @Operation(
            summary = "Modificar un curso",
            description = "Actualiza un curso",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del curso a modificar",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "  \"name\": \"Matemáticas avanzadas\",\n" +
                                            "  \"profesor\": {\n" +
                                            "    \"id\": 1\n" +
                                            "  }\n" +
                                            "}"
                            )
                    )
            )
    )
    @PutMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso modificado"),
            @ApiResponse(responseCode = "404", description = "Curso no existe en sistema")
    })
    @Parameter(name = "id", description = "Id del curso a actualizar", required = true)
    public ResponseEntity<String> updateCurso(@RequestBody Curso curso, @PathVariable int id) {
        return cursoService.updateCurso(curso, id);
    }

    //Alumo

    @Operation(summary = "Obtener lista curso", description = "Devuelve la lista de alumnos de un curso por id")
    @GetMapping("/{idCurso}/alumnos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de curso"),
                    @ApiResponse(responseCode = "204", description = "Se obtuvo la lista de curso vacia (no hay alumnos)"),
                    @ApiResponse(responseCode = "404", description = "Curso no existe en sistema")
            }
    )
    public ResponseEntity<List<Alumno>> getAlumnos(@PathVariable int idCurso) {
        return cursoService.getAlumnos(idCurso);
    }

    @Operation(
            summary = "Eliminar alumno del curso",
            description = "Elimina al alumno de la lista del curso, no del sistema"
    )
    @DeleteMapping("/{idCurso}/alumnos/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alumno eliminado del curso"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado o alumno no pertenece al curso")
    })
    @Parameter(name = "idCurso",description = "Id del curso")
    @Parameter(name = "id", description = "Id del alumno a eliminar")
    public ResponseEntity<String> deleteAlumno(@PathVariable int idCurso, @PathVariable int id) {
        return cursoService.deleteAlumno(id, idCurso);
    }

    @Operation(
            summary = "Agregar un alumno al curso",
            description = "Agrega un alumno a la lista curso (tiene que existir en el sistema)"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se agrego el alumno a la lista de curso"),
                    @ApiResponse(responseCode = "404", description = "Alumno no existe en el sistema"),
                    @ApiResponse(responseCode = "404", description = "Curso no existe en el sistema"),
                    @ApiResponse(responseCode = "409", description = "Alumno ya inscrito en el curso")
            }
    )
    @Parameter(name = "idCurso",description = "Id del curso")
    @Parameter(name = "id", description = "Id del alumno a agregar")
    @PostMapping("/{idCurso}/alumnos")
    public ResponseEntity<String> addAlumno(@PathVariable int idCurso, @RequestBody Alumno alumno) {
        return cursoService.addAlumno(idCurso,alumno.getId());
    }

    // Contenido

    @Operation(
            summary = "Obtener lista de contenidos",
            description = "Devuelve la lista de contenidos de un curso por id"
    )
    @GetMapping("/{idCurso}/contenidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de contenidos"),
            @ApiResponse(responseCode = "204", description = "Se obtuvo la lista de contenidos vacía (no hay contenidos)"),
            @ApiResponse(responseCode = "404", description = "Curso no existe en el sistema")
    })
    public ResponseEntity<List<Contenido>> getContenidos(@PathVariable int idCurso) {
        return cursoService.getContenidos(idCurso);
    }

    @Operation(
            summary = "Eliminar contenido del curso",
            description = "Elimina un contenido de la lista del curso, no del sistema"
    )
    @DeleteMapping("/{idCurso}/contenidos/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contenido eliminado del curso"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado o contenido no pertenece al curso")
    })
    @Parameter(name = "idCurso", description = "Id del curso")
    @Parameter(name = "id", description = "Id del contenido a eliminar")
    public ResponseEntity<String> deleteContenido(
            @PathVariable int idCurso,
            @PathVariable int id
    ) {
        return cursoService.deleteContenido(id, idCurso);
    }

    @Operation(
            summary = "Agregar un contenido al curso",
            description = "Agrega un contenido a la lista del curso (tiene que existir en el sistema)"
    )
    @PostMapping("/{idCurso}/contenidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se agregó el contenido a la lista del curso"),
            @ApiResponse(responseCode = "404", description = "Contenido no existe en el sistema"),
            @ApiResponse(responseCode = "404", description = "Curso no existe en el sistema"),
            @ApiResponse(responseCode = "409", description = "Contenido ya inscrito en el curso")
    })
    @Parameter(name = "idCurso", description = "Id del curso")
    @Parameter(name = "id", description = "Id del contenido a agregar")
    public ResponseEntity<String> addContenido(
            @PathVariable int idCurso,
            @RequestBody Contenido contenido
    ) {
        return cursoService.addContenido(idCurso, contenido.getId());
    }

}
