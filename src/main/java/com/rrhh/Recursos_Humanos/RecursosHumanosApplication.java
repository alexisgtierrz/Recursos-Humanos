package com.rrhh.Recursos_Humanos;

import com.rrhh.Recursos_Humanos.Modelos.Usuario;
import com.rrhh.Recursos_Humanos.Repositorios.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class RecursosHumanosApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecursosHumanosApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UsuarioRepository usuarioRepository, BCryptPasswordEncoder encoder) {
		return args -> {
			// Verifica si ya existe el usuario admin
			if (usuarioRepository.findByUsername("admin").isEmpty()) {
				Usuario admin = new Usuario();
				admin.setUsername("admin");
				admin.setPassword("1234"); // encriptar la contraseña
				admin.setRoles(Set.of("ADMIN")); // rol ADMIN
				usuarioRepository.save(admin);
				System.out.println("Usuario admin creado con éxito!");
			}
		};
	}
}
