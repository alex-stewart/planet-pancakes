package fun.pancakes.planet_pancakes.service.resource;

import fun.pancakes.planet_pancakes.persistence.entity.PriceHistory;
import fun.pancakes.planet_pancakes.persistence.repository.PriceHistoryRepository;
import fun.pancakes.planet_pancakes.service.exception.PriceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class PriceHistoryService {

    private PriceHistoryRepository priceHistoryRepository;

    @Autowired
    public PriceHistoryService(PriceHistoryRepository priceHistoryRepository) {
        this.priceHistoryRepository = priceHistoryRepository;
    }

    public void addPriceHistory(String resourceName, Long resourcePrice, Date date) {
        PriceHistory priceHistory = PriceHistory.builder()
                .id(UUID.randomUUID().toString())
                .resourceName(resourceName)
                .price(resourcePrice)
                .date(date)
                .build();

        log.debug("Persisting price history {}.", priceHistory.toString());
        priceHistoryRepository.save(priceHistory);
    }

    public boolean hasPriceHistory(String resourceName, Date date) {
        return priceHistoryRepository.findByResourceNameAndDate(resourceName, date).isPresent();
    }

    public Long getMostRecentPriceForResource(String resourceName) throws PriceNotFoundException {
        return priceHistoryRepository.findTopByResourceNameOrderByDateDesc(resourceName)
                .map(PriceHistory::getPrice)
                .orElseThrow(PriceNotFoundException::new);
    }

    public List<PriceHistory> getPriceHistoryForResource(String resourceName) {
        return priceHistoryRepository.findAllByResourceName(resourceName);
    }
}
