/*
 * Developers: Aaron Pierdon
 * Date: Mar 7, 2018
 * Description :
 * 
 */

package timerecorder;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import timerecorderdatamodel.Task;


public class PieChartController {

    
    // The tasks to assess
    private ArrayList<Task> tasks;

    private PieChart pieChart;
    private StackPane pieRoot;
    
    
    public PieChartController(){
        this.pieChart = new PieChart();
        this.pieRoot = new StackPane();
        tasks = new ArrayList<>();
    }
    
    protected StackPane getPieChart( ArrayList<Task> tasks){
        this.tasks = tasks;
        buildPieChart(); 
        return pieRoot;
    }
    
    private void buildPieChart(){
        ArrayList<Data> data = getPieChartMap();
    
        // Tasks to compare?
        if(data != null){
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            for(Data dataElement : data)
            {
                pieChartData.add(dataElement);
            }


            pieChart.setData(pieChartData);
            pieRoot.getChildren().add(pieChart);
        } 
        
        // Display no task notification
        else{
            this.pieRoot.getChildren().clear();
            Label noData = new Label("No Data");
            noData.setStyle("-fx-font-size: 3em");
            
            this.pieRoot.getChildren().add(noData);
        }

    }
    
    private ArrayList<Data> getPieChartMap(){
        
        if(!tasks.isEmpty()){
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
        } else
            return null;

    }
    
    

    
 
    
    
}
