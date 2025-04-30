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

    public List<Contenido> getAllContenido() {
        return contenidoRepository.getContenidos();
    }

    public Contenido getContenidoById(String id) {
        return contenidoRepository.getContenidoById(id);
    }

    public String saveContenido(Contenido contenido) {
        return contenidoRepository.addContenido(contenido);
    }

    public String updateContenido(Contenido contenido) {
        return contenidoRepository.addContenido(contenido);
    }

    public String deleteContenido(String id) {
        return contenidoRepository.deleteContenido(id);
    }
}
