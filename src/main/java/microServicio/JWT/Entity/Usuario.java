package microServicio.JWT.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_User;

    @NotNull(message = "El campo 'username' no puede ser nulo")
    @Column(name = "username")
    private String username;

    @NotNull(message = "El campo 'email' no puede ser nulo")
    @Column(name = "email")
    private String email;

    @NotNull(message = "El campo 'password' no puede ser nulo")
    @Column(name = "password")
    private String password;

    @ManyToOne
    private Rol role;

}
