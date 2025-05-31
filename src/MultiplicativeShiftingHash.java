import java.util.Random;

public class MultiplicativeShiftingHash implements HashFactory<Long> {
    private HashingUtils utils;
    private Random rand;


    public MultiplicativeShiftingHash() {
        this.utils = new HashingUtils();
        this.rand = new Random();
    }

    @Override
    public HashFunctor<Long> pickHash(int k) {
        return new Functor(k);
    }

    public class Functor implements HashFunctor<Long> {
        final public static long WORD_SIZE = 64;
        final private long a;
        final private long k;
        final private int m;


        public Functor(int k) {
            this.k = k;
            this.m = 1 << k;
            this.a = rand.nextLong(Long.MAX_VALUE - 1) + 2;
        }
        @Override
        public int hash(Long key) {
            return (int) ((a * key) >>> (WORD_SIZE - k));
        }

        public long a() {
            return a;
        }

        public long k() {
            return k;
        }

    }
}
