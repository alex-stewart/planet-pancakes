package fun.pancakes.planet_pancakes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${island.directory}")
    private String islandDirectory;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/islands/**")
                .addResourceLocations(islandDirectory);
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/WEB-INF/view/react/build/static/");
        registry.addResourceHandler("/*.js")
                .addResourceLocations("/WEB-INF/view/react/build/");
        registry.addResourceHandler("/*.css")
                .addResourceLocations("/WEB-INF/view/react/build/");
        registry.addResourceHandler("/*.json")
                .addResourceLocations("/WEB-INF/view/react/build/");
        registry.addResourceHandler("/*.ico")
                .addResourceLocations("/WEB-INF/view/react/build/");
        registry.addResourceHandler("/index.html")
                .addResourceLocations("/WEB-INF/view/react/build/index.html");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}