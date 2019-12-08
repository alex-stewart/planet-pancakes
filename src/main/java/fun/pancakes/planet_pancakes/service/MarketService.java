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

    public boolean buyResourceIfEnoughCoins(String userId, String resourceName) throws PriceNotFoundException, UserNotFoundException {
        Resource resource = resourceRepository.findByResourceName(resourceName)
                .orElseThrow(PriceNotFoundException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (user.getCoins() >= resource.getPrice()) {
            user.setCoins(user.getCoins() - resource.getPrice());
            updateUserResourceCount(user, resourceName, 1);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    public boolean sellResourceIfResourceOwned(String userId, String resourceName) throws PriceNotFoundException, UserNotFoundException {
        Resource resource = resourceRepository.findByResourceName(resourceName)
                .orElseThrow(PriceNotFoundException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);


        Integer resourceCount = user.getResources().getOrDefault(resourceName, 0);

        if (resourceCount > 0) {
            user.setCoins(user.getCoins() + resource.getPrice());
            updateUserResourceCount(user, resourceName, -1);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    private void updateUserResourceCount(User user, String resource, Integer resourceCountChange) {
        Integer currentCount = user.getResources().getOrDefault(resource, 0);
        int updatedCount = currentCount + resourceCountChange;
        user.getResources().put(resource, updatedCount);
    }
}
