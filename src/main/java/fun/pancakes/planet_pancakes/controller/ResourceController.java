package fun.pancakes.planet_pancakes.controller;

import fun.pancakes.planet_pancakes.dto.ResourceDto;
import fun.pancakes.planet_pancakes.service.resource.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping()
    public ResponseEntity getAllResources() {
        log.debug("Request for all resources.");

        try {
            List<ResourceDto> resourceDtos = resourceService.findAllResources();
            return ResponseEntity.ok(resourceDtos);
        } catch (Exception e) {
            log.error("Unable to build resources response.");
            return buildErrorResponse();
        }
    }

    private ResponseEntity buildErrorResponse() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
