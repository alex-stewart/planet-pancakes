package fun.pancakes.planet_pancakes.service;

import fun.pancakes.planet_pancakes.persistence.entity.User;
import fun.pancakes.planet_pancakes.persistence.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationServiceTest {

    private static final String USER_ID = "pancakes2";
    private static final String USER_NAME = "Pancakes";
    private static final String START_LOCATION = "start city";

    @Mock
    private UserRepository mockUserRepository;

    private UserRegistrationService userRegistrationService;

    private ArgumentCaptor<User> userCaptor;

    @Before
    public void setUp() {
        userRegistrationService = new UserRegistrationService(mockUserRepository, START_LOCATION);
        userCaptor = ArgumentCaptor.forClass(User.class);
        when(mockUserRepository.findById(USER_ID)).thenReturn(Optional.empty());
    }

    @Test
    public void shouldNotCreateUserIfAlreadyExists() {
        when(mockUserRepository.findById(USER_ID)).thenReturn(Optional.of(anExistingUser()));

        userRegistrationService.createUserIfNotExist(USER_ID, USER_NAME);

        verify(mockUserRepository, times(0)).insert(any(User.class));
    }

    @Test
    public void shouldCreateUserIfAlreadyExists() {
        userRegistrationService.createUserIfNotExist(USER_ID, USER_NAME);

        verify(mockUserRepository, times(1)).insert(userCaptor.capture());

        assertThat(userCaptor.getValue().getId()).isEqualTo(USER_ID);
        assertThat(userCaptor.getValue().getName()).isEqualTo(USER_NAME);
    }

    @Test
    public void shouldGiveUserInitialCoins() {
        userRegistrationService.createUserIfNotExist(USER_ID, USER_NAME);

        verify(mockUserRepository, times(1)).insert(userCaptor.capture());

        assertThat(userCaptor.getValue().getCoins()).isEqualTo(500);
    }

    @Test
    public void shouldSetStartLocation() {
        userRegistrationService.createUserIfNotExist(USER_ID, USER_NAME);

        verify(mockUserRepository, times(1)).insert(userCaptor.capture());

        assertThat(userCaptor.getValue().getLocation()).isEqualTo(START_LOCATION);
    }

    private User anExistingUser() {
        return User.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .build();
    }
}