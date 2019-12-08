package fun.pancakes.planet_pancakes.service;

import fun.pancakes.planet_pancakes.persistence.entity.Island;
import fun.pancakes.planet_pancakes.persistence.entity.Ring;
import fun.pancakes.planet_pancakes.persistence.repository.RingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Component
public class IslandPositionService {

    private RingRepository ringRepository;

    @Autowired
    public IslandPositionService(RingRepository ringRepository) {
        this.ringRepository = ringRepository;
    }

    public void enrichIslandPosition(Island island) {
        Double currentTimeInSeconds = (double) Instant.now().getEpochSecond();
        Ring ring = ringRepository.findDistinctById(island.getRing());
        island.setBearing(islandBearingAtTime(island, ring, currentTimeInSeconds));
        island.setRadius(islandRadiusAtTime(island, ring, currentTimeInSeconds));
    }

    private Double islandRadiusAtTime(Island island, Ring ring, Double currentTimeInSeconds) {
        if (island.getWobbleCycleDays() != null) {
            Double cycleFraction = fractionIntoCurrentCycle(currentTimeInSeconds, island.getWobbleCycleDays());
            Double offset = island.getWobbleRadius() * Math.sin(cycleFraction * 360);
            return ring.getRadius() + offset;
        } else {
            return ring.getRadius();
        }
    }

    private Double islandBearingAtTime(Island island, Ring ring, Double currentTimeInSeconds) {
        if (ring.getYearDays() == null) {
            return 0d;
        }
        Double yearFraction = fractionIntoCurrentCycle(currentTimeInSeconds, ring.getYearDays());
        Double angle = yearFraction * 360;
        if (ring.getClockwise()) {
            return island.getBearing() + angle;
        } else {
            return island.getBearing() - angle;
        }
    }

    private Double fractionIntoCurrentCycle(Double currentTimeInSeconds, Integer cycleDays) {
        if (cycleDays == 0) {
            return 0d;
        }
        Double cycleSeconds = (double) TimeUnit.DAYS.toSeconds(cycleDays);
        Double remainderSeconds = currentTimeInSeconds % cycleSeconds;
        return remainderSeconds / cycleSeconds;
    }
}
