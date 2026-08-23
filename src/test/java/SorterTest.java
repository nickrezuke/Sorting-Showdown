import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SorterTest {
    // 1. Centralized array of all algorithms to test
    private static Sorter[] sorterProvider() {
        return SortingShowdown.getAllAlgorithms();
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testCountersAreNonNegative(Sorter sorter) {
        int[] input = { 5, 3, 8, 1, 2 };
        SortResult result = sorter.sort(input);
        Assertions.assertTrue(result.numberOfComparisons() >= 0,
                sorter.getName() + " reported negative comparisons.");
        Assertions.assertTrue(result.numberOfExchanges() >= 0,
                sorter.getName() + " reported negative exchanges.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testRandomArray(Sorter sorter) {
        int[] input = { 5, 3, 8, 1, 2, 9, 4, 7, 6 };
        int[] expected = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " failed on a standard random array.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testAlreadySorted(Sorter sorter) {
        int[] input = { 1, 2, 3, 4, 5 };
        int[] expected = { 1, 2, 3, 4, 5 };
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " modified an already sorted array.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testReverseSorted(Sorter sorter) {
        int[] input = { 5, 4, 3, 2, 1 };
        int[] expected = { 1, 2, 3, 4, 5 };
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " failed on a reverse-sorted array.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testEmptyArray(Sorter sorter) {
        int[] input = {};
        int[] expected = {};
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output,
                sorter.getName() + " threw an exception or failed on an empty array.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testSingleElementArray(Sorter sorter) {
        int[] input = { 42 };
        int[] expected = { 42 };
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " failed on a single-element array.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testAllIdenticalElements(Sorter sorter) {
        int[] input = { 7, 7, 7, 7, 7 };
        int[] expected = { 7, 7, 7, 7, 7 };
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output,
                sorter.getName() + " failed (or infinitely looped) on identical elements.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testDuplicateElements(Sorter sorter) {
        int[] input = { 3, 1, 3, 2, 1, 2 };
        int[] expected = { 1, 1, 2, 2, 3, 3 };
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " failed to handle duplicate values.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testMaxValue(Sorter sorter) {
        int[] input = { Integer.MAX_VALUE, 4, 25, 1, 11 };
        int[] expected = { 1, 4, 11, 25, Integer.MAX_VALUE };
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " failed on max integer limit.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testNegativeNumbers(Sorter sorter) {
        int[] input = { -5, -1, -10, -3, -8 };
        int[] expected = { -10, -8, -5, -3, -1 };
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " failed on negative numbers.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testMixedSignNumbers(Sorter sorter) {
        int[] input = { -5, 0, 3, -1, 10 };
        int[] expected = { -5, -1, 0, 3, 10 };
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " failed on mixed-sign numbers.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testMinValue(Sorter sorter) {
        int[] input = { Integer.MIN_VALUE, -4, -25, -1, -11 };
        int[] expected = { Integer.MIN_VALUE, -25, -11, -4, -1 };
        int[] output = sorter.sort(input).sortedArray();
        Assertions.assertArrayEquals(expected, output, sorter.getName() + " failed on min integer limit.");
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testLargeArray(Sorter sorter) {
        int[] input = new int[1000];
        for (int i = 0; i < 1000; i++) {
            input[i] = 1000 - i; // Reverse sorted large array
        }
        int[] output = sorter.sort(input).sortedArray();
        // Verify it's sorted
        for (int i = 0; i < output.length - 1; i++) {
            Assertions.assertTrue(output[i] <= output[i + 1],
                    sorter.getName() + " failed to sort 1000-element reverse array.");
        }
    }

    @ParameterizedTest
    @MethodSource("sorterProvider")
    public void testSortingIsConsistent(Sorter sorter) {
        int[] input = { 5, 3, 8, 1, 2 };
        int[] result1 = sorter.sort(input.clone()).sortedArray();
        int[] result2 = sorter.sort(input.clone()).sortedArray();
        Assertions.assertArrayEquals(result1, result2,
                sorter.getName() + " produces inconsistent results.");
    }
}
