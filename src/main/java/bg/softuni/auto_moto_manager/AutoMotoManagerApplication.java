package bg.softuni.auto_moto_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AutoMotoManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoMotoManagerApplication.class, args);
    }

}
