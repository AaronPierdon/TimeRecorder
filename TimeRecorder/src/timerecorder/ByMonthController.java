/*
 * Developers: Aaron Pierdon
 * Date: Mar 26, 2018
 * Description :
 * 
 */

package timerecorder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import timerecorderdatamodel.Task;
import utility.io.parse.TimeConverter;


public class ByMonthController {

    
    private Task theTask;
    private int theYear;
    
    
    public ByMonthController() {
    }

    public BarChart getByMonthChart(Task task, String year){
        
        this.theTask = task;
        this.theYear = Integer.valueOf(year);
        
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Month");
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Time");
        
        
        
        
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        XYChart.Series series  = new XYChart.Series();
        
        HashMap<String, Long> chartMap = getByMonthMap();
        
        ArrayList<Integer> months = new ArrayList<>();
        
        for(String month : chartMap.keySet()){
            months.add(getMonthInt(month));
        }
        
        
        
        Collections.sort(months);
        
        // Sorted list back to string
        
        ArrayList<String> strMonths = new ArrayList<>();
        for(Integer month : months)
            strMonths.add(getMonthString(month));
        
        
        // Build the XYChart data
        for(String month : strMonths){
            long raw = chartMap.get(month);
            int dur = (int) raw;
            int duration = TimeConverter.secondsToHours(dur / 1000);
            series.getData().add(new XYChart.Data(month, duration));
        }
        
        chart.getData().add(series);
        return chart;
    }
    
    public HashMap<String, Long> getByMonthMap(){
        HashMap<Calendar, Long> sessions = theTask.getSessions();
        
        // Holds the month/duration pair
        HashMap<Integer, Long> theMap = new HashMap<>();
        
        // Add each month as a unique key in theMap
        // Put the duration of type Long as the value for the key
        // If the key already exists, add the value to the existing key's value
        for(Calendar keyCal : sessions.keySet()){
            boolean found = false;
            
            for(Integer month : theMap.keySet()){
                
                // Make sure the month does not exist already and that the 
                // year of the session is equal to this.theYear before 
                // adding record to map
                if((keyCal.get(Calendar.MONTH) == month) && 
                        (keyCal.get(Calendar.YEAR) == theYear)){
                    found = true;
                    // Using the keyCal key to access the duratin value fot
                    // The current session
                    //Using theMap to update the pre-existing key/value
                    theMap.put(month, 
                            (theMap.get(month) + sessions.get(keyCal)));
                }
            }
            
            // Make sure that the 
            // year of the session is equal to this.theYear before 
            // adding record to map
            if(!found && keyCal.get(Calendar.YEAR) == theYear)
                theMap.put(keyCal.get(Calendar.MONTH), sessions.get(keyCal));
        }
        
        // Convert the key to string
        
        HashMap<String, Long> theNewMap = new HashMap<>();
        for(Integer month : theMap.keySet()){
            theNewMap.put(getMonthString(month), theMap.get(month));
        }
        
        
        return theNewMap;
    }
    
    
    public String getMonthString(int month){
        
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
    
    public int getMonthInt(String month){
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
