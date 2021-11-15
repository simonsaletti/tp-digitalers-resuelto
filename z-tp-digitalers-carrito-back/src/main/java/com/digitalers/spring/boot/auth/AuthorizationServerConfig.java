package com.digitalers.spring.boot.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/*
 * Esta clase se encarga de todo el proceso de autenticación relacionado con todo lo que tenga que
 * ver con el token, desde el proceso de login, crear el token, validarlo, entre otras operaciones.
 * Al igual que en la clase SpringSecurityConfig, debemos heredar (extender) desde una configuración
 * base y, luego, implementar algunos métodos.
 */

// Anotamos la clase con @Configuration para decir que contiene configuraciones de Spring.
@Configuration
// Usamos @EnableAuthorizationServer para habilitar el authorization server.
@EnableAuthorizationServer
// Esta clase de configuración debe heredar de AuthorizationServerConfigurerAdapter.
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    // Inyectamos el password encoder, definido como Bean en la clase SpringSecurityConfig.
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Inyectamos el authentication manager, definido como Bean en la clase SpringSecurityConfig.
    @Autowired
    // Si tenemos muchos authentication manager, podríamos usar un calificador (ahora no hace falta).
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    // Creamos un componente de tipo JwtAccessTokenConverter, que necesitaremos luego para trabajar con
    // el token (decodificar/codificar su información).
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        return jwtAccessTokenConverter;
    }

    // Creamos un componente de tipo TokenStore.
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    // Sobrescribimos el método configure(AuthorizationServerEndpointsConfigurer endpoints).
    /*
     * Configuramos el endpoint del authorization server, que se encarga de todo el proceso de
     * autenticación y validación del token.
     */
    /*
     * Primero, cada vez que iniciamos sesión, enviamos nuestro usuario y contraseña y, si todo sale
     * bien, se realiza la autenticación, se genera el token y se lo entrega al usuario para que éste
     * use dicho token para acceder a las distintas páginas y recursos de nuestra aplicación backend.
     * Cada vez que accedemos con un token a un recurso, éste tiene que ser validado por el servidor de
     * autorización.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // Registramos el authenticationManager, inyectado arriba de todo.
                .authenticationManager(authenticationManager)
                // Registramos el accesTokenConverter, que se encarga varias cosas relacionadas con el token
                /*
                 * Por ejemplo, almacenar los datos de autenticación del usuario: el usarname, los roles y datos
                 * extra que necesitemos agregar (también conocidos como claims).
                 */
                /*
                 * El accesTokenConverter también se encarga de traducir toda esta información mencionada que viene
                 * codificada dentro del token, con el objetivo de convertirla en una información decodificada para
                 * que el authentication manager, mediante un protocolo llamado "oauth2", pueda realizar el proceso
                 * de autenticación. En general se encarga de traducir los datos del token para verificar que el
                 * mismo sea válido.
                 */
                /*
                 * A este componente, por debajo, lo va a utilizar el token storage, otro componente que se encarga
                 * de almacenar los tokens.
                 */
                .tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter());

        super.configure(endpoints);
    }

    // Sobrescribimos el método configure(ClientDetailsServiceConfigurer clients) para registrar el
    // frontend desde el cual vamos a hacer solicitudes.
    /*
     * Como tenemos una sola aplicación frontend que envía solicitudes hacia este backend, entonces
     * registramos un solo cliente. En caso de que haya muchas aplicaciones que nos consulten este
     * backend, tendríamos que configurar más clientes.
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                // Seleccionamos el tipo de almacenamiento para el ejemplo.
                .inMemory()
                // Registramos un id del cliente (el cliente es la aplicación frontend).
                .withClient("carrito")
                // Le colocamos una contraseña (debe estar encriptada utilizando el password encoder inyectado).
                .secret(passwordEncoder.encode("12345"))
                // Le damos permiso de lectura y escritura al cliente.
                .scopes("read", "write")
                // Aclaramos cómo va a ser la autenticación del usuario (será a través de una constraseña).
                .authorizedGrantTypes("password", "refresh_token")
                // Especificamos en cuánto tiempo va a caducar nuestro token (ponemos una hora).
                .accessTokenValiditySeconds(3600).refreshTokenValiditySeconds(3600);
    }

    // Sobrescribimos el método configure(AuthorizationServerSecurityConfigurer security) para
    // Configurar qué usuarios pueden autenticarse en nuestro backend.
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // Damos acceso a cualquier usuario para poder autenticarse utilizando el endpoint de autenticación.
                // El endpoint de autenticación es /oauth/token.
                .tokenKeyAccess("permitAll()")
                // Cada vez que intentemos acceder a un recurso de esta aplicación Java, vamos a enviar el token en
                // la cabecera de la solicitud.
                // Solamente pueden acceder a los recursos los clientes autenticados.
                .checkTokenAccess("isAuthenticated()");
    }

}
