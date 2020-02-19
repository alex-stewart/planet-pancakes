package fun.pancakes.planet_pancakes.dto.converter;

import fun.pancakes.planet_pancakes.dto.ResourceDto;
import fun.pancakes.planet_pancakes.persistence.entity.PriceHistory;
import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.persistence.repository.PriceHistoryRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ResourceConverter {

    private PriceHistoryRepository priceHistoryRepository;

    public ResourceConverter(PriceHistoryRepository priceHistoryRepository) {
        this.priceHistoryRepository = priceHistoryRepository;
    }

    public ResourceDto convertToDto(Resource resource) {
        return ResourceDto.builder()
                .resourceName(resource.getResourceName())
                .price(resource.getPrice())
                .priceHistory(getPriceHistoriesForResource(resource.getResourceName()))
                .build();
    }

    private Map<Instant, Long> getPriceHistoriesForResource(String resourceName) {
        List<PriceHistory> priceHistories = priceHistoryRepository.findAllByResourceName(resourceName);
        return priceHistories == null ? new HashMap<>() : convertPriceHistoryMap(priceHistories);
    }

    private Map<Instant, Long> convertPriceHistoryMap(List<PriceHistory> priceHistories) {
        return priceHistories.stream()
                .collect(Collectors.toMap(price -> price.getDate().toInstant(), PriceHistory::getPrice));
    }

}
