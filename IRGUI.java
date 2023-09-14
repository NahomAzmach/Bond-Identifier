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
    private JTextArea output;

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
        tree.insert(2150, 2150, "narrow", 2, "C=C=O Stretching (Ketene)");
        tree.insert(2120, 2145, "narrow", 3, "N=C=N stretching (Carbodiimide)");
        tree.insert(2100, 2140, "narrow", 1, "CΞC stretching (Monosubstituted alkyne)");
        tree.insert(1900, 2140, "narrow", 3, "N=C=S stretching (Isothiocyanate)");
        tree.insert(1900, 2000, "narrow", 2, "C=C=C stretching (Allene)");




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
        JLabel shapeLabel = new JLabel("Shape of peak(narrow/broad) [If you have to guess, guess narrow]:");
        shapeField = new JTextField();
        JButton findButton = new JButton("Find Bond");
        output = new JTextArea();
        output.setEditable(false); //read only
        JScrollPane scroll = new JScrollPane(output); // Add a scroll pane



        panel.add(freqLabel);
        panel.add(freqField);
        panel.add(strengthLabel);
        panel.add(strengthField);
        panel.add(shapeLabel);
        panel.add(shapeField);
        panel.add(findButton, CENTER_ALIGNMENT);
        panel.add(scroll);

        add(panel);

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int inputFreq = Integer.parseInt(freqField.getText());
                    int inputStrength = Integer.parseInt(strengthField.getText());
                    String inputShape = shapeField.getText().toLowerCase();

                    List<String> bonds = tree.lookup(inputFreq, inputShape, inputStrength);
                    displayOutput(bonds);
                } catch (NumberFormatException exception) {
                    displayOutput("Not a valid input. Please enter valid values.");
                }
            }
        });
    }

    private void displayOutput(List<String> outputLines) {
        StringBuilder outputText = new StringBuilder();
        for (String line : outputLines) {
            outputText.append(line).append("\n");
        }
        output.setText(outputText.toString());
    }

    private void displayOutput(String outputLine) {
        output.setText(outputLine);
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
            if (strength > node.strength) {
                // New node is stronger, insert it on the left
                node.left = insertHlpr(node.left, minFreq, maxFreq, shape, strength, bond);
            } else {
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

    public List<String> lookup(int freq, String shape, int strength) {
        List<String> bondsThatApply = new ArrayList<>();

        // Use lookupHelper for more specific search
        lookupHelper(root, freq, shape, strength, bondsThatApply);

        if (bondsThatApply.isEmpty()) {
            bondsThatApply.add("Bond not found");
        }

        return bondsThatApply;
    }


    private void lookupHelper(Node node, int freq, String shape, int strength, List<String> matches) {
        if (node == null) {
            return;
        }

        if (freq >= node.minFreq && freq <= node.maxFreq && shape.equalsIgnoreCase(node.shape) && strength == node.strength) {
            matches.add(node.bond);
        }

        lookupHelper(node.left, freq, shape, strength, matches);
        lookupHelper(node.right, freq, shape, strength, matches);
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
