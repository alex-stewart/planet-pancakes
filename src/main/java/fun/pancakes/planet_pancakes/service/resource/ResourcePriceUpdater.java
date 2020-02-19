package fun.pancakes.planet_pancakes.service.resource;

import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.persistence.repository.ResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class ResourcePriceUpdater {

    private ResourceService resourceService;
    private ResourceRepository resourceRepository;
    private PriceHistoryService priceHistoryService;

    @Autowired
    public ResourcePriceUpdater(ResourceService resourceService,
                                ResourceRepository resourceRepository,
                                PriceHistoryService priceHistoryService) {
        this.resourceService = resourceService;
        this.resourceRepository = resourceRepository;
        this.priceHistoryService = priceHistoryService;
    }

    public void updatePrices(Date date) {
        resourceRepository.findAll().stream()
                .filter(resource -> this.priceDoesNotExistForResourceAtTime(resource, date))
                .forEach(resource -> updatePrice(resource, date));
    }

    private void updatePrice(Resource resource, Date date) {
        resource = resourceService.updateResourceWithPriceAtTime(resource);
        persistResource(resource);
        addPriceHistory(resource, date);
    }

    private boolean priceDoesNotExistForResourceAtTime(Resource resource, Date date) {
        boolean priceHistoryExits = priceHistoryService.hasPriceHistory(resource.getResourceName(), date);
        if (priceHistoryExits) {
            log.warn("Resource {} already has a price at {}.", resource.getResourceName(), date);
        }
        return !priceHistoryExits;
    }

    private void persistResource(Resource resource) {
        log.debug("Persisting resource {}.", resource.toString());
        resourceRepository.save(resource);
    }

    private void addPriceHistory(Resource resource, Date date) {
        priceHistoryService.addPriceHistory(resource.getResourceName(), resource.getPrice(), date);
    }

}
