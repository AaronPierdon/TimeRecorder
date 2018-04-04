/*
 * Developers: Aaron Pierdon
 * Date: Mar 9, 2018
 * Description :
 * 
 */

package timerecorder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import timerecorderdatamodel.Task;
import utility.io.parse.TimeConverter;
import utility.stringUtility.CalendarParser;


public class ByDayController {

   
    private Task theTask;
    // 0 for January. This the month for which to display recorded sesssions
    private int theMonth;
    protected BubbleChart<Number, Number> chart;
    
  
    
    protected BubbleChart getChart(Task task, int month){
        this.theTask = task;
        this.theMonth = month;
    
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
            
            for(Calendar calKey : sessions.keySet()){
                if(theMonth == calKey.get(Calendar.MONTH)){
                    
                    System.out.println(calKey.get(Calendar.DAY_OF_WEEK));
                    System.out.println(sessions.get(calKey));
                    series1.getData().add(new XYChart.Data((calKey.get(Calendar.DAY_OF_MONTH) / 1000), 
                        sessions.get(calKey)));
                }}

           
           
            // Add data to chart
            chart.getData().add(series1);
    }
    
    private void buildChart(){
        
        // Make sure there is data to work with
        if(this.theTask != null && !this.theTask.getSessions().isEmpty()){
            //buildDurationByYearMap();
            
            
            // Load the x axis with the year keys from durationByYear hashmap
            NumberAxis xAxis = new NumberAxis(0, 31, 1);
            
            // Load the y axis with the min and max values from the durationByyear
            NumberAxis yAxis = new NumberAxis();

            // Style axes
            xAxis.setTickLabelFill(Color.rgb(0, 255, 0));
            yAxis.setTickLabelFill(Color.rgb(0, 255, 0));
            xAxis.setLabel("Year");
            yAxis.setLabel("Duration\r\nin Hours");
            
            
            // Set up the chart
            this.chart = new BubbleChart<>(xAxis, yAxis);
            this.chart.setStyle("-fx-background-color: #000000");
            this.chart.setStyle("-fx-text-fill: #3ee028");
            this.chart.setTitle("Activity by Day");


            
        

            
        }

        
    }

    
    private int getHours(long time){
        int timeInSeconds = (int) time / 1000;
        
        int a = TimeConverter.secondsToHours(timeInSeconds);
        return a;
    }
    
    
}
