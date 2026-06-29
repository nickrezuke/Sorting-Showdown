public class CombSorter implements Sorter {
    public String getName() {
        //return "\u0428 Comb Sort";

        if (!"vscode".equalsIgnoreCase(System.getenv("TERM_PROGRAM"))) {
            return Character.toString(0x1FAAE) + " Comb Sort";
        } else {
            return Character.toString(0x1FAAE) + "  Comb Sort\u3000";
        }
        // VS Code's Terminal handles the new emoji width on its own, but Terminal (and others) need the full-width space padding helper
        // TODO: Check if other environments need this, or if there's clearly a better solution to this...
    }

    public SortResult sort(int[] array) {
        int numberOfComparisons = 0;
        int numberOfExchanges = 0;

        //TODO: Implement Comb Sort

        return new SortResult(array, numberOfComparisons, numberOfExchanges);
    }
}