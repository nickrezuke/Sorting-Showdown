class FisherYatesShuffler implements Shuffler {
    public String getName() {
        //return Character.toString(0x1F500) + " Fisher Yates Shuffle";
        return "Fisher Yates Shuffle";
    }

    // For convenient on-the-fly shuffling
    public static ShuffleResult shuffleArray(int[] array) {
        FisherYatesShuffler shuffler = new FisherYatesShuffler();
        return shuffler.shuffle(array);

    }

    public ShuffleResult shuffle(int[] passedArray) {
        int[] array = new int[passedArray.length];
        for (int i = 0; i < passedArray.length; i++) {
            array[i] = passedArray[i]; // To get a deep copy
        }

        int numberOfExchanges = 0;
        for (int i = array.length - 1; i > 0; i--)
            {
                int j = (int)(Math.random() * (i + 1));
                if (i != j) { // Only swap if the indices are different to track how many exchanges we made
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    numberOfExchanges++;
                }
            }

        // Here, the list is shuffled
        return new ShuffleResult(array, numberOfExchanges);
    }
}