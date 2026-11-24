package lw06.unguided;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        int target;

        try (Scanner read = new Scanner(Main.class.getResourceAsStream("treenumbers.txt"));
             Scanner numbers = new Scanner(read.nextLine())) {
            while (numbers.hasNextInt()) {
                tree.insert(numbers.nextInt());
            }
            target = read.nextInt();
        }

        System.out.print("Preorder : ");
        tree.preOrder();
        System.out.print("Inorder : ");
        tree.inOrder();
        System.out.print("Postorder : ");
        tree.postOrder();
        System.out.println("Search " + target + " : " + tree.search(target));
        System.out.println("Nodes : " + tree.countNodes());
        System.out.println("Leaves : " + tree.countLeaves());
    }
}
