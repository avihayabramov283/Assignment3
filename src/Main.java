public class Main {
    public static void main(String[] args) {
        // ניצור רשימה עם הסתברות 0.5
        IndexableSkipList skipList = new IndexableSkipList(0.5);

        // נכניס כמה איברים
        skipList.insert(10);
        skipList.insert(20);
        skipList.insert(30);
        skipList.insert(40);

        // הדפסה של כל המבנה
        System.out.println("Skip List Structure:");
        System.out.println(skipList);

        // נבדוק את find
        System.out.println("Find 25 (should stop at 20): " + skipList.find(25).key());
        System.out.println("Find 30 (should be 30): " + skipList.find(30).key());

        // נבדוק את generateHeight עם כמה דגימות
        System.out.println("Generated Heights:");
        for (int i = 0; i < 10; i++) {
            System.out.println("Height: " + skipList.generateHeight());
        }

        // נבדוק את calculateExpectedHeight
        double p = 0.5;
        double expected = SkipListUtils.calculateExpectedHeight(p);
        System.out.println("Expected Height with p = " + p + ": " + expected);
    }
}
