// Lets have an abstract Shuffler interface that all of our shuffling algorithms will implement, 
// so we can easily run the same tests on all of them without needing to change the code in main at all, 
// and just add new classes that implement Shuffler for any new shuffling algorithms we want to test in the future
public interface Shuffler {
    // This method will shuffle an array of ints based off some specific algorithm
    public ShuffleResult shuffle(int[] array);

    // This method will return the name of the shuffling algorithm
    public String getName();
}