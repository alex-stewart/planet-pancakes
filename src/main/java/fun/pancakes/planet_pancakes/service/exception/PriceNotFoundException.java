package fun.pancakes.planet_pancakes.service.exception;

public class PriceNotFoundException extends Exception {

    public PriceNotFoundException() {
        super("Resource not found.");
    }
}
