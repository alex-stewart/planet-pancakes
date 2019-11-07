package fun.pancakes.planet_pancakes.controller;

import fun.pancakes.planet_pancakes.persistence.entity.News;
import fun.pancakes.planet_pancakes.persistence.repository.NewsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class NewsController {

    private static final int PAGE_SIZE = 1;

    private NewsRepository newsRepository;

    @Autowired
    public NewsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping("/news")
    public ResponseEntity getAllNews(@RequestParam(defaultValue = "0") Integer pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, PAGE_SIZE);
        Page<News> pagedResult = newsRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return ResponseEntity.ok(pagedResult.getContent());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
