package fun.pancakes.planet.pancakes.service;

import fun.pancakes.planet.pancakes.entity.Island;
import fun.pancakes.planet.pancakes.entity.Ring;
import fun.pancakes.planet.pancakes.repository.RingRepository;
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
        if (ring.getYearDays() != null) {
            Double yearFraction = fractionIntoCurrentCycle(currentTimeInSeconds, ring.getYearDays());
            Double angle = yearFraction * 360;
            if (ring.getClockwise()) {
                return island.getBearing() + angle;
            } else {
                return island.getBearing() - angle;
            }
        } else {
            return 0d;
        }
    }

    private Double fractionIntoCurrentCycle (Double currentTimeInSeconds, Integer cycleDays) {
        Double cycleSeconds = (double) TimeUnit.DAYS.toSeconds(cycleDays);
        Double remainderSeconds = currentTimeInSeconds % cycleSeconds;
        return remainderSeconds / cycleSeconds;
    }
}
