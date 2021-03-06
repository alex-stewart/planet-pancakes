package fun.pancakes.planet_pancakes.service.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PriceCalculator {

    private Random random;

    @Autowired
    public PriceCalculator(Random random) {
        this.random = random;
    }

    public Long determineResourceNewPrice(Long currentPrice, Double priceTrendPercent, Double maxChangePercent) {
        Long priceChange = priceChangeAmount(currentPrice, priceTrendPercent, maxChangePercent);
        long newPrice = currentPrice + priceChange;
        return newPrice < 1 ? 1 : newPrice;
    }

    private Long priceChangeAmount(Long currentPrice, Double priceTrendPercent, Double maxChangePercent) {
        Long randomPriceChange = randomPriceChangeAmount(currentPrice, maxChangePercent);
        Long trendPriceChange = trendPriceChangeAmount(currentPrice, priceTrendPercent);
        return randomPriceChange + trendPriceChange;
    }

    private Long randomPriceChangeAmount(Long currentPrice, Double maxChangePercent) {
        Double zeroCenteredRandomNumber = random.nextDouble() * 2 - 1;
        Double maxChangeDecimal = decimalFromPercentage(maxChangePercent);
        Double randomChange = maxChangeDecimal * zeroCenteredRandomNumber;
        return Math.round(currentPrice * randomChange);
    }

    private Long trendPriceChangeAmount(Long currentPrice, Double priceTrendPercent) {
        Double priceChangeDecimal = decimalFromPercentage(priceTrendPercent);
        return Math.round(currentPrice * priceChangeDecimal);
    }

    private Double decimalFromPercentage(Double percentage) {
        return percentage / 100d;
    }

}
