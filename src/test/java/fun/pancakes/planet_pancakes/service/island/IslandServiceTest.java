package fun.pancakes.planet_pancakes.service.island;

import fun.pancakes.planet_pancakes.dto.IslandDto;
import fun.pancakes.planet_pancakes.persistence.entity.Island;
import fun.pancakes.planet_pancakes.persistence.repository.IslandRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IslandServiceTest {

    private static final Island ISLAND = generateIsland();

    @Mock
    private IslandRepository islandRepository;

    @Mock
    private IslandPositionService islandPositionService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private IslandService islandService;

    @Before
    public void setUpMock() {
        when(islandPositionService.enrichIslandPosition(ISLAND)).thenReturn(ISLAND);
    }

    @Test
    public void shouldReturnAllIslands() {
        List<Island> islandList = new ArrayList<>();
        islandList.add(ISLAND);

        IslandDto islandDto = new IslandDto();
        islandDto.setId(1);

        when(islandRepository.findAll()).thenReturn(islandList);
        when(modelMapper.map(ISLAND, IslandDto.class)).thenReturn(islandDto);

        List<IslandDto> result = islandService.getAllIslands();
        assertEquals(islandDto, result.get(0));
    }

    @Test
    public void shouldReturnEmptyListWhenNoIslands() {
        when(islandRepository.findAll()).thenReturn(Collections.emptyList());

        List<IslandDto> result = islandService.getAllIslands();
        assertEquals(Collections.emptyList(), result);
    }

    private static Island generateIsland() {
        return Island.builder()
                .id(1)
                .build();
    }
}