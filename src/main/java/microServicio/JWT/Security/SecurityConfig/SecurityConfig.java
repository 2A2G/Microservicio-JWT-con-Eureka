//package microServicio.JWT.Security.SecurityConfig;
//
//import microServicio.JWT.Repository.UsersRepository;
//import microServicio.JWT.Security.Filter.AuthorizationFilter;
//import microServicio.JWT.Security.JWT.JWT_Utils;
//import microServicio.JWT.Services.UserDetailServicesImpl;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//
//@Configuration
//@EnableWebSecurity
//@CrossOrigin(origins = "*")
//public class SecurityConfig {
//
//    final JWT_Utils jwtUtils;
//
//    final UserDetailServicesImpl userDetailServices;
//
//    final AuthorizationFilter jwtAuthorizationFilter;
//
//    private final UsersRepository usersRepository;
//
//
//    public SecurityConfig(JWT_Utils jwtUtils, UserDetailServicesImpl userDetailServices, AuthorizationFilter jwtAuthorizationFilter, UsersRepository usersRepository) {
//        this.jwtUtils = jwtUtils;
//        this.userDetailServices = userDetailServices;
//        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
//        this.usersRepository = usersRepository;
//    }
//
//
