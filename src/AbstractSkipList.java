import java.util.Arrays;
import java.util.NoSuchElementException;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractSkipList {
    final protected SkipListNode head;
    final protected SkipListNode tail;
    protected int size;


    public AbstractSkipList() {
        head = new SkipListNode(Integer.MIN_VALUE);
        tail = new SkipListNode(Integer.MAX_VALUE);
        this.size = 0;
        increaseHeight();
    }

    public void increaseHeight() {
        head.addLevel(tail, null);
        tail.addLevel(null, head);

        head.span = new int[head.height + 1];
        for (int i = 0; i < head.span.length; i++) {
            head.span[i] = 0;
        }

        if (tail.span == null) {
            tail.span = new int[tail.height + 1];
            for (int i = 0; i < tail.span.length; i++) {
                tail.span[i] = 1;
            }
        } else {
            int oldLength = tail.span.length;
            int[] oldSpan = tail.span;
            tail.span = new int[head.height + 1];

            for (int i = 0; i < oldLength; i++) {
                tail.span[i] = oldSpan[i];
            }

            tail.span[tail.span.length - 1] = size + 1;
        }
    }
	
    abstract void decreaseHeight();

    abstract SkipListNode find(int key);

    abstract int generateHeight();

    public SkipListNode search(int key) {
        SkipListNode curr = find(key);

        return curr.key() == key ? curr : null;
    }

    public SkipListNode insert(int key) {
        int nodeHeight = generateHeight();

        while (nodeHeight > head.height()) {
            increaseHeight();
        }

        SkipListNode prev = find(key);
        if (prev.key() == key) {
            return null;
        }

        SkipListNode newNode = new SkipListNode(key);
        newNode.span = new int[nodeHeight + 1];
        int totalSteps = 1;

        for (int lvl = 0; lvl <= nodeHeight && prev != null; lvl++) {
            SkipListNode next = prev.getNext(lvl);

            newNode.addLevel(next, prev);
            prev.setNext(lvl, newNode);
            next.setPrev(lvl, newNode);

            newNode.span[lvl] = totalSteps;

            while (prev != null && prev.height() == lvl) {
                totalSteps += prev.span[lvl];
                prev = prev.getPrev(lvl);
            }
        }

        for (int lvl = 0; lvl <= newNode.height; lvl++) {
            SkipListNode next = newNode.getNext(lvl);
            next.span[lvl] = next.span[lvl] - newNode.span[lvl] + 1;
        }

        int lvl = nodeHeight;
        SkipListNode current = newNode.getNext(newNode.height);
        while (current != null) {
            while (current.height > lvl) {
                current.span[lvl + 1]++;
                lvl++;
            }
            current = current.getNext(lvl);
        }

        size++;
        return newNode;
    }

    public boolean delete(SkipListNode skipListNode) {
        for (int i = 0; i <= skipListNode.height(); i++) {
            SkipListNode before = skipListNode.getPrev(i);
            SkipListNode after = skipListNode.getNext(i);

            before.setNext(i, after);
            after.setPrev(i, before);
        }


        for (int i = 0; i <= skipListNode.height; i++) {
            SkipListNode after = skipListNode.getNext(i);
            after.span[i] = after.span[i] + skipListNode.span[i] - 1;
        }

        int level = skipListNode.height;
        SkipListNode walker = skipListNode.getNext(level);
        while (walker != null) {
            while (walker.height > level) {
                walker.span[level + 1]--;
                level++;
            }
            walker = walker.getNext(level);
        }

        size--;
        return true;
    }

    public SkipListNode predecessor(SkipListNode skipListNode) {
        return skipListNode.getPrev(0);
    }

    public SkipListNode successor(SkipListNode skipListNode) {
        return skipListNode.getNext(0);
    }

    public SkipListNode minimum() {
        if (head.getNext(0) == tail) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return head.getNext(0);
    }

    public SkipListNode maximum() {
        if (tail.getPrev(0) == head) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return tail.getPrev(0);
    }

    private void levelToString(StringBuilder s, int level) {
        s.append("H    ");
        SkipListNode curr = head.getNext(0);

        while (curr != tail) {
            if (curr.height >= level) {
                s.append(curr.key());
                s.append("    ");
            }
            else {
            	s.append("    ");
            	for (int i = 0; i < curr.key().toString().length(); i = i + 1)
            		s.append(" ");
            }

            curr = curr.getNext(0);
        }

        s.append("T\n");
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int level = head.height(); level >= 0; --level) {
            levelToString(str, level);
        }

        return str.toString();
    }

    public static class SkipListNode extends Element<Integer, Object> {
        final private List<SkipListNode> next;
        final private List<SkipListNode> prev;
        private int height;
        private int[] span;

        public SkipListNode(int key) {
        	super(key);
            next = new ArrayList<>();
            prev = new ArrayList<>();
            this.height = -1;

        }

        public SkipListNode getPrev(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return prev.get(level);
        }

        public SkipListNode getNext(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return next.get(level);
        }

        public void setNext(int level, SkipListNode next) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.next.set(level, next);
        }

        public void setPrev(int level, SkipListNode prev) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.prev.set(level, prev);
        }

        public void addLevel(SkipListNode next, SkipListNode prev) {
            ++height;
            this.next.add(next);
            this.prev.add(prev);
        }
		
		public void removeLevel() {           
            this.next.remove(height);
            this.prev.remove(height);
            --height;
        }

        public int height() { return height; }

        public int[] getSpan(){
            return span;
        }


    }
}
