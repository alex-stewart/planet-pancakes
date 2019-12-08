package fun.pancakes.planet_pancakes.converter;

import fun.pancakes.planet_pancakes.dto.ResourceDto;
import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ResourceConverter {

    public ResourceDto convertToDto(Resource resource) {
        return ResourceDto.builder()
                .resourceName(resource.getResourceName())
                .price(resource.getPrice())
                .priceHistory(convertPriceHistory(resource))
                .build();
    }

    private Map<Instant, Long> convertPriceHistory(Resource resource) {
        return Optional.ofNullable(resource.getPriceHistory())
                .map(this::convertPriceHistoryMap)
                .orElse(new HashMap<>());
    }

    private Map<Instant, Long> convertPriceHistoryMap(Map<Date, Long> priceHistory) {
        Map<Instant, Long> newPriceHistory = new HashMap<>();
        priceHistory.forEach((key, value) -> newPriceHistory.put(key.toInstant(), value));
        return newPriceHistory;
    }

}
