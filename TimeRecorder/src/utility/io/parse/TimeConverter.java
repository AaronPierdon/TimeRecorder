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
    
    
    public int secondsToMinutes(int secs){
        return secs * 60;
    }
    public int secondsToHours(int secs){
        return secs * 60 * 60;
    }
    public int minutesToSeconds(int mins){
        return mins / 60;
    }
    public int minutesToHours(int mins){
        return mins * 60;
    }
    public int hoursToSeconds(int hrs){
        return hrs / 60 / 60;
    }
    public int hoursToMinutes(int hrs){
        return hrs / 60;
    }

}
