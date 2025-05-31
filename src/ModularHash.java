import java.util.Random;

public class ModularHash implements HashFactory<Integer> {
    private Random rand;
    private HashingUtils utils;

    public ModularHash() {
        this.rand = new Random();
        this.utils = new HashingUtils();
    }

    @Override
    public HashFunctor<Integer> pickHash(int k) {
        return new Functor(k);
    }

    public class Functor implements HashFunctor<Integer> {
        final private int a;
        final private int b;
        final private long p;
        final private int m;

        public Functor(int k){
            this.m = 1 << k;
            this.p = utils.genPrime((long) Integer.MAX_VALUE + 1, Long.MAX_VALUE);
            this.a = rand.nextInt(Integer.MAX_VALUE - 1) + 1;
            this.b = rand.nextInt(Integer.MAX_VALUE);
        }

        @Override
        public int hash(Integer key) {
            long axb = (long) a * key + b;
            long modP = HashingUtils.mod(axb, p);
            return (int) (modP % m);
        }

        public int a() {
            return a;
        }

        public int b() {
            return b;
        }

        public long p() {
            return p;
        }

        public int m() {
            return m;
        }
    }
}
