package fun.pancakes.planet_pancakes.service.resource;

import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResourceService {

    private PriceCalculator priceCalculator;

    @Autowired
    public ResourceService(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }

    public Resource updateResourceWithPriceAtTime(Resource resource) {
        Long price = calculateNewPrice(resource);
        resource.setPrice(price);
        log.info("New price for resource {} is now {}.", resource.getResourceName(), price);
        return resource;
    }

    private Long calculateNewPrice(Resource resource) {
        return priceCalculator.determineResourceNewPrice(resource.getPrice(), resource.getPriceTrendPercent(), resource.getPriceMaxChangePercent());
    }
}
