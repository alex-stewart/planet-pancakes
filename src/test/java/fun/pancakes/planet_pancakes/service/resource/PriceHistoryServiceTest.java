package fun.pancakes.planet_pancakes.service.resource;

import fun.pancakes.planet_pancakes.persistence.entity.PriceHistory;
import fun.pancakes.planet_pancakes.persistence.repository.PriceHistoryRepository;
import fun.pancakes.planet_pancakes.service.exception.PriceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

    @Captor
    private ArgumentCaptor<PriceHistory> priceHistoryArgumentCaptor;

    @Test
    public void shouldReturnFalseIfNoPriceHistory() {
        mockPriceHistory(null);

        boolean result = priceHistoryService.hasPriceHistory(RESOURCE_NAME, DATE);

        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueIfPriceHistory() {
        mockPriceHistory(buildPriceHistory());

        boolean result = priceHistoryService.hasPriceHistory(RESOURCE_NAME, DATE);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldAddPriceHistory() {
        priceHistoryService.addPriceHistory(RESOURCE_NAME, PRICE, DATE);

        verifyPriceHistorySaved();
    }

    @Test(expected = PriceNotFoundException.class)
    public void whenFindingCurrentPrice_andNoPrice_shouldReturnEmpty() throws Exception{
        mockMostRecentPriceHistory(null);

        priceHistoryService.getMostRecentPriceForResource(RESOURCE_NAME);
    }

    @Test
    public void whenFindingCurrentPrice_shouldReturnMostRecentPrice() throws Exception{
        mockMostRecentPriceHistory(buildPriceHistory());

        Long result = priceHistoryService.getMostRecentPriceForResource(RESOURCE_NAME);

        assertThat(result).isEqualTo(PRICE);
    }

    @Test
    public void whenFindingCurrentPrice_shouldCallPriceHistoryRepository() throws Exception{
        mockMostRecentPriceHistory(buildPriceHistory());

        priceHistoryService.getMostRecentPriceForResource(RESOURCE_NAME);

        verify(priceHistoryRepository).findTopByResourceNameOrderByDateDesc(RESOURCE_NAME);
    }

    private void mockMostRecentPriceHistory(PriceHistory priceHistory) {
        Optional<PriceHistory> priceHistoryOptional = Optional.ofNullable(priceHistory);
        when(priceHistoryRepository.findTopByResourceNameOrderByDateDesc(any())).thenReturn(priceHistoryOptional);
    }

    private void mockPriceHistory(PriceHistory priceHistory) {
        when(priceHistoryRepository.findByResourceNameAndDate(any(), any())).thenReturn(Optional.ofNullable(priceHistory));
    }

    private void verifyPriceHistorySaved() {
        verify(priceHistoryRepository).save(priceHistoryArgumentCaptor.capture());

        PriceHistory priceHistory = priceHistoryArgumentCaptor.getValue();
        assertThat(priceHistory.getResourceName()).isEqualTo(RESOURCE_NAME);
        assertThat(priceHistory.getPrice()).isEqualTo(PRICE);
        assertThat(priceHistory.getDate()).isEqualTo(DATE);
    }

    private PriceHistory buildPriceHistory() {
        return PriceHistory.builder()
                .resourceName(RESOURCE_NAME)
                .date(DATE)
                .price(PRICE)
                .build();
    }

}
