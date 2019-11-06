package fun.pancakes.planet_pancakes.service;

import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.persistence.entity.User;
import fun.pancakes.planet_pancakes.persistence.repository.ResourceRepository;
import fun.pancakes.planet_pancakes.persistence.repository.UserRepository;
import fun.pancakes.planet_pancakes.service.exception.PriceNotFoundException;
import fun.pancakes.planet_pancakes.service.exception.UserNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MarketService {

    private UserRepository userRepository;
    private ResourceRepository resourceRepository;

    public MarketService(UserRepository userRepository, ResourceRepository resourceRepository) {
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
    }

    public boolean buyResourceIfEnoughCoins(String userId, String resource) throws PriceNotFoundException, UserNotFoundException {
        Resource price = resourceRepository.findByResourceName(resource)
                .orElseThrow(PriceNotFoundException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (user.getCoins() >= price.getPrice()) {
            user.setCoins(user.getCoins() - price.getPrice());
            updateUserResourceCount(user, resource, 1);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    public boolean sellResourceIfResourceOwned(String userId, String resource) throws PriceNotFoundException, UserNotFoundException {
        Resource price = resourceRepository.findByResourceName(resource)
                .orElseThrow(PriceNotFoundException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);


        Integer resourceCount = user.getResources().getOrDefault(resource, 0);

        if (resourceCount > 0) {
            user.setCoins(user.getCoins() + price.getPrice());
            updateUserResourceCount(user, resource, -1);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    private void updateUserResourceCount(User user, String resource, Integer resourceCountChange) {
        Integer currentCount = user.getResources().getOrDefault(resource, 0);
        Integer updatedCount = currentCount + resourceCountChange;
        user.getResources().put(resource, updatedCount);
    }
}
