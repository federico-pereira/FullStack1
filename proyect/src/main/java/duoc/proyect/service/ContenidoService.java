package duoc.proyect.service;

import duoc.proyect.model.Contenido;
import duoc.proyect.repositoy.ContenidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContenidoService {

    @Autowired
    private ContenidoRepository contenidoRepository;

    public String getContenidos() {
        String output = "";
        for (Contenido contenido : contenidoRepository.findAll()) {
            output += contenido.toString() + "\n";
        }
        if (contenidoRepository.findAll().isEmpty()) {
            return "No hay contenidos";
        }
        return output;
    }

    public String getContenidoById(int id) {
        if (contenidoRepository.existsById(id)){
            for (Contenido contenido : contenidoRepository.findAll()) {
                if (contenido.getId() == id) {
                    return contenido.toString();
                }
            }
        }
        return "Contenido no encontrado";
    }

    public String addContenido(Contenido contenido) {
        contenidoRepository.save(contenido);
        return "Contenido agregado";
    }

    public String updateContenido(Contenido contenido) {
        contenidoRepository.save(contenido);
        return "Contenido actualizado";
    }

    public String deleteContenido(int id) {
        if (contenidoRepository.existsById(id)) {
            contenidoRepository.deleteById(id);
            return "Contenido eliminado";
        }
        return "Contenido no encontrado";
    }
}
