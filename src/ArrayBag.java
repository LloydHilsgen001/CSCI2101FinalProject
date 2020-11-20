
/**
 * A class of bags whose entries are stored in a fixed-size array.
 */


public final class ArrayBag<T> implements BagInterface<T> {
    private final T[] bag;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 25;
    private boolean integrityOk;
    private static final int MAX_CAPACITY = 10000;

    // default constructor.
    public ArrayBag() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructor
     * @param desiredCapacity the capacity of the bag being constructed
     */
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

    /**
     * Adds a new entry
     * @param newEntry Entry to be added.
     * @return whether entry was succesfully added
     */
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

    /**
     * Removes a random entry
     * @return the entry removed
     */
    public T remove() {
        checkIntegrity();
        return removeEntry((int) (Math.random() * numberOfEntries));
        //Returns a random entry in the bag.
    }

    /**
     * Removes the copy of the entry inside the bag
     * @param anEntry the entry to be removed
     * @return if the entry was in the bag.
     */
    public boolean remove(T anEntry) {
        checkIntegrity();
        int index = getIndexOf(anEntry);
        T result = removeEntry(index);
        return anEntry.equals(result);
    }

    /**
     * removes a given entry
     * @param givenIndex index of entry to remove
     * @return then entry removed
     */
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

    /**
     * gets the index of an entry in the bag
     * @param anEntry Entry to be searched for
     * @return the index of the entry, or -1 if not found.
     */
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

    /**
     * replaces an existing entry with a new one
     * @param anEntry the entry to be removed
     * @param newEntry the entry to be added
     * @return the entry removed.
     */
    public T replace(T anEntry, T newEntry) {
        add(newEntry); //Adds the new entry to the end
        return removeEntry(getIndexOf(anEntry));
        //Finds existing version of specified entry in bag, then removes and returns it
    }

    //empties the bag.
    public void clear() {
        checkIntegrity();
        for (int i = 0; i < numberOfEntries; i++)
            bag[i] = null; //sets all items in bag to null
        numberOfEntries = 0; //resets counter
    }

    /**
     * Finds how many times an entry is in the bag
     * @param anEntry The entry to be searched for
     * @return The number of times the entry is in the bag.
     */
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

    /**
     * Returns whether an entry is in the bag.
     * @param anEntry Entry to be checked for if in the bag.
     * @return True if in the bag, false if not.
     */
    public boolean contains(T anEntry) {
        checkIntegrity();
        return getFrequencyOf(anEntry) > 0;
    }

    /**
     * Creates a copy of the array inside the bag and gives it to the user.
     * @return A copy of the array inside.
     */
    public T[] toArray() {
        // This is safe because the new array is empty.
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[numberOfEntries];
        for (int i = 0; i < numberOfEntries; i++) {
            result[i] = bag[i]; //if bag contains entries with nulls in between, those entries may be lost
        } //could be foreach or Arrays.copyof
        return result;
    }

    /**
     * Checks whether the bag is full
     * @return True if full, false if not
     */
    private boolean isArrayFull() { // Assumes that there has not been anything removed/lost from bag
        return numberOfEntries >= bag.length;
    }

    /**
     * Gives the number of entries inside bag
     * @return the number of entries
     */
    public int getCurrentSize() {
        return numberOfEntries;
    }

    /**
     * Checks if bag is empty
     * @return true if empty, false if not.
     */
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    //Checks if bag has been instantiated properly.
    private void checkIntegrity() {
        if (!integrityOk) throw new SecurityException("ArrayBag object is corrupt.");
    }
        
}



