package microServicio.JWT.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Rol;

    @NotNull
    private String nombreRol;

    @ManyToMany(mappedBy = "roles")
    private Set<Permiso> permisos;
}
