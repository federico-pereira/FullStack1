package duoc.proyect.controller;

import duoc.proyect.model.DetalleEvaluacion;
import duoc.proyect.service.DetalleEvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/detalleEvaluaciones")
public class DetalleEvaliacionController {

    @Autowired
    DetalleEvaluacionService detalleEvaluacionService;

    @GetMapping
    public ResponseEntity<List<DetalleEvaluacion>> getDetalleEvaluaciones(){
        return detalleEvaluacionService.getDetalleEvaluaciones();
    }

    @PostMapping
    public ResponseEntity<Object> createDetalleEvaluacion(@RequestBody DetalleEvaluacion detalleEvaluacion){
        return detalleEvaluacionService.addDetalleEvaluacion(detalleEvaluacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDetalleEvaluacionById(@PathVariable("id") int id){
        return detalleEvaluacionService.getDetalleEvaluacionById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDetalleEvaluacionById(@PathVariable("id") int id){
        return detalleEvaluacionService.deleteDetalleEvaluacion(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDetalleEvaluacion(@PathVariable("id") int id, @RequestBody DetalleEvaluacion detalleEvaluacion){
        return detalleEvaluacionService.updateDetalleEvaluacion(id, detalleEvaluacion);
    }
}
