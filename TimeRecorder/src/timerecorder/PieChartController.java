/*
 * Developers: Aaron Pierdon
 * Date: Mar 7, 2018
 * Description :
 * 
 */

package timerecorder;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import timerecorderdatamodel.Task;


public class PieChartController {

    
    // The tasks to assess
    private ArrayList<Task> tasks;
    private Stage primaryStage;
    private boolean done;

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }
    
    @FXML protected PieChart pieChart;
    @FXML protected StackPane pieRoot;
    @FXML protected BorderPane rootPane;
    
    
    public PieChartController(){
        done = true;
        tasks = new ArrayList<>();
    }
    
    protected void showPieChart(Stage primaryStage, ArrayList<Task> tasks){
        this.done = false;
        this.primaryStage = primaryStage;
        this.tasks = tasks;
        buildRootView();
        buildPieChart();        
        show();
    }
    
    private void buildPieChart(){
        ArrayList<Data> data = getPieChartMap();
    
        
        
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
         
        for(Data dataElement : data)
        {
            pieChartData.add(dataElement);
        }
        
        
        pieChart.setData(pieChartData);
    }
    
    private ArrayList<Data> getPieChartMap(){
        
        ArrayList<Data> chartMap = new ArrayList<>();
        
        // Total Durations of each task
        ArrayList<Long> totals = new ArrayList<>();
        
        // Get the totals
        for(Task task : this.tasks){
            totals.add(task.getTotalTime());
        }
        
        // Get the total of all the tasks's durations
        long allTasksDuration = 0;
        
        for(Long duration : totals)
            allTasksDuration += duration;

    
        
        Iterator taskNames = tasks.iterator();
        for(Long total : totals){
            Double percentOfTotal =  (Double)(Double.valueOf(total) / Double.valueOf(allTasksDuration));
            Task tempTask = (Task) taskNames.next();
            chartMap.add(new Data((String) tempTask.getName(), percentOfTotal));
        }

        return chartMap;
    }
    
  
    private void buildRootView(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PieChart.fxml"));
        
        loader.setController(this);
        
        try{
            rootPane = loader.load();
            rootPane.getStylesheets().add(getClass().getResource("/timerecorder/stylesheet.css").toString());
            Scene scene = new Scene(rootPane);
            primaryStage.setScene(scene);
            
        }catch(IOException e){}
    }
    
    private void show(){
    }
    
    @FXML protected void closePieChart(ActionEvent event){
        this.done = true;
    }
    
    
}
