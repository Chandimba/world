package ao.it.chandsoft.world.exceptions;

public class JwtTokenException extends RuntimeException{

    public JwtTokenException(String message) {
        super(message);
    }
}
