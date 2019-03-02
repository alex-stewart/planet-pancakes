package fun.pancakes.planet.pancakes.controller;

import fun.pancakes.planet.pancakes.dto.IslandDto;
import fun.pancakes.planet.pancakes.service.IslandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
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
