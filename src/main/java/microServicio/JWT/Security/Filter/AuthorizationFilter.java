package microServicio.JWT.Security.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import microServicio.JWT.Security.JWT.JWT_Utils;
import microServicio.JWT.Services.UserDetailServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JWT_Utils jwtUtils;
    @Autowired
    private UserDetailServicesImpl userDetailServicesImpl;


    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {

        String tokenHeder = request.getHeader("Authorization");
        if (tokenHeder != null && tokenHeder.startsWith("Bearer ")) {
            String token = tokenHeder.substring(7);
            if (jwtUtils.isTokenValid(token)) {
                String Username = jwtUtils.getUsernameFromToken(token);
                UserDetails userDetail = userDetailServicesImpl.loadUserByUsername(Username);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                throw new RuntimeException("Token no válido");
            }

        } else {
            throw new RuntimeException("Token no encontrado");
        }
    }

}
