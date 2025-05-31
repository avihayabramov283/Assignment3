public class Main {
    public static void main(String[] args) {
        System.out.println("=== Testing ModularHash (int → [m]) ===");
        HashFactory<Integer> intFactory = new ModularHash();
        HashFunctor<Integer> intHasher = intFactory.pickHash(4); // m = 16

        System.out.println("Hash values for integers 0–9:");
        for (int i = 0; i < 10; i++) {
            int h = intHasher.hash(i);
            System.out.println("hash(" + i + ") = " + h);
        }

        ModularHash.Functor intFunctor = (ModularHash.Functor) intHasher;
        System.out.println("Params: a = " + intFunctor.a() + ", b = " + intFunctor.b() +
                ", p = " + intFunctor.p() + ", m = " + intFunctor.m());

        System.out.println();
        System.out.println("=== Testing MultiplicativeShiftingHash (long → [m]) ===");
        HashFactory<Long> longFactory = new MultiplicativeShiftingHash();
        HashFunctor<Long> longHasher = longFactory.pickHash(5); // m = 32

        System.out.println("Hash values for longs 0–9:");
        for (long i = 0; i < 10; i++) {
            int h = longHasher.hash(i);
            System.out.println("hash(" + i + ") = " + h);
        }

        MultiplicativeShiftingHash.Functor longFunctor = (MultiplicativeShiftingHash.Functor) longHasher;
        System.out.println("Params: a = " + longFunctor.a() + ", k = " + longFunctor.k());
    }
}

