package fun.pancakes.planet_pancakes.service.resource;

import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.persistence.repository.ResourceRepository;
import fun.pancakes.planet_pancakes.service.exception.PriceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class ResourcePriceUpdater {

    private ResourceRepository resourceRepository;
    private PriceHistoryService priceHistoryService;
    private PriceCalculator priceCalculator;

    @Autowired
    public ResourcePriceUpdater(ResourceRepository resourceRepository,
                                PriceHistoryService priceHistoryService,
                                PriceCalculator priceCalculator) {
        this.resourceRepository = resourceRepository;
        this.priceHistoryService = priceHistoryService;
        this.priceCalculator = priceCalculator;
    }

    public void updatePrices(Date date) {
        resourceRepository.findAll().stream()
                .filter(resource -> this.priceDoesNotExistForResourceAtTime(resource, date))
                .forEach(resource -> updatePrice(resource, date));
    }

    private void updatePrice(Resource resource, Date date) {
        try {
            Long currentPrice = currentPriceOfResource(resource.getResourceName());
            Long newPrice = generateNewPriceForResource(resource, currentPrice);
            addPriceHistory(resource.getResourceName(), newPrice, date);
        } catch(PriceNotFoundException e){
            log.error("Resource {} does not have a current price.", resource.getResourceName());
        }
    }

    private boolean priceDoesNotExistForResourceAtTime(Resource resource, Date date) {
        boolean priceHistoryExits = priceHistoryService.hasPriceHistory(resource.getResourceName(), date);
        if (priceHistoryExits) {
            log.warn("Resource {} already has a price at {}.", resource.getResourceName(), date);
        }
        return !priceHistoryExits;
    }

    private void addPriceHistory(String resourceName, Long resourcePrice, Date date) {
        priceHistoryService.addPriceHistory(resourceName, resourcePrice, date);
    }

    private Long generateNewPriceForResource(Resource resource, Long currentPrice){
        return priceCalculator.determineResourceNewPrice(currentPrice, resource.getPriceTrendPercent(), resource.getPriceMaxChangePercent());
    }

    private Long currentPriceOfResource(String resourceName) throws PriceNotFoundException {
        return priceHistoryService.getMostRecentPriceForResource(resourceName);
    }

}
