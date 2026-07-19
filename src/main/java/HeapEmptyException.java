public class HeapEmptyException extends RuntimeException {
    public HeapEmptyException(String message) {
        super(message);
        System.out.println(message);
    }
}
