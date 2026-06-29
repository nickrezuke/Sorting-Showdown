public class BucketSorter implements Sorter {
    public String getName() {
        //return "\u229E Bucket Sort";

        //Bucket is a new Emoji, so it isnt supported like the others are...
        if (!"vscode".equalsIgnoreCase(System.getenv("TERM_PROGRAM"))) {
            return Character.toString(0x1FAA3) + " Bucket Sort";
        } else {
            return Character.toString(0x1FAA3) + "  Bucket Sort\u3000";
        }
        // VS Code's Terminal handles the new emoji width on its own, but Terminal (and others) need the full-width space padding helper
        // TODO: Check if other environments need this, or if there's clearly a better solution to this...
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        //TODO: Implement Bucket Sort
        
        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}