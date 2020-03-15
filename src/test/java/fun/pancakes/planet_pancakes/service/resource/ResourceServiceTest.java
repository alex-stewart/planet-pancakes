package fun.pancakes.planet_pancakes.service.resource;

import fun.pancakes.planet_pancakes.dto.ResourceDto;
import fun.pancakes.planet_pancakes.dto.converter.ResourceConverter;
import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.persistence.repository.ResourceRepository;
import fun.pancakes.planet_pancakes.service.exception.PriceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResourceServiceTest {

    private static final String RESOURCE_1_NAME = "wood";
    private static final String RESOURCE_2_NAME = "coal";
    private static final long RESOURCE_1_PRICE = 10L;
    private static final long RESOURCE_2_PRICE = 20L;

    private static final Resource RESOURCE_1 = generateResource(RESOURCE_1_NAME);
    private static final Resource RESOURCE_2 = generateResource(RESOURCE_2_NAME);
    private static final List<Resource> RESOURCE_LIST = Arrays.asList(RESOURCE_1, RESOURCE_2);

    private static final ResourceDto RESOURCE_1_DTO = generateResourceDto(RESOURCE_1_NAME, RESOURCE_1_PRICE);
    private static final ResourceDto RESOURCE_2_DTO = generateResourceDto(RESOURCE_2_NAME, RESOURCE_2_PRICE);
    private static final List<ResourceDto> RESOURCE_DTO_LIST = Arrays.asList(RESOURCE_1_DTO, RESOURCE_2_DTO);

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private ResourceConverter resourceConverter;

    @InjectMocks
    private ResourceService resourceService;

    private static Resource generateResource(String resourceName) {
        return Resource.builder()
                .resourceName(resourceName)
                .build();
    }

    private static ResourceDto generateResourceDto(String resourceName, Long resourcePrice) {
        return ResourceDto.builder()
                .resourceName(resourceName)
                .price(resourcePrice)
                .build();
    }

    @Before
    public void setUp() throws Exception {
        when(resourceRepository.findAll()).thenReturn(RESOURCE_LIST);

        when(resourceConverter.convertToDto(RESOURCE_1)).thenReturn(RESOURCE_1_DTO);
        when(resourceConverter.convertToDto(RESOURCE_2)).thenReturn(RESOURCE_2_DTO);
    }

    @Test
    public void shouldRetrieveAllResources() {
        resourceService.findAllResources();

        verify(resourceRepository, times(1)).findAll();
    }

    @Test
    public void shouldConvertEachResource() throws Exception {
        resourceService.findAllResources();

        verify(resourceConverter, times(1)).convertToDto(RESOURCE_1);
        verify(resourceConverter, times(1)).convertToDto(RESOURCE_2);
    }

    @Test
    public void shouldReturnResourceDTOs() {
        List<ResourceDto> result = resourceService.findAllResources();

        assertThat(result).isEqualTo(RESOURCE_DTO_LIST);
    }

    @Test
    public void shouldNotReturnResourceIfFailedToConvert() throws Exception{
        when(resourceConverter.convertToDto(RESOURCE_2)).thenThrow(PriceNotFoundException.class);

        List<ResourceDto> result = resourceService.findAllResources();

        assertThat(result).contains(RESOURCE_1_DTO);
        assertThat(result).doesNotContain(RESOURCE_2_DTO);
    }

}
