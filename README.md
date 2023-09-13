# Bond Identifier
---


# Overall
---
The Bond Frequency Identifier is a Java program that identifies chemical bonds from infrared (IR) spectroscopy data. It makes use of a binary search tree (BST) data structure to match user-inputted peak characteristics to the corresponding bond. 

# How does it work
---
The Bond Frequency Identifier works by taking 3 user inputs:

1.) Peak Shape: The shape of the IR peak, which can be either "narrow" or "broad."<br>
2.) Peak Strength: The strength of the peak on a scale from 1 to 3, where 1 represents a weak peak, 2 represents a medium peak, and 3 represents a strong peak. <br>
3.) Approximate Bond Frequency: The estimated frequency (without units) of the IR spectrum peak user wants to know about.<br>
Based on these inputs, the program employs a binary search tree (BST) to find and output the chemical bond corresponding to the provided peak characteristics.<br>

# Methods
---
● freqKey class<br><br>
 &emsp;• Represents the frequency range of the bond (minimum and maximum).<br>
 &emsp;• Used as a key in the frequency-to-bond mapping.<br><br>
● Node class<br><br>
 &emsp;• Stores bond frequency range, shape, strength, and bond type<br>
 &emsp;• Implements the tree nodes<br><br>
● Tree class<br><br>
 &emsp;• Contains root node<br>
 &emsp;• Used a freqToBond map for efficient lookup.<br>
 &emsp;• insert() - Inserts a new node into the tree<br>
&emsp; • lookup() - Searches the tree to find the bond for given inputs<br><br>
● IRGUI class<br><br>
 &emsp;• Main class that boots up the GUI. The GUI is written using the Jframes and swing frameworks.<br>
 &emsp;• Takes in user inputs for peak characteristics and outputs the identified bond.<br><br>
  
# Data Structure
---
The main data structure I used was a binary search tree (BST). Nodes are inserted based on the frequency range. This allows efficient searching to find the bond corresponding to the input frequency.

Using a BST provides O(log n) lookup time on average. A hash table is used at the same time for constant time access.

# Key Lessons for me / challenges
---
&emsp;• Implementing a BST with insert and lookup functions<br>
&emsp;• Handling the insertion of overlapping frequency ranges in the BST.<br>
&emsp;• Writing clean by separating tree and node implementations because at first, I tried to implement this all under one class. <br>
