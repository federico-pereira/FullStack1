package duoc.proyect.repository;

import duoc.proyect.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

    boolean existsByName(String name);
}
