package fun.pancakes.planet_pancakes.dto.converter;

import fun.pancakes.planet_pancakes.dto.ResourceDto;
import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceConverterTest {

    private static final String RESOURCE_NAME = "beans";
    private static final Long RESOURCE_PRICE = 400L;

    private static final Date HISTORY_DATE_1 = new Date(1575759688000L);
    private static final Date HISTORY_DATE_2 = new Date(1575759709000L);
    private static final Instant HISTORY_INSTANT_1 = Instant.ofEpochMilli(1575759688000L);
    private static final Instant HISTORY_INSTANT_2 = Instant.ofEpochMilli(1575759709000L);
    private static final long HISTORY_PRICE_1 = 399L;
    private static final long HISTORY_PRICE_2 = 400L;

    private ResourceConverter resourceConverter;

    @Before
    public void setUp() {
        resourceConverter = new ResourceConverter();
    }

    @Test
    public void shouldConvertResourceToDtoWithNoHistory() {
        Resource resource = aResource();

        ResourceDto resourceDto = resourceConverter.convertToDto(resource);

        assertThat(resourceDto).isEqualTo(aResourceDto());
    }

    @Test
    public void shouldConvertResourceToDtoWithHistory() {
        Resource resource = aResourceWithHistory();

        ResourceDto resourceDto = resourceConverter.convertToDto(resource);

        assertThat(resourceDto).isEqualTo(aResourceDtoWithHistory());
    }

    private static Resource aResourceWithHistory() {
        Resource resource = aResource();

        Map<Date, Long> priceHistory = new HashMap<>();
        priceHistory.put(HISTORY_DATE_1, HISTORY_PRICE_1);
        priceHistory.put(HISTORY_DATE_2, HISTORY_PRICE_2);
        resource.setPriceHistory(priceHistory);

        return resource;
    }

    private static Resource aResource() {
        return Resource.builder()
                .resourceName(RESOURCE_NAME)
                .price(RESOURCE_PRICE)
                .priceTrendPercent(10)
                .priceMaxChangePercent(20)
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