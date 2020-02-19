package fun.pancakes.planet_pancakes.persistence.repository;

import fun.pancakes.planet_pancakes.persistence.entity.PriceHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PriceHistoryRepository extends MongoRepository<PriceHistory, Long> {
    Optional<PriceHistory> findByResourceNameAndDate(String resourceName, Date date);
    List<PriceHistory> findAllByResourceName(String resourceName);
}
