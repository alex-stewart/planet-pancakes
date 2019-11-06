package fun.pancakes.planet_pancakes.service.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("User not found.");
    }
}
