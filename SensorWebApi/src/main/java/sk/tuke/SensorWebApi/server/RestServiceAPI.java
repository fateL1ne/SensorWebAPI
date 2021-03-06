package sk.tuke.SensorWebApi.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RestServiceAPI {
    public static void main(String[] args) {
        SpringApplication.run(RestServiceAPI.class, args);
    }
}
