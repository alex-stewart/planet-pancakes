package fun.pancakes.planet_pancakes.service.resource;

import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Slf4j
@Service
public class ResourceService {

    private PriceCalculator priceCalculator;

    @Autowired
    public ResourceService(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }

    public Resource updateResourceWithPriceAtTime(Resource resource, Date time) {
        Long price = calculateNewPrice(resource);
        resource.setPrice(price);
        updatePriceHistory(resource, time, price);
        log.info("New price for resource {} is now {}.", resource.getResourceName(), price);
        return resource;
    }

    private Long calculateNewPrice(Resource resource) {
        return priceCalculator.determineResourceNewPrice(resource.getPrice(), resource.getPriceTrendPercent(), resource.getPriceMaxChangePercent());
    }

    private void updatePriceHistory(Resource resource, Date time, Long price) {
        if (resource.getPriceHistory() == null) {
            resource.setPriceHistory(new HashMap<>());
        }
        resource.getPriceHistory().put(time, price);
    }
}
