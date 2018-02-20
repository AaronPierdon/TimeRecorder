/**
 * Developer:       Aaron Pierdon
 * 
 * Description:     gets a string from the user
 * 
 * Date:            9/13/2017
 */
package utility.io.getAnswer;

import java.util.Scanner;

/**
 *
 * @author Aaron Pierdon
 */
public class GetStringInput {
    
    //gets a string from the user and returns it to caller
    public static String getStringInput(){
        String temp = null;
        
        
        System.out.println("Please input a string: ");
        while(temp == null || temp.length() == 0){
            temp = new Scanner(System.in).nextLine();
            
            
        }
        
        return temp;
    }
    
}
