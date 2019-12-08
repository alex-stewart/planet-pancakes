package fun.pancakes.planet_pancakes.controller;

import fun.pancakes.planet_pancakes.service.MarketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MarketControllerTest {

    private static final String RESOURCE = "wood";
    private static final String USER_ID = "user-123";

    @Mock
    private MarketService marketServiceMock;

    @Mock
    private Principal principalMock;

    private MarketController marketController;

    @Before
    public void setUp() {
        when(principalMock.getName()).thenReturn(USER_ID);

        marketController = new MarketController(marketServiceMock);
    }

    @Test
    public void shouldReturnOkWhenBoughtResource() throws Exception {
        when(marketServiceMock.buyResourceIfEnoughCoins(USER_ID, RESOURCE)).thenReturn(true);
        ResponseEntity responseEntity = marketController.buyResource(principalMock, RESOURCE);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void shouldReturnBadRequestWhenNotBoughtResource() throws Exception {
        when(marketServiceMock.buyResourceIfEnoughCoins(USER_ID, RESOURCE)).thenReturn(false);
        ResponseEntity responseEntity = marketController.buyResource(principalMock, RESOURCE);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void shouldReturnOkWhenSoldResource() throws Exception {
        when(marketServiceMock.sellResourceIfResourceOwned(USER_ID, RESOURCE)).thenReturn(true);
        ResponseEntity responseEntity = marketController.sellResource(principalMock, RESOURCE);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void shouldReturnBadRequestWhenNotSoldResource() throws Exception {
        when(marketServiceMock.sellResourceIfResourceOwned(USER_ID, RESOURCE)).thenReturn(false);
        ResponseEntity responseEntity = marketController.sellResource(principalMock, RESOURCE);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
    }
}