package duoc.proyect.controller;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.Matricula;
import duoc.proyect.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matriculas")
public class MatriculaController {

    @Autowired
    MatriculaService matriculaService;

    //Matriculas

    @GetMapping
    public ResponseEntity<List<Matricula>> getMatriculas() {
        return matriculaService.getMatriculas();
    }

    @PostMapping
    public ResponseEntity<Object> addMatricula(@RequestBody Matricula matricula) {
        return matriculaService.addMatricula(matricula);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMatriculaById (@PathVariable int id){
        return matriculaService.getMatriculaById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMatricula(@PathVariable int id) {
        return matriculaService.deleteMatricula(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMatricula(@PathVariable int id, @RequestBody Matricula matricula) {
        return matriculaService.updateMatricula(id, matricula);
    }

    //Alumno

    @GetMapping("/{id}/alumno")
    public ResponseEntity<Object> getAlumnoMatricula(@PathVariable int id) {
        return matriculaService.getAlumno(id);
    }

    @PostMapping("/{id}/alumno")
    public ResponseEntity<Object> addAlumno(@PathVariable int id, @RequestBody Alumno alumno) {
        return matriculaService.addAlumno(id,alumno.getId());
    }

    @DeleteMapping("/{id}/alumno")
    public ResponseEntity<Object> deleteAlumno(@PathVariable int id) {
        return matriculaService.deleteAlumno(id);
    }

    //Cupon

        @GetMapping("/{id}/cupon")
    public ResponseEntity<Object> getCuponMatricula(@PathVariable int id) {
        return matriculaService.getCuponMatricula(id);
    }

    @PostMapping("/{id}/cupon/{idCupon}")
    public ResponseEntity<Object> addCupon(@PathVariable int id, @PathVariable int idCupon) {
        return matriculaService.addCuponDescuento(id,idCupon);
    }

    @DeleteMapping("/{id}/cupon")
    public ResponseEntity<Object> deleteCupon(@PathVariable int id) {
        return matriculaService.deleteCuponDescuento(id);
    }
}
