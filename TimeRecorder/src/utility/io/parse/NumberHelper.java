


package utility.io.parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberHelper{

    public static boolean isANumber(String testString){
        
         
            Pattern intPattern = Pattern.compile("\\d+");
            Matcher patMatcher = intPattern.matcher(testString);
            
            //how many sets of integer strings found? should be 1 for a real
            //number 
            int intCounter = 0;
            String firstGroup = null; //contains the first block of integers found
                                        //in the temp string so we can persist
                                        //a Matcher's .group() return value
                                        
            while(patMatcher.find()){
                firstGroup = patMatcher.group();
                intCounter++;
            }
                
            //match found?
            if(firstGroup != null && !firstGroup.isEmpty()){
                //only one group found? Could be a numerical value
                //only one group found? Could be a numerical value
                //the length of the match group == length of test string??
                 //most likely is a numerical value return true
                return intCounter == 1 && firstGroup.length() == testString.length();
            }else
                return false;
            

        
    }
}
