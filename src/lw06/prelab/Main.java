package lw06.prelab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.StringJoiner;

public class Main {
    static class Node {
        final int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
        }
    }

    static Node insert(Node root, int data) {
        if (root == null) {
            return new Node(data);
        }

        if (data < root.data) {
            root.left = insert(root.left, data);
        } else if (data > root.data) {
            root.right = insert(root.right, data);
        }

        return root;
    }

    static boolean search(Node root, int target) {
        if (root == null) {
            return false;
        }
        if (target == root.data) {
            return true;
        }
        if (target < root.data) {
            return search(root.left, target);
        }
        return search(root.right, target);
    }

    static void preorder(Node root, StringJoiner result) {
        if (root == null) {
            return;
        }

        result.add(Integer.toString(root.data));
        preorder(root.left, result);
        preorder(root.right, result);
    }

    static void inorder(Node root, StringJoiner result) {
        if (root == null) {
            return;
        }

        inorder(root.left, result);
        result.add(Integer.toString(root.data));
        inorder(root.right, result);
    }

    static void postorder(Node root, StringJoiner result) {
        if (root == null) {
            return;
        }

        postorder(root.left, result);
        postorder(root.right, result);
        result.add(Integer.toString(root.data));
    }

    public static void main(String[] args) throws IOException {
        Path inputPath = args.length == 0 ? Path.of("treenumbers.txt") : Path.of(args[0]);
        Node root = null;
        int target;

        try (Scanner input = new Scanner(Files.newBufferedReader(inputPath));
             Scanner scores = new Scanner(input.nextLine())) {
            while (scores.hasNext()) {
                root = insert(root, scores.nextInt());
            }
            target = input.nextInt();
        }

        StringJoiner preorderResult = new StringJoiner(" ");
        StringJoiner inorderResult = new StringJoiner(" ");
        StringJoiner postorderResult = new StringJoiner(" ");
        preorder(root, preorderResult);
        inorder(root, inorderResult);
        postorder(root, postorderResult);

        System.out.println("Preorder : " + preorderResult);
        System.out.println("Inorder   : " + inorderResult);
        System.out.println("Postorder : " + postorderResult);
        System.out.println("Search " + target + " : " + search(root, target));
    }
}
