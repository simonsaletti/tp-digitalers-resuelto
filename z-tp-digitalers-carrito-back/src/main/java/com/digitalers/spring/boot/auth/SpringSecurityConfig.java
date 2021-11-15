package com.digitalers.spring.boot.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Anotamos la clase con @Configuration para decir que contiene configuraciones de Spring.
@Configuration
// Esta clase de configuración debe heredar de WebSecurityConfigurerAdapter.
// No hace falta que la clase se llame sí o sí SpringSecurityConfig.
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    // Esto representa al servicio de usuario (es la interfaz padre implementada por UsuarioService).
    private UserDetailsService usuarioService;

    // Creamos un objeto encriptador de contraseñas y lo anotamos como Bean para inyectarlo luego.
    // Al estar registrado como Bean, también podemos inyectarlo en otro lado fácilmente usando
    // @Autowired.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Creamos un objeto AuthenticationManager y lo anotamos como Bean para inyectarlo luego.
    // Al estar registrado como Bean, también podemos inyectarlo en otro lado fácilmente usando
    // @Autowired.
    // Vamos a necesitar este objeto para que intervenga en el proceso de login.
    // Recordar que, por defecto, este componente va a quedar registrado con el nombre
    // "authenticationManager", debido al nombre del método.
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // Sobrescribimos el método "configure(AuthenticationManagerBuilder auth)".
    @Override
    // Usamos @Autowired para que se inyecte un AuthenticationManagerBuilder por parámetro.
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                // Registramos el objeto de tipo UserDetailsService.
                .userDetailsService(this.usuarioService)
                // Configuramos el password encoder.
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // .authorizeRequests().antMatchers(HttpMethod.GET,
                // "/carritos").permitAll().anyRequest().authenticated().and().csrf().disable()

                // Desactivamos las sesiones, ya que nos vamos a manejar con tokens.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }



}
