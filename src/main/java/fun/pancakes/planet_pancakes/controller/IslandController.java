package fun.pancakes.planet_pancakes.controller;

import fun.pancakes.planet_pancakes.dto.IslandDto;
import fun.pancakes.planet_pancakes.service.IslandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@EnableSwagger2
public class IslandController {

    private IslandService islandService;

    @Autowired
    public IslandController(IslandService islandService) {
        this.islandService = islandService;
    }

    @GetMapping(value = "/islands")
    public List<IslandDto> getAllIslands() {
        return islandService.getAllIslands();
    }
}
