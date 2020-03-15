package fun.pancakes.planet_pancakes.service.resource;

import fun.pancakes.planet_pancakes.dto.ResourceDto;
import fun.pancakes.planet_pancakes.dto.converter.ResourceConverter;
import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.persistence.repository.ResourceRepository;
import fun.pancakes.planet_pancakes.service.exception.PriceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ResourceService {

    private ResourceRepository resourceRepository;
    private ResourceConverter resourceConverter;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository,
                                            ResourceConverter resourceConverter) {
        this.resourceRepository = resourceRepository;
        this.resourceConverter = resourceConverter;
    }

    public List<ResourceDto> findAllResources() {
        List<Resource> resources = resourceRepository.findAll();
        return convertResourcesToResourceDTOs(resources);
    }

    private List<ResourceDto> convertResourcesToResourceDTOs(List<Resource> resources) {
        List<ResourceDto> resourceDTOs = new ArrayList<>();

        for (Resource resource: resources) {
            try {
                ResourceDto resourceDto = resourceConverter.convertToDto(resource);
                resourceDTOs.add(resourceDto);
            } catch (PriceNotFoundException e) {
                log.warn("Unable to map resource {}, unable to find price history.", resource.getResourceName());
            }
        }

        return resourceDTOs;
    }
}
