package fun.pancakes.planet_pancakes.persistence.repository;

import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ResourceRepository extends MongoRepository<Resource, Integer> {
    Optional<Resource> findByResourceName(String resource);
}
