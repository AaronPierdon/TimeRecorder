/*
 * Developers: Aaron Pierdon
 * Date: Mar 26, 2018
 * Description :
 * 
 */
package javaapplication7;

import java.util.HashMap;

/**
 *
 * @author Aaron
 */
public class JavaApplication7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HashMap<String, Long> ba = new HashMap<>();
        
        
        ba.put("2017", (ba.get("2017") + 20L));
        
        System.out.println(ba.get("2017"));
    }
    
}
