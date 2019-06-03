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
        Ring ring = ringRepository.findDistinctById(island.getRing());
        island.setBearing(getBearing(island, ring));
        island.setRadius(getRadius(island, ring));
    }

    private Double getRadius(Island island, Ring ring) {
        Double unixSeconds = (double) Instant.now().getEpochSecond();
        if (island.getWobbleCycleDays() != null) {
            Double cycleSeconds = (double) TimeUnit.DAYS.toSeconds(island.getWobbleCycleDays());
            Double remainderSeconds = unixSeconds % cycleSeconds;
            Double cycleFraction = remainderSeconds / cycleSeconds;
            Double offset = island.getWobbleRadius() * Math.sin(cycleFraction * 360);
            return ring.getRadius() + offset;
        } else {
            return ring.getRadius();
        }
    }

    private Double getBearing(Island island, Ring ring) {
        Long unixSeconds = Instant.now().getEpochSecond();
        if (ring.getYearDays() != null) {
            Double yearSeconds = (double) TimeUnit.DAYS.toSeconds(ring.getYearDays());
            Double remainderSeconds = unixSeconds % yearSeconds;
            Double yearFraction = remainderSeconds / yearSeconds;
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
}
