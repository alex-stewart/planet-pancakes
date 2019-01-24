package fun.pancakes.planet.pancakes.repository;

import fun.pancakes.planet.pancakes.entity.Island;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IslandRepository extends MongoRepository<Island, String> {

    List<Island> findAll();
}
