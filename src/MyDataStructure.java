import java.util.List;
import java.util.ArrayList;

public class MyDataStructure {

    private IndexableSkipList skipList;
    private ProbingHashTable<Long, Pair<Integer, AbstractSkipList.SkipListNode>> hashTable;

    /*
     * You may add any fields that you wish to add.
     * Remember that all the data-structures you use must be YOUR implementations,
     * except for the List and its implementation for the operation Range(low, high).
     */

    /***
     * This function is the Init function described in Part 4.
     *
     * @param N The maximal number of items that may reside in the DS.
     */

    /**
     * Initializes the data structure.
     * Uses IndexableSkipList for ordered operations,
     * and ProbingHashTable for fast existence checks.
     */
    public MyDataStructure(int N) {
        skipList = new IndexableSkipList(0.5);
        int k = (int) (Math.log(N) / Math.log(2)) + 1;
        hashTable = new ProbingHashTable<>(new MultiplicativeShiftingHash(), k, 0.75);
    }

    /*
     * In the following functions,
     * you should REMOVE the place-holder return statements.
     */

    /**
     * Inserts the given value if not already present.
     * Returns true if inserted, false otherwise.
     * Time complexity: Expected Θ(log n)
     */
    public boolean insert(int value) {
        long key = value;
        if (hashTable.search(key) != null) return false;

        AbstractSkipList.SkipListNode node = skipList.insert(value);
        if (node == null) return false;

        hashTable.insert(key, new Pair<>(value, node));
        return true;
    }

    /**
     * Deletes the given value if it exists.
     * Returns true if deleted, false otherwise.
     * Time complexity: Θ(log n) if exists, Θ(1) if not
     */
    public boolean delete(int value) {
        long key = value;
        Pair<Integer, AbstractSkipList.SkipListNode> pair = hashTable.search(key);
        if (pair == null) return false;

        hashTable.delete(key);
        skipList.delete(pair.second());
        return true;
    }

    /**
     * Checks if the given value exists in the structure.
     * Time complexity: Expected Θ(1)
     */
    public boolean contains(int value) {
        return hashTable.search((long) value) != null;
    }

    /**
     * Returns the number of elements less than the given value.
     * Time complexity: Expected Θ(log n)
     */
    public int rank(int value) {
        return skipList.rank(value);
    }

    /**
     * Returns the element at the given index (0-based).
     * Time complexity: Expected Θ(log n)
     */
    public int select(int index) {
        return skipList.select(index);
    }

    /**
     * Returns a list of elements between [low, high] inclusive, in ascending order.
     * If low is not in the structure, returns null.
     * Time complexity: Expected Θ(|L|)
     */
    public List<Integer> range(int low, int high) {
        if (hashTable.search((long) low) == null) return null;

        List<Integer> result = new ArrayList<>();
        AbstractSkipList.SkipListNode curr = skipList.search(low);
        if (curr == null || curr.key() < low) {
            curr = skipList.successor(curr);
        }

        while (curr != null && curr.key() <= high && curr != skipList.tail) {
            result.add(curr.key());
            curr = skipList.successor(curr);
        }

        return result;    }
}
