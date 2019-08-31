package fun.pancakes.planet_pancakes.security;

import fun.pancakes.planet_pancakes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private UserService userService;

    @Autowired
    private UserAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {

        createUserIfNotExists(authentication.getName());

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("userId", authentication.getName());
        session.setAttribute("authorities", authentication.getAuthorities());

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.sendRedirect("/");
    }

    private void createUserIfNotExists(String userId) {
        if (!userService.retrieveUserById(userId).isPresent()){
            userService.createUser(userId);
        }
    }
}
