package fun.pancakes.planet_pancakes.service.island;

import fun.pancakes.planet_pancakes.persistence.entity.Island;
import fun.pancakes.planet_pancakes.persistence.entity.Ring;
import fun.pancakes.planet_pancakes.persistence.repository.RingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Component
public class IslandPositionService {

    private RingRepository ringRepository;
    private Clock clock;

    @Autowired
    public IslandPositionService(RingRepository ringRepository, Clock clock) {
        this.ringRepository = ringRepository;
        this.clock = clock;
    }

    public Island enrichIslandPosition(Island island) {
        Long currentTimeInSeconds = Instant.now(clock).getEpochSecond();
        Ring ring = ringRepository.findDistinctById(island.getRing());
        island.setBearing(islandBearingAtTime(island, ring, currentTimeInSeconds));
        island.setRadius(islandRadiusAtTime(island, ring, currentTimeInSeconds));
        return island;
    }

    private Double islandRadiusAtTime(Island island, Ring ring, Long currentTimeInSeconds) {
        Double radius = ring.getRadius();

        if (island.getWobbleCycleDays() != null) {
            radius += calculateRadiusOffset(island, currentTimeInSeconds);
        }

        return radius;
    }

    private Double calculateRadiusOffset(Island island, Long currentTimeInSeconds) {
        Double cycleFraction = fractionIntoCurrentCycle(currentTimeInSeconds, island.getWobbleCycleDays());
        return island.getWobbleRadius() * Math.sin(cycleFraction * 360);
    }

    private Double islandBearingAtTime(Island island, Ring ring, Long currentTimeInSeconds) {
        if (ring.getYearDays() == null) {
            return 0d;
        }
        Double yearFraction = fractionIntoCurrentCycle(currentTimeInSeconds, ring.getYearDays());
        Double angle = yearFraction * 360;
        return calculateNewIslandBearingOffset(island, ring, angle);
    }

    private Double calculateNewIslandBearingOffset(Island island, Ring ring, Double angle) {
        Double bearing = island.getBearing();

        if (ring.getClockwise()) {
            bearing += angle;
        } else {
            bearing -= angle;
        }

        return bearing;
    }

    private Double fractionIntoCurrentCycle(Long currentTimeInSeconds, Integer cycleDays) {
        if (cycleDays == 0) {
            return 0d;
        }
        Long cycleSeconds = TimeUnit.DAYS.toSeconds(cycleDays);
        Double remainderSeconds = (double) currentTimeInSeconds % cycleSeconds;
        return remainderSeconds / cycleSeconds;
    }
}
