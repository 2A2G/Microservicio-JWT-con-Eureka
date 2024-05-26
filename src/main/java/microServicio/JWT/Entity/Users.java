package microServicio.JWT.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El campo 'username' no puede ser nulo")
    @Column(name = "username")
    private String username;

    @NotNull(message = "El campo 'password' no puede ser nulo")
    @Column(name = "password")
    private String password;

    @NotNull(message = "El campo 'role' no puede ser nulo")
    @Column(name = "role")
    private String role;

}
