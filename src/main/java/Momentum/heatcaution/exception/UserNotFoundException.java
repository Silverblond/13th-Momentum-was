package Momentum.heatcaution.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("아이디가 존재하지 않습니다.");
    }
}
