package fun.pancakes.planet_pancakes.controller;

import fun.pancakes.planet_pancakes.dto.IslandDto;
import fun.pancakes.planet_pancakes.service.IslandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IslandControllerTest {

    @Mock
    private IslandService islandService;

    @InjectMocks
    private IslandController islandController;

    @Test
    public void shouldReturnAllIslands() {
        List<IslandDto> islandDtoList = new ArrayList<>();
        islandDtoList.add(new IslandDto());
        when(islandService.getAllIslands()).thenReturn(islandDtoList);

        List<IslandDto> result = islandController.getAllIslands();

        assertEquals(islandDtoList, result);
    }
}