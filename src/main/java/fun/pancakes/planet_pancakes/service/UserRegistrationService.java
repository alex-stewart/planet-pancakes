package fun.pancakes.planet_pancakes.service;

import fun.pancakes.planet_pancakes.persistence.entity.User;
import fun.pancakes.planet_pancakes.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationService {

    private UserRepository userRepository;

    @Autowired
    public UserRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUserIfNotExist(String userId, String username) {
        if (!userRepository.findById(userId).isPresent()) {
            User newUser = createNewUser(userId, username);
            this.userRepository.insert(newUser);
        }
    }

    private User createNewUser(String userId, String username) {
        return User.builder()
                .id(userId)
                .name(username)
                .coins(500)
                .build();
    }

}
