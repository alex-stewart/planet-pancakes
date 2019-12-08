package fun.pancakes.planet_pancakes.controller;

import fun.pancakes.planet_pancakes.converter.ResourceConverter;
import fun.pancakes.planet_pancakes.dto.ResourceDto;
import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.persistence.repository.ResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private static Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private ResourceRepository resourceRepository;
    private ResourceConverter resourceConverter;

    @Autowired
    public ResourceController(ResourceRepository resourceRepository, ResourceConverter resourceConverter) {
        this.resourceRepository = resourceRepository;
        this.resourceConverter = resourceConverter;
    }

    @GetMapping()
    public ResponseEntity getAllResources() {
        logger.debug("Request for all resources.");

        try {
            List<Resource> resources = resourceRepository.findAll();
            List<ResourceDto> resourceDtos = convertResourceListToDto(resources);
            logger.debug("Resources found and returned: {}", resourceDtos);
            return ResponseEntity.ok(resourceDtos);
        } catch (Exception e) {
            logger.error("Unable to build resources response.");
            return buildErrorResponse();
        }
    }

    private ResponseEntity buildErrorResponse() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    private List<ResourceDto> convertResourceListToDto(List<Resource> resources) {
        return resources.stream()
                .map(resourceConverter::convertToDto)
                .collect(Collectors.toList());
    }
}
