/**
 * Developer:       Aaron Pierdon
 * 
 * Description:     This class get's input from a user. The input should be an 
 *                  integer between min and max. This input corresponds to a menu
 *                  from the calling code.
 * 
 * Date:            9/13/2017
 */


package utility.io.getAnswer;

import java.util.Scanner;

public class GetMenuSelection{


	public static int getMenuSelection(int min, int max){

		int selection = 0;
		while(selection == 0){
			selection = new Scanner(System.in).nextInt();
			if(selection < min || selection > max)
				selection = 0;
			else
				return selection;
		}

		return -1;
	}
}