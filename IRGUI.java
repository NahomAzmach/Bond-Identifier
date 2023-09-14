import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class IRGUI extends JFrame {

    private JTextField freqField;
    private JTextField shapeField;
    private JTextField strengthField;
    private JLabel output;

    public IRGUI() {
        Tree tree = new Tree();


        // Insert some sample data
        tree.insert(3584, 3700,"narrow", 2, "O-H Stretching (Alcohol)");
        tree.insert(3200, 3550, "broad", 3, "O-H Stretching (Alcohol)");
        tree.insert(3500, 3500, "narrow", 2, "N-H Stretching (Primary Amine)");
        tree.insert(3300, 3400, "narrow", 2, "N-H Stretching (Aliphatic primary Amine)");
        tree.insert(3310, 3350, "narrow", 2, "N-H Stretching (Secondary Amine)");
        tree.insert(2500, 3300, "broad", 3, "O-H stretching (Carbroxylic Acid)");
        tree.insert(2700, 3200, "broad", 1, "O-H Stretching (Alcohol)");
        tree.insert(2800, 3000, "broad", 3, "N-H stretching (Amine Salt)");
        tree.insert(3267, 3333, "narrow", 3, "C-H Stretching (Alkyne)");
        tree.insert(3000, 3100, "narrow", 2, "C-H Stretching (Alkene)");
        tree.insert(2840, 3000, "narrow", 2, "C-H Stretching (Alkane)");
        tree.insert(2695, 2830, "narrow", 2, "C-H Stretching (Aldehyde)");
        tree.insert(2350, 2350, "narrow", 3, "Carbon Dioxide");
        tree.insert(2250, 2275, "broad", 3, "N=C=O Stretching (Isocyanate)");
        tree.insert(2222, 2260, "narrow", 1, "C Ξ N Stretching (Nitrile)");
        tree.insert(2190, 2260, "narrow", 1, "C Ξ C Stretching (Alkyne)");
        tree.insert(2140, 2175, "narrow", 3, "S - C Ξ N Stretching (Thiocyanate)");
        tree.insert(2120, 2160, "narrow", 3, "N=N=N Stretching (Azide)");
        tree.insert(2150, 2150, "narrow", 2, "C=C=O Stretching ");




        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(4, 2, 10, 20));


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Bond Frequency Identifier");
        // Call pack to set the window size to preferred size
        pack();

        // Center the window on the screen
        setLocationRelativeTo(null);

        JLabel freqLabel = new JLabel("Frequency of the observed peak (without units):");
        freqField = new JTextField();
        JLabel strengthLabel = new JLabel("Strength of observed peak (Weak[1], Medium[2], Strong[3]):");
        strengthField = new JTextField();
        JLabel shapeLabel = new JLabel("Shape of peak (narrow/broad):");
        shapeField = new JTextField();
        JButton findButton = new JButton("Find Bond");
        output = new JLabel();


        panel.add(freqLabel);
        panel.add(freqField);
        panel.add(strengthLabel);
        panel.add(strengthField);
        panel.add(shapeLabel);
        panel.add(shapeField);
        panel.add(findButton, CENTER_ALIGNMENT);
        panel.add(output);

        add(panel);

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int inputFreq = Integer.parseInt(freqField.getText());
                    int inputStrength = Integer.parseInt(strengthField.getText());
                    String inputShape = shapeField.getText().toLowerCase();

                    String bond = tree.lookup(inputFreq, inputShape, inputStrength);
                    output.setText("The bond is: " + bond);
                } catch (NumberFormatException exception) {
                    output.setText("Not a valid input. Please enter valid values.");
                }
            }
        });
    }
    public static void main (String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new IRGUI().setVisible(true);
            }
        });
//
//
//        Scanner sc = new Scanner(System.in);
//
//        System.out.println("\nHello, this program is intended to find the corresponding bond given the strength, shape, and the frequency of the specific IR spectrum peak\n");
//
//        System.out.println("Enter the estimated frequency of your peak without units: ");
//        int inputFreq = sc.nextInt();
//
//        System.out.println("Now enter the strength of your peak on a scale from 1-3 (Weak[1], Medium[2], Strong[3]): ");
//        int inputStrength = sc.nextInt();
//
//        System.out.println("Finally, what's the shape of your peak? Is it narrow or broad: ");
//        String inputShape = sc.next();
//
//        String bond = tree.lookup(inputFreq, inputShape, inputStrength);
//        System.out.println("\nBond you are looking for: " + bond);

    }

}
// freqKey class represents frequency range
class freqKey {
    int minFreq;
    int maxFreq;

    freqKey(int min, int max){
        this.minFreq = min;
        this.maxFreq = max;
    }
}

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
    HashMap<freqKey, String> freqToBond;

    public Tree() {
        freqToBond = new HashMap<>();
    }

    // inserts nodes for range, adds to map
    public void insert(int min, int max, String shape, int strength, String bond) {


        //inserts node into tree as a range
        root = insertHlpr(root, min, max, shape, strength, bond);
        // creat freqKey object as map key
        freqKey key = new freqKey(min, max);
        freqToBond.put(key, bond);
    }

    private Node insertHlpr(Node node, int minFreq, int maxFreq, String shape, int strength, String bond) {

        if(node == null) {
            return new Node(minFreq, maxFreq, shape, strength, bond);
        }

        // checks if ranges overlap, resorts to order by strength if it overlaps.
        if (maxFreq >= node.minFreq && minFreq <= node.maxFreq) {
            // Compare strengths
            if (strength > node.strength) {
                // New node is stronger, insert it on the left
                node.left = insertHlpr(node.left, minFreq, maxFreq, shape, strength, bond);
            } else {
                // New node is weaker or equal, insert it on the right
                node.right = insertHlpr(node.right, minFreq, maxFreq, shape, strength, bond);
            }
        } else if(maxFreq < node.minFreq) {
            node.left = insertHlpr(node.left, minFreq, maxFreq, shape, strength, bond);
        }
        else if(minFreq > node.maxFreq) {
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
        if (freq < root.minFreq) {
            root.left = deleteNode(root.left, freq);
        } else if (freq > root.maxFreq) {
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
        int minValue = node.minFreq;
        while (node.left != null) {
            minValue = node.left.minFreq;
            node = node.left;
        }
        return minValue;
    }


    public String lookup(int freq, String shape, int strength) {

        List<freqKey> keys = new ArrayList<>(freqToBond.keySet());

        // check frequency map first to see if in range of key
        for (int i = 0; i < keys.size(); i++) {
            freqKey key = keys.get(i);
            if(key.minFreq <= freq && freq <= key.maxFreq){
                // if so, get bond and return
                return freqToBond.get(key);
            }
        }

        // Use lookupHelper for more specific search
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

        /* if the target frequency (freq) is less than the current node's
        minimum frequency, it searches in the left subtree . */
        if (freq < node.minFreq) {
            return lookupHelper(node.left, freq, shape, strength);
        } else if (freq > node.maxFreq) { // check right subtree recursively
            return lookupHelper(node.right, freq, shape, strength);
        }

        return null;
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
