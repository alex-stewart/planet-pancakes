package fun.pancakes.planet_pancakes.service.resource;

import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.persistence.repository.ResourceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResourcePriceUpdaterTest {

    private static final Date DATE = new Date();

    private static final long RESOURCE_PRICE = 10L;
    private static final long RESOURCE_1_PRICE = 12L;
    private static final long RESOURCE_2_PRICE = 9L;
    private static final String RESOURCE_1_NAME = "PANCAKE";
    private static final String RESOURCE_2_NAME = "WAFFLE";
    private static final Resource RESOURCE_1 = buildResource(RESOURCE_1_NAME, RESOURCE_PRICE);
    private static final Resource RESOURCE_2 = buildResource(RESOURCE_2_NAME, RESOURCE_PRICE);

    @Mock
    private ResourceService resourceService;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private PriceHistoryService priceHistoryService;

    @InjectMocks
    private ResourcePriceUpdater resourcePriceUpdater;

    @Before
    public void setUp() {
        mockResources();
        mockUpdateResources();
    }

    @Test
    public void shouldNotUpdatePricesIfPricesExistForTime() {
        mockExistingPriceHistories(RESOURCE_1_NAME, RESOURCE_2_NAME);

        resourcePriceUpdater.updatePrices(DATE);

        verifyZeroInteractions(resourceService);
        verify(priceHistoryService, times(0)).addPriceHistory(any(), any(), any());
        verify(resourceRepository, times(0)).save(any());
    }

    @Test
    public void shouldCalculateNewPriceForResource() {
        resourcePriceUpdater.updatePrices(DATE);

        verify(resourceService).updateResourceWithPriceAtTime(RESOURCE_1);
        verify(resourceService).updateResourceWithPriceAtTime(RESOURCE_2);
    }

    @Test
    public void shouldAddPricesToPriceHistory() {
        resourcePriceUpdater.updatePrices(DATE);

        verify(priceHistoryService).addPriceHistory(RESOURCE_1_NAME, RESOURCE_1_PRICE, DATE);
        verify(priceHistoryService).addPriceHistory(RESOURCE_2_NAME, RESOURCE_2_PRICE, DATE);
    }

    @Test
    public void shouldUpdateResourcePrice() {
        resourcePriceUpdater.updatePrices(DATE);

        verifyResourcePriceUpdated(RESOURCE_1_NAME, RESOURCE_1_PRICE);
        verifyResourcePriceUpdated(RESOURCE_2_NAME, RESOURCE_2_PRICE);
    }

    private void mockResources() {
        when(resourceRepository.findAll()).thenReturn(Arrays.asList(RESOURCE_1, RESOURCE_2));
    }

    private void mockUpdateResources() {
        when(resourceService.updateResourceWithPriceAtTime(RESOURCE_1)).thenReturn(buildResource(RESOURCE_1_NAME, RESOURCE_1_PRICE));
        when(resourceService.updateResourceWithPriceAtTime(RESOURCE_2)).thenReturn(buildResource(RESOURCE_2_NAME, RESOURCE_2_PRICE));
    }

    private void mockExistingPriceHistories(String... resourceNames) {
        for (String resourceName : resourceNames) {
            when(priceHistoryService.hasPriceHistory(resourceName, DATE)).thenReturn(true);
        }
    }

    private void verifyResourcePriceUpdated(String resourceName, Long price) {
        Resource resource = buildResource(resourceName, price);
        verify(resourceRepository).save(resource);
    }

    private static Resource buildResource(String resourceName, Long price) {
        return Resource.builder()
                .resourceName(resourceName)
                .price(price)
                .build();
    }
}
