package fun.pancakes.planet_pancakes.service.resource;

import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.persistence.repository.ResourceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class ResourcePriceUpdaterTest {

    private static final String RESOURCE_1 = "PANCAKE";
    private static final String RESOURCE_2 = "WAFFLE";
    private static final long RESOURCE_PRICE = 10L;

    private static final Date DATE = new Date();

    @Mock
    private ResourceService resourceService;

    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private ResourcePriceUpdater resourcePriceUpdater;

    @Test
    public void shouldCallUpdatePriceServiceWithAllResources() {
        List<Resource> resources = generateResourceList(RESOURCE_1, RESOURCE_2);
        when(resourceRepository.findAll()).thenReturn(resources);

        resourcePriceUpdater.updatePrices(DATE);

        verifyResourcesUpdated(resources);
    }

    @Test
    public void shouldNotCallUpdatePriceServiceWithResourceWhenPriceSet() {
        List<Resource> resources = generateResourceListWithPrices(RESOURCE_1, RESOURCE_2);
        when(resourceRepository.findAll()).thenReturn(resources);

        resourcePriceUpdater.updatePrices(DATE);

        verifyZeroInteractions(resourceService);
    }

    @Test
    public void shouldSaveUpdatedPricesToRepository() {
        List<Resource> resources = generateResourceList(RESOURCE_1, RESOURCE_2);
        when(resourceRepository.findAll()).thenReturn(resources);
        mockUpdateResourceService(resources);

        resourcePriceUpdater.updatePrices(DATE);

        verifyResourcesSaved(resources);
    }

    private List<Resource> generateResourceList(String... resourceNames) {
        return Arrays.stream(resourceNames)
                .map(this::generateResource)
                .collect(Collectors.toList());
    }

    private List<Resource> generateResourceListWithPrices(String... resourceNames) {
        List<Resource> resources = generateResourceList(resourceNames);

        resources.forEach(resource -> resource.getPriceHistory().put(DATE, RESOURCE_PRICE));

        return resources;
    }

    private Resource generateResource(String resourceName) {
        return Resource.builder()
                .resourceName(resourceName)
                .priceHistory(new HashMap<>())
                .build();
    }

    private void verifyResourcesUpdated(List<Resource> resources) {
        for (Resource resource : resources) {
            verify(resourceService, times(1)).updateResourceWithPriceAtTime(resource, DATE);
        }
    }

    private void mockUpdateResourceService(List<Resource> resources) {
        for (Resource resource : resources) {
            when(resourceService.updateResourceWithPriceAtTime(resource, DATE)).thenReturn(resource);
        }
    }

    private void verifyResourcesSaved(List<Resource> resources) {
        verify(resourceRepository, times(1)).saveAll(resources);
    }
}