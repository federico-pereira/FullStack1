package duoc.proyect.controller;

import duoc.proyect.model.Contenido;
import duoc.proyect.service.ContenidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contenidos")
public class ContenidoController {

    @Autowired
    private ContenidoService contenidoService;

    @GetMapping
    public ResponseEntity<List<Contenido>> getContenido() {
        return contenidoService.getContenidos();
    }

    @PostMapping
    public ResponseEntity<String> addContenido(@RequestBody Contenido contenido) {
        return contenidoService.addContenido(contenido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getContenidoId(@PathVariable int id) {return contenidoService.getContenidoById(id);}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContenido(@PathVariable int id) {
        return contenidoService.deleteContenido(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateContenido(@RequestBody Contenido contenido,@PathVariable int id) {
        return contenidoService.updateContenido(id,contenido);
    }
}
