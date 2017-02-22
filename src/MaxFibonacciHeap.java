/*
 * MaxFibonacciHeap Class
 */

import java.util.ArrayList;
import java.util.List;

public class MaxFibonacciHeap {
    
    /**********VARIABLES**********/
    // Declare a node to store the maximum key value of MaxFibonacciHeap 
    Node maxNode;
    
    // Declare the size of MaxFibonacciHeap
    int size;
    
    
    /**********METHODS**********/
    /*
    Method: MaxFibonacciHeap(constructor)
    Parm: None
    Description: MaxFibonacciHeap class constructor which initializes the class variables
    Return value: None
    */
    public MaxFibonacciHeap() {
        
        // Initialize the maxNode and size of MaxFibonacciHeap
        maxNode = null;
        size = 0;
    }
    
    
    /*
    Method: max
    Parm: None
    Description: Returns the maxNode of MaxFibonacciHeap. 
    Return value: The maxNode of MaxFibonacciHeap.
    */
    public Node max() {
        if (maxNode != null)
            return maxNode;
        else 
            return null;
    }
    
    
    /*
    Method: insert
    Parm: String tag & int key
    Description: Inserts new node with passed hashtag and frequency key values and melds it with existing list of roots.
    Return value: The newly created node.
    */
    public Node insert(String tag, int key){
        
        // Create new node with passed hashtag and frequency key values
        Node node = new Node(key, tag);
        
        //Meld the node into the existing list of roots and update the maxNode
        maxNode = meld(maxNode, node);
        
        // Increment the size of MaxFibonacciHeap
        size++;
        
        // Return the newly created node
        return node;
        
    }
    
    
    /*
    Method: removeMax
    Parm: None
    Description: Removes maxNode and melds its children with existing list of roots followed by pairwiseCombine of trees with equal degree. 
    Return value: The removed maxNode.
    */
    public Node removeMax(){
       
        // Save the maxNode being removed to return later 
        Node maxNodeOld = maxNode;
            
        // Proceed if maxNode is non null
        if (maxNode != null) {
            
            // Detach the maxNode from the existing list of roots by setting pointers from its prev to its next.
            maxNode.prev.next = maxNode.next;
            maxNode.next.prev = maxNode.prev;
            
            // Save the next node of maxNode 
            Node maxNext = null;
            if (maxNode.next == maxNode) 
                maxNext = null;
            else maxNext = maxNode.next;
        
            //Set the prev and next of maxNode to refer itself
            maxNode.prev = maxNode.next = maxNode;
            
            // Decrement the size of MaxFibonacciHeap
            size--;     
            
            // Update the parent node of all maxNode children to null
            if (maxNode.child != null) {
                maxNode.child.parent = null;
                for(Node iter = maxNode.child.next; iter != maxNode.child; iter = iter.next) {
                    iter.parent = null;
                }  
            }
        
            // Meld the child of the removed maxNode with its previously saved next node and update maxNode
            maxNode = meld(maxNext, maxNode.child);
            
            // Pairwise combine node trees of equal degree if maxNode is not the only node in MaxFibonacciHeap 
            if (maxNode != null) {
                pairwiseCombine();
            }
        }    
        
        // Return the saved removed maxNode
        return maxNodeOld;
    }
    
    
    /*
    Method: increaseKey
    Parm: Node node & int newKey
    Description: Increases the key value of the passed node to newKey value. It also cuts the node if node.key >
    node.parent.key followed up cascadingCut on heap.
    Return value: None
    Throws: New key is smaller than old key if newKey < node.key.
    */
    public void increaseKey(Node node, int newKey) {
      
        // Throw exception if newKey < node.key
        if (newKey < node.key) {
            throw new IllegalArgumentException("New key is smaller than old key.");
        }
        
        // Set the key value of the node to the newKey value passed.
        node.key = newKey;
        
       // Cut the node from its parent and siblings if node.key > node.parent.key, follow up by cascadingCut
        if (node.parent != null && node.compare(node.parent) > 0) {
            
            // Obtain the parent of node
            Node parent = node.parent;
            
            // Cut the node
            cut(node,parent);
            
            // Perform cascadingCut on the path from node's parent till root
            cascadingCut(parent);
        }
        
        // Update the maxNode  
        if (node.compare(maxNode) > 0)
            maxNode = node;
        
    }
    
    
    /*
    Method: meld
    Parm: Node first node & Node second   
    Description: Melds the first and second nodes into the MaxFibonacciHeap. 
    Return value: The maximum node of the two passed nodes.
    */
    public Node meld(Node first, Node second){
        
        // If both nodes are non null
        if (first != null && second != null) {
            
            // Meld the nodes
            Node temp = first.next;
            first.next = second.next;
            first.next.prev = first;
            second.next = temp;
            second.next.prev = second;
            
            // Return the maximum of the two nodes
            if (first.compare(second) < 0)
                return second;
            else if (first.compare(second) > 0)
                return first;
            else return first;
        }
        
        // Else if first node is null, return second
        else if (first == null) {
            return second; 
        }    
            
        // Else if second is null, return first
        else if (second == null) {
            return first;
        }
        
        // Else both are null, return null
        else return null;
        
    }
    
    
    /*
    Method: cascadingCut
    Parm: None
    Description: cascadingCut operation peformed on MaxFibonacciHeap beginning from parent of node cut away til the root
    where if childCut value of node is true it is cut away from its parent else its childCut value is changed to true.
    Return value: None
    */
    public void cascadingCut(Node node){
        
        // Save the node's parent
        Node parent = node.parent;
        
        // If node's parent is not null
        if (parent != null) {
            
            // If the childCut value of node is false, set it to true
            if ( node.childCut == false ) 
                node.childCut = true;
            
            // Else, cut the node 
            else {
                cut(node,parent);
                cascadingCut(parent);
            }    
        }
        
    }
    
    
    /*
    Method: cut
    Parm: Node node & Node parent
    Description: Cut the passed node from its parent and meld it with existing list of roots.
    Return value: None
    */
    public void cut(Node node, Node parent){
        
        // Detach the node by setting pointers between its prev node and its next node
        node.prev.next = node.next;
        node.next.prev = node.prev;
        
        // Save its next node
        Node nextNode = node.next;
        
        // Make next and prev of the node to point to itself
        node.next = node;
        node.prev = node;
        
        // Decrement the detached node's parent degree
        parent.degree--;
        
        // Set the child of detached node's parent to its sibling or null
        if (parent.child == node){
            if (nextNode != node)
                parent.child = nextNode;
            else parent.child = null;    
        }
        
        // Set the node's parent to null
        node.parent = null;
        
        // Meld the node with the existing list of roots
        maxNode = meld(maxNode,node);
        
        // Set the childCut value of detached node to false 
        node.childCut = false;
        
    }
    
    
    /*
    Method: pairwiseCombine
    Parm: None
    Description: Combines trees of equal degree considering two at a time and updates the maxNode at the end
    by examining the trees from the treeTable.
    Return value: None
    */
    public void pairwiseCombine() {
        
        // ArrayList to keep track of already encountered nodes and their degree
        List<Node> treeTable = new ArrayList<Node>();
        
        // ArrayList to store the list of roots of MaxFibonacciHeap for traversal
        List<Node> mergeList = new ArrayList<Node>();
        
        // Add the current list of roots to mergeList for traversal
        Node curr = maxNode;
        do {
            mergeList.add(curr);
            curr = curr.next;
        } while(maxNode != curr);
        
        // Traverse mergeList and perform the pairwisecombine of trees of equal degree
        for (Node iter : mergeList) {
            
            // Expand the treeTable to accomodate the entry for iter.degree
            while (iter.degree >= treeTable.size()) {
                    treeTable.add(null);
            }
            
            // Keep merging until a tree of equal degree exists
            while (treeTable.get(iter.degree) != null) {
                
                // If an equal degree tree exists for encountered node, retrieve its entry from treeTable 
                Node other = treeTable.get(iter.degree);
                
                // Clear the treeTable entry after retrieval
                treeTable.set(iter.degree, null); 
                
                // Determine the maximum and minimum list root nodes 
                Node max,min;
                if (iter.compare(other) > 0) {
                    max = iter;
                    min = other;
                }
                else {
                    max = other;
                    min = iter;
                }    
                
                // Detach the min out of list of roots and meld it into max's children list
                min.next.prev = min.prev;
                min.prev.next = min.next;
                               
                // Make min node singleton 
                min.next = min.prev = min;
                
                // Meld the max's child and min and set the maximum of the two as max's child node
                max.child = meld(max.child, min);
                
                // Set max as parent of min
                min.parent = max;
                
                // Reset the childCut value of min to false after it got its new parent 
                min.childCut = false;

                // Increment max node's degree
                max.degree++;                
                
                // Continue combining the max tree  
                iter = max;
                
                // Expand the treeTable to accomodate the entry for iter.degree 
                while (iter.degree >= treeTable.size()) {
                    treeTable.add(null);
                }
            }
            
            // If an equal degree match is not found, make an entry for iter in treeTable at iter.degree slot
            if (treeTable.get(iter.degree) == null) 
                treeTable.set(iter.degree, iter);
        }
        
        // Update maxNode by melding and examining the remaining list of roots in treeTable
        maxNode = null;
        for (Node n: treeTable) {
            if (n!=null) {
            // Make the prev and next of node to refer itself before melding with current maxNode
            n.next = n;
            n.prev = n;
            maxNode = meld(maxNode, n);
            }
        }
    }
    
    
    /*
    Method: reinsertMaxNodes
    Parm: List<Node> list
    Description: Reinserts the nodes in the passed list into the existing list of roots of MaxFibonacciHeap. 
    Return value: None
    */
    public void reinsertMaxNodes(List<Node> list) {
       
       // Iterate through the passed list and meld each node into the existing list of roots
       for(Node iter: list) {
           
           // Set the child, parent to null and degree to zero for encountered node
           iter.child = null;
           iter.parent = null;
           iter.degree = 0;
           
           // Meld the encountered node with existing list of roots and update maxNode
           maxNode = meld(maxNode, iter);
       } 
       
       // Clear the list after processing
       list.clear();      
    }
    
}
