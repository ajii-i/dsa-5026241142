import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SortBot {
    public static void main(String[] args) throws FileNotFoundException {
        int[] parcels;
        int[] targets;
        try (Scanner scanner = new Scanner(new File("parcels.txt"))) {
            parcels = readArray(scanner);
            targets = readArray(scanner);
        }

        bubbleSortPasses(parcels, 0, 2);
        int[] afterTwoPasses = parcels.clone();
        bubbleSortPasses(parcels, 2, parcels.length - 1);

        System.out.println("==== After 2 Passes ====");
        printArray(afterTwoPasses);
        System.out.println();
        System.out.println("==== Fully Sorted ====");
        printArray(parcels);
        System.out.println();
        System.out.println("==== Search Results ====");
        for (int target : targets) {
            printSearchResult(parcels, target);
        }
    }

    private static int[] readArray(Scanner scanner) {
        int[] values = new int[scanner.nextInt()];
        for (int i = 0; i < values.length; i++) {
            values[i] = scanner.nextInt();
        }
        return values;
    }

    private static void bubbleSortPasses(int[] values, int startPass, int endPass) {
        for (int pass = startPass; pass < endPass; pass++) {
            for (int j = 0; j < values.length - 1 - pass; j++) {
                if (values[j] < values[j + 1]) {
                    int temp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = temp;
                }
            }
        }
    }

    private static void printArray(int[] values) {
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(values[i]);
        }
        System.out.println();
    }

    private static void printSearchResult(int[] values, int target) {
        int low = 0;
        int high = values.length - 1;
        int steps = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            steps++;
            if (values[mid] == target) {
                System.out.printf("%d: FOUND at index %d (steps: %d)%n", target, mid, steps);
                return;
            }
            if (target > values[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        System.out.printf("%d: NOT FOUND (steps: %d)%n", target, steps);
    }
}
