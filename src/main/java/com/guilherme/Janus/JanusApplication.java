package com.guilherme.Janus;

import com.guilherme.Janus.service.PomodoroService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JanusApplication {

	public static void main(String[] args) {
		SpringApplication.run(JanusApplication.class, args);

        PomodoroService service = new PomodoroService();

        int count = 0;
        while(true){
            count++;
            service.temporizador(25, "Modo Foco"); // 25min

            if(count == 5){
                service.temporizador(15, "Modo Descanso Longo"); //15 min
                count = 0;
            } else {
                service.temporizador(5, "Modo Descanso"); // 5min
            }
        }
    }
}
