public class IndexableSkipList extends AbstractSkipList {
    final protected double p;	// p is the probability for "success" in the geometric process generating the height of each node.
    public IndexableSkipList(double probability) {
        super();
        this.p = probability;
    }

	@Override
    public void decreaseHeight() {
        while (head.height() >= 0 && head.getNext(head.height()) == tail) {
            head.removeLevel();
            tail.removeLevel();
        }
    }

    @Override
    public SkipListNode find(int key) {
        SkipListNode current = head;
        int level = head.height();

        while (level >= 0) {
            while (current.getNext(level) != tail && current.getNext(level).key() <= key) {
                current = current.getNext(level);
            }
            level--;
        }

        return current;
    }

    @Override
    public int generateHeight() {
        int height = 0;
        while (Math.random() < p) {
            height++;
        }
        return height;
    }

    public int rank(int key) {
        int ans = 0;
        SkipListNode current = head;
        for (int idx = head.height(); idx >= 0; idx--) {
            while (current.getNext(idx).key() < key) {
                current = current.getNext(idx);
                ans += current.getSpan()[idx];
            }
        }
        return ans;
    }

    public int select(int index) {
        if (index < 0) return minimum().key();
        if (index >= size) return maximum().key();

        SkipListNode current = head;
        int pass = 0;
        index++;
        for (int j = head.height(); j >= 0; j--) {
            while ((current.getNext(j).getSpan()[j]) + pass <= index) {
                current = current.getNext(j);
                pass += current.getSpan()[j];
            }
        }
        int ans = current.key();
        return ans;
    }

}
