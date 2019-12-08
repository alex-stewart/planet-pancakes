package fun.pancakes.planet_pancakes.service.island;

import fun.pancakes.planet_pancakes.persistence.entity.Island;
import fun.pancakes.planet_pancakes.persistence.entity.Ring;
import fun.pancakes.planet_pancakes.persistence.repository.RingRepository;
import org.assertj.core.data.Percentage;
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

    private static final Instant START_OF_TIME = Instant.ofEpochSecond(0L);
    private static final Instant HALF_CYCLE = Instant.ofEpochSecond(86400L);
    private static final Instant QUARTER_CYCLE = Instant.ofEpochSecond(43200L);

    private static final int STATIC_RING_ID = 0;
    private static final Integer STATIC_RING_YEAR_DAYS = null;
    private static final Boolean STATIC_RING_CLOCKWISE = null;
    private static final double STATIC_RING_RADIUS = 0;

    private static final int STATIC_ISLAND_ID = 0;
    private static final double STATIC_ISLAND_BEARING = 0d;

    private static final int RING_ID = 1;
    private static final int RING_YEAR_DAYS = 2;
    private static final boolean RING_CLOCKWISE = true;
    private static final double RING_RADIUS = 100;

    private static final int ISLAND_1_ID = 1;
    private static final double ISLAND_1_BEARING = 40d;
    private static final double ISLAND_1_OFFSET_BEARING = 220d;
    private static final double ISLAND_1_WOBBLE_RADIUS = 5.0;
    private static final int ISLAND_1_WOBBLE_CYCLE_DAYS = 2;

    private static final int ANTICLOCKWISE_RING_ID = 2;
    private static final int ANTICLOCKWISE_RING_YEAR_DAYS = 2;
    private static final boolean RING_ANTICLOCKWISE = false;
    private static final double ANTICLOCKWISE_RING_RADIUS = 200;

    private static final int ISLAND_2_ID = 2;
    private static final double ISLAND_2_BEARING = 220d;
    private static final double ISLAND_2_OFFSET_BEARING = 40d;

    @Mock
    private Clock clock;

    @Mock
    private RingRepository ringRepository;

    @InjectMocks
    private IslandPositionService islandPositionService;

    @Test
    public void shouldNotUpdateBearingWhenRingDoesNotCycle() {
        when(clock.instant()).thenReturn(HALF_CYCLE);
        mockRing(STATIC_RING_ID, STATIC_RING_YEAR_DAYS, STATIC_RING_CLOCKWISE, STATIC_RING_RADIUS);

        Island result = enrichIsland(STATIC_ISLAND_ID, STATIC_RING_ID, STATIC_ISLAND_BEARING);

        assertThat(result.getBearing()).isEqualTo(STATIC_ISLAND_BEARING);
    }

    @Test
    public void shouldNotUpdateBearingWhenStartOfCycle() {
        when(clock.instant()).thenReturn(START_OF_TIME);
        mockRing(RING_ID, RING_YEAR_DAYS, RING_CLOCKWISE, RING_RADIUS);

        Island result = enrichIsland(ISLAND_1_ID, RING_ID, ISLAND_1_BEARING);

        assertThat(result.getBearing()).isEqualTo(ISLAND_1_BEARING);
    }

    @Test
    public void shouldUpdateIslandBearingClockwise() {
        when(clock.instant()).thenReturn(HALF_CYCLE);
        mockRing(RING_ID, RING_YEAR_DAYS, RING_CLOCKWISE, RING_RADIUS);

        Island result = enrichIsland(ISLAND_1_ID, RING_ID, ISLAND_1_BEARING);

        assertThat(result.getBearing()).isEqualTo(ISLAND_1_OFFSET_BEARING);
    }

    @Test
    public void shouldUpdateIslandBearingAnticlockwise() {
        when(clock.instant()).thenReturn(HALF_CYCLE);
        mockRing(ANTICLOCKWISE_RING_ID, ANTICLOCKWISE_RING_YEAR_DAYS, RING_ANTICLOCKWISE, ANTICLOCKWISE_RING_RADIUS);

        Island result = enrichIsland(ISLAND_2_ID, ANTICLOCKWISE_RING_ID, ISLAND_2_BEARING);

        assertThat(result.getBearing()).isEqualTo(ISLAND_2_OFFSET_BEARING);
    }

    @Test
    public void shouldNotUpdateIslandRadiusWhenIslandDoesntWobble() {
        when(clock.instant()).thenReturn(HALF_CYCLE);
        mockRing(RING_ID, RING_YEAR_DAYS, RING_CLOCKWISE, RING_RADIUS);

        Island result = enrichIsland(ISLAND_1_ID, RING_ID, ISLAND_1_BEARING);

        assertThat(result.getRadius()).isEqualTo(RING_RADIUS);
    }

    @Test
    public void shouldNotUpdateIslandRadiusWhenRingHasZeroRadius() {
        when(clock.instant()).thenReturn(HALF_CYCLE);
        mockRing(STATIC_RING_ID, STATIC_RING_YEAR_DAYS, STATIC_RING_CLOCKWISE, STATIC_RING_RADIUS);

        Island result = enrichIsland(STATIC_ISLAND_ID, STATIC_RING_ID, STATIC_ISLAND_BEARING);

        assertThat(result.getRadius()).isEqualTo(STATIC_RING_RADIUS);
    }

    @Test
    public void shouldNotUpdateIslandRadiusWhenStartOfWobblePeriod() {
        when(clock.instant()).thenReturn(START_OF_TIME);
        mockRing(RING_ID, RING_YEAR_DAYS, RING_CLOCKWISE, RING_RADIUS);

        Island result = enrichIsland(ISLAND_1_ID, RING_ID, ISLAND_1_BEARING);

        assertThat(result.getRadius()).isEqualTo(RING_RADIUS);
    }

    @Test
    public void shouldUpdateIslandRadiusWhenWobbling() {
        when(clock.instant()).thenReturn(QUARTER_CYCLE);
        mockRing(RING_ID, RING_YEAR_DAYS, RING_CLOCKWISE, RING_RADIUS);

        Island result = enrichWobblingIsland();

        assertThat(result.getRadius()).isCloseTo(RING_RADIUS + ISLAND_1_WOBBLE_RADIUS, Percentage.withPercentage(1));
    }

    private Island enrichIsland(Integer islandId, Integer ringId, Double bearing) {
        return islandPositionService.enrichIslandPosition(generateIsland(islandId, ringId, bearing));
    }

    private Island enrichWobblingIsland() {
        return islandPositionService.enrichIslandPosition(generateIslandWithWobble());
    }

    private static Island generateIsland(int islandId, int ringId, double bearing) {
        return Island.builder()
                .id(islandId)
                .ring(ringId)
                .bearing(bearing)
                .build();
    }

    private static Island generateIslandWithWobble() {
        Island island = generateIsland(ISLAND_1_ID, RING_ID, ISLAND_1_BEARING);
        island.setWobbleRadius(ISLAND_1_WOBBLE_RADIUS);
        island.setWobbleCycleDays(ISLAND_1_WOBBLE_CYCLE_DAYS);
        return island;
    }

    private void mockRing(Integer ringId, Integer ringYearDays, Boolean clockwise, Double radius) {
        when(ringRepository.findDistinctById(ringId)).thenReturn(generateRing(ringId, ringYearDays, clockwise, radius));
    }

    private static Ring generateRing(int ringId, Integer yearDays, Boolean clockwise, Double radius) {
        return Ring.builder()
                .id(ringId)
                .yearDays(yearDays)
                .clockwise(clockwise)
                .radius(radius)
                .build();
    }
}