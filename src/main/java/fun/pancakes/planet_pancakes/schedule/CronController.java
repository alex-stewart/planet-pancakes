package fun.pancakes.planet_pancakes.schedule;

import fun.pancakes.planet_pancakes.service.resource.ResourcePriceUpdater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Controller
public class CronController {

    private ResourcePriceUpdater resourcePriceUpdater;
    private Clock clock;

    @Autowired
    public CronController(ResourcePriceUpdater resourcePriceUpdater,
                          Clock clock) {
        this.resourcePriceUpdater = resourcePriceUpdater;
        this.clock = clock;
    }

    @Scheduled(cron = "${resource-price-updater.cron}")
    public void updatePricesOnCron() {
        Date time = getTimeAtHourStart();
        log.info("Running price update for {}.", time);
        resourcePriceUpdater.updatePrices(time);
    }

    private Date getTimeAtHourStart() {
        Instant startOfHourInstant = Instant.now(clock).truncatedTo(ChronoUnit.HOURS);
        return Date.from(startOfHourInstant);
    }
}
