import java.util.*;
import java.util.Scanner;
import java.util.HashMap;


class Node {
    int freq;
    int minFreq;
    int maxFreq;

    String shape;
    int strength; // 1, 2, or 3 (Strong, medium, weak)
    String bond;

    Node left;
    Node right;

    Node(int minFreq, int maxFreq, String shape, int strength, String bond) {
        this.minFreq = minFreq;
        this.maxFreq = maxFreq;

        this.shape = shape;
        this.strength = strength;
        this.bond = bond;
    }
}

class Tree {

    Node root;
    HashMap<String, String> freqToBond;

    public Tree() {
        freqToBond = new HashMap<>();
    }

    public void insert(int min, int max, String shape, int strength, String bond) {

        if (min != max) {
            // not a single frequency
            root = insertHlpr(root, min, max, shape, strength, bond);
        }
        //insert node into tree as a range
        root = insertHlpr(root, min, min, shape, strength, bond);
        // update hashmap
        String key = min + "-" + max;
        freqToBond.put(key, bond);
    }

    private Node insertHlpr(Node node, int minFreq, int maxFreq, String shape, int strength, String bond) {
        if(node == null) {
            return new Node(minFreq, maxFreq, shape, strength, bond);
        }
        if (minFreq < node.minFreq) {
            node.left = insertHlpr(node.left, minFreq, maxFreq, shape, strength, bond);
        } else {
            node.right = insertHlpr(node.right, minFreq, maxFreq, shape, strength, bond);
        }
        return node;
    }

    public void delete(int freq) {
        root = deleteNode(root, freq);
    }

    private Node deleteNode(Node root, int freq) {
        if (root == null) {
            return root; // Node not found
        }

        // Recursively search for the node to delete
        if (freq < root.freq) {
            root.left = deleteNode(root.left, freq);
        } else if (freq > root.freq) {
            root.right = deleteNode(root.right, freq);
        } else {
            // Node with the given frequency found, handle different cases

            // Case 1: Node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Case 2: Node with two children, find the in-order successor (smallest in the right subtree)
            root.freq = minValue(root.right);

            // Delete the in-order successor
            root.right = deleteNode(root.right, root.freq);
        }

        return root;
    }

    private int minValue(Node node) {
        int minValue = node.freq;
        while (node.left != null) {
            minValue = node.left.freq;
            node = node.left;
        }
        return minValue;
    }


    public String lookup(int freq, String shape, int strength) {

       Node ans = lookupHelper(root, freq, shape, strength);

        if (ans != null) {
            return ans.bond;
        } else {
            return "Bond Not Found";
        }

    }


    private Node lookupHelper(Node node, int freq, String shape, int strength) {
        if (node == null) {
            return null;
        }

        if (freq >= node.minFreq && freq <= node.maxFreq && shape.equalsIgnoreCase(node.shape) && strength == node.strength) {
            return node;
        }
        if (freq < node.freq) {
            return lookupHelper(node.left, freq, shape, strength);
        } else {
            return lookupHelper(node.right, freq, shape, strength);
        }
    }

    public void dumpTree() {
        System.out.println("Contents of tree:");
        inOrderTraversal(root);
    }

    private void inOrderTraversal(Node node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println("Min and Max Frequency respectively: " + node.minFreq + ", " + node.maxFreq + ", Shape: " + node.shape + ", Strength: " + node.strength + ", Bond: " + node.bond);
            inOrderTraversal(node.right);
        }
    }
}
class Main {
    public static void main (String[] args) {

        Tree tree = new Tree();


        // Insert some sample data
        tree.insert(3584, 3700,"narrow", 2, "O-H Stretching (Alcohol)");
        tree.insert(3200, 3550, "broad", 3, "O-H Stretching (Alcohol)");
        tree.insert(3500, 3500, "narrow", 2, "N-H Stretching (Primary Amine)");
        // Perform lookups

        tree.dumpTree();
//        Scanner sc = new Scanner(System.in);
//
//        System.out.println("\nHello, this program is intended to find the corresponding bond given the strength, shape, and the frequency of the specific IR spectrum peak\n");
//
//        System.out.println("Enter the estimated frequency of your peak without units: ");
//            int inputFreq = sc.nextInt();
//
//        System.out.println("Now enter the strength of your peak on a scale from 1-3 (Weak[1], Medium[2], Strong[3]): ");
//            int inputStrength = sc.nextInt();
//
//        System.out.println("Finally, what's the shape of your peak? Is it narrow or broad: ");
//            String inputShape = sc.next();
//
//        String bond = tree.lookup(inputFreq, inputShape, inputStrength);
//        System.out.println("\nThe bond that you are looking at right now is a " + bond + " bond");
    }

}
