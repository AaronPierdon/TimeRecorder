/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility.stringUtility;

/**
 *
 * @author Aaron Pierdon
 */
public class CalendarParser {
    
    
    public static String getMonthString(int month){
        
        String strMonth;
        
        switch(month){
            case 0 : strMonth =  "Jan";
            break;
            
            case 1 : strMonth =  "Feb";
            break;
            
            case 2 : strMonth =  "Mar";
            break;
            
            case 3 : strMonth =  "Apr";
            break;
            
            case 4 : strMonth =  "May";
            break;
            
            case 5 : strMonth =  "Jun";
            break;
            
            case 6 : strMonth =  "Jul";
            break;
            
            case 7 : strMonth =  "Aug";
            break;
            
            case 8 : strMonth =  "Sep";
            break;
            
            case 9 : strMonth =  "Oct";
            break;
            
            case 10 : strMonth =  "Nov";
            break;
            
            case 11 : strMonth =  "Dec";
            break;
            
            default: strMonth = "Unknown Month";
        }
        
        return strMonth;
        
    }
    
    public static int getMonthInt(String month){
            int intMonth;
            
            switch(month){
                case "Jan" : intMonth =  0;
                break;

                case "Feb" : intMonth =  1;
                break;

                case "Mar" : intMonth =  2;
                break;

                case "Apr" : intMonth =  3;
                break;

                case "May" : intMonth =  4;
                break;

                case "Jun" : intMonth =  5;
                break;

                case "Jul" : intMonth =  6;
                break;

                case "Aug" : intMonth =  7;
                break;

                case "Sep" : intMonth =  8;
                break;

                case "Oct" : intMonth =  9;
                break;

                case "Nov" : intMonth =  10;
                break;

                case "Dec" : intMonth =  11;
                break;

                default: intMonth = 0;
            }
            
            return intMonth;
        }
}
