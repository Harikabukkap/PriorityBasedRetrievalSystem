/*
 * HashTagMap Class  
 */

import java.util.HashMap;
import java.util.Map;

public class HashTagMap {
    
    /**********VARIABLES**********/
    // Declare the Map object to store (hashTag, hashTagFreq) pairs
    private Map<String, Node> map;
    
    
    /**********METHODS**********/
    /*
    Method: HashTagMap(constructor)
    Parm: None
    Description: HashTagMap class constructor which initializes the class variables
    Return value: None
    */
    HashTagMap(){
        this.map = new HashMap<String, Node>();
    }
    
    
    /*
    Method: insertInMap
    Parm: String hashTag, Node hashTagFreq 
    Description: Inserts the passed (hasgTag, hashTagFreq ) entry in Hashmap. 
    Return value: None
    */
    public void insertInMap(String hashTag,Node hashTagFreq) {
        map.put(hashTag, hashTagFreq);
    }
    
    
    /*
    Method: retrieveFromMap
    Parm: String hashTag 
    Description: Retrieves hashTagFreq node associated with the hashTag entry from Hashmap. 
    Return value: hashTagFreq node  
    */
    public Node retrieveFromMap(String hashTag) {
        return map.get(hashTag);
    }
    
}
