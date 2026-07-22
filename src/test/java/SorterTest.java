import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SorterTest {

    // 1. Centralized array of all algorithms to test without using streams
    private static Sorter[] sorterProvider() {
        return new Sorter[] {
            new CycleSorter(), new ExchangeSorter(), new BubbleSorter(), 
            new CocktailShakerSorter(), new BrickSorter(), new PancakeSorter(), 
            new CombSorter(), new InsertionSorter(), new SelectionSorter(), 
            new DoubleSelectionSorter(), new GnomeSorter(), new ShellSorter(), 
            new IterativeQuickSorter(), new RecursiveQuickSorter(), 
            new BottomUpHeapSorter(), new TopDownHeapSorter(), new MergeSorter(), 
            new IntroSorter(), new BucketSorter(), new LSDRadixSorter(), 
            new MSDRadixSorter(), new CountingSorter()
        };
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testRandomArray(Sorter sorter) {
        int[] input = {5, 3, 8, 1, 2, 9, 4, 7, 6};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " failed on a standard random array.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testAlreadySorted(Sorter sorter) {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " modified an already sorted array.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testReverseSorted(Sorter sorter) {
        int[] input = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " failed on a reverse-sorted array.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testEmptyArray(Sorter sorter) {
        int[] input = {};
        int[] expected = {};
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " threw an exception or failed on an empty array.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testSingleElementArray(Sorter sorter) {
        int[] input = {42};
        int[] expected = {42};
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " failed on a single-element array.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testAllIdenticalElements(Sorter sorter) {
        int[] input = {7, 7, 7, 7, 7};
        int[] expected = {7, 7, 7, 7, 7};
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " failed (or infinitely looped) on identical elements.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testDuplicateElements(Sorter sorter) {
        int[] input = {3, 1, 3, 2, 1, 2};
        int[] expected = {1, 1, 2, 2, 3, 3};
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " failed to handle duplicate values.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testExtremeValues(Sorter sorter) {
        int[] input = {Integer.MAX_VALUE, 4, 25, 1, 11};
        int[] expected = {1, 4, 11, 25, Integer.MAX_VALUE};
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " failed on extreme integer limits.");
    }
}
