# Bond-Identifier
---

My project applies infrared spectroscopy concepts to a program that identifies bond peaks from IR charts. It takes 3 visible identifier inputs: 1.) peak shape (narrow/broad) 2.) peak strength (strong/medium/weak) 3.) approximate bond frequency wavelength. Based on these inputs, it outputs the bond structure represented by the peak.


# The logic behind the program
---
The Bond-Identifier program allows identifying bonds from infrared spectroscopy peak data. It takes user input on peak shape, strength, and frequency and outputs the corresponding bond using a binary search tree data structure.

# Methods
---
● Node class<br><br>
 &emsp;• Stores bond frequency range, shape, strength, and bond type<br>
 &emsp;• Implements the tree nodes<br><br>
● Tree class<br><br>
 &emsp;• Contains root node<br>
  &emsp;• insert() - Inserts a new node into the tree<br>
&emsp; • lookup() - Searches the tree to find the bond for given inputs<br><br>
● Main class<br><br>
 &emsp; • Creates the tree<br>
&emsp; • Takes user input<br>
&emsp; • Calls lookup() to identify bond<br><br>
  
# Data Structure
---
The main data structure I used was a binary search tree (BST). Nodes are inserted based on the frequency range. This allows efficient searching to find the bond corresponding to the input frequency.

Using a BST provides O(log n) lookup time on average. A hash table is used at the same time for constant time access.

# Key Lessons for me
---
 &emsp;• Implementing a BST with insert and lookup functions<br>
&emsp;• Traversing a BST recursively to find target nodes<br>
&emsp; • Writing clean by separating tree and node implementations because at first, I tried to&emsp;implement this all under one class. <br>
