package com.digitalers.spring.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
/*
 * Implementamos la interfaz CommandLineRunner para poder correr cosas en la consola. La idea es
 * hacer un for que genere contraseñas encriptadas, para guardarlas en la base de datos.
 */
public class ZzCarritoComprasSpringBootApplication implements CommandLineRunner {

    @Autowired
    private BCryptPasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(ZzCarritoComprasSpringBootApplication.class, args);
    }

    // Usamos este método para generar contraseñas encriptadas.
    @Override
    public void run(String... args) throws Exception {
        String password = "12345";

        for (int i = 0; i < 2; i++) {
            String passEncriptada = encoder.encode(password);
            System.out.println(passEncriptada);
        }

    }

}
