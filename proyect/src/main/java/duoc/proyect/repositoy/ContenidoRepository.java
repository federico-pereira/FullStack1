package duoc.proyect.repositoy;

import duoc.proyect.model.Contenido;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ContenidoRepository {

    private List<Contenido> contenidos = new ArrayList<>();

    public ContenidoRepository() {
        contenidos.add(new Contenido("1","testContent1","testDescription1","testFile1.jpg"));
        contenidos.add(new Contenido("2","testContent2","testDescription2","testFile2.jpg"));
        contenidos.add(new Contenido("3","testContent3","testDescription3","testFile3.jpg"));

    }

    public List<Contenido> getContenidos() {
        return contenidos;
    }

    public Contenido getContenidoById(String id) {
        for (Contenido contenido : contenidos) {
            if (contenido.getId().equals(id)) {
                return contenido;
            }
        }
        return null;
    }

    public Contenido addContenido(Contenido contenido) {
        contenidos.add(contenido);
        return contenido;
    }

    public String deleteContenido(String id) {
        for (Contenido contenido : contenidos) {
            if (contenido.getId().equals(id)) {
                contenidos.remove(contenido);
                return contenido + "removed";
            }
        }
        return "Contenido not found";
    }

    public String updateContenido(Contenido contenido) {
        for (Contenido contenido2 : contenidos) {
            if (contenido2.getId().equals(contenido.getId())) {
                contenido2.setNombre(contenido.getNombre());
                contenido2.setDescripcion(contenido.getDescripcion());
                contenido2.setArchivo(contenido.getArchivo());
                return contenido2 + "updated";
            }
        }
        return contenido + "not found";
    }


}
