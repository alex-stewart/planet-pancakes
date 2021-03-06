package fun.pancakes.planet_pancakes.service.island;

import fun.pancakes.planet_pancakes.dto.IslandDto;
import fun.pancakes.planet_pancakes.persistence.entity.Island;
import fun.pancakes.planet_pancakes.persistence.repository.IslandRepository;
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

        return islands.stream()
                .map(islandPositionService::enrichIslandPosition)
                .map(this::islandToIslandDto)
                .collect(Collectors.toList());
    }

    private IslandDto islandToIslandDto(Island island) {
        return modelMapper.map(island, IslandDto.class);
    }
}
