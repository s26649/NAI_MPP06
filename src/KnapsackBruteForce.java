import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class KnapsackBruteForce {
    private int capacity, numberOfItems;
    private int[] weights, values;

    public KnapsackBruteForce(String filePath) throws FileNotFoundException {
        readData(filePath);
    }

    private void readData(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        capacity = scanner.nextInt();
        numberOfItems = scanner.nextInt();
        weights = new int[numberOfItems];
        values = new int[numberOfItems];

        for (int i = 0; i < numberOfItems; i++) {
            weights[i] = scanner.nextInt();
        }
        for (int i = 0; i < numberOfItems; i++) {
            values[i] = scanner.nextInt();
        }
        scanner.close();
    }

    public void solve() {
        int bestValue = 0, bestWeight = 0, bestCombination = 0;
        int totalCombinations = (1 << numberOfItems); // O(n) = 2^n

        for (int i = 1; i < totalCombinations; i++) {
            int currentWeight = 0;
            int currentValue = 0;

            for (int j = 0; j < numberOfItems; j++) {
                if ((i & (1 << j)) != 0) {
                    currentWeight += weights[j];
                    currentValue += values[j];
                }
            }

            if (currentWeight <= capacity && currentValue > bestValue) {
                bestValue = currentValue;
                bestWeight = currentWeight;
                bestCombination = i;
            }

            System.out.printf("Iteracja: %d, Najlepsza kombinacja: %s, Masa: %d, Wartosc: %d%n",
                    i, formatCombination(bestCombination),bestWeight, bestValue);
        }

        System.out.println("\nNajlepsza kombinacja:");
        System.out.println(formatCombination(bestCombination));
        System.out.printf("Masa: %d, Wartosc: %d%n", bestWeight, bestValue);
    }

    private String formatCombination(int combination) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfItems; i++) {
            sb.append((combination & (1 << i)) != 0 ? "1" : "0");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }

        try {
            KnapsackBruteForce knapsack = new KnapsackBruteForce(args[0]);
            knapsack.solve();
        } catch (FileNotFoundException e) {
            System.err.println("nie znaleziono pliku:" + args[0]);
        }
    }
}
