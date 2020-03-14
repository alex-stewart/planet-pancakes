package fun.pancakes.planet_pancakes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final String CLASSPATH_STATIC = "classpath:/static/";

    @Value("${island.directory}")
    private String islandDirectory;

    @Value("${flag.directory}")
    private String flagDirectory;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        addResourceHandler(registry, "/islands/**", islandDirectory);
        addResourceHandler(registry, "/flags/**", flagDirectory);
        addResourceHandler(registry, "/static/**", CLASSPATH_STATIC + "static/");
    }

    private void addResourceHandler(ResourceHandlerRegistry registry, String pathPattern, String resourceLocations) {
        registry.addResourceHandler(pathPattern)
                .addResourceLocations(resourceLocations)
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.DAYS));
    }
}