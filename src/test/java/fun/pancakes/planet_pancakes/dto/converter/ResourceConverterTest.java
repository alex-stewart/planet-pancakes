package fun.pancakes.planet_pancakes.dto.converter;

import fun.pancakes.planet_pancakes.dto.ResourceDto;
import fun.pancakes.planet_pancakes.persistence.entity.PriceHistory;
import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.service.exception.PriceNotFoundException;
import fun.pancakes.planet_pancakes.service.resource.PriceHistoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResourceConverterTest {

    private static final String RESOURCE_NAME = "beans";
    private static final Long RESOURCE_PRICE = 400L;

    private static final Date HISTORY_DATE_1 = new Date(1575759688000L);
    private static final Date HISTORY_DATE_2 = new Date(1575759709000L);
    private static final Instant HISTORY_INSTANT_1 = Instant.ofEpochMilli(1575759688000L);
    private static final Instant HISTORY_INSTANT_2 = Instant.ofEpochMilli(1575759709000L);
    private static final long HISTORY_PRICE_1 = 399L;
    private static final long HISTORY_PRICE_2 = 400L;

    private static final double RESOURCE_PRICE_TREND = 10d;
    private static final double RESOURCE_MAX_CHANGE_PERCENT = 20d;

    @Mock
    private PriceHistoryService priceHistoryService;

    @InjectMocks
    private ResourceConverter resourceConverter;

    private static Resource aResource() {
        return Resource.builder()
                .resourceName(RESOURCE_NAME)
                .price(RESOURCE_PRICE)
                .priceTrendPercent(RESOURCE_PRICE_TREND)
                .priceMaxChangePercent(RESOURCE_MAX_CHANGE_PERCENT)
                .build();
    }

    @Test
    public void shouldPopulateEmptyPriceHistoryIfNoPriceHistory() throws Exception {
        ResourceDto resourceDto = resourceConverter.convertToDto(aResource());

        assertThat(resourceDto.getPriceHistory()).isEmpty();
    }

    @Test
    public void shouldPopulatePriceHistory() throws Exception {
        mockPriceHistory();
        ResourceDto resourceDto = resourceConverter.convertToDto(aResource());

        assertThat(resourceDto.getPriceHistory()).containsOnly(
                entry(HISTORY_INSTANT_1, HISTORY_PRICE_1),
                entry(HISTORY_INSTANT_2, HISTORY_PRICE_2));
    }

    @Test
    public void shouldGetPriceHistory() throws Exception {
        resourceConverter.convertToDto(aResource());

        verify(priceHistoryService, times(1)).getPriceHistoryForResource(RESOURCE_NAME);
    }

    @Test
    public void shouldMapResourceName() throws Exception {
        ResourceDto resourceDto = resourceConverter.convertToDto(aResource());

        assertThat(resourceDto.getResourceName()).isEqualTo(RESOURCE_NAME);
    }

    @Test
    public void shouldSetResourceCurrentPrice() throws Exception {
        when(priceHistoryService.getMostRecentPriceForResource(any())).thenReturn(RESOURCE_PRICE);
        ResourceDto resourceDto = resourceConverter.convertToDto(aResource());

        assertThat(resourceDto.getPrice()).isEqualTo(RESOURCE_PRICE);
    }

    @Test(expected = PriceNotFoundException.class)
    public void shouldThrowExceptionWhenCantFindCurrentPriceOfResource() throws Exception {
        when(priceHistoryService.getMostRecentPriceForResource(any())).thenThrow(new PriceNotFoundException());
        resourceConverter.convertToDto(aResource());
    }

    @Test
    public void shouldGetMostRecentPriceForResource() throws Exception {
        resourceConverter.convertToDto(aResource());

        verify(priceHistoryService, times(1)).getMostRecentPriceForResource(RESOURCE_NAME);
    }

    private void mockPriceHistory() {
        List<PriceHistory> priceHistoryList = new ArrayList<>();
        priceHistoryList.add(buildPriceHistory(HISTORY_DATE_1, HISTORY_PRICE_1));
        priceHistoryList.add(buildPriceHistory(HISTORY_DATE_2, HISTORY_PRICE_2));

        when(priceHistoryService.getPriceHistoryForResource(any()))
                .thenReturn(priceHistoryList);
    }

    private PriceHistory buildPriceHistory(Date date, Long price) {
        return PriceHistory.builder()
                .resourceName(RESOURCE_NAME)
                .date(date)
                .price(price)
                .build();
    }
}
