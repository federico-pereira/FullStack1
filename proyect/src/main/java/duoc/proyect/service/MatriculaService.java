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

@Service
public class MatriculaService {

    @Autowired
    MatriculaRepository matriculaRepository;

    @Autowired
    AlumnoService alumnoService;

    @Autowired
    CuponDescuentoSevice cuponDescuentoService;

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

    public ResponseEntity<Object> addAlumno(int id, int idAlumno) {
        Alumno alumnoControl = matriculaRepository.findById(id).get().getAlumno();
        if (alumnoControl == null) {
            if (matriculaRepository.existsById(id)) {
                Matricula matricula = matriculaRepository.findById(id).get();
                ResponseEntity<Object> alumno = alumnoService.getAlumnoById(idAlumno);
                if (alumno == null) {
                    return new ResponseEntity<>("Alumno no encontrado", HttpStatus.NO_CONTENT);
                }
                matricula.setAlumno((Alumno) alumno.getBody());
                matriculaRepository.save(matricula);
                return new ResponseEntity<>(matricula, HttpStatus.OK);
            }
            return new ResponseEntity<>("Matricula no encontrada", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Ya hay un alumno registrado "+alumnoControl, HttpStatus.CONFLICT);
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
                ResponseEntity<Object> cuponDescuento = cuponDescuentoService.getCuponDescuentoById(idCupon);
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
