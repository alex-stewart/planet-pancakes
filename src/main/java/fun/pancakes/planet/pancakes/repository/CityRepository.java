package fun.pancakes.planet.pancakes.repository;

import fun.pancakes.planet.pancakes.entity.City;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CityRepository extends MongoRepository<City, String> {

    List<City> findAll();
}
