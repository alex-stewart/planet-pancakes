package fun.pancakes.planet.pancakes.service;

import fun.pancakes.planet.pancakes.entity.Island;
import fun.pancakes.planet.pancakes.entity.Ring;
import fun.pancakes.planet.pancakes.repository.RingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class IslandPositionService {

    private RingRepository ringRepository;

    @Autowired
    public IslandPositionService(RingRepository ringRepository) {
        this.ringRepository = ringRepository;
    }

    public void enrichIslandPosition(Island island) {
        Ring ring = ringRepository.findDistinctById(island.getRing());
        island.setBearing( getBearing(island, ring));
        island.setRadius(getRadius(island, ring));
    }

    private Double getRadius(Island island, Ring ring) {
        Double unixSeconds = (double)Instant.now().getEpochSecond();
        Double cycleSeconds = island.getWobbleCycleSeconds();
        if (cycleSeconds != null) {
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
        Double yearSeconds = ring.getYearSeconds();
        if (yearSeconds > 0) {
            Double remainderSeconds = unixSeconds % yearSeconds;
            Double yearFraction = remainderSeconds / yearSeconds;
            Double angle = yearFraction * 360;
            if (ring.isClockwise()) {
                return island.getBearing() + angle;
            } else {
                return island.getBearing() - angle;
            }
        } else {
            return 0d;
        }
    }
}
