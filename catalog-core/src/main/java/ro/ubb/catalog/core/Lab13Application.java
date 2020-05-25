package ro.ubb.catalog.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"ro.ubb.catalog.core"})
@EntityScan("ro.ubb.catalog.core.model")
public class Lab13Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab13Application.class, args);
    }

}
