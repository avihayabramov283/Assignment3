import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class ChainedHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 2;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private List<Element<K,V>>[] table;
    private int size;

    /*
     * You should add additional private fields as needed.
     */

    public ChainedHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ChainedHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.table = new List[this.capacity];
        this.size = 0;

    }

    public V search(K key) {
        int index = hashFunc.hash(key);
        List<Element<K, V>> bucket = table[index];

        if (bucket == null) return null;

        for (Element<K, V> element : bucket) {
            if (element.key().equals(key)) {
                return element.satelliteData();
            }
        }

        return null;
    }

    public void insert(K key, V value) {
        if ((double)(size + 1) / capacity > maxLoadFactor) {
            rehash();
        }

        int index = hashFunc.hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        for (Element<K, V> element : table[index]) {
            if (element.key().equals(key)) {
                element.setSatData(value);
                return;
            }
        }

        table[index].add(new Element<>(key, value));
        size++;
    }

    public boolean delete(K key) {
        int index = hashFunc.hash(key);
        List<Element<K, V>> bucket = table[index];

        if (bucket == null) return false;

        for (Element<K, V> element : bucket) {
            if (element.key().equals(key)) {
                bucket.remove(element);
                size--;
                return true;
            }
        }

        return false;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }

    private void rehash() {
        capacity *= 2;
        hashFunc = hashFactory.pickHash((int)(Math.log(capacity) / Math.log(2)));
        List<Element<K, V>>[] oldTable = table;
        table = new List[capacity];
        size = 0;

        for (List<Element<K, V>> bucket : oldTable) {
            if (bucket != null) {
                for (Element<K, V> element : bucket) {
                    insert(element.key(), element.satelliteData());
                }
            }
        }
    }
}
