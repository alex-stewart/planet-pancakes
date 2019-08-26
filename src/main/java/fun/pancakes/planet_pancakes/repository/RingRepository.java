package fun.pancakes.planet_pancakes.repository;

import fun.pancakes.planet_pancakes.entity.Ring;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RingRepository extends MongoRepository<Ring, Integer> {

    Ring findDistinctById(Integer id);
}
