package microServicio.JWT.Repository;

import microServicio.JWT.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<Usuario, Long> {


}
