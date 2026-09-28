package dev.victor.swapimcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SwapiMcpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwapiMcpApplication.class, args);
    }
}
