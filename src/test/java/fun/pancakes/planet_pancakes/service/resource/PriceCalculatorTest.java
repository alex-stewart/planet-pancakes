package fun.pancakes.planet_pancakes.service.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PriceCalculatorTest {

    private static final double HIGH_RANDOM_DOUBLE = 0.75;
    private static final double MIDDLE_RANDOM_DOUBLE = 0.5;
    private static final double LOW_RANDOM_DOUBLE = 0.25;

    private static final long START_PRICE = 100L;
    private static final long MINIMAL_PRICE = 1L;

    private static final double ZERO_TREND_PERCENT = 0;
    private static final double TREND_PERCENT = 5;
    private static final double NEGATIVE_TREND_PERCENT = -5;
    private static final double EXTREME_NEGATIVE_TREND_PERCENT = -200;

    private static final double ZERO_MAX_CHANGE_PERCENT = 0;
    private static final double MAX_CHANGE_PERCENT = 20;

    @Mock
    private Random random;

    @InjectMocks
    private PriceCalculator priceCalculator;

    @Test
    public void shouldNotAdjustPriceWhenMaxChangePercentIsZeroAndNoTrend() {
        mockPositiveRandomChangeOf50Percent();

        Long newPrice = priceCalculator.determineResourceNewPrice(START_PRICE, ZERO_TREND_PERCENT, ZERO_MAX_CHANGE_PERCENT);

        assertThat(newPrice).isEqualTo(START_PRICE);
    }

    @Test
    public void shouldAdjustPriceByTrendWhenZeroMaxChangePercent() {
        mockPositiveRandomChangeOf50Percent();

        Long newPrice = priceCalculator.determineResourceNewPrice(START_PRICE, TREND_PERCENT, ZERO_MAX_CHANGE_PERCENT);

        assertThat(newPrice).isEqualTo(START_PRICE + 5L);
    }

    @Test
    public void shouldAdjustPriceByTrendWhenZeroRandomNumber() {
        mockZeroRandomChange();

        Long newPrice = priceCalculator.determineResourceNewPrice(START_PRICE, TREND_PERCENT, MAX_CHANGE_PERCENT);

        assertThat(newPrice).isEqualTo(START_PRICE + 5L);
    }

    @Test
    public void shouldAdjustPriceByNegativeTrendWhenZeroMaxChangePercent() {
        mockPositiveRandomChangeOf50Percent();

        Long newPrice = priceCalculator.determineResourceNewPrice(START_PRICE, NEGATIVE_TREND_PERCENT, ZERO_MAX_CHANGE_PERCENT);

        assertThat(newPrice).isEqualTo(START_PRICE + -5L);
    }

    @Test
    public void shouldAdjustPriceByNegativeTrendWhenZeroRandomNumber() {
        mockZeroRandomChange();

        Long newPrice = priceCalculator.determineResourceNewPrice(START_PRICE, NEGATIVE_TREND_PERCENT, MAX_CHANGE_PERCENT);

        assertThat(newPrice).isEqualTo(START_PRICE + -5L);
    }

    @Test
    public void shouldRandomlyIncreasePriceGivenPositiveRandomNumberAndNoTrend() {
        mockPositiveRandomChangeOf50Percent();

        Long newPrice = priceCalculator.determineResourceNewPrice(START_PRICE, ZERO_TREND_PERCENT, MAX_CHANGE_PERCENT);

        assertThat(newPrice).isEqualTo(START_PRICE + 10L);
    }

    @Test
    public void shouldRandomlyIncreasePriceGivenNegativeRandomNumberAndNoTrend() {
        mockNegativeRandomChangeOf50Percent();

        Long newPrice = priceCalculator.determineResourceNewPrice(START_PRICE, ZERO_TREND_PERCENT, MAX_CHANGE_PERCENT);

        assertThat(newPrice).isEqualTo(START_PRICE + -10L);
    }

    @Test
    public void shouldUpdatePriceByPositiveRandomChangeAndPositiveTrend() {
        mockPositiveRandomChangeOf50Percent();

        Long newPrice = priceCalculator.determineResourceNewPrice(START_PRICE, TREND_PERCENT, MAX_CHANGE_PERCENT);

        assertThat(newPrice).isEqualTo(START_PRICE + 15L);
    }


    @Test
    public void shouldUpdatePriceByPositiveRandomChangeAndNegativeTrend() {
        mockPositiveRandomChangeOf50Percent();

        Long newPrice = priceCalculator.determineResourceNewPrice(START_PRICE, NEGATIVE_TREND_PERCENT, MAX_CHANGE_PERCENT);

        assertThat(newPrice).isEqualTo(START_PRICE + 5L);
    }


    @Test
    public void shouldUpdatePriceByNegativeRandomChangeAndPositiveTrend() {
        mockNegativeRandomChangeOf50Percent();

        Long newPrice = priceCalculator.determineResourceNewPrice(START_PRICE, TREND_PERCENT, MAX_CHANGE_PERCENT);

        assertThat(newPrice).isEqualTo(START_PRICE + -5L);
    }


    @Test
    public void shouldUpdatePriceByNegativeRandomChangeAndNegativeTrend() {
        mockNegativeRandomChangeOf50Percent();

        Long newPrice = priceCalculator.determineResourceNewPrice(START_PRICE, NEGATIVE_TREND_PERCENT, MAX_CHANGE_PERCENT);

        assertThat(newPrice).isEqualTo(START_PRICE + -15L);
    }

    @Test
    public void shouldNotReducePriceLowerThanOne() {
        mockNegativeRandomChangeOf50Percent();

        Long newPrice = priceCalculator.determineResourceNewPrice(START_PRICE, EXTREME_NEGATIVE_TREND_PERCENT, MAX_CHANGE_PERCENT);

        assertThat(newPrice).isEqualTo(MINIMAL_PRICE);
    }

    private void mockZeroRandomChange() {
        when(random.nextDouble()).thenReturn(MIDDLE_RANDOM_DOUBLE);
    }

    private void mockPositiveRandomChangeOf50Percent() {
        when(random.nextDouble()).thenReturn(HIGH_RANDOM_DOUBLE);
    }

    private void mockNegativeRandomChangeOf50Percent() {
        when(random.nextDouble()).thenReturn(LOW_RANDOM_DOUBLE);
    }
}