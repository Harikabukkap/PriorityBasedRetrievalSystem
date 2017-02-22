/*
 * Node Class  
 */

public class Node {
    
    /**********VARIABLES**********/
    // Frequency of hashtag
    int key;
    // Degree of node
    int degree;
    // Hashtag 
    String tag;
    // Parent of node
    Node parent;
    // Child of node
    Node child;
    // Previous node 
    Node prev;
    // Next node
    Node next;
    // childCut value of node
    boolean childCut;
    
    
    /**********METHODS**********/
    /*
    Method: Node(constructor)
    Parm: int key & String tag
    Description: Node class constructor which initializes the class variables with the passed parameters if any
    Return value: None
    */
    public Node(int key, String tag) {
            this.key = key;
            this.tag = tag;
            next = this;
            prev = this;
            child = null;
            parent = null;
            this.degree = 0;
            
        }
        
        
    /*
    Method: compare
    Parm: Node other 
    Description: Compares the key values of this and other node. 
    Return value: -1 if this.key < other.key, 0 if this.key = other.key and 1 if this.key > other.key
    */
    public int compare(Node other) {
            if (this.key == other.key)
                return 0;
            else if (this.key > other.key)
                return 1;
            else return -1;
        }
    
}
