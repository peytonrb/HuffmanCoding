/* 
   Peyton Bischof
   12/8/2020
   CSE143X
   TA: Chandan Hedge
   Assignment #10
   
   This class defines object HuffmanNode that contains a character value,
   frequency of the node, and references to the left and right
   HuffmanNode.
*/ 

import java.util.*;

public class HuffmanNode implements Comparable<HuffmanNode> {
   public int frequency;
   public int character; // ascii value 
   public HuffmanNode right;
   public HuffmanNode left;
   
   // constructs a HuffmanNode passed with a frequency and character value 
   //    on the ASCII scale   
   public HuffmanNode(int x, int y) {
      this.frequency = x;
      this.character = y;
   }
   
   // given a value for the left and right nodes, 
   //    constructs a HuffmanNode that points to left and right HuffmanNode. 
   // sets frequency to the left and right frequencies combined
   public HuffmanNode(HuffmanNode left, HuffmanNode right) {
      this.right = right;
      this.left = left;
      this.frequency = left.frequency + right.frequency;
   }
   
   // accepts parameter HuffmanNode node to compare 
   // returns -1, 0, or 1 depending on the comparison of the frequency of this 
   //    HuffmanNode and another
   public int compareTo(HuffmanNode node) {
      if (this.frequency < node.frequency) {
         return -1;
      } else if (this.frequency > node.frequency) {
         return 1;
      }
      
      return 0;
   }
}