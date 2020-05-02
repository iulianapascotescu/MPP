package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import service.*;
import ui.Console;

@Configuration
@ComponentScan({"repository.dbRepository", "service", "ui"})
public class AppConfiguration {
    @Bean
    Console console(){
        return new Console();
    }

    @Bean
    ServiceClientInterface clientService() {
        return new ServiceClient();
    }

    @Bean
    ServiceMovieInterface movieService()  {
        return new ServiceMovie();
    }

    @Bean
    ServiceRentInterface rentService()  {
        return new ServiceRent();
    }
}
