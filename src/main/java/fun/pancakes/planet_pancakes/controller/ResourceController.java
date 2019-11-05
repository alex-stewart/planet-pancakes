package fun.pancakes.planet_pancakes.controller;

import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.persistence.repository.ResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@EnableSwagger2
public class ResourceController {

    private ResourceRepository resourceRepository;

    public ResourceController(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @GetMapping("/resources")
    public List<Resource> getResources() {
        return resourceRepository.findAll();
    }
}
