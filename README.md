# Bond-Identifier

My project applies infrared spectroscopy concepts to a program that identifies bond peaks from IR charts. It takes 3 visible identifier inputs: #i) peak shape (narrow/broad) #ii) peak strength (strong/medium/weak) #iii) approximate bond frequency wavelength. Based on these inputs, it outputs the bond structure represented by the peak.


# The logic behind the program

The Bond-Identifier program allows identifying bonds from infrared spectroscopy peak data. It takes user input on peak shape, strength, and frequency and outputs the corresponding bond using a binary search tree data structure.

# Methods

• Node class<br>
  • Stores bond frequency range, shape, strength, and bond type<br>
  • Implements the tree nodes<br>
• Tree class<br>
  • Contains root node<br>
  • insert() - Inserts a new node into the tree<br>
  • lookup() - Searches the tree to find the bond for given inputs<br>
• Main class<br>
  • Creates the tree<br>
  • Takes user input<br>
  • Calls lookup() to identify bond<br>
  
# Data Structure

The main data structure I used was a binary search tree (BST). Nodes are inserted based on the frequency range. This allows efficient searching to find the bond corresponding to the input frequency.

Using a BST provides O(log n) lookup time on average. A hash table is used at the same time for constant time access.

# Key Lessons for me
  • Implementing a BST with insert and lookup functions<br>
  • Traversing a BST recursively to find target nodes<br>
  • Writing clean by separating tree and node implementations because at first, I tried to implement       this all under one class. <br>
