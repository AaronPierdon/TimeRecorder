/*
 * Developers: Aaron Pierdon
 * Date: Mar 9, 2018
 * Description :
 * 
 */

package timerecorder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import timerecorderdatamodel.Task;
import utility.io.parse.TimeConverter;
import utility.stringUtility.CalendarParser;


public class ByDayController {

   
    private Task theTask;
    // 0 for January. This the month for which to display recorded sesssions
    private int theMonth;
    private int theYear;
    private long maxDuration;
    protected BarChart<String, Number> chart;
    
  
    
    protected BarChart getChart(Task task, int month, int year){
        this.theTask = task;
        this.theMonth = month;
        this.theYear = year;
        this.maxDuration = 86_400_000;
        
            buildChart();
            addData();

        return chart;
    }
    
    private void addData(){
            
            // Get a list of sessions that occur within the specified month
            


            // Set up and load the data model
            XYChart.Series series1 = new XYChart.Series();
            series1.setName("Activity by " + CalendarParser.getMonthString(theMonth));
            
            HashMap<Calendar, Long> sessions = theTask.getSessions();
            
            HashMap<Integer, Long> dayTotals = new HashMap<>();
            
            for(Calendar calKey : sessions.keySet()){
                if(theMonth == calKey.get(Calendar.MONTH) && 
                        theYear == calKey.get(Calendar.YEAR)){
                    int day = calKey.get(Calendar.DAY_OF_MONTH);
                    
                    // get duration for the session
                    long duration = sessions.get(calKey);
                    
                    // if time for that day has not been totaled or added
                    // create an entry in map
                    
                    if(!dayTotals.containsKey(day)){
                        dayTotals.put(day, duration);
                    } else{
                        dayTotals.put(day, (dayTotals.get(day) + duration));
                    }
                    
                    
                }
                
                for(Integer day : dayTotals.keySet()){
                    series1.getData().add(new XYChart.Data(String.valueOf(day), 
                            (TimeConverter.secondsToHours(TimeConverter.milliToSeconds(dayTotals.get(day))))));
                    System.out.println(series1.getData());
                }
            }
            
            

           
           
            // Add data to chart
            chart.getData().add(series1);
    }
    
    private void buildChart(){
        
        // Make sure there is data to work with
        if(this.theTask != null && !this.theTask.getSessions().isEmpty()){
            //buildDurationByYearMap();
            
            
            // Load the x axis with the year keys from durationByYear hashmap
            ArrayList<String> days = new ArrayList<>();
            for(int day = 0; day <= 24; day++){
                days.add(String.valueOf(day));
            }
            ObservableList<String> list = FXCollections.observableArrayList(days);
            CategoryAxis xAxis = new CategoryAxis(list);
            
            // Load the y axis with the min and max values from the durationByyear
            NumberAxis yAxis = new NumberAxis(0, 24, .5);

            // Style axes
            xAxis.setTickLabelFill(Color.rgb(0, 255, 0));
            yAxis.setTickLabelFill(Color.rgb(0, 255, 0));
            xAxis.setLabel("Day of Month (" + CalendarParser.getMonthString(theMonth) + ")");
            yAxis.setLabel("Duration\r\n (Hours)");
            
            
            // Set up the chart
            this.chart = new BarChart<>(xAxis, yAxis);
            this.chart.setStyle("-fx-background-color: #000000");
            this.chart.setStyle("-fx-text-fill: #3ee028");
            this.chart.setTitle("Activity by Day");
            this.chart.setLegendVisible(false);

            
        

            
        }

        
    }


    
    // use only for bubble chart impl. 
    /*private double getPercentageOfMax(long duration){
        
        // Convert long milliseconds to double hours
        if(duration > 0){
            System.out.println(duration);
              duration = duration / 1000 / 60 / 60;
            double maxDur = this.maxDuration / 1000 / 60 / 60;

            // Turn the percentage into a whole number by shifting two decimal places
            // to the left and rounding the resulting number. Then turn the rounded number
            // back into a decimal by dividing by 100


            System.out.println("dur: " + duration + " max: " + maxDur);
            double unRounded = duration / maxDur;
            double rounded = Math.round(unRounded * 100.0) / 100.0;
            System.out.println(rounded);
            
            // for now omit, just use a static size... makes bubble chart impractcal choice
           // return rounded; //
           
           return .25;
           
        } else
            return 0;
  
    
    }*/
    
    private int getHours(long time){
        int timeInSeconds = (int) time / 1000;
        
        int a = TimeConverter.secondsToHours(timeInSeconds);
        return a;
    }
    
    
}
