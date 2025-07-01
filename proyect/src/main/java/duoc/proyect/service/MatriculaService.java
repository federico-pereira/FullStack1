package duoc.proyect.service;

import duoc.proyect.model.Alumno;
import duoc.proyect.model.CuponDescuento;
import duoc.proyect.model.Matricula;
import duoc.proyect.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatriculaService {

    @Autowired
    MatriculaRepository matriculaRepository;

    @Autowired
    AlumnoService alumnoService;

    @Autowired
    CuponDescuentoService cuponDescuentoService;

    //Matricula

    public ResponseEntity<List<Matricula>> getMatriculas() {
        List<Matricula> matriculas = matriculaRepository.findAll();
        if (matriculas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(matriculas, HttpStatus.OK);
    }

    public ResponseEntity<Object> getMatriculaById(int id) {
        if (matriculaRepository.existsById(id)) {
            Matricula matricula = matriculaRepository.findById(id).get();
            return new ResponseEntity<>(matricula, HttpStatus.OK);
        }
        return new ResponseEntity<>("Matricula no encontrada",HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> addMatricula(Matricula matricula) {
        matriculaRepository.save(matricula);
        return new ResponseEntity<>(matricula, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateMatricula(int id,Matricula matricula) {
        matricula.setId(id);
        if (matriculaRepository.existsById(id)) {
            matriculaRepository.save(matricula);
            return new ResponseEntity<>("Matricula actualizada: "+matricula, HttpStatus.OK);
        }
        return new ResponseEntity<>("Matricula no encontrada",HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> deleteMatricula(int id) {
        if (matriculaRepository.existsById(id)) {
            matriculaRepository.deleteById(id);
            return new ResponseEntity<>("Matricula eliminada", HttpStatus.OK);
        }
        return new ResponseEntity<>("Matricula no encontrada",HttpStatus.NO_CONTENT);
    }

    //Alumno

    public ResponseEntity<Object> getAlumno(int id) {
        Alumno alumno = matriculaRepository.findById(id).get().getAlumno();
        if (alumno == null) {
            return new ResponseEntity<>("Alumno no encontrado", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(alumno, HttpStatus.OK);
    }

    public ResponseEntity<Object> addAlumno(int matriculaId, int alumnoId) {
        // 1. Busco la matrícula
        Optional<Matricula> optMatricula = matriculaRepository.findById(matriculaId);
        if (optMatricula.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Matrícula con id " + matriculaId + " no encontrada");
        }

        Matricula matricula = optMatricula.get();

        // 2. Compruebo si ya hay un alumno asignado
        if (matricula.getAlumno() != null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Ya hay un alumno registrado: " + matricula.getAlumno());
        }

        // 3. Traigo el alumno desde el servicio
        ResponseEntity<Alumno> respAlumno = alumnoService.getAlumnoById(alumnoId);
        if (!respAlumno.getStatusCode().equals(HttpStatus.OK) || respAlumno.getBody() == null) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Alumno con id " + alumnoId + " no encontrado");
        }

        Alumno alumno = respAlumno.getBody();

        // 4. Asigno el alumno y guardo
        matricula.setAlumno(alumno);
        matriculaRepository.save(matricula);

        // 5. Devuelvo la matrícula actualizada
        return ResponseEntity
                .ok(matricula);
    }


    public ResponseEntity<Object> deleteAlumno(int id) {
        if (matriculaRepository.existsById(id)) {
            Matricula matricula = matriculaRepository.findById(id).get();
            if (matricula.getAlumno() == null) {
                return new ResponseEntity<>("Matricula no tiene alumno", HttpStatus.NO_CONTENT);
            }
            matricula.setAlumno(null);
            matriculaRepository.save(matricula);
            return new ResponseEntity<>(matricula, HttpStatus.OK);
        }
        return new ResponseEntity<>("Alumno no encontrado", HttpStatus.NO_CONTENT);
    }

    //Cupon

    public ResponseEntity<Object> getCuponMatricula(int id) {
        if (matriculaRepository.existsById(id)) {
            CuponDescuento cupon = matriculaRepository.findById(id).get().getCuponDescuento();
            if (cupon == null) {
                return new ResponseEntity<>("Matricula no tiene cupon asignado", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cupon, HttpStatus.OK);
        }
        return new ResponseEntity<>("Matricula no encontrada", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> addCuponDescuento(int id, int idCupon) {
        if (matriculaRepository.existsById(id)) {
            CuponDescuento cuponControl = matriculaRepository.findById(id).get().getCuponDescuento();
            if (cuponControl == null) {
                Matricula matricula = matriculaRepository.findById(id).get();
               ResponseEntity cuponDescuento = cuponDescuentoService.getCuponDescuentoById(idCupon);
                matricula.setCuponDescuento((CuponDescuento) cuponDescuento.getBody());
                matriculaRepository.save(matricula);
                return new ResponseEntity<>(matricula, HttpStatus.OK);
            }
            return new ResponseEntity<>("Matricula ta tiene cupon, elimine el existente primero", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Matricula no encontrada", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> deleteCuponDescuento(int id) {
        if (matriculaRepository.existsById(id)) {
            CuponDescuento cuponControl = matriculaRepository.findById(id).get().getCuponDescuento();
            if (cuponControl == null) {
                return new ResponseEntity<>("Matricula no tiene cupon", HttpStatus.NO_CONTENT);
            }
            Matricula matricula = matriculaRepository.findById(id).get();
            matricula.setCuponDescuento(null);
            matriculaRepository.save(matricula);
            return new ResponseEntity<>(matricula, HttpStatus.OK);
        }
        return new ResponseEntity<>("Matricula no encontrada", HttpStatus.NO_CONTENT);

    }
}
