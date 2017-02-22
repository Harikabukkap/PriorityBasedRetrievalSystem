/*
 * hashtagcounter Class(contains main)  
 */

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class hashtagcounter {
    
    /**********VARIABLES**********/
    // Declare the HashTagMap object to store (hashtag, hashTagFreq) pairs in HashMap
    private HashTagMap map;
    
    // Declare the MaxFibonacciHeap object to create and operate on the hashTagFreq Nodes
    private MaxFibonacciHeap mfh;
    
    
    /**********METHODS**********/
    /*
    Method: hashtagcounter(constructor)
    Parm: None
    Description: hashtagcounter class constructor which initializes the class variables
    Return value: None
    */
    public hashtagcounter() {
        
        // Initialize the object of HashTagMap class
        map = new HashTagMap();
        
        // Initialize the object of MaxfibonacciHeap class
        mfh = new MaxFibonacciHeap();
    }
    
   
     
    /**********************************FILE METHODS*******************************************/
    /*
    Method: writeToFile
    Parm: BufferedWriter w, Node node & char newLine 
    Description: Writes the hashTag value associated with the passed hashTagFreq node in the Output file associated to BufferedWriter w. 
    Return value: None 
    */
    public void writeToFile(BufferedWriter w,Node node, char newLine) {
        
        try {
            
            // Write the hashtag associated to passed node into Output file associated to BufferWriter w
            if (newLine == 'Y') {
                w.write( node.tag.substring(node.tag.indexOf('#')+1, node.tag.length()));
                
                // Print new line if last hashTag of current query
                w.newLine();
            }
            else
                // Append a comma at the end of hashTag
                w.write( node.tag.substring(node.tag.indexOf('#')+1, node.tag.length())+  ',');
        
        } catch (IOException e) {
            System.err.println("Error in writing to output_file.txt.");
        }
        
    }
    
    
    /*
    Method: readFromFile
    Parm: BufferedReader in & BufferedWriter w
    Description: Reads and processes the Input file passed associated with BufferedReader in.
    Return value: None 
    */
    public void readFromFile(BufferedReader in, BufferedWriter w) {
        
        // Process the input file associated with BufferedReader in
        try {
            
            // Array list to store the removed maxNodes to be reinserted after query processing
            List<Node> reinsertList = new ArrayList<Node>();
            
            // String line to store the input file line read 
            String line = new String();
            
            // Read the input file line by line
            while ( !(line = in.readLine()).equalsIgnoreCase("STOP") ) {
               
                // If line read is a query
                if ( !(line.startsWith("#")) ) {
                    
                    // Convert string query line into an int
                    int query = Integer.parseInt(line);
                  
                    // Process the query n to remove <=n number of maxNodes from MaxFibonacciHeap 
                    for (int i=1; (i<= query && mfh.max() != null) ; i++) {
                        
                        // Remove the required number of max nodes one by one 
                        Node maxNode = mfh.removeMax();
                        
                        // Write the hashtag value associated with removed maxNode to Output file
                        if ( (i==query)|| (maxNode == null))
                            writeToFile(w,maxNode,'Y');
                        else
                            writeToFile(w,maxNode,'N');
                        
                        // Store maxNodes being removed to be re-inserted after current query is processed
                        reinsertList.add(maxNode);
                    }
                        
                    // Reinsert the removed maxNodes after query processing
                    if (!reinsertList.isEmpty())
                        mfh.reinsertMaxNodes(reinsertList);
                }
                
                // Else, if line read is a hasgtag 
                else { 
                    
                    // Split and obtain the hashTag and hashTagFreq values from the line read
                    String splitLine[] = line.split(" ");
                    
                    // Retrieve the hashFreq node from the map if already exists
                    Node hashTagFreq = map.retrieveFromMap(splitLine[0]);
                    
                    // If hashTag entry already exists in map, increaseKey for hashTagFreq node 
                    if (hashTagFreq != null) {
                        mfh.increaseKey(hashTagFreq, (hashTagFreq.key + Integer.parseInt(splitLine[1])) );
                    }
                    
                    // Else, create a new hashTagFreq node    
                    else {
                        hashTagFreq = mfh.insert( splitLine[0], Integer.parseInt(splitLine[1]) );
                        
                        // Insert (hashTag, hashTagFreq node) pair into the Hashmap
                        map.insertInMap(splitLine[0], hashTagFreq);
                        
                    }
                }
            }
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
      
    }
    
    
    /* 
    Method: main
    Description: Creates object of hashtagcounter class and BufferedReader, BufferedWriter for text file processing.
    It invokes its readFromFile method to process the inputFile passed in the command line.
    Return value: None
    */
    public static void main(String[] args) throws IOException {
        
        // Create an object of MaxFibonacciHeap class
        hashtagcounter htc = new hashtagcounter();
        
        try {
            // Obtain the output file to be written into using BufferedWriter
            BufferedWriter w = new BufferedWriter(new FileWriter("output_file.txt"));
        
            // Obtain the input file to be read using BufferedReader
            BufferedReader in = new BufferedReader(new FileReader(args[0]));
        
            // Invoke the readFromFile method to process the inputFile passed in the command line
            htc.readFromFile(in,w);
        
            // Close the BufferedWriter and BufferedReader after processing
            try {
                
                in.close();
                w.close();
                
            } catch (IOException ex) {
                
                System.out.println(ex);
            }
        
        } catch (FileNotFoundException ex) {
            
            System.out.println(ex);
            
        }
      
    }
    
}
