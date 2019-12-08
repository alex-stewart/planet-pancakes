package fun.pancakes.planet_pancakes.security;

import fun.pancakes.planet_pancakes.service.user.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final String USER_ID = "userId";
    private static final String USERNAME = "username";
    private static final String AUTHORITIES = "authorities";

    private UserRegistrationService userRegistrationService;

    @Autowired
    private UserAuthenticationSuccessHandler(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {

        Map<String, Object> userAttributes = getUserAttributes(authentication);
        String username = (String) userAttributes.get(USERNAME);

        userRegistrationService.createUserIfNotExist(authentication.getName(), username);

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(USER_ID, authentication.getName());
        session.setAttribute(AUTHORITIES, authentication.getAuthorities());

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.sendRedirect("/");
    }

    private Map<String, Object> getUserAttributes(Authentication authentication) {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
        return oAuth2User.getAttributes();
    }

}
