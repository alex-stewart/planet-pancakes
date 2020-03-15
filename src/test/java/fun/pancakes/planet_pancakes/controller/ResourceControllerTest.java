package fun.pancakes.planet_pancakes.controller;

import com.mongodb.MongoTimeoutException;
import fun.pancakes.planet_pancakes.dto.ResourceDto;
import fun.pancakes.planet_pancakes.service.resource.ResourceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResourceControllerTest {

    private static final String RESOURCE_1_NAME = "wood";
    private static final String RESOURCE_2_NAME = "coal";
    private static final long RESOURCE_1_PRICE = 10L;
    private static final long RESOURCE_2_PRICE = 20L;

    private static final ResourceDto RESOURCE_1_DTO = generateResourceDto(RESOURCE_1_NAME, RESOURCE_1_PRICE);
    private static final ResourceDto RESOURCE_2_DTO = generateResourceDto(RESOURCE_2_NAME, RESOURCE_2_PRICE);
    private static final List<ResourceDto> RESOURCE_DTO_LIST = Arrays.asList(RESOURCE_1_DTO, RESOURCE_2_DTO);

    @Mock
    private ResourceService resourceService;

    @InjectMocks
    private ResourceController resourceController;

    private static ResourceDto generateResourceDto(String resourceName, Long resourcePrice) {
        return ResourceDto.builder()
                .resourceName(resourceName)
                .price(resourcePrice)
                .build();
    }

    @Before
    public void initMocks() {
        when(resourceService.findAllResources()).thenReturn(RESOURCE_DTO_LIST);
    }

    @Test
    public void shouldReturnAllResources() {
        ResponseEntity responseEntity = resourceController.getAllResources();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(RESOURCE_DTO_LIST);
    }

    @Test
    public void shouldReturnErrorWhenUnableToRetrieveResources() {
        when(resourceController.getAllResources()).thenThrow(MongoTimeoutException.class);

        ResponseEntity responseEntity = resourceController.getAllResources();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}