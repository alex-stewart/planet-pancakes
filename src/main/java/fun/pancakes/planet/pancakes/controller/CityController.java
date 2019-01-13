package fun.pancakes.planet.pancakes.controller;

import fun.pancakes.planet.pancakes.entity.City;
import fun.pancakes.planet.pancakes.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CityController {

    private CityRepository cityRepository;

    @Autowired
    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping(value = "/cities")
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }
}
