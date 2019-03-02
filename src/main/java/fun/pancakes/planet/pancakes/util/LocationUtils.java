package fun.pancakes.planet.pancakes.util;

import java.time.Instant;
import java.util.Map;

public class LocationUtils {

    private static final Map<Integer, Double> RING_RADIUS = Map.of(
        0, 0D,
        1, 10D,
        2, 20D,
        3, 30D
    );

    private static final Map<Integer, Double> RING_YEAR_LENGTH = Map.of(
            0, 0d,
            1, 864000d,
            2, 1728000d,
            3, 3456000d
    );

    public static Double radiusForRing(Integer ring) {
        return RING_RADIUS.get(ring);
    }

    public static Double calculateCurrentBearing(Double initialBearing, Integer ring) {
        Long unixSeconds = Instant.now().getEpochSecond();
        Double yearSeconds = RING_YEAR_LENGTH.get(ring);
        if (yearSeconds > 0) {
            Double remainderSeconds = unixSeconds % yearSeconds;
            Double yearFraction = remainderSeconds / yearSeconds;
            Double angle = yearFraction * 360;
            return initialBearing + angle;
        }
        return 0d;
    }
}
