import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class ProbingHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private Element<K,V>[] table;
    private int size;
    private int deletedCount;


    /*
     * You should add additional private fields as needed.
     */

    public ProbingHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.table = new Element[capacity];
        this.size = 0;
        this.deletedCount = 0;

    }
	
	public ProbingHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public V search(K key) {
        int index = hashFunc.hash(key);

        for (int i = 0; i < capacity; i++) {
            int probe = (index + i) % capacity;
            Element<K, V> elem = table[probe];
            if (elem == null) return null;
            if (!elem.isDeleted() && elem.key().equals(key)) {
                return elem.satelliteData();
            }
        }

        return null;
    }

    public void insert(K key, V value) {
        if ((double)(size + deletedCount + 1) / capacity > maxLoadFactor) {
            rehash();
        }

        int index = hashFunc.hash(key);
        int firstDeleted = -1;

        for (int i = 0; i < capacity; i++) {
            int probe = (index + i) % capacity;
            Element<K, V> elem = table[probe];

            if (elem == null) {
                if (firstDeleted != -1) {
                    table[firstDeleted] = new Element<>(key, value);
                } else {
                    table[probe] = new Element<>(key, value);
                }
                size++;
                return;
            }

            if (elem.isDeleted()) {
                if (firstDeleted == -1) firstDeleted = probe;
            } else if (elem.key().equals(key)) {
                elem.setSatData(value); // Update
                return;
            }
        }
    }

    public boolean delete(K key) {
        int index = hashFunc.hash(key);

        for (int i = 0; i < capacity; i++) {
            int probe = (index + i) % capacity;
            Element<K, V> elem = table[probe];

            if (elem == null) return false;
            if (!elem.isDeleted() && elem.key().equals(key)) {
                elem.setDeleted(true);
                size--;
                deletedCount++;
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
        Element<K, V>[] oldTable = table;
        capacity *= 2;
        hashFunc = hashFactory.pickHash((int)(Math.log(capacity) / Math.log(2)));
        table = new Element[capacity];
        size = 0;
        deletedCount = 0;

        for (Element<K, V> elem : oldTable) {
            if (elem != null && !elem.isDeleted()) {
                insert(elem.key(), elem.satelliteData());
            }
        }
    }
}
