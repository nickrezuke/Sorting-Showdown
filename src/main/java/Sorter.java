// Lets have an abstract Sorter interface that all of our sorting algorithms will implement, 
// so we can easily run the same tests on all of them without needing to change the code in main at all, 
// and just add new classes that implement Sorter for any new sorting algorithms we want to test in the future
public interface Sorter {
    // This method will sort an array of ints based off some specific algorithm
    public SortResult sort(int[] array);

    // This method will return the name of the sorting algorithm
    public String getName();
}