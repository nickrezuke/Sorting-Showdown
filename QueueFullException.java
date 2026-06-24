public class QueueFullException extends RuntimeException {
    public QueueFullException(String message) {
        super(message);
        System.out.println(message);
    }
}
