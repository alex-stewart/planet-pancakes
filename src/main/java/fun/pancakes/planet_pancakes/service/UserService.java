package fun.pancakes.planet_pancakes.service;

import fun.pancakes.planet_pancakes.dto.ProfileDto;
import fun.pancakes.planet_pancakes.persistence.entity.User;
import fun.pancakes.planet_pancakes.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<ProfileDto> getProfileForUser(String userId, boolean isLoggedInUser) {
        return userRepository.findById(userId)
                .map(u -> profileForUser(u, isLoggedInUser));
    }

    private ProfileDto profileForUser(User user, boolean isLoggedInUser) {
        if (isLoggedInUser) {
            return profileForLoggedInUser(user);
        } else {
            return profileForGenericUser(user);
        }
    }

    private ProfileDto profileForLoggedInUser(User user) {
        return profileForGenericUser(user).toBuilder()
                .coins(user.getCoins())
                .resources(user.getResources())
                .build();
    }

    private ProfileDto profileForGenericUser(User user) {
        return ProfileDto.builder()
                .id(user.getId())
                .name(user.getName())
                .location(user.getLocation())
                .build();
    }
}
