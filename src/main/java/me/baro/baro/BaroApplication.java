package me.baro.baro;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BaroApplication {
    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "/home/ec2-user/config/real-application.yml"
            ;

    public static void main(String[] args) {
        new SpringApplicationBuilder(BaroApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }
}
