package fun.pancakes.planet_pancakes.service;

import fun.pancakes.planet_pancakes.persistence.entity.User;
import fun.pancakes.planet_pancakes.persistence.repository.UserRepository;
import fun.pancakes.planet_pancakes.service.exception.PriceNotFoundException;
import fun.pancakes.planet_pancakes.service.exception.UserNotFoundException;
import fun.pancakes.planet_pancakes.service.resource.PriceHistoryService;
import org.springframework.stereotype.Component;

@Component
public class MarketService {

    private UserRepository userRepository;
    private PriceHistoryService priceHistoryService;

    public MarketService(UserRepository userRepository, PriceHistoryService priceHistoryService) {
        this.userRepository = userRepository;
        this.priceHistoryService = priceHistoryService;
    }

    public boolean buyResourceIfEnoughCoins(String userId, String resourceName) throws PriceNotFoundException, UserNotFoundException {
        Long price = currentPriceForResource(resourceName);
        User user = retrieveUser(userId);

        if (user.getCoins() >= price) {
            user.setCoins(user.getCoins() - price);
            updateUserResourceCount(user, resourceName, 1);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    public boolean sellResourceIfResourceOwned(String userId, String resourceName) throws PriceNotFoundException, UserNotFoundException {
        Long price = currentPriceForResource(resourceName);
        User user = retrieveUser(userId);

        Integer resourceCount = user.getResources().getOrDefault(resourceName, 0);

        if (resourceCount > 0) {
            user.setCoins(user.getCoins() + price);
            updateUserResourceCount(user, resourceName, -1);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    private User retrieveUser(String userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private Long currentPriceForResource(String resourceName) throws PriceNotFoundException {
        return priceHistoryService.getMostRecentPriceForResource(resourceName);
    }

    private void updateUserResourceCount(User user, String resource, Integer resourceCountChange) {
        Integer currentCount = user.getResources().getOrDefault(resource, 0);
        int updatedCount = currentCount + resourceCountChange;
        user.getResources().put(resource, updatedCount);
    }
}
