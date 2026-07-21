public class StackFullException extends RuntimeException {
    public StackFullException(String message) {
        super(message);
        System.out.println(message);
    }
}