public class Main {
    public static void main(String[] args) {
        System.out.println("=== Testing ProbingHashTable ===");

        // יוצרים טבלת גיבוב עם capacity = 4 ו-load factor = 0.75
        HashFactory<Integer> factory = new ModularHash();
        ProbingHashTable<Integer, String> table = new ProbingHashTable<>(factory, 2, 0.75); // m = 4

        // הכנסת ערכים
        table.insert(1, "one");
        table.insert(2, "two");
        table.insert(3, "three");

        // חיפוש
        System.out.println("Search key 2: " + table.search(2)); // "two"
        System.out.println("Search key 5 (not found): " + table.search(5)); // null

        // עדכון ערך קיים
        table.insert(2, "TWO");
        System.out.println("Search updated key 2: " + table.search(2)); // "TWO"

        // מחיקה
        boolean deleted = table.delete(3);
        System.out.println("Deleted key 3: " + deleted); // true
        System.out.println("Search key 3 after delete: " + table.search(3)); // null

        // הכנסת עוד איברים שיגרמו ל-rehash
        table.insert(4, "four");
        table.insert(5, "five");

        System.out.println("After rehash, capacity = " + table.capacity());
        System.out.println("Search key 5: " + table.search(5)); // "five"
        System.out.println("Search key 1: " + table.search(1)); // "one"

        System.out.println("=== Done Testing ProbingHashTable ===");
    }
}
