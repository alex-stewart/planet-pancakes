package fun.pancakes.planet.pancakes.repository;

import fun.pancakes.planet.pancakes.entity.Ring;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RingRepository extends MongoRepository<Ring, Integer> {

    Ring findDistinctById(Integer id);
}
