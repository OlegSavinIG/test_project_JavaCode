package task.javacode.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String notEnoughMoney) {
        super(notEnoughMoney);
    }
}
