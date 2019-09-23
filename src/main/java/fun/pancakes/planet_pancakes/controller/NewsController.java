package fun.pancakes.planet_pancakes.controller;

import fun.pancakes.planet_pancakes.date.InvalidWorldDateStringException;
import fun.pancakes.planet_pancakes.date.WorldDate;
import fun.pancakes.planet_pancakes.persistence.repository.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api")
@EnableSwagger2
public class NewsController {

    private NewsRepository newsRepository;

    @Autowired
    public NewsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping("/news/")
    public ResponseEntity getNewsForCurrentDate() {
        Long day = TimeUnit.SECONDS.toDays(Instant.now().getEpochSecond());
        return newsRepository.findById(day)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/news/{worldDate}")
    public ResponseEntity getNewsForSpecificDate(@PathVariable("worldDate") String worldDateString) {
        try {
            WorldDate worldDate = new WorldDate(worldDateString);
            return newsRepository.findById(worldDate.daysSinceEpoch())
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (InvalidWorldDateStringException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
