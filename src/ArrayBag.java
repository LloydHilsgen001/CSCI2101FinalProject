/**
 * A class of bags whose entries are stored in a fixed-size array.
 */


public final class ArrayBag<T> implements BagInterface<T> {
    private final T[] bag;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 25;
    private boolean integrityOk;
    private static final int MAX_CAPACITY = 10000;

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
        return removeEntry((int)(Math.random()*numberOfEntries));
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
         while(remove(anEntry)); //Loops until it does not remove an entry
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
        for(int i=0; i<numberOfEntries; i++)
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

}