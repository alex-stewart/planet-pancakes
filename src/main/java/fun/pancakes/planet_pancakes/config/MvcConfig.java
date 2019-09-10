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
        registry.addResourceHandler("/*.js")
                .addResourceLocations(CLASSPATH_STATIC);
        registry.addResourceHandler("/*.css")
                .addResourceLocations(CLASSPATH_STATIC);
        registry.addResourceHandler("/*.json")
                .addResourceLocations(CLASSPATH_STATIC);
        registry.addResourceHandler("/*.ico")
                .addResourceLocations(CLASSPATH_STATIC);
        registry.addResourceHandler("/index.html")
                .addResourceLocations(CLASSPATH_STATIC + "index.html");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}