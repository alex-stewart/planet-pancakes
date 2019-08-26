package fun.pancakes.planet_pancakes.service;

import fun.pancakes.planet_pancakes.dto.IslandDto;
import fun.pancakes.planet_pancakes.entity.Island;
import fun.pancakes.planet_pancakes.repository.IslandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IslandService {

    private IslandRepository islandRepository;
    private IslandPositionService islandPositionService;
    private ModelMapper modelMapper;

    @Autowired
    public IslandService(IslandRepository islandRepository,
                         IslandPositionService islandPositionService,
                         ModelMapper modelMapper) {
        this.islandRepository = islandRepository;
        this.islandPositionService = islandPositionService;
        this.modelMapper = modelMapper;
    }

    public List<IslandDto> getAllIslands() {
        List<Island> islands = islandRepository.findAll();

        return islands.stream().map(
                island -> {
                    islandPositionService.enrichIslandPosition(island);
                    return modelMapper.map(island, IslandDto.class);
                }
        ).collect(Collectors.toList());
    }
}
