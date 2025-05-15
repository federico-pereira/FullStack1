package duoc.proyect.controller;

import duoc.proyect.model.Contenido;
import duoc.proyect.service.ContenidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contenidos")
public class ContenidoController {

    @Autowired
    private ContenidoService contenidoService;

    @GetMapping
    public String getContenido() {
        return contenidoService.getContenidos();
    }

    @PostMapping
    public String addContenido(@RequestBody Contenido contenido) {
        return contenidoService.addContenido(contenido);
    }

    @GetMapping("/{id}")
    public String getContenidoId(@PathVariable int id) {return contenidoService.getContenidoById(id);}

    @DeleteMapping("/{id}")
    public String deleteContenido(@PathVariable int id) {
        return contenidoService.deleteContenido(id);
    }

    @PutMapping("/{id}")
    public String updateContenido(@RequestBody Contenido contenido) {
        return contenidoService.updateContenido(contenido);
    }
}
