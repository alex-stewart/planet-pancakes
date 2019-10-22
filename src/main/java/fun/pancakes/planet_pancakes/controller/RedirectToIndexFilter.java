package fun.pancakes.planet_pancakes.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
public class RedirectToIndexFilter implements Filter {

    private static final String[] URI_PREFIXES = {"/api", "/islands", "/static"};

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();

        for (String uriPrefix : URI_PREFIXES) {
            if (requestURI.startsWith(uriPrefix)) {
                chain.doFilter(request, response);
                return;
            }
        }

        request.getRequestDispatcher("/").forward(request, response);
    }

}