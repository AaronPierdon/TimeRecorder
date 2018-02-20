/**
********************************************************************************
********************************************************************************
Developer: Aaron Pierdon
Last Revision: 7/28/2017
Description: 
 * 
 * this class loops until the user correctly inputs a yes or no in the forms 
 * that are coded below. The result is sent back to the caller in true or 
 * false form representing a yes or no (true or false)
 */
package utility.io.getAnswer;

import java.util.Scanner;




public class GetYesOrNo{
    public GetYesOrNo(){}
    
    public static boolean getYesOrNo(){
        Scanner input = new Scanner(System.in);
        boolean exitLoop = false;
        boolean returnAnswer = false;
        
                while(exitLoop == false){
            String answer = input.next();
            if(answer.equals("0")){
                returnAnswer = false;
                exitLoop = true;
            }
                
            if(answer.equals("1")){
                returnAnswer = true;
                exitLoop = true;
            }
                
            if(answer.equalsIgnoreCase("yes")){
                returnAnswer = true;
                exitLoop = true;
            }
            
            if(answer.equalsIgnoreCase("no")){
                returnAnswer = false;
                exitLoop = true;
            }          
            
            if(answer.equalsIgnoreCase("y")){
                returnAnswer = true;
                exitLoop = true;
            }
            
            if(answer.equalsIgnoreCase("n")){
                returnAnswer = false;
                exitLoop = true;
            }
            
            if(exitLoop == false){
                System.out.println("Sorry, didn't get that. Type:\n"
                        + "1 or 0, y or n, or yes or no...\n");
            }
                
        }
                
                return returnAnswer;
    }
}
