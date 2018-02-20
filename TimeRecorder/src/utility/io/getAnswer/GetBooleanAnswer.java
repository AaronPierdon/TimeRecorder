/**
 * Developer:       Aaron Pierdon
 * 
 * Description:     abstract class can be called without instance. Used to 
 *                  get a yes or no answer from user.
 * 
 * Date:            9/13/2017
 */
package utility.io.getAnswer;

import java.util.Scanner;

public abstract class GetBooleanAnswer {
    
    
    //returns user boolean choice
    public static Boolean getBooleanAnswer(){
        Boolean answer = null;
        int exitValue = 0;
        System.out.println("Please input true or flase: ");
        while(exitValue == 0){
            
            Scanner in = new Scanner(System.in);
            
            String test = in.nextLine();
            
            if(test.equalsIgnoreCase("true")){
                answer = true;
                exitValue = 1;
            }
            if(test.equalsIgnoreCase("t")){
                answer = true;
                exitValue = 1;
            }
            if(test.equals("1")){
                answer = true;
                exitValue = 1;
            }
            if(test.equalsIgnoreCase("false")){
                answer = false;
                exitValue = 1;
            }
            if(test.equalsIgnoreCase("f")){
                answer = false;
                exitValue = 1;
            }
            if(test.equals("0")){
                answer = false;
                exitValue = 1;
            }
            
            if(answer == null){
                System.out.println("Sorry, please just input \"true\" or \"false\"");
            }
            
        }
        
        return answer;
    }
}
