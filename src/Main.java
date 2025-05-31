public class Main {
    public static void main(String[] args) {
        System.out.println("=== Measuring Insertions Probing ===");
        double[] probeInsert = HashingExperimentUtils.measureInsertionsProbing();
        printResults(probeInsert);

        System.out.println("=== Measuring Searches Probing ===");
        double[] probeSearch = HashingExperimentUtils.measureSearchesProbing();
        printResults(probeSearch);

        System.out.println("=== Measuring Insertions Chaining ===");
        double[] chainInsert = HashingExperimentUtils.measureInsertionsChaining();
        printResults(chainInsert);

        System.out.println("=== Measuring Searches Chaining ===");
        double[] chainSearch = HashingExperimentUtils.measureSearchesChaining();
        printResults(chainSearch);
    }

    private static void printResults(double[] results) {
        for (int i = 0; i < results.length; i++) {
            System.out.printf("α[%d] = %.2f ns\n", i, results[i]);
        }
    }
}

