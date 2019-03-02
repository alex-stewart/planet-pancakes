package fun.pancakes.planet.pancakes.service;

import fun.pancakes.planet.pancakes.dto.IslandDto;
import fun.pancakes.planet.pancakes.entity.Island;
import fun.pancakes.planet.pancakes.repository.IslandRepository;
import fun.pancakes.planet.pancakes.util.LocationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IslandService {

    private IslandRepository islandRepository;
    private ModelMapper modelMapper;

    @Autowired
    public IslandService(IslandRepository islandRepository, ModelMapper modelMapper) {
        this.islandRepository = islandRepository;
        this.modelMapper = modelMapper;
    }

    public List<IslandDto> getAllIslands() {
        List<Island> islands = islandRepository.findAll();

        return islands.stream().map(
                this::convertToDto
        ).collect(Collectors.toList());
    }

    private IslandDto convertToDto(Island island) {
        IslandDto islandDto = modelMapper.map(island, IslandDto.class);
        islandDto.setRadius(LocationUtils.radiusForRing(island.getRing()));
        islandDto.setBearing(LocationUtils.calculateCurrentBearing(island.getBearing(), island.getRing()));
        return islandDto;
    }

}
