package microServicio.JWT.Security.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import microServicio.JWT.Entity.Usuario;
import microServicio.JWT.Repository.UsersRepository;
import microServicio.JWT.Security.JWT.JWT_Utils;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
public class AutenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JWT_Utils jwtUtils;

    private final UsersRepository usersRepository;

    private PasswordEncoder passwordEncoder;

    public AutenticationFilter(JWT_Utils jwtUtils, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Usuario userEntity = null;
        String username = "";
        String password = "";
        try {
            userEntity = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            username = userEntity.getUsername();
            password = userEntity.getPassword();
        } catch (Exception e) {
            throw new BadCredentialsException("Error al leer el usuario");
        }

        Usuario userDatails = usersRepository.findByUsername(username);

        if (userDatails == null) {
            throw new BadCredentialsException("Usuario no encontrado o password incorrecto");
        }

        boolean mactches = passwordEncoder.matches(password, userDatails.getPassword());
        if (!mactches) {
            throw new BadCredentialsException("Usuario no encontrado o password incorrecto");
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDatails, password);
        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException, IOException {


        User user = (User) authResult.getPrincipal();
        Usuario userDatails = usersRepository.findByUsername(user.getUsername());

        String token = jwtUtils.generateToken(userDatails);
        response.addHeader("Authorization", "Bearer " + token);

        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("token", token);
        httpResponse.put("role", userDatails.getRole().getNombreRol());
        httpResponse.put("username", userDatails.getUsername());

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(httpResponse));
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}