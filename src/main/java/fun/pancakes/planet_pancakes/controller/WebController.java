package fun.pancakes.planet_pancakes.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class WebController {

    @GetMapping(value = {"/", ""})
    public String getIndex() {
        return "index.html";
    }
}