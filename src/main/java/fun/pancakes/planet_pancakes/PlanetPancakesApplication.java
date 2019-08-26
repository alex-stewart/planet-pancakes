package fun.pancakes.planet_pancakes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;

@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
public class PlanetPancakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanetPancakesApplication.class, args);
    }

}

