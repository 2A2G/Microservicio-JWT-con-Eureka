package microServicio.JWT.Services;

import microServicio.JWT.Entity.Usuario;
import microServicio.JWT.Repository.UsersRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserDetailServicesImpl implements UserDetailsService {

    private final UsersRepository usuarioRepository;

    public UserDetailServicesImpl(UsersRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuarioDetails = usuarioRepository.findByUsername(username);

        Set<GrantedAuthority> authorities = Stream.of(usuarioDetails.getRole().getNombreRol())
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol))
                .collect(Collectors.toSet());


        return new User(usuarioDetails.getUsername(), usuarioDetails.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }

}