package fun.pancakes.planet_pancakes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final String CLASSPATH_STATIC = "classpath:/static/";

    @Value("${island.directory}")
    private String islandDirectory;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/islands/**")
                .addResourceLocations(islandDirectory);
        registry.addResourceHandler("/static/**")
                .addResourceLocations(CLASSPATH_STATIC + "static/");
    }
}