/**
*Developer:     Aaron Pierdon
*
*Description:   
*
*Date:           
*
*/

package utility.io.parse;


public abstract class LongToReadableTime {
    
    public static String getReadableTime(Long milliseconds){

        
        //get seconds and divide by 60 which gives minutes. The remainder from 
        //that division would be the seconds
        int seconds = (int) (milliseconds / 1000) % 60;
        
        //get minutes and divide by 60, which would give hours.
        //The remainder would be the minutes
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        
        //get hours 
        int hours   = (int) ((milliseconds / (1000*60*60)));
        
        return               makeTimeValueReadable(hours) + ":"
                                +makeTimeValueReadable(minutes) + ":"
                                +makeTimeValueReadable(seconds);
                            
        
    }
    
    private static String makeTimeValueReadable(int timeValue){
        
        if(timeValue == 0)
            return "00";
        if(timeValue <= 9 && timeValue > 0)
            return "0" + timeValue;
        
        return Integer.toString(timeValue);
    }

}
