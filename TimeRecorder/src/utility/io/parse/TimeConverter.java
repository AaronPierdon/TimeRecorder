/**
*Developer:     Aaron Pierdon
*
*Description:   Takes in an int that is a number of either seconds, minutes,
*               or horus and returns, based on the function called, a whole 
*               number representing that value in another form of either
*               seconds, minutes, or hours.
*
*Date:          9/16/2017
*
*/

package utility.io.parse;


public class TimeConverter {
    
    
    public static int secondsToMinutes(int secs){
        return secs * 60;
    }
    public static int secondsToHours(int secs){
        return secs / 60 / 60;
    }
    public static int minutesToSeconds(int mins){
        return mins / 60;
    }
    public static int minutesToHours(int mins){
        return mins * 60;
    }
    public static int hoursToSeconds(int hrs){
        return hrs * 60 * 60;
    }
    public static int hoursToMinutes(int hrs){
        return hrs / 60;
    }
    
    public static int milliToSeconds(long milli){
        Long num = milli / 1000;
        return num.intValue();
        
    }

}
