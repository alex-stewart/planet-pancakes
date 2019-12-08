package fun.pancakes.planet_pancakes.controller;

import fun.pancakes.planet_pancakes.service.resource.ResourcePriceUpdater;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CronControllerTest {

    private static final Instant INSTANT = Instant.ofEpochMilli(1575667034000L);
    private static final Date START_OF_HOUR_Date = new Date(1575666000000L);

    @Mock
    private Clock clock;

    @Mock
    private ResourcePriceUpdater resourcePriceUpdater;

    @InjectMocks
    private CronController cronController;

    @Before
    public void initMocks() {
        when(clock.instant()).thenReturn(INSTANT);
    }

    @Test
    public void shouldUpdatePricesOnCronTrigger() {
        cronController.updatePricesOnCron();

        verify(resourcePriceUpdater, times(1)).updatePrices(START_OF_HOUR_Date);
    }

}