public class Main {
    public static void main(String[] args) {
        MyDataStructure ds = new MyDataStructure(100);

        System.out.println("Inserting values:");
        System.out.println(ds.insert(10)); // true
        System.out.println(ds.insert(20)); // true
        System.out.println(ds.insert(10)); // false

        System.out.println("Contains:");
        System.out.println(ds.contains(10)); // true
        System.out.println(ds.contains(15)); // false

        System.out.println("Rank:");
        System.out.println(ds.rank(15)); // 1

        System.out.println("Select:");
        System.out.println(ds.select(0)); // 10
        System.out.println(ds.select(1)); // 20

        System.out.println("Range:");
        System.out.println(ds.range(10, 20)); // [10, 20]
        System.out.println(ds.range(15, 25)); // null

        System.out.println("Delete:");
        System.out.println(ds.delete(10)); // true
        System.out.println(ds.contains(10)); // false
    }
}




