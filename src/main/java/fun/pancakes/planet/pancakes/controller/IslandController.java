package fun.pancakes.planet.pancakes.controller;

import fun.pancakes.planet.pancakes.entity.Island;
import fun.pancakes.planet.pancakes.repository.IslandRepository;
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

    private IslandRepository islandRepository;

    @Autowired
    public IslandController(IslandRepository islandRepository) {
        this.islandRepository = islandRepository;
    }

    @GetMapping(value = "/islands")
    public List<Island> getAllIslands() {
        return islandRepository.findAll();
    }
}
