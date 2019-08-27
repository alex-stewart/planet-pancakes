package fun.pancakes.planet_pancakes.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.ResourceResolver;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


//@Slf4j
//@Configuration
//public class MvcConfig implements WebMvcConfigurer {
//
//    @Value("${island.directory}")
//    private String islandDirectory;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/islands/**")
//                .addResourceLocations(islandDirectory);
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("/WEB-INF/view/react/build/static/");
//        registry.addResourceHandler("/*.js")
//                .addResourceLocations("/WEB-INF/view/react/build/");
//        registry.addResourceHandler("/*.json")
//                .addResourceLocations("/WEB-INF/view/react/build/");
//        registry.addResourceHandler("/*.ico")
//                .addResourceLocations("/WEB-INF/view/react/build/");
//        registry.addResourceHandler("/index.html")
//                .addResourceLocations("/WEB-INF/view/react/build/index.html");
//    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/{spring:\\w+}")
//                .setViewName("forward:/");
//        registry.addViewController("/**/{spring:\\w+}")
//                .setViewName("forward:/");
//        registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css||\\.svg)$}")
//                .setViewName("forward:/");
//    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**");
//    }
//}

@Slf4j
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${island.directory}")
    private String islandDirectory;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/islands/**")
                .addResourceLocations(islandDirectory);
        registry.addResourceHandler("/**")
                .addResourceLocations("/WEB-INF/view/react/build/")
                .resourceChain(false)
                .addResolver(new PushStateResourceResolver());
    }

    private class PushStateResourceResolver implements ResourceResolver {
        private Resource index = new ClassPathResource("/WEB-INF/view/react/build/index.html");
        private List<String> handledExtensions = Arrays.asList("html", "js", "json", "css", "svg");
        private List<String> ignoredPaths = Arrays.asList("api", "islands");

        @Override
        public Resource resolveResource(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
            return resolve(requestPath, locations);
        }

        @Override
        public String resolveUrlPath(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
            Resource resolvedResource = resolve(resourcePath, locations);
            if (resolvedResource == null) {
                return null;
            }
            try {
                return resolvedResource.getURL().toString();
            } catch (IOException e) {
                return resolvedResource.getFilename();
            }
        }

        private Resource resolve(String requestPath, List<? extends Resource> locations) {
            if (isIgnored(requestPath)) {
                return null;
            }
            if (isHandled(requestPath)) {
                return locations.stream()
                        .map(loc -> createRelative(loc, requestPath))
                        .filter(resource -> resource != null && resource.exists())
                        .findFirst()
                        .orElseGet(null);
            }
            return index;
        }

        private Resource createRelative(Resource resource, String relativePath) {
            try {
                return resource.createRelative(relativePath);
            } catch (IOException e) {
                return null;
            }
        }

        private boolean isIgnored(String path) {
            return ignoredPaths.contains(path);
        }

        private boolean isHandled(String path) {
            String extension = StringUtils.getFilenameExtension(path);
            return handledExtensions.stream().anyMatch(ext -> ext.equals(extension));
        }
    }
}