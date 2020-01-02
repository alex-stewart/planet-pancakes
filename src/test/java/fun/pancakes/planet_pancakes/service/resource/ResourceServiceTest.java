package fun.pancakes.planet_pancakes.service.resource;

import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResourceServiceTest {

    private static final String RESOURCE_NAME = "PANCAKE";
    private static final Date DATE = new Date();
    private static final long NEW_RESOURCE_PRICE = 123L;
    private static final long CURRENT_RESOURCE_PRICE = 100L;
    private static final double PRICE_TREND_PERCENT = 10d;
    private static final double PRICE_MAX_CHANGE_PERCENT = 20d;

    @Mock
    PriceCalculator priceCalculator;

    @InjectMocks
    ResourceService resourceService;

    @Test
    public void shouldAddPriceToPriceHistory() {
        mockPriceCalculationService();

        Resource updatedResource = resourceService.updateResourceWithPriceAtTime(aBasicResource(), DATE);

        assertThat(updatedResource.getPriceHistory()).containsEntry(DATE, NEW_RESOURCE_PRICE);
    }

    @Test
    public void shouldSetResourceCurrentPrice() {
        mockPriceCalculationService();

        Resource updatedResource = resourceService.updateResourceWithPriceAtTime(aBasicResource(), DATE);

        assertThat(updatedResource.getPrice()).isEqualTo(NEW_RESOURCE_PRICE);
    }

    @Test
    public void shouldCreatePriceHistoryMapIfNotExist() {
        mockPriceCalculationService();

        Resource updatedResource = resourceService.updateResourceWithPriceAtTime(aResourceWithoutPriceHistory(), DATE);

        assertThat(updatedResource.getPriceHistory()).containsEntry(DATE, NEW_RESOURCE_PRICE);
    }

    private void mockPriceCalculationService() {
        when(priceCalculator.determineResourceNewPrice(CURRENT_RESOURCE_PRICE, PRICE_TREND_PERCENT, PRICE_MAX_CHANGE_PERCENT))
                .thenReturn(NEW_RESOURCE_PRICE);
    }

    private Resource aResourceWithoutPriceHistory() {
        return Resource.builder()
                .resourceName(RESOURCE_NAME)
                .price(CURRENT_RESOURCE_PRICE)
                .priceTrendPercent(PRICE_TREND_PERCENT)
                .priceMaxChangePercent(PRICE_MAX_CHANGE_PERCENT)
                .build();
    }

    private Resource aBasicResource() {
        Resource resource = aResourceWithoutPriceHistory();
        resource.setPriceHistory(new HashMap<>());
        return resource;
    }
}