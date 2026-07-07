class FisherYatesShuffler implements Shuffler {
    public String getName() {
        return Character.toString(0x1F500) + " Fischer Yates Shuffle";
    }

    // For convenient on the fly shuffling
    public static ShuffleResult shuffleArray(int[] array) {
        FisherYatesShuffler shuffler = new FisherYatesShuffler();
        return shuffler.shuffle(array);

    }
    
    // Despite being a shuffler, we can still count comparisons and exchanges for fun / principle
    private static int numberOfExchanges;

    public ShuffleResult shuffle(int[] array) {
        numberOfExchanges = 0;
        for (int i = array.length - 1; i > 0; i--)
            {
                int j = (int)(Math.random() * (i + 1));
                swap(array, i, j);
            }

        // Here, the list is shuffled
        return new ShuffleResult(array, numberOfExchanges);
    }

    private static void swap(int[] arr, int i, int j) {
        if (i != j) { // Only swap if the indices are different to track how many exchanges we made
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            numberOfExchanges++;
        }
    }
}