/* 
   Peyton Bischof
   12/8/2020
   CSE143X
   TA: Chandan Hedge
   Assignment #10
   
   This class compresses a text file passed from the client to 
   represent the frequency of characters in the file. This program uses 
   less bits for characters than usual. It uses the Huffman coding
   algorithm to store the code to an output, and encodes translated
   characters into the new compressed message.  
*/ 

import java.util.*;
import java.io.PrintStream;

public class HuffmanTree {
   private HuffmanNode root;
   private HuffmanNode root2;
   private int[] freq;
   private PriorityQueue<HuffmanNode> queue;
   private Map<Integer, String> value;
   
   // takes array of frequencies of characters as a parameter
   // method constructs initial HuffmanTree
   // counts number of occurences of each character until they have all
   //    been counted
   // assumes that the frequency > 0
   public HuffmanTree(int[] count) {
      this.freq = count;
      makeTree();
   }

   // prints tree of given output stream in standard form
   // takes PrintStream of output as parameter
   public void write(PrintStream output) {
      getTree(root, "", output);
   }
  
   // takes the node being examined, and the String form of the output as parameters
   // puts node information into the proper format by keying node characters to 
   //    their binary code
   // decides if node is a leaf node and prints the ascii value keyed in the map and 
   //    their binary code 
   private void getTree(HuffmanNode node, String str, PrintStream output) {
      value = new HashMap<Integer, String>();
      
      if (node != null) {
         if (node.left == null && node.right == null) {
            value.put((int) node.character, str);
         }
               
         for (Map.Entry<Integer, String> temp : value.entrySet()) { 
            output.println(temp.getKey());
            output.println(temp.getValue());
         }
      
         getTree(node.left, str + '0', output);
         getTree(node.right, str + '1', output);
      }
   } 
   
   // creates new HuffmanNodes and adds them to the PriorityQueue
   // builds the HuffmanTree in standard format node by node based on frequency
   //    so that more frequent characters are accessed near the top of the tree
   private void makeTree() {
      queue = new PriorityQueue<HuffmanNode>(freq.length);
      HuffmanNode temp;
      
      for (int i = 0; i < freq.length; i++) {
         if (freq[i] > 0) { 
            temp = new HuffmanNode(freq[i], i);
            queue.add(temp);
         }
      }
      
      // end of file node
      temp = new HuffmanNode(1, 256);
      queue.add(temp);
      
      while (queue.size() > 1) {
         HuffmanNode node = new HuffmanNode(0, 0);
         HuffmanNode node1 = node.left;
         HuffmanNode node2 = node.right;
         node1 = queue.poll();
         node2 = queue.poll();
         HuffmanNode node3 = new HuffmanNode(node1, node2);
         queue.add(node3);
      }
      
      root = queue.poll();
   }
     
   // accepts a Scanner as a parameter that reads a file that
   //    contains a Tree in standard format
   // method reconstructs the Tree from the file into the program
   public HuffmanTree(Scanner input) {
      String code = "";
      root2 = new HuffmanNode(0, 0);
      
      while (input.hasNextLine()) {
         int n = Integer.parseInt(input.nextLine());
         code = input.nextLine(); // binary
         makeTree2(n, code, root2);
      }
   }
   
   // assumes that the input stream contains a legal encoding of characters
   // accepts an input stream that reads sequences of encoded characters, a 
   //    PrintStream that is responsible for printing the code, and an int 
   //    that represents the end of file index as parameters
   // method reverses the encoding process by figuring out what the original
   //    characters were
   // uses the end of file integer value to find end of encoded sequence 
   // method prints decoded words
   public void decode(BitInputStream input, PrintStream output, int eof) {
      HuffmanNode node = root2;
      int bitRead = input.readBit();
      boolean atEnd = (bitRead == eof);
            
      while (!atEnd) {
         if (bitRead == 0) {
            node = node.left;
         } else {
            node = node.right;
         }
         
         if (node.left == null && node.right == null) {
            output.write((char) node.character);  
            node = root2;
         }
         
         bitRead = input.readBit();
         atEnd = (bitRead == -1);
      }
   }
   
   // method builds tree backwards recursively starting with a root node
   // accepts the integer ASCII value of the word, the binary value in String form, 
   //    and the pre-initialized HuffmanNode as parameters 
   private HuffmanNode makeTree2(int ascii, String code, HuffmanNode node) {
      if (node == null) {
         node = new HuffmanNode(0, 0);   
      }
      
      if (code.equals("0")) {
         node.left = new HuffmanNode(0, ascii);
         
      } else if (code.equals("1")) {
         node.right = new HuffmanNode(0, ascii);
         
      } else {
         if (code.charAt(0) == '0') {
            node.left = makeTree2(ascii, code.substring(1), node.left);
         } else {
            node.right = makeTree2(ascii, code.substring(1), node.right);
         }
      }
      
      return node;
   }
}