package com.guilherme.Janus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JanusApplication {

	public static void main(String[] args) {
		SpringApplication.run(JanusApplication.class, args);

        /*
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode("1234");
        System.out.print(senhaCriptografada);
        */
	}


}
