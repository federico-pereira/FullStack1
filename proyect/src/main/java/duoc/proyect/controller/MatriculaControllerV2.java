package duoc.proyect.controller;

import duoc.proyect.Assembler.MatriculaModelAssembler;
import duoc.proyect.Assembler.MatriculaModelAssembler;
import duoc.proyect.model.Matricula;
import duoc.proyect.service.MatriculaService;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/matriculas")
public class MatriculaControllerV2 {

    private final MatriculaService matriculaService;
    private final MatriculaModelAssembler assembler;

    public MatriculaControllerV2(MatriculaService matriculaService, MatriculaModelAssembler assembler) {
        this.matriculaService = matriculaService;
        this.assembler = assembler;
    }

    // Obtener todas las matrículas
    @GetMapping
    public ResponseEntity<?> getMatriculas() {
        return matriculaService.getMatriculas();
    }

    // Obtener matrícula por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getMatriculaById(@PathVariable int id) {
        return matriculaService.getMatriculaById(id);
    }

    // Crear nueva matrícula
    @PostMapping
    public ResponseEntity<?> addMatricula(@RequestBody Matricula matricula) {
        return matriculaService.addMatricula(matricula);
    }

    // Actualizar matrícula existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMatricula(@PathVariable int id, @RequestBody Matricula matricula) {
        return matriculaService.updateMatricula(id, matricula);
    }

    // Eliminar matrícula por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMatricula(@PathVariable int id) {
        return matriculaService.deleteMatricula(id);
    }

    // Obtener alumno asociado a una matrícula
    @GetMapping("/{id}/alumno")
    public ResponseEntity<?> getAlumno(@PathVariable int id) {
        return matriculaService.getAlumno(id);
    }

    // Asignar alumno a una matrícula
    @PostMapping("/{id}/alumno/{alumnoId}")
    public ResponseEntity<?> addAlumno(@PathVariable int id, @PathVariable int alumnoId) {
        return matriculaService.addAlumno(id, alumnoId);
    }

    // Eliminar alumno asociado a una matrícula
    @DeleteMapping("/{id}/alumno")
    public ResponseEntity<?> deleteAlumno(@PathVariable int id) {
        return matriculaService.deleteAlumno(id);
    }

    // Obtener cupón asociado a una matrícula
    @GetMapping("/{id}/cupon")
    public ResponseEntity<?> getCupon(@PathVariable int id) {
        return matriculaService.getCuponMatricula(id);
    }

    // Asignar cupón a una matrícula
    @PostMapping("/{id}/cupon/{cuponId}")
    public ResponseEntity<?> addCupon(@PathVariable int id, @PathVariable int cuponId) {
        return matriculaService.addCuponDescuento(id, cuponId);
    }

    // Eliminar cupón de una matrícula
    @DeleteMapping("/{id}/cupon")
    public ResponseEntity<?> deleteCupon(@PathVariable int id) {
        return matriculaService.deleteCuponDescuento(id);
    }
}
