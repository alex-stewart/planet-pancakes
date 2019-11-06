package fun.pancakes.planet_pancakes.controller;

import fun.pancakes.planet_pancakes.dto.ErrorDto;
import fun.pancakes.planet_pancakes.service.MarketService;
import fun.pancakes.planet_pancakes.service.exception.PriceNotFoundException;
import fun.pancakes.planet_pancakes.service.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api")
@EnableSwagger2
public class MarketController {

    private static final String RESPONSE_NOT_ENOUGH_COINS = "Not enough coins.";
    private static final String RESPONSE_NOT_ENOUGH_RESOURCE = "Not enough of resource: %s.";
    private static final String RESPONSE_USER_NOT_FOUND = "User not found.";
    private static final String RESPONSE_RESOURCE_NOT_FOUND = "Price not found for resource: %s.";

    private MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @GetMapping("/market/buy")
    public ResponseEntity buyResource(Principal loggedInUser, @RequestParam("resource") String resource) {
        try {
            return marketService.buyResourceIfEnoughCoins(loggedInUser.getName(), resource)
                    ? new ResponseEntity(HttpStatus.OK)
                    : buildBadRequestResponse(RESPONSE_NOT_ENOUGH_COINS);
        } catch (UserNotFoundException userNotFoundException) {
            return buildBadRequestResponse(RESPONSE_USER_NOT_FOUND);
        } catch (PriceNotFoundException priceNotFoundException) {
            return buildBadRequestResponse(String.format(RESPONSE_RESOURCE_NOT_FOUND, resource));
        }
    }

    @GetMapping("/market/sell")
    public ResponseEntity sellResource(Principal loggedInUser, @RequestParam("resource") String resource) {
        try {
            return marketService.sellResourceIfResourceOwned(loggedInUser.getName(), resource)
                    ? new ResponseEntity(HttpStatus.OK)
                    : buildBadRequestResponse(String.format(RESPONSE_NOT_ENOUGH_RESOURCE, resource));
        } catch (UserNotFoundException userNotFoundException) {
            return buildBadRequestResponse(RESPONSE_USER_NOT_FOUND);
        } catch (PriceNotFoundException priceNotFoundException) {
            return buildBadRequestResponse(String.format(RESPONSE_RESOURCE_NOT_FOUND, resource));
        }
    }

    public ResponseEntity buildBadRequestResponse(String message) {
        return new ResponseEntity<>(new ErrorDto(message), HttpStatus.BAD_REQUEST);
    }
}
