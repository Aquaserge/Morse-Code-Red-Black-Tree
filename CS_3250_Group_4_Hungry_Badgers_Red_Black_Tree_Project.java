//import org.pushingpixels.radiance.theming.api.skin.RadianceNightShadeLookAndFeel;

import javax.swing.*;
import java.util.*;
import java.io.*;
import java.lang.*;

// Project Name
class CS_3410_Group_4_Hungry_Badgers_Morse_Code_Red_Black_Tree_Project {}

//	Creates a node for the Red-Black Tree. A RBT node has a left child, right child, element, and assigned color of each node.
class RedBlackNode {
   RedBlackNode leftChild, rightChild;
   char element;
   int color;


   //	Sets the value of a node having a null left and right child.
   public RedBlackNode(char element)
   {
      this(element, null, null);
   }


   //	Sets value of element, leftChild, rightChild, and color.
   public RedBlackNode(char element, RedBlackNode leftChild, RedBlackNode rightChild)
   {
      this.element = element;
      this.leftChild = leftChild;
      this.rightChild = rightChild;
      color = 1;
   }
}


// Create a Red-Black Tree.
class RedBlackTree {

   // Define each type of node.
   private static RedBlackNode nullNode;
   private RedBlackNode current;
   private RedBlackNode parent;
   private RedBlackNode root;

   // Define variables for later use.
   int numOfChars = 0;
   int numOfDashes = 0;
   int numOfDots = 0;
   int numOfWords = 0;
   int traversalDepthTotal = 0;
   int traversalTimes = 0;
   
   // Array to hold decoded character counts.
   ArrayList<Character> allCharacters = new ArrayList<>();


   // Create a Red-Black Tree.
   static final int RED   = 0;
   static final int BLACK = 1;

   // Initialize nullNode.
   static {
      nullNode = new RedBlackNode('0');
      nullNode.leftChild = nullNode;
      nullNode.rightChild = nullNode;
   }


   // Create root node with two null children, one to the left, and the other to the right.
   public RedBlackTree(char root) {
      this.root = new RedBlackNode(root);
      this.root.leftChild = nullNode;
      this.root.rightChild = nullNode;
   }
  
  
   /* Typically a Red-Black Tree uses a lesser than or greater than algorightm to determine whether each new element should be placed as a left or right child 
      respectively. Morse code works a little differently. Without completely substituting each dot or dash for a numerical value that can be mathmatically 
      compared, the only way to do so is by hard coding which direction the tree is traversed, with dots going left and dashes going right. These directions 
      are arbitrary due to Morse code being binary, so the tree can be mirrored with the same results. Using the call signal of each letter as a "map" to
      traverse the tree, each character has a predetermined location within the tree based off the length of the Morse code call signal. For example the Morse
      code call signal of the letter 'Y' is "-.--", therefor the tree traversal map would read - right, left, right, right. Once the method has traversed the
      call signal to completion, it will insert the character element in it's final location.
   */
   
   // Creates the Morse code RBT by accepting the character to insert once traversal is complete, while using the actual Morse code of each letter to traverse the tree and determine the characters location.
   public void InsertNewMorse(char character, String morse) {
      current = parent = root;                             // Sets current and parent to the root in preparation for traversing the tree.
   
      String pattern = null;                               // Variable to store the single piece of character Morse code to traverse tree.
   
      for (int i = 0; i < morse.length(); i++) {           // Use the length of the character Morse code to determine how far down the tree to go.
         pattern = String.valueOf(morse.charAt(i));
         if (pattern.equals(".")) {                        // Travels left down the tree if the Morse symbol is a dot ".".
            if (i < morse.length() - 1) {
               current = parent.leftChild;                 // Sets the current marker to the left child of the parent in preparation of the next loop.
               parent = current;
            } 
            else {
               parent = current;
               System.out.println("The parent of this node is " + parent.element);           
               current.leftChild = new RedBlackNode(character, nullNode, nullNode);           // Inserts the element into the leftChild node.
               current = current.leftChild;
               System.out.println("Inserted character " + current.element + " as left node.");
            }
         } 
         else {                                          // Travels right down the tree, since morse code is a binary language, there is no need to declare the dash "-".
            if (i < morse.length() - 1) {
               current = parent.rightChild;
               parent = current;
            } 
            else {
               parent = current;
               System.out.println("The parent of this node is " + parent.element);            // Inserts the element into the rightChild node.
               current.rightChild = new RedBlackNode(character, nullNode, nullNode);
               current = current.rightChild;
               System.out.println("Inserted character " + current.element + " as right node.");
            }
         }
      } // End for loop.
   } // End of InsertNewMorse method.


   /* The same traversal algorigthm for building the Morse code Red-Black tree can be used for searching the tree, only instead of inserting the character in
      the element, this method returns it. Again, dots are used for left traversal, and dashes are used for right traversal.
   
   */

   // Morse tree Search Method searches a string of Morse code and finds the matching character.
   public char MorseTreeSearch(String morse) {
      current = parent = root;                             // Sets current and parent to the root in preparation for traversing the tree.
      traversalTimes++;
      char alphabet = '`';
      char pattern;
      
      for(int i = 0; i < morse.length(); i++) {            // Begins for loop used to traverse the tree, incrementing once per dot or dash within the passed String in each loop.
         pattern = (morse.charAt(i));
         if(pattern == '.') {                              // Increment variable and traverse tree if a dot is found.
            if(i <= morse.length()) {                      // Traverse tree and continue loop if the length of the morse string has not been reached.
               current = current.leftChild;
               numOfDots++;                                // Increase dot count number by 1.
               traversalDepthTotal++;                      // Increase traversal depth counter by 1.
               alphabet = current.element;                 // Sets the letter assigned to the current node element to alphabet.
            }
            else {                                         // If end of the Morse code String is reached and is a dot, complete task by assigning the proper node element character to alphabet.
               current = current.leftChild;                
               numOfDots++;                                // Increase dot counter by 1.
               traversalDepthTotal++;                      // Increase traversla depth counter by 1.
               alphabet = current.element;                 // Sets the letter assigned to the current node element to alphabet.
               break;
            }
         }
         else {                                            // Increment variable and traverse tree if a dash is found.   
            if(i <= morse.length()) {
               current = current.rightChild;
               numOfDashes++;                              // Increase dash counter by 1.
               traversalDepthTotal++;                      // Increase traversla depth counter by 1.
               alphabet = current.element;                 // Sets the letter assigned to the current node element to alphabet.
            }
            else {                                         // If end of the Morse code string is reached and is a dash, complete task by assigning the proper node element character to alphabet.
               current = current.rightChild;
               numOfDashes++;                              // Increase dash counter by 1.
               traversalDepthTotal++;                      // Increase traversla depth counter by 1.
               alphabet = current.element;                 // Sets the letter assigned to the current node element to alphabet.      
               break;
            }
         }
      } // End for loop.
   
      allCharacters.add(alphabet);                         // Adds the letter to the allCharacters array for later analysis.                       
      return alphabet;                                     // Returns the letter to continue constructing the decoded word.
   } // End of MorseTreeSearch method.


   // Insert Morse Code Tree is a heavily modifies insert method that populated the Red Black Tree
   public void MorseCodeTreeInsert() throws IOException {
      Reader treeReader = new FileReader(new File("morse_code_codex.txt"));   // Creates reader file out of the Morse Code Codex txt file.
      Scanner MorseCodeList = new Scanner(treeReader);                        // Create new object out of Reader file variable.
   
      while (MorseCodeList.hasNextLine()) {                                   // Continues While loop so long until the txt file content ends.
         String letter = MorseCodeList.nextLine();                            // Sets variable to the next letter and Morse code.
         if (letter.length() > 0) {                                           // If statement used to ensure 
            String morse = letter.substring(2);                               // Gathers the morse code from the letter variable at the predesignated location in the String.
            char character = letter.charAt(0);
            InsertNewMorse(character, morse);
         
            System.out.println("Sending new character " + character + " and morse code " + morse + " to add to the tree\n\n");
         }
      } // End while loop.
   } // End of MorseCodeTreeInsert method.


   // Encrypted Breakdown method takes in a file path and returns an arraylist of the Morse code words inside.
   public ArrayList<String> EncryptedBreakdown(String filePath) throws IOException {
      Reader searchReader = new FileReader(new File(filePath));
      Scanner MorseCodeSearch = new Scanner(searchReader);
      ArrayList<String> toReturn = new ArrayList<>();
   
      while (MorseCodeSearch.hasNextLine()) {
         String message = MorseCodeSearch.nextLine();
         System.out.println("Encoded Morse: " + message);
         String[] encodedWords = message.split("     ");
         numOfWords = encodedWords.length;
         for (int i = 0; i < encodedWords.length; i++) {
            toReturn.add(encodedWords[i]);
         }
      }
      return toReturn;
   
   } // End of EncryptedBreakdown method.


   // Morse word method decodes and constructs the words it's passed based on our red black tree.
   public String morseWord(String encWord) {
      String decodedWord = "";
      char decodedLetter = 'a';
      String[] tempArr = encWord.split(" ");;
      numOfChars += tempArr.length;
      for(int i = 0; i < tempArr.length; i++) {
         decodedLetter = MorseTreeSearch(tempArr[i]);
         // Add the decoded letters to the word.
         decodedWord = decodedWord.concat(String.valueOf(decodedLetter)).trim();
      }
      
      return decodedWord;  
   } // End of MorseWord method.
} // End of MorseCode class.


// Main Static class for running without GUI
class ProgramMain {
   public static void main(String[] args) {
   
      Scanner scan = new Scanner(System.in);
   
      String placeHolder = "0";
      RedBlackTree rbt = new RedBlackTree('*');
      char choice;
   
      //create switch case for performing the operation in Red Black Tree
      do {
         System.out.println("\nSelect an operation:\n");
         System.out.println("1. Build Red-Black Tree by Morse Code characters. ");
         System.out.println("2. Decode message.");
      
         //get choice from user
         int ch = scan.nextInt();
         switch (ch) {
            case 1 :
               try {
                  rbt.MorseCodeTreeInsert();
                  System.out.println("Tree constructed.");
                  System.out.println("Root value is ");
               }
               catch (IOException e) {
                  System.out.println("Error Parsing Codex File. Does it exist?");
               }
               break;
            case 2 :
               // Runs decoder entirely in console with hardcoded text file below.
               try {
                  ArrayList<String> tempAL = rbt.EncryptedBreakdown("morse_to_english.txt"); // Arraylist holds our text file values to parse
                  long startTime = System.currentTimeMillis(); // used to calculate total time taken to decode.
                  System.out.print("Decoded Morse: ");
                  for(int i = 0; i < tempAL.size(); i++) { // for loop to iterate over and decode the Morse code
                     System.out.print(rbt.morseWord(tempAL.get(i)) + " ");
                  }
                  long endTime = System.currentTimeMillis(); // used to calculate total time taken to decode.
                  System.out.println("\nThe total number of dashes is: " + rbt.numOfDashes); // Additional Information sent to the console.
                  System.out.println("The total number of dots is: " + rbt.numOfDots);
                  System.out.println("The total number of characters in this code was: " + rbt.numOfChars);
                  System.out.println("The total number of words in this code was: " + rbt.numOfWords);
                  System.out.println("The total number of traversals was: " + rbt.traversalTimes + " and the average depth of traversal was: " + (rbt.traversalDepthTotal / rbt.traversalTimes) + ".");
                  System.out.println("The total time to run this translation was: " + (endTime - startTime) + " milliseconds.");
                  Set<Character> set = new HashSet<Character>(rbt.allCharacters);
                  for(char c : set) {
                     System.out.println("The total number of '" + c + "' occurrences is: " + Collections.frequency(rbt.allCharacters, c));
                  }
               
               }
               catch (IOException e) {
                  System.out.println("Error with the input file. Is it spelled correctly in the code and does it exist?");
               }
               break;
                // Optional case for running a GUI to allow file select.
            case 3 :
               //GUIClass gui = new GUIClass();
            
               break;
            default :
               System.out.println("\n ");
               break;
         } // End of switch.
         
         System.out.println("\nPress 'y' or 'Y' to continue \n");
         choice = scan.next().charAt(0);
      } // End of do.
      
      while (choice == 'Y'|| choice == 'y');
   } // End of Main method.
} // End of Program.