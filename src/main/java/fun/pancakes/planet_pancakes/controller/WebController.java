package fun.pancakes.planet_pancakes.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class WebController {

    @GetMapping(value = {"/", ""})
    public String getIndex(HttpServletRequest request) {
        return "index.html";
    }
}