import java.util.Scanner;

/**
 * A class of bags whose entries are stored in a fixed-size array.
 */


public final class ArrayBag<T> implements BagInterface<T> {
    private final T[] bag;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 25;
    private boolean integrityOk;
    private static final int MAX_CAPACITY = 10000;
    public int numberOfLives = 3;
    public boolean gameOver;
    private int numberOfPoints;
    public String gameOverMessage = ("Game Over! Score:" + numberOfPoints);
    //  BagInterface<String> aBag = new ArrayBag<>();

    public String[] nonSeenWords = {"Sans", "Boogie", "Hatred", "Bull", "Bingo", "Bullseye", "Beige", "Hitch",
            "Why", "No"};

    public ArrayBag() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayBag(int desiredCapacity) {
        integrityOk = false;
        if (desiredCapacity <= MAX_CAPACITY) {
            // This is safe because the new array is empty.
            @SuppressWarnings("unchecked")
            T[] tempBag = (T[]) new Object[desiredCapacity];
            bag = tempBag;
            numberOfEntries = 0;
            integrityOk = true;
        } else
            throw new IllegalStateException("Attempt to create a bag whose capacity exceeds allowed maximum.");
    }

    public boolean add(T newEntry) {
        checkIntegrity();
        if (isArrayFull()) {
            return false; //return instantly exits the method
        } else {
            bag[numberOfEntries] = newEntry;
            numberOfEntries++;
        }
        return true;
    }

    public T remove() {
        checkIntegrity();
        return removeEntry((int) (Math.random() * numberOfEntries));
        //Returns a random entry in the bag.
    }

    public boolean remove(T anEntry) {
        checkIntegrity();
        int index = getIndexOf(anEntry);
        T result = removeEntry(index);
        return anEntry.equals(result);
    }

    //private to make sure that integrity is checked and verify that the correct entry is removed
    private T removeEntry(int givenIndex) {
        T result = null;
        if (!isEmpty() && (givenIndex >= 0)) {
            result = bag[givenIndex]; // Entry to remove
            bag[givenIndex] = bag[numberOfEntries - 1]; // Replace it with last entry
            bag[numberOfEntries - 1] = null; // Remove last entry
            numberOfEntries--;
        }
        return result;
    }

    public void removeEvery(T anEntry) {
        while (remove(anEntry)) ; //Loops until it does not remove an entry
    }

    private int getIndexOf(T anEntry) { //private so that the index of entries cannot be directly accessed by clients
        int where = -1;
        boolean found = false;
        int index = 0;
        while (!found && (index < numberOfEntries)) {
            if (anEntry.equals(bag[index])) {
                found = true;
                where = index;
            } else
                index++;
        }
        return where;
    }

    public T replace(T anEntry, T newEntry) {
        add(newEntry); //Adds the new entry to the end
        return removeEntry(getIndexOf(anEntry));
        //Finds existing version of specified entry in bag, then removes and returns it
    }

    public void clear() {
        checkIntegrity();
        for (int i = 0; i < numberOfEntries; i++)
            bag[i] = null; //sets all items in bag to null
        numberOfEntries = 0; //resets counter
    }

    public int getFrequencyOf(T anEntry) {
        checkIntegrity();
        int counter = 0;
        for (int index = 0; index < numberOfEntries; index++) {
            if (anEntry.equals(bag[index])) {
                counter++;
            }
        }
        return counter;
    }

    public boolean contains(T anEntry) {
        checkIntegrity();
        return getFrequencyOf(anEntry) > 0;
    }

    public T[] toArray() {
        // This is safe because the new array is empty.
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[numberOfEntries];
        for (int i = 0; i < numberOfEntries; i++) {
            result[i] = bag[i]; //if bag contains entries with nulls in between, those entries may be lost
        } //could be foreach or Arrays.copyof
        return result;
    }
//----------------------------------------------------------------------------------------------------------------------

    private boolean isArrayFull() { // Assumes that there has not been anything removed/lost from bag
        return numberOfEntries >= bag.length;
    }

    public int getCurrentSize() {
        return numberOfEntries;
    }

    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    private void checkIntegrity() {
        if (integrityOk) throw new SecurityException("ArrayBag object is corrupt.");
    }


    // attempts at methods for game
//----------------------------------------------------------------------------------------------------------------------

    /**
     * determines if word has already been added to bag
     *
     * @param anEntry
     * @return boolean
     */
    public boolean seenBefore(T anEntry) {
        checkIntegrity();
        if (getFrequencyOf(anEntry) > 0) { // if number of times word is in array bag is more than 0
            return true;
            //deductLife(numberOfLives); // decides how many lives are left
        }
        System.out.println("Correct!");
        return false;

    }// end seenBefore
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Decides whether to deduct one life or set game to over
     *
     * @param numberOfLives
     * @return number of lives, boolean */
    public int deductLife(int numberOfLives) {
        if (numberOfLives == 0) {
            gameOver = true; // if lives at 0, set gameOver to true and end game
        }
        numberOfLives = numberOfLives - 1; // if 1+ lives, deduct one life and continue on
        System.out.println("Wrong!" + numberOfLives + " lives left"); // displays number of lives left

        return numberOfLives; // returns number of lives left.
    } // end deductLife


    public boolean isGameOver(boolean gameOver) {
        return gameOver;
    }

//----------------------------------------------------------------------------------------------------------------------

    // adds a random word from list of unseen words to bag
    public void addToSeen() {
        BagInterface<String> aBag = new ArrayBag<>();
        String anEntry = nonSeenWords[(int) (Math.random() * nonSeenWords.length)];
        aBag.add(anEntry);
        System.out.print(anEntry);

    } // end addToSeen

// running game (not finished)
    public T game(T anEntry) {
        while (gameOver == false) {
            Scanner answer = new Scanner(System.in);
            String word;
            // enter SEEN or NEW
            System.out.println(anEntry);
            word = answer.next();
            if (seenBefore((T) word)) { // if this has been seen before, deduct a life or end game
                deductLife(numberOfLives); }




        }
    return null;

    }// end game
}



