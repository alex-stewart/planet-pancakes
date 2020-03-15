package fun.pancakes.planet_pancakes.service;

import fun.pancakes.planet_pancakes.persistence.entity.User;
import fun.pancakes.planet_pancakes.persistence.repository.UserRepository;
import fun.pancakes.planet_pancakes.service.exception.PriceNotFoundException;
import fun.pancakes.planet_pancakes.service.exception.UserNotFoundException;
import fun.pancakes.planet_pancakes.service.resource.PriceHistoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
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
    private static final String RESOURCE_NAME = "wood";
    private static final long RESOURCE_PRICE = 500L;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private PriceHistoryService priceHistoryServiceMock;

    @InjectMocks
    private MarketService marketService;

    private ArgumentCaptor<User> userArgumentCaptor;

    @Before
    public void setUp() throws Exception{
        when(priceHistoryServiceMock.getMostRecentPriceForResource(RESOURCE_NAME)).thenReturn(RESOURCE_PRICE);
        userArgumentCaptor = ArgumentCaptor.forClass(User.class);
    }

    @Test
    public void shouldAddResourceWhenUserBuysFirstResource() throws Exception {
        mockUserWithCoinsAndResources(500L, new HashMap<>());
        marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE_NAME);

        verify(userRepositoryMock, times(1)).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue().getResources().get(RESOURCE_NAME)).isEqualTo(1);
    }

    @Test
    public void shouldIncrementResourceCountWhenUserAlreadyHasResource() throws Exception {
        mockUserWithCoins(500L);
        marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE_NAME);

        verify(userRepositoryMock, times(1)).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue().getResources().get(RESOURCE_NAME)).isEqualTo(2);
    }

    @Test
    public void shouldDeductCoinsWhenEnoughCoins() throws Exception {
        mockUserWithCoins(500L);
        marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE_NAME);

        verify(userRepositoryMock, times(1)).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue().getCoins()).isEqualTo(0);
    }

    @Test
    public void shouldNotUpdateUserWhenNotEnoughCoins() throws Exception {
        mockUserWithCoins(499L);
        marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE_NAME);

        verify(userRepositoryMock, times(0)).save(any(User.class));
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldNotUpdateUserWhenNoUserFound() throws Exception {
        when(userRepositoryMock.findById(any())).thenReturn(Optional.empty());

        marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE_NAME);

        verify(userRepositoryMock, times(0)).save(any(User.class));
    }

    @Test(expected = PriceNotFoundException.class)
    public void shouldNotUpdateUserWhenNoResourcePriceFound() throws Exception {
        when(priceHistoryServiceMock.getMostRecentPriceForResource(RESOURCE_NAME)).thenThrow(new PriceNotFoundException());
        mockUserWithCoins(500L);

        marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE_NAME);

        verify(userRepositoryMock, times(0)).save(any(User.class));
    }

    @Test
    public void shouldReturnTrueIfItemBought() throws Exception {
        mockUserWithCoins(500L);

        boolean result = marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE_NAME);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseIfNotEnoughCoins() throws Exception {
        mockUserWithCoins(499L);

        boolean result = marketService.buyResourceIfEnoughCoins(USER_ID, RESOURCE_NAME);

        assertThat(result).isFalse();
    }

    @Test
    public void shouldDecrementUserResourcesWhenUserPurchasesResource() throws Exception {
        mockUserWithCoins(0L);
        marketService.sellResourceIfResourceOwned(USER_ID, RESOURCE_NAME);

        verify(userRepositoryMock, times(1)).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue().getResources().get(RESOURCE_NAME)).isEqualTo(0);
    }

    @Test
    public void shouldAddCoinsWhenUserSellsResource() throws Exception {
        mockUserWithCoins(0L);

        marketService.sellResourceIfResourceOwned(USER_ID, RESOURCE_NAME);

        verify(userRepositoryMock, times(1)).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue().getCoins()).isEqualTo(RESOURCE_PRICE);
    }

    @Test
    public void shouldReturnTrueWhenUserSellsResource() throws Exception {
        mockUserWithCoins(0L);

        boolean result = marketService.sellResourceIfResourceOwned(USER_ID, RESOURCE_NAME);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldNotUpdateUserWhenUserDoesNotHaveResourceToSell() throws Exception {
        mockUserWithCoinsAndResources(0L, new HashMap<>());

        marketService.sellResourceIfResourceOwned(USER_ID, RESOURCE_NAME);

        verify(userRepositoryMock, times(0)).save(any(User.class));
    }

    @Test
    public void shouldReturnFalseWhenUserDoesNotHaveResourceToSell() throws Exception {
        mockUserWithCoinsAndResources(0L, new HashMap<>());

        boolean result = marketService.sellResourceIfResourceOwned(USER_ID, RESOURCE_NAME);

        assertThat(result).isFalse();
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldNotUpdateUserWhenNoUserFoundWhenSellingResource() throws Exception {
        when(userRepositoryMock.findById(any())).thenReturn(Optional.empty());

        marketService.sellResourceIfResourceOwned(USER_ID, RESOURCE_NAME);

        verify(userRepositoryMock, times(0)).save(any(User.class));
    }

    @Test(expected = PriceNotFoundException.class)
    public void shouldNotUpdateUserWhenResourceNotFoundWhenSellingResource() throws Exception {
        when(priceHistoryServiceMock.getMostRecentPriceForResource(RESOURCE_NAME)).thenThrow(new PriceNotFoundException());
        mockUserWithCoins(500L);

        marketService.sellResourceIfResourceOwned(USER_ID, RESOURCE_NAME);

        verify(userRepositoryMock, times(0)).save(any(User.class));
    }

    private void mockUserWithCoinsAndResources(Long coins, HashMap<String, Integer> resources) {
        User user = User.builder()
                .id(USER_ID)
                .coins(coins)
                .resources(resources)
                .build();
        when(userRepositoryMock.findById(USER_ID)).thenReturn(Optional.of(user));
    }

    private void mockUserWithCoins(Long coins) {
        HashMap<String, Integer> resources = new HashMap<>();
        resources.put(RESOURCE_NAME, 1);
        mockUserWithCoinsAndResources(coins, resources);
    }
}
