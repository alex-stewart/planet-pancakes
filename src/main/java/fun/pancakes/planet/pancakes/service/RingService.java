package fun.pancakes.planet.pancakes.service;

import fun.pancakes.planet.pancakes.entity.Island;
import fun.pancakes.planet.pancakes.entity.Ring;
import fun.pancakes.planet.pancakes.repository.RingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RingService {

    private RingRepository ringRepository;

    @Autowired
    public RingService(RingRepository ringRepository) {
        this.ringRepository = ringRepository;
    }

    public Island enrichIslandPosition(Island island) {
        Ring ring = ringRepository.findDistinctById(island.getRing());
        Long unixSeconds = Instant.now().getEpochSecond();
        Double yearSeconds = ring.getYearSeconds();
        island.setRadius(ring.getRadius());
        if (yearSeconds > 0) {
            Double remainderSeconds = unixSeconds % yearSeconds;
            Double yearFraction = remainderSeconds / yearSeconds;
            Double angle = yearFraction * 360;
            if (ring.isClockwise()) {
                island.setBearing(island.getBearing() + angle);
            } else {
                island.setBearing(island.getBearing() - angle);
            }
        }
        return island;
    }
}
