package fun.pancakes.planet_pancakes.persistence.repository;

import fun.pancakes.planet_pancakes.persistence.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
