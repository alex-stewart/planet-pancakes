package fun.pancakes.planet_pancakes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Clock;

@EnableScheduling
@Configuration
public class ScheduleConfig {
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
