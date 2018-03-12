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


public class BubbleChartController {

    private Stage stage;
    protected StackPane bubbleRoot;
    @FXML protected BorderPane root;
    private Task theTask;
    protected HashMap<Integer, Integer> durationByYear;
    protected BubbleChart<Number, Number> chart;
    
    public BubbleChartController(){
        this.stage = new Stage();
        this.bubbleRoot = new StackPane();
        theTask = null;
        durationByYear = new HashMap<>();
        
    }
    
    protected void displayChart(Task task){
        this.theTask = task;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BubbleChart.fxml"));
        loader.setController(this);
        
        try{
            root = loader.load();
            root.getStylesheets().add(getClass().getResource("/timerecorder/stylesheet.css").toString());
            buildChart();
            root.setCenter(bubbleRoot);
            Scene scene = new Scene(root, 600, 600);
            addData();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            
        }
        
    }
    
    private void addData(){
            // Set up and load the data model
            XYChart.Series series1 = new XYChart.Series();
            series1.setName("Activity by Year");
            
            // Add each pair from durationByYear as a XYChart.Data object
            for(Integer keyYear : durationByYear.keySet()){
                XYChart.Data data = new XYChart.Data(keyYear, durationByYear.get(keyYear), .2);
                series1.getData().add(data);
            }
            
                // Add data to chart
            chart.getData().add(series1);
    }
    
    private void buildChart(){
        
        // Make sure there is data to work with
        if(this.theTask != null && !this.theTask.getSessions().isEmpty()){
            buildDurationByYearMap();
            
            
            // Load the x axis with the year keys from durationByYear hashmap
            NumberAxis xAxis = getXAxis();
            
            // Load the y axis with the min and max values from the durationByyear
            NumberAxis yAxis = getYAxis();

            // Style axes
            xAxis.setTickLabelFill(Color.rgb(0, 255, 0));
            yAxis.setTickLabelFill(Color.rgb(0, 255, 0));
            xAxis.setLabel("Year");
            yAxis.setLabel("Duration\r\nin Hours");
            
            
            // Set up the chart
            this.chart = new BubbleChart<>(xAxis, yAxis);
            this.chart.setStyle("-fx-background-color: #000000");
            this.chart.setStyle("-fx-text-fill: #3ee028");
            this.chart.setTitle("Activity by Year");


            this.bubbleRoot.getChildren().add(chart);
        

            
        }

        
    }
    

    
    private void buildDurationByYearMap(){
        HashMap<Long, Long> sessions = theTask.getSessions();
        
        this.durationByYear = new HashMap<>();
        
        
        for(Long keyDate : sessions.keySet()){
            Date date = new Date(keyDate);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
           
            int year = calendar.get(Calendar.YEAR);
            long newDuration  = sessions.get(date.getTime());
            
            
            // Year exists in the hashmap already
            if(durationByYear.get(year) != null){
                newDuration += durationByYear.get(year) * 1000;
                
                // update the value for the year key
                durationByYear.remove(year);
                
                System.out.println("getting hours: " + getHours(newDuration));
                durationByYear.put(year, getHours(newDuration));
            }
            
            // Year does not exist in the hash map
            else{
                durationByYear.put(year, getHours(newDuration));
            }
            
            
        }
        


    }
    
    private NumberAxis getXAxis(){
        return new NumberAxis(getMinYear() - 2, getMaxYear() + 2, 1);
    }
    
    private int getMinYear(){
        int minYear = 0;
        
        for(Integer keyYear : durationByYear.keySet()){
            // Load the first key from the set
            if(minYear <= 0)
                minYear = keyYear;
            else{
                if(minYear > keyYear)
                    minYear = keyYear;
            }
                
        }
        
        return minYear;
    }
    
    private int getMaxYear(){
        int maxYear = 0;
        
        for(Integer keyYear : durationByYear.keySet()){
            // Load the first key from the set
            if(maxYear <= 0)
                maxYear = keyYear;
            else{
                if(maxYear < keyYear)
                    maxYear = keyYear;
            }
                
        }
        
        return maxYear;
    }
    
    private NumberAxis getYAxis(){
        return new NumberAxis(0, 24, 1);
    }
    
    private int getMinDuration(){
        long minDuration = 0;
        
        for(Integer key : durationByYear.keySet()){
            // Set minDuration to the first value of the set
            if(minDuration == 0){
                minDuration = durationByYear.get(key);
            }else{
                if(minDuration > durationByYear.get(key))
                    minDuration = durationByYear.get(key);
            }
        }
        
        return getHours(minDuration);
    }
    
    private int getMaxDuration(){
        long maxDuration = 0;
        
        for(Integer key : durationByYear.keySet()){
            // Set maxDuration to the first value of the set
            if(maxDuration == 0){
                maxDuration = durationByYear.get(key);
            }else{
                if(maxDuration < durationByYear.get(key))
                    maxDuration = durationByYear.get(key);
            }
        }
        
        return getHours(maxDuration);
    }
    
    private int getHours(long time){
        int timeInSeconds = (int) time / 1000;
        
        int a = TimeConverter.secondsToHours(timeInSeconds);
        return a;
    }
    
    
    @FXML protected void close(){
        stage.close();
    }
}
