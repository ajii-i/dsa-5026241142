import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class BankTransactionProcessing {
    public static void main(String[] args) throws FileNotFoundException {
        LinkedList<String[]> transactions = new LinkedList<>();
        LinkedList<String[]> customers = new LinkedList<>();
        Queue<String[]> transactionQueue = new LinkedList<>();
        Stack<String[]> failedWithdrawals = new Stack<>();

        try (Scanner input = new Scanner(new File("transactions.txt"))) {
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] transaction = line.split("\\s+");
                transactions.add(transaction);

                boolean customerExists = false;
                for (String[] customer : customers) {
                    if (customer[0].equals(transaction[0])) {
                        customerExists = true;
                        break;
                    }
                }
                if (!customerExists) {
                    customers.add(new String[] {transaction[0], "0"});
                }
            }
        }

        while (!transactions.isEmpty()) {
            transactionQueue.offer(transactions.removeFirst());
        }

        while (!transactionQueue.isEmpty()) {
            String[] transaction = transactionQueue.poll();
            String name = transaction[0];
            String type = transaction[1];
            int amount = Integer.parseInt(transaction[2]);

            for (String[] customer : customers) {
                if (!customer[0].equals(name)) {
                    continue;
                }

                int balance = Integer.parseInt(customer[1]);
                if (type.equals("DEPOSIT")) {
                    customer[1] = String.valueOf(balance + amount);
                } else if (type.equals("WITHDRAW")) {
                    if (amount > balance) {
                        failedWithdrawals.push(transaction);
                    } else {
                        customer[1] = String.valueOf(balance - amount);
                    }
                }
                break;
            }
        }

        System.out.println("=== Final Balances ===");
        for (String[] customer : customers) {
            System.out.println(customer[0] + " : " + customer[1]);
        }

        System.out.println();
        System.out.println("=== Failed Transactions ===");
        while (!failedWithdrawals.isEmpty()) {
            String[] transaction = failedWithdrawals.pop();
            System.out.println(transaction[0] + " " + transaction[1] + " " + transaction[2]);
        }
    }
}
