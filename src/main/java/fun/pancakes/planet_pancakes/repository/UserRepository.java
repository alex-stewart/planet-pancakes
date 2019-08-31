package fun.pancakes.planet_pancakes.repository;

import fun.pancakes.planet_pancakes.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
