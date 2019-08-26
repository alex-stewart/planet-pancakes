package fun.pancakes.planet_pancakes.repository;

import fun.pancakes.planet_pancakes.entity.Island;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IslandRepository extends MongoRepository<Island, Integer> {

    List<Island> findAll();
}
