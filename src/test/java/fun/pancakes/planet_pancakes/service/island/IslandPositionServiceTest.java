package fun.pancakes.planet_pancakes.service.island;

import fun.pancakes.planet_pancakes.persistence.entity.Island;
import fun.pancakes.planet_pancakes.persistence.entity.Ring;
import fun.pancakes.planet_pancakes.persistence.repository.RingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IslandPositionServiceTest {

    private static final Instant INSTANT = Instant.ofEpochMilli(0L);

    private static final int STATIC_RING_ID = 0;
    private static final Integer STATIC_RING_YEAR_DAYS = null;
    private static final Boolean STATIC_RING_CLOCKWISE = null;
    private static final Ring STATIC_RING = generateRing(STATIC_RING_ID, STATIC_RING_YEAR_DAYS, STATIC_RING_CLOCKWISE);

    private static final int STATIC_ISLAND_ID = 1;
    private static final double STATIC_ISLAND_BEARING = 0d;
    private static final Island STATIC_ISLAND = generateIsland(STATIC_ISLAND_ID, STATIC_RING_ID, STATIC_ISLAND_BEARING);

    private static final int RING_ID = 1;
    private static final int RING_YEAR_DAYS = 200;
    private static final boolean RING_CLOCKWISE = true;
    private static final Ring RING = generateRing(RING_ID, RING_YEAR_DAYS, RING_CLOCKWISE);

    private static final int ISLAND_1_ID = 1;
    private static final double ISLAND_1_BEARING = 40d;
    private static final Island ISLAND_1 = generateIsland(ISLAND_1_ID, RING_ID, ISLAND_1_BEARING);

    @Mock
    private Clock clock;

    @Mock
    private RingRepository ringRepository;

    @InjectMocks
    private IslandPositionService islandPositionService;

    @Before
    public void initMocks() {
        when(clock.instant()).thenReturn(INSTANT);
    }

    @Test
    public void shouldNotUpdateBearingWhenRingDoesNotCycle() {
        when(ringRepository.findDistinctById(STATIC_RING_ID)).thenReturn(STATIC_RING);

        Island result = islandPositionService.enrichIslandPosition(STATIC_ISLAND);

        assertThat(result.getBearing()).isEqualTo(STATIC_ISLAND_BEARING);
    }

    @Test
    public void shouldNotUpdateBearingWhenStartOfCycle() {
        when(ringRepository.findDistinctById(RING_ID)).thenReturn(RING);

        Island result = islandPositionService.enrichIslandPosition(ISLAND_1);

        assertThat(result.getBearing()).isEqualTo(ISLAND_1_BEARING);
    }

    private static Ring generateRing(int ringId, Integer yearDays, Boolean clockwise) {
        return Ring.builder()
                .id(ringId)
                .yearDays(yearDays)
                .clockwise(clockwise)
                .build();
    }

    private static Island generateIsland(int islandId, int ringId, double bearing) {
        return Island.builder()
                .id(islandId)
                .ring(ringId)
                .bearing(bearing)
                .build();
    }

}