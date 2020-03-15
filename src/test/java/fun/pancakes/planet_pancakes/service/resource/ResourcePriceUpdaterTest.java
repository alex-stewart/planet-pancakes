package fun.pancakes.planet_pancakes.service.resource;

import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.persistence.repository.ResourceRepository;
import fun.pancakes.planet_pancakes.service.exception.PriceNotFoundException;
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

    private static final String RESOURCE_1_NAME = "PANCAKE";
    private static final String RESOURCE_2_NAME = "WAFFLE";
    private static final long RESOURCE_1_PRICE = 12L;
    private static final long RESOURCE_2_PRICE = 9L;
    private static final long RESOURCE_1_NEW_PRICE = 13L;
    private static final long RESOURCE_2_NEW_PRICE = 8L;
    private static final double RESOURCE_1_PRICE_TREND = 1D;
    private static final double RESOURCE_2_PRICE_TREND = 2D;
    private static final double RESOURCE_1_MAX_PRICE_CHANGE = 10D;
    private static final double RESOURCE_2_MAX_PRICE_CHANGE = 20D;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private PriceHistoryService priceHistoryService;

    @Mock
    private PriceCalculator priceCalculator;

    @InjectMocks
    private ResourcePriceUpdater resourcePriceUpdater;

    @Before
    public void setUp() throws Exception {
        mockPriceCalculator();
        mockResources();
        mockMostRecentPrices();
    }

    @Test
    public void shouldGetAllResource() {
        resourcePriceUpdater.updatePrices(DATE);

        verify(resourceRepository, times(1)).findAll();
    }

    @Test
    public void shouldCheckIfPriceForTimeExistsForEachResource() {
        resourcePriceUpdater.updatePrices(DATE);

        verify(priceHistoryService).hasPriceHistory(RESOURCE_1_NAME, DATE);
        verify(priceHistoryService).hasPriceHistory(RESOURCE_2_NAME, DATE);
    }

    @Test
    public void shouldNotUpdatePricesIfPricesExistForTime() {
        mockExistingPriceHistoriesForDate();

        resourcePriceUpdater.updatePrices(DATE);

        verifyZeroInteractions(priceCalculator);
        verify(priceHistoryService, times(0)).addPriceHistory(any(), any(), any());
    }

    @Test
    public void shouldGetTheCurrentPriceOfEachResource() throws Exception {
        resourcePriceUpdater.updatePrices(DATE);

        verify(priceHistoryService).getMostRecentPriceForResource(RESOURCE_1_NAME);
        verify(priceHistoryService).getMostRecentPriceForResource(RESOURCE_2_NAME);
    }

    @Test
    public void shouldCalculateNewPrices() {
        resourcePriceUpdater.updatePrices(DATE);

        verify(priceCalculator).determineResourceNewPrice(RESOURCE_1_PRICE, RESOURCE_1_PRICE_TREND, RESOURCE_1_MAX_PRICE_CHANGE);
        verify(priceCalculator).determineResourceNewPrice(RESOURCE_2_PRICE, RESOURCE_2_PRICE_TREND, RESOURCE_2_MAX_PRICE_CHANGE);
    }

    @Test
    public void shouldNotUpdatePriceIfNoCurrentPrice() throws Exception {
        when(priceHistoryService.getMostRecentPriceForResource(RESOURCE_1_NAME)).thenThrow(new PriceNotFoundException());

        resourcePriceUpdater.updatePrices(DATE);

        verify(priceHistoryService, times(1)).addPriceHistory(eq(RESOURCE_2_NAME), anyLong(), any(Date.class));
    }

    @Test
    public void shouldAddPricesToPriceHistory() {
        resourcePriceUpdater.updatePrices(DATE);

        verify(priceHistoryService).addPriceHistory(RESOURCE_1_NAME, RESOURCE_1_NEW_PRICE, DATE);
        verify(priceHistoryService).addPriceHistory(RESOURCE_2_NAME, RESOURCE_2_NEW_PRICE, DATE);
    }

    private void mockResources() {
        Resource resource1 = buildResource(RESOURCE_1_NAME, RESOURCE_1_PRICE_TREND, RESOURCE_1_MAX_PRICE_CHANGE);
        Resource resource2 = buildResource(RESOURCE_2_NAME, RESOURCE_2_PRICE_TREND, RESOURCE_2_MAX_PRICE_CHANGE);
        when(resourceRepository.findAll()).thenReturn(Arrays.asList(resource1, resource2));
    }

    private Resource buildResource(String resourceName, Double priceTrend, Double priceMaxChange) {
        return Resource.builder()
                .resourceName(resourceName)
                .priceTrendPercent(priceTrend)
                .priceMaxChangePercent(priceMaxChange)
                .build();
    }

    private void mockPriceCalculator() {
        when(priceCalculator.determineResourceNewPrice(eq(RESOURCE_1_PRICE), anyDouble(), anyDouble())).thenReturn(RESOURCE_1_NEW_PRICE);
        when(priceCalculator.determineResourceNewPrice(eq(RESOURCE_2_PRICE), anyDouble(), anyDouble())).thenReturn(RESOURCE_2_NEW_PRICE);
    }

    private void mockMostRecentPrices() throws Exception {
        when(priceHistoryService.getMostRecentPriceForResource(RESOURCE_1_NAME)).thenReturn(RESOURCE_1_PRICE);
        when(priceHistoryService.getMostRecentPriceForResource(RESOURCE_2_NAME)).thenReturn(RESOURCE_2_PRICE);
    }

    private void mockExistingPriceHistoriesForDate() {
        when(priceHistoryService.hasPriceHistory(RESOURCE_1_NAME, DATE)).thenReturn(true);
        when(priceHistoryService.hasPriceHistory(RESOURCE_2_NAME, DATE)).thenReturn(true);
    }

}
