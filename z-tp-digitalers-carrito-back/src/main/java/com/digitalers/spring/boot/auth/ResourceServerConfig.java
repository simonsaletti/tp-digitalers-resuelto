package com.digitalers.spring.boot.auth;

import java.util.Arrays;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/*
 * El ResourceServerConfig se encarga de otorgarle acceso a nuestros clientes a los recursos de
 * nuestra aplicación, siempre y cuando el token que nos envía el cliente sea válido.
 */

// Anotamos la clase con @Configuration para decir que contiene configuraciones de Spring.
@Configuration
// Anotamos la clase con @EnableResourceServer para habilitar este servidor de recursos.
@EnableResourceServer
// Esta clase de configuración debe heredar de ResourceServerConfigurerAdapter.
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  // Creamos un objeto que nos devuelva la configuración para el CORS y lo anotamos como Bean.
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5501"));
    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.setAllowCredentials(true);
    config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }


  @Bean
  public FilterRegistrationBean<CorsFilter> corsFilter() {
    FilterRegistrationBean<CorsFilter> bean =
        new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }

  // Sobrescribimos el método configure(HttpSecurity http) para configurar los recursos permitidos.
  @Override
  public void configure(HttpSecurity http) throws Exception {
    String[] endpointsPermitidosPorGet =
        new String[] {"/img/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**"};
    String[] endpointsPermitidosPorPost = new String[] {"/token"};
    http
        // Le damos acceso al listado de clientes para todo.
        .authorizeRequests()
        // Especificamos las rutas públicas (y verbos) a las que cualquier usuario puede acceder.
        .antMatchers(HttpMethod.GET, endpointsPermitidosPorGet).permitAll()
        .antMatchers(HttpMethod.POST, endpointsPermitidosPorPost).permitAll()
        // Cualquier otra página será sólo para usuarios autenticados.
        .anyRequest().authenticated()
        // Configuramos el CORS.
        .and().cors().configurationSource(corsConfigurationSource());
  }

}
