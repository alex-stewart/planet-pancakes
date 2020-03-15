package fun.pancakes.planet_pancakes.dto.converter;

import fun.pancakes.planet_pancakes.dto.ResourceDto;
import fun.pancakes.planet_pancakes.persistence.entity.PriceHistory;
import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.service.exception.PriceNotFoundException;
import fun.pancakes.planet_pancakes.service.resource.PriceHistoryService;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ResourceConverter {

    private PriceHistoryService priceHistoryService;

    public ResourceConverter(PriceHistoryService priceHistoryService) {
        this.priceHistoryService = priceHistoryService;
    }

    public ResourceDto convertToDto(Resource resource) throws PriceNotFoundException {
        String resourceName = resource.getResourceName();

        return ResourceDto.builder()
                .resourceName(resourceName)
                .priceHistory(getPriceHistoriesForResource(resourceName))
                .price(getCurrentPriceForResource(resourceName))
                .category(resource.getCategory())
                .build();
    }

    private Long getCurrentPriceForResource(String resourceName) throws PriceNotFoundException {
        return priceHistoryService.getMostRecentPriceForResource(resourceName);
    }

    private Map<Instant, Long> getPriceHistoriesForResource(String resourceName) {
        List<PriceHistory> priceHistories = priceHistoryService.getPriceHistoryForResource(resourceName);
        return priceHistories == null ? new HashMap<>() : convertPriceHistoryMap(priceHistories);
    }

    private Map<Instant, Long> convertPriceHistoryMap(List<PriceHistory> priceHistories) {
        return priceHistories.stream()
                .collect(Collectors.toMap(price -> price.getDate().toInstant(), PriceHistory::getPrice));
    }

}
