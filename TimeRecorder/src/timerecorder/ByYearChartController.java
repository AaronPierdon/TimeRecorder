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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import timerecorderdatamodel.Task;
import utility.io.parse.TimeConverter;


public class ByYearChartController {

    private Task theTask;
    




    public BarChart getByYearChart(Task task){
        this.theTask = task;
        
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Years");
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Time");
        
        // Build the bar chart
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);

        
        XYChart.Series series1  = new XYChart.Series();
        
        // Build the data model
        HashMap<String, Long> chartMap = getDurationByYearMap();

        // This will be a sorted list of the years in the chartMap that can be used
        // as a means of orderly accessing the key value pairs of the hash map
        ArrayList<String> years = new ArrayList<>();
        
        
        
        // build the sorted array list
        for(String year : chartMap.keySet()){
            years.add(year);
        }        
        Collections.sort(years);       
        
        
        // Build the XYChart data
        for(String year : years){
            // Convert duration to hours
            long raw = chartMap.get(year);
            int dur = (int) raw;
            int duration = TimeConverter.secondsToHours(dur / 1000);
            series1.getData().add(new XYChart.Data(year, duration));
        }
        
        // Add xychart data to the chart
        chart.getData().add(series1);

        chart.setLegendVisible(false);
        chart.setCategoryGap(80);
        return chart;
    }
    

    
    public HashMap<String, Long> getDurationByYearMap(){
        HashMap<String, Long> durationsByYear = new HashMap<>();
        HashMap<Calendar, Long> sessions = theTask.getSessions();
        
        for(Calendar keyCal : sessions.keySet()){
            // The int year in string format
            String year = String.valueOf(keyCal.get(Calendar.YEAR));
            
            // Does the record year exist yet
            if(durationsByYear.containsKey(year)){
                // Add the old vlaue to the new value for the key keyCal that 
                // corresponds to the sessions hashmap
                durationsByYear.put(year, (durationsByYear.get(year) + sessions.get(keyCal)));
                
            } else{
                durationsByYear.put(year, sessions.get(keyCal));
            }
        }

        return durationsByYear;
    }
}