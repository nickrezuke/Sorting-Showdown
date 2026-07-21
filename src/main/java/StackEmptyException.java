public class StackEmptyException extends RuntimeException {
    public StackEmptyException(String message) {
        super(message);
        System.out.println(message);
    }
}