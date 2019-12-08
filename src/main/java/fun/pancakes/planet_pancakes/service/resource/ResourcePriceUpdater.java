package fun.pancakes.planet_pancakes.service.resource;

import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.persistence.repository.ResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ResourcePriceUpdater {

    private ResourceService resourceService;
    private ResourceRepository resourceRepository;

    @Autowired
    public ResourcePriceUpdater(ResourceService resourceService,
                                ResourceRepository resourceRepository) {
        this.resourceService = resourceService;
        this.resourceRepository = resourceRepository;
    }

    public void updatePrices(Date time) {
        List<Resource> resources = resourceRepository.findAll().stream()
                .filter(resource -> priceDoesNotExistForResourceAtTime(resource, time))
                .map(resource -> resourceService.updateResourceWithPriceAtTime(resource, time))
                .collect(Collectors.toList());

        log.debug("Persisting resources {}.", resources.toString());
        resourceRepository.saveAll(resources);
    }

    private boolean priceDoesNotExistForResourceAtTime(Resource resource, Date time) {
        boolean containsKey = resource.getPriceHistory().containsKey(time);
        if (containsKey) {
            log.warn("Resource {} already has a price at {}.", resource.getResourceName(), time);
        }
        return !containsKey;
    }
}
