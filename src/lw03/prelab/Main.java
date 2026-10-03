import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        processPlaylist();
        System.out.println();
        processParticipants();
        System.out.println();
        processInventory();
    }

    private static void processPlaylist() throws FileNotFoundException {
        List<String> playlist = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File("playlist.txt"), "UTF-8")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] operation = line.split("\\s+", 2);
                String type = operation[0];

                switch (type) {
                    case "ADD":
                        playlist.add(operation[1]);
                        break;
                    case "INSERT":
                        String[] insertion = operation[1].split("\\s+", 2);
                        int index = Integer.parseInt(insertion[0]);
                        playlist.add(index, insertion[1]);
                        break;
                    case "REMOVE":
                        playlist.remove(operation[1]);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown playlist operation: " + type);
                }
            }
        }

        System.out.println("===== Problem 1 =====");
        System.out.println("Total songs: " + playlist.size());
        for (int i = 0; i < playlist.size(); i++) {
            System.out.println(i + ": " + playlist.get(i));
        }
    }

    private static void processParticipants() throws FileNotFoundException {
        Set<String> participants = new LinkedHashSet<>();
        int duplicateRegistrations = 0;

        try (Scanner scanner = new Scanner(new File("participants.txt"), "UTF-8")) {
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().trim();
                if (name.isEmpty()) {
                    continue;
                }

                if (!participants.add(name)) {
                    duplicateRegistrations++;
                }
            }
        }

        System.out.println("===== Problem 2 =====");
        System.out.println("Unique participants: " + participants.size());
        int number = 1;
        for (String name : participants) {
            System.out.println(number + ". " + name);
            number++;
        }
        System.out.println("Duplicate registrations: " + duplicateRegistrations);
    }

    private static void processInventory() throws FileNotFoundException {
        Map<String, Integer> inventory = new LinkedHashMap<>();
        Set<String> productOrder = new LinkedHashSet<>();
        int failedSales = 0;

        try (Scanner scanner = new Scanner(new File("inventory.txt"), "UTF-8")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] operation = line.split("\\s+");
                String type = operation[0];
                String product = operation[1];
                int quantity = Integer.parseInt(operation[2]);

                productOrder.add(product);

                switch (type) {
                    case "ADD":
                        int currentStock = inventory.getOrDefault(product, 0);
                        inventory.put(product, currentStock + quantity);
                        break;
                    case "SELL":
                        if (inventory.containsKey(product)
                                && inventory.get(product) >= quantity) {
                            inventory.put(product, inventory.get(product) - quantity);
                        } else {
                            failedSales++;
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown inventory operation: " + type);
                }
            }
        }

        System.out.println("===== Problem 3 =====");
        for (String product : productOrder) {
            if (inventory.containsKey(product)) {
                System.out.println(product + ": " + inventory.get(product));
            }
        }
        System.out.println("Failed sales: " + failedSales);
    }
}
