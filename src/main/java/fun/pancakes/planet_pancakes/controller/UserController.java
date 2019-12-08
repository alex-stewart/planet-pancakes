package fun.pancakes.planet_pancakes.controller;

import fun.pancakes.planet_pancakes.dto.ProfileDto;
import fun.pancakes.planet_pancakes.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static java.util.Objects.isNull;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/users/me")
    public ResponseEntity<ProfileDto> user(Principal loggedInUser) {
        if (isNull(loggedInUser)) {
            return ResponseEntity.notFound().build();
        }

        return userService.getProfileForUser(loggedInUser.getName(), true)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
