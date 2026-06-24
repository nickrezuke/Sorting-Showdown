public class HeapFullException extends RuntimeException {
    public HeapFullException(String message) {
        super(message);
        System.out.println(message);
    }
}
