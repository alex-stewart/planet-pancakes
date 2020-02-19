package fun.pancakes.planet_pancakes.service.resource;

import fun.pancakes.planet_pancakes.persistence.entity.PriceHistory;
import fun.pancakes.planet_pancakes.persistence.repository.PriceHistoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PriceHistoryServiceTest {

    private static final String RESOURCE_NAME = "EGG";
    private static final Long PRICE = 99L;
    private static final Date DATE = new Date();

    @Mock
    private PriceHistoryRepository priceHistoryRepository;

    @InjectMocks
    private PriceHistoryService priceHistoryService;

    @Test
    public void shouldReturnFalseIfNoPriceHistory() {
        mockPriceHistory(null);

        boolean result = priceHistoryService.hasPriceHistory(RESOURCE_NAME, DATE);

        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueIfPriceHistory() {
        mockPriceHistory(PriceHistory.builder().build());

        boolean result = priceHistoryService.hasPriceHistory(RESOURCE_NAME, DATE);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldAddPriceHistory() {
        priceHistoryService.addPriceHistory(RESOURCE_NAME, PRICE, DATE);

        verifyPriceHistorySaved();
    }

    private void mockPriceHistory(PriceHistory priceHistory) {
        when(priceHistoryRepository.findByResourceNameAndDate(any(), any())).thenReturn(Optional.ofNullable(priceHistory));
    }

    private void verifyPriceHistorySaved() {
        PriceHistory expectedPriceHistory = PriceHistory.builder()
                .resourceName(RESOURCE_NAME)
                .price(PRICE)
                .date(DATE)
                .build();
        verify(priceHistoryRepository).save(expectedPriceHistory);
    }

}
