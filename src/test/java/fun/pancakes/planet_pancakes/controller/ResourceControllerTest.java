package fun.pancakes.planet_pancakes.controller;

import com.mongodb.MongoTimeoutException;
import fun.pancakes.planet_pancakes.converter.ResourceConverter;
import fun.pancakes.planet_pancakes.dto.ResourceDto;
import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.persistence.repository.ResourceRepository;
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

    private static final Resource RESOURCE_1 = generateResource(RESOURCE_1_NAME, RESOURCE_1_PRICE);
    private static final Resource RESOURCE_2 = generateResource(RESOURCE_2_NAME, RESOURCE_2_PRICE);
    private static final List<Resource> RESOURCE_LIST = Arrays.asList(RESOURCE_1, RESOURCE_2);

    private static final ResourceDto RESOURCE_1_DTO = generateResourceDto(RESOURCE_1_NAME, RESOURCE_1_PRICE);
    private static final ResourceDto RESOURCE_2_DTO = generateResourceDto(RESOURCE_2_NAME, RESOURCE_2_PRICE);
    private static final List<ResourceDto> RESOURCE_DTO_LIST = Arrays.asList(RESOURCE_1_DTO, RESOURCE_2_DTO);

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private ResourceConverter resourceConverter;

    @InjectMocks
    private ResourceController resourceController;

    @Before
    public void initMocks() {
        when(resourceConverter.convertToDto(RESOURCE_1)).thenReturn(RESOURCE_1_DTO);
        when(resourceConverter.convertToDto(RESOURCE_2)).thenReturn(RESOURCE_2_DTO);
    }

    @Test
    public void shouldReturnAllResources() {
        when(resourceRepository.findAll()).thenReturn(RESOURCE_LIST);

        ResponseEntity responseEntity = resourceController.getAllResources();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(RESOURCE_DTO_LIST);
    }

    @Test
    public void shouldReturnErrorWhenUnableToRetrieveResources() {
        when(resourceRepository.findAll()).thenThrow(MongoTimeoutException.class);

        ResponseEntity responseEntity = resourceController.getAllResources();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static Resource generateResource(String resourceName, Long resourcePrice) {
        return Resource.builder()
                .resourceName(resourceName)
                .price(resourcePrice)
                .build();
    }

    private static ResourceDto generateResourceDto(String resourceName, Long resourcePrice) {
        return ResourceDto.builder()
                .resourceName(resourceName)
                .price(resourcePrice)
                .build();
    }

}