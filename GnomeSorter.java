public class GnomeSorter implements Sorter {
    // public String getName() {
    //     // return "\u229E Gnome Sort";

    //     // Troll is a new Emoji, so it isnt supported like the others are...
    //     if (!"vscode".equalsIgnoreCase(System.getenv("TERM_PROGRAM"))) {
    //         return Character.toString(0x1F9CC) + " Gnome Sort";
    //     } else {
    //         return Character.toString(0x1F9CC) + "  Gnome Sort\u3000";
    //     }
    //     // VS Code's Terminal handles the new emoji width on its own, but Terminal (and others) need the full-width space padding helper
    //     // TODO: Check if other environments need this, or if there's clearly a better solution to this...
    // }
    public String getName() {
        return "Gnome Sort";
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        int currentIndex = 1;
        int nextUnsortedIndex = 2;

        while (currentIndex < array.length) {
            int previousIndex = currentIndex - 1;

            numberOfComparisons++;
            if (array[previousIndex] <= array[currentIndex]) {
                // Elements are in order; teleport forward to the next unsorted area
                currentIndex = nextUnsortedIndex;
                nextUnsortedIndex++;
            } else {
                // Elements are out of order; swap them
                int temporaryValue = array[previousIndex];
                array[previousIndex] = array[currentIndex];
                array[currentIndex] = temporaryValue;
                numberOfExchanges++;

                // Step backward to bubble the element down
                currentIndex--;

                // If we reach the starting edge, jump back up to the unsorted front
                if (currentIndex == 0) {
                    currentIndex = nextUnsortedIndex;
                    nextUnsortedIndex++;
                }
            }
        }

        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}