import java.util.ArrayList;
import java.util.Collections; // can be useful
import java.util.List;

public class HashingExperimentUtils {
    final private static int k = 16;

    public static double[] measureInsertionsProbing() {
        double[] alphas = {0.5, 0.75, 0.875, 0.9375};
        double[] averages = new double[alphas.length];
        int experiments = 30;
        int capacity = 1 << k;

        for (int i = 0; i < alphas.length; i++) {
            double totalTime = 0;
            int insertions = (int)(capacity * alphas[i]);

            for (int exp = 0; exp < experiments; exp++) {
                ProbingHashTable<Long, String> table = new ProbingHashTable<>(new MultiplicativeShiftingHash(), k, alphas[i]);

                long start = System.nanoTime();
                for (int j = 0; j < insertions; j++) {
                    table.insert((long) j, Long.toString(j));
                }
                long end = System.nanoTime();

                totalTime += (end - start) / (double) insertions;
            }

            averages[i] = totalTime / experiments;
        }

        return averages;    }

    public static double[] measureSearchesProbing() {
        double[] alphas = {0.5, 0.75, 0.875, 0.9375};
        double[] averages = new double[alphas.length];
        int experiments = 30;
        int capacity = 1 << k;

        for (int i = 0; i < alphas.length; i++) {
            double totalTime = 0;
            int insertions = (int)(capacity * alphas[i]);

            for (int exp = 0; exp < experiments; exp++) {
                ProbingHashTable<Long, String> table = new ProbingHashTable<>(new MultiplicativeShiftingHash(), k, alphas[i]);
                List<Long> insertedKeys = new ArrayList<>();

                for (long j = 0; j < insertions; j++) {
                    table.insert(j, Long.toString(j));
                    insertedKeys.add(j);
                }

                long start = System.nanoTime();
                for (int j = 0; j < insertions; j++) {
                    if (j % 2 == 0)
                        table.search(insertedKeys.get(j));
                    else
                        table.search(-1L - j);
                }
                long end = System.nanoTime();

                totalTime += (end - start) / (double) insertions;
            }

            averages[i] = totalTime / experiments;
        }

        return averages;
    }

    public static double[] measureInsertionsChaining() {
        double[] alphas = {0.5, 0.75, 1.0, 1.5, 2.0};
        double[] averages = new double[alphas.length];
        int experiments = 30;
        int capacity = 1 << k;

        for (int i = 0; i < alphas.length; i++) {
            double totalTime = 0;
            int insertions = (int)(capacity * alphas[i]);

            for (int exp = 0; exp < experiments; exp++) {
                ChainedHashTable<Integer, String> table =
                        new ChainedHashTable<>(new ModularHash(), k, alphas[i]);

                long start = System.nanoTime();
                for (int j = 0; j < insertions; j++) {
                    table.insert(j, Integer.toString(j));
                }
                long end = System.nanoTime();

                totalTime += (end - start) / (double) insertions;
            }

            averages[i] = totalTime / experiments;
        }

        return averages;
    }

    public static double[] measureSearchesChaining() {
        double[] alphas = {0.5, 0.75, 1.0, 1.5, 2.0};
        double[] averages = new double[alphas.length];
        int experiments = 30;
        int capacity = 1 << k;

        for (int i = 0; i < alphas.length; i++) {
            double totalTime = 0;
            int insertions = (int)(capacity * alphas[i]);

            for (int exp = 0; exp < experiments; exp++) {
                ChainedHashTable<Integer, String> table =
                        new ChainedHashTable<>(new ModularHash(), k, alphas[i]);

                for (int j = 0; j < insertions; j++) {
                    table.insert(j, Integer.toString(j));
                }

                long start = System.nanoTime();
                for (int j = 0; j < insertions; j++) {
                    if (j % 2 == 0)
                        table.search(j);
                    else
                        table.search(j + insertions);
                }
                long end = System.nanoTime();

                totalTime += (end - start) / (double) insertions;
            }

            averages[i] = totalTime / experiments;
        }

        return averages;
    }
}
