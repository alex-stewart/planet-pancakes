package fun.pancakes.planet_pancakes.dto.converter;

import fun.pancakes.planet_pancakes.dto.ResourceDto;
import fun.pancakes.planet_pancakes.persistence.entity.PriceHistory;
import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.persistence.repository.PriceHistoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    private PriceHistoryRepository priceHistoryRepository;

    @InjectMocks
    private ResourceConverter resourceConverter;

    @Test
    public void shouldConvertResourceToDtoWithNoHistory() {
        Resource resource = aResource();

        ResourceDto resourceDto = resourceConverter.convertToDto(resource);

        assertThat(resourceDto).isEqualTo(aResourceDto());
    }

    @Test
    public void shouldConvertResourceToDtoWithHistory() {
        mockPriceHistory();

        Resource resource = aResource();

        ResourceDto resourceDto = resourceConverter.convertToDto(resource);

        assertThat(resourceDto).isEqualTo(aResourceDtoWithHistory());
    }

    private void mockPriceHistory() {
        List<PriceHistory> priceHistoryList = new ArrayList<>();
        priceHistoryList.add(buildPriceHistory(HISTORY_DATE_1, HISTORY_PRICE_1));
        priceHistoryList.add(buildPriceHistory(HISTORY_DATE_2, HISTORY_PRICE_2));

        when(priceHistoryRepository.findAllByResourceName(any()))
                .thenReturn(priceHistoryList);
    }

    private PriceHistory buildPriceHistory(Date date, Long price) {
        return PriceHistory.builder()
                .resourceName(RESOURCE_NAME)
                .date(date)
                .price(price)
                .build();
    }

    private static Resource aResource() {
        return Resource.builder()
                .resourceName(RESOURCE_NAME)
                .price(RESOURCE_PRICE)
                .priceTrendPercent(RESOURCE_PRICE_TREND)
                .priceMaxChangePercent(RESOURCE_MAX_CHANGE_PERCENT)
                .build();
    }

    private static ResourceDto aResourceDtoWithHistory() {
        ResourceDto resourceDto = aResourceDto();

        Map<Instant, Long> priceHistory = new HashMap<>();
        priceHistory.put(HISTORY_INSTANT_1, HISTORY_PRICE_1);
        priceHistory.put(HISTORY_INSTANT_2, HISTORY_PRICE_2);
        resourceDto.setPriceHistory(priceHistory);

        return resourceDto;
    }

    private static ResourceDto aResourceDto() {
        return ResourceDto.builder()
                .resourceName(RESOURCE_NAME)
                .price(RESOURCE_PRICE)
                .priceHistory(new HashMap<>())
                .build();
    }
}