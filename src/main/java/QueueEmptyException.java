public class QueueEmptyException extends RuntimeException {
    public QueueEmptyException(String message) {
        super(message);
        System.out.println(message);
    }
}
