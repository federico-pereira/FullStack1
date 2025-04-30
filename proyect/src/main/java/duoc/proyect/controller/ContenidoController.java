package duoc.proyect.controller;

import duoc.proyect.model.Contenido;
import duoc.proyect.service.ContenidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contenido")
public class ContenidoController {

    @Autowired
    private ContenidoService contenidoService;

    @GetMapping
    public List<Contenido> getContenido() {
        return contenidoService.getAllContenido();
    }

    @PostMapping
    public Contenido addContenido(@RequestBody Contenido contenido) {
        return contenidoService.saveContenido(contenido);
    }

    @GetMapping("/{id}")
    public Contenido getContenidoId(@PathVariable String id) {
        return contenidoService.getContenidoById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteContenido(@PathVariable String id) {
        return contenidoService.deleteContenido(id);
    }

    @PatchMapping("/{id}")
    //using id later
    public Contenido updateContenido(@PathVariable String id, @RequestBody Contenido contenido) {
        return contenidoService.updateContenido(contenido);
    }
}
