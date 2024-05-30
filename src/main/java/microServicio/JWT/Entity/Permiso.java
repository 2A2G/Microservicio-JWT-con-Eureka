package microServicio.JWT.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "permisos")
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Permiso;

    @NotNull(message = "El campo 'nombrePermiso' no puede ser nulo")
    private String nombrePermiso;

    @ManyToMany
    @JoinTable(name = "rol_permiso",
            joinColumns = @JoinColumn(name = "id_Permiso"),
            inverseJoinColumns = @JoinColumn(name = "id_Rol"))
    private Set<Rol> roles;
}
