package fun.pancakes.planet_pancakes.service;

import fun.pancakes.planet_pancakes.persistence.entity.Resource;
import fun.pancakes.planet_pancakes.persistence.entity.User;
import fun.pancakes.planet_pancakes.persistence.repository.ResourceRepository;
import fun.pancakes.planet_pancakes.persistence.repository.UserRepository;
import fun.pancakes.planet_pancakes.service.exception.PriceNotFoundException;
import fun.pancakes.planet_pancakes.service.exception.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MarketServiceTest {

    private static final String USER_ID = "123";
    private static final String RESOURCE = "wood";
    private static final int RESOURCE_PRICE = 500;

    private MarketService marketService;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private ResourceRepository resourceRepositoryMock;

    private ArgumentCaptor<User> userArgumentCaptor;

    @Before
    public void setUp() {
        Resource resource = Resource.builder()
                .resourceName(RESOURCE)
                .price(RESOURCE_PRICE)
                .build();
        when(resourceRepositoryMock.findByResourceName(RESOURCE)).thenReturn(Optional.of(resource));

        marketService = new MarketService(userRepositoryMock, resourceRepositoryMock);
        userArgumentCaptor = ArgumentCaptor.forClass(User.class);
    }

    @Test
    public void shouldAddResourceWhenUserBuysFirstResource() throws Exception {
        mockUserWithCoinsAndResources(500, new HashMap());
        marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE);

        verify(userRepositoryMock, times(1)).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue().getResources().get(RESOURCE)).isEqualTo(1);
    }

    @Test
    public void shouldIncrementResourceCountWhenUserAlreadyHasResource() throws Exception {
        mockUserWithCoins(500);
        marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE);

        verify(userRepositoryMock, times(1)).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue().getResources().get(RESOURCE)).isEqualTo(2);
    }

    @Test
    public void shouldDeductCoinsWhenEnoughCoins() throws Exception {
        mockUserWithCoins(500);
        marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE);

        verify(userRepositoryMock, times(1)).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue().getCoins()).isEqualTo(0);
    }

    @Test
    public void shouldNotUpdateUserWhenNotEnoughCoins() throws Exception {
        mockUserWithCoins(499);
        marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE);

        verify(userRepositoryMock, times(0)).save(any(User.class));
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldNotUpdateUserWhenNoUserFound() throws Exception {
        when(userRepositoryMock.findById(any())).thenReturn(Optional.empty());

        marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE);

        verify(userRepositoryMock, times(0)).save(any(User.class));
    }

    @Test(expected = PriceNotFoundException.class)
    public void shouldNotUpdateUserWhenNoResourcePriceFound() throws Exception {
        when(resourceRepositoryMock.findByResourceName(RESOURCE)).thenReturn(Optional.empty());
        mockUserWithCoins(500);

        marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE);

        verify(userRepositoryMock, times(0)).save(any(User.class));
    }

    @Test
    public void shouldReturnTrueIfItemBought() throws Exception {
        mockUserWithCoins(500);

        boolean result = marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseIfNotEnoughCoins() throws Exception {
        mockUserWithCoins(499);

        boolean result = marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE);

        assertThat(result).isFalse();
    }

    @Test
    public void shouldDecrementUserResourcesWhenUserPurchasesResource() throws Exception {
        mockUserWithCoins(0);
        marketService.sellResourceIfResourceOwned(USER_ID, RESOURCE);

        verify(userRepositoryMock, times(1)).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue().getResources().get(RESOURCE)).isEqualTo(0);
    }

    @Test
    public void shouldAddCoinsWhenUserSellsResource() throws Exception {
        mockUserWithCoins(0);

        marketService.sellResourceIfResourceOwned(USER_ID, RESOURCE);

        verify(userRepositoryMock, times(1)).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue().getCoins()).isEqualTo(RESOURCE_PRICE);
    }

    @Test
    public void shouldReturnTrueWhenUserSellsResource() throws Exception {
        mockUserWithCoins(0);

        boolean result = marketService.sellResourceIfResourceOwned(USER_ID, RESOURCE);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldNotUpdateUserWhenUserDoesNotHaveResourceToSell() throws Exception {
        mockUserWithCoinsAndResources(0, new HashMap<>());

        marketService.sellResourceIfResourceOwned(USER_ID, RESOURCE);

        verify(userRepositoryMock, times(0)).save(any(User.class));
    }

    @Test
    public void shouldReturnFalseWhenUserDoesNotHaveResourceToSell() throws Exception {
        mockUserWithCoinsAndResources(0, new HashMap<>());

        boolean result = marketService.sellResourceIfResourceOwned(USER_ID, RESOURCE);

        assertThat(result).isFalse();
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldNotUpdateUserWhenNoUserFoundWhenSellingResource() throws Exception {
        when(userRepositoryMock.findById(any())).thenReturn(Optional.empty());

        marketService.sellResourceIfResourceOwned(USER_ID, RESOURCE);

        verify(userRepositoryMock, times(0)).save(any(User.class));
    }

    @Test(expected = PriceNotFoundException.class)
    public void shouldNotUpdateUserWhenResourceNotFoundWhenSellingResource() throws Exception {
        when(resourceRepositoryMock.findByResourceName(RESOURCE)).thenReturn(Optional.empty());
        mockUserWithCoins(500);

        marketService.sellResourceIfResourceOwned(USER_ID, RESOURCE);

        verify(userRepositoryMock, times(0)).save(any(User.class));
    }

    private void mockUserWithCoinsAndResources(Integer coins, HashMap<String, Integer> resources) {
        User user = User.builder()
                .id(USER_ID)
                .coins(coins)
                .resources(resources)
                .build();
        when(userRepositoryMock.findById(USER_ID)).thenReturn(Optional.of(user));
    }

    private void mockUserWithCoins(Integer coins) {
        HashMap<String, Integer> resources = new HashMap<>();
        resources.put(RESOURCE, 1);
        mockUserWithCoinsAndResources(coins, resources);
    }
}