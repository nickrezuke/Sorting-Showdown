import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SorterTest {

    @Test
    public void testBubbleSortRandom() {
        int[] input = {5, 3, 8, 1, 2};
        int[] expected = {1, 2, 3, 5, 8};
        
        BubbleSorter bubbleSorter = new BubbleSorter();
        int[] output = bubbleSorter.sort(input).sortedArray(); 
        
        assertArrayEquals(expected, output, "Bubble Sort needs to leave the array sorted in ascending order!");
    }

    @Test
    public void testBubbleSortAlreadySorted() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        BubbleSorter bubbleSorter = new BubbleSorter();
        int[] output = bubbleSorter.sort(input).sortedArray();

        assertArrayEquals(expected, output);
    }
}
