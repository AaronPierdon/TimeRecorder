/*
 * Developers: Aaron Pierdon
 * Date: Mar 13, 2018
 * Description :
 * 
 */
package timerecorder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import timerecorderdatamodel.Task;

public class ChartController {

    // Lets caller know when the controller is done and main can take over
    private boolean doneWithCharts;
    private ArrayList<Task> tasks;

    @FXML
    private BorderPane chartRoot;
    
    @FXML
    private TreeView rootTreeView;

    private Stage stage;


    public ChartController() {
        this.doneWithCharts = true;
        this.tasks = new ArrayList<>();
        stage = new Stage();
        
        rootTreeView = new TreeView();
    }

    // Entry point for passing control to the chart views and their controls
    protected void showCharts(ArrayList<Task> tasks) {
        this.doneWithCharts = false;
        this.tasks = tasks;
        rootTreeView = new TreeView();
        this.buildRoot();
        
       
    }

    // Sets up the root border pane and calls other view building functions
    private void buildRoot() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChartRoot.fxml"));
        loader.setController(this);

        try {
            chartRoot = loader.load();
            chartRoot.getStylesheets().add(getClass().getResource("/timerecorder/stylesheet.css").toString());
        } catch (IOException e) {
            // could not load the view so let MainController take over
            this.doneWithCharts = true;
        }

        // Make sure that loading the root did not fail and that execution of this
        // function should continue
        if (doneWithCharts == false) {
            buildTreeView();
            // Set root node to selected
            buildPieChartView();

            Scene scene = new Scene(chartRoot, 600, 600);
            this.stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }

    }

    // Builds the left node tree view structure
    private void buildTreeView() {
        VBox rootroot = new VBox();
        StackPane treeRoot = new StackPane();

        Node rootIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/blackclock.png")));
        TreeItem<String> rootNode = new TreeItem<>("Tasks", rootIcon);
        
        // Build task nodes and insert into the root
        rootNode = addTaskNodes(rootNode);
        
        // Build Years for each task and insert into respective task
        for(TreeItem<String> item : rootNode.getChildren()){
            item = addYearNodes(item);
        }
        
        // Build and add month nodes for and to each year node in each task
        for(TreeItem<String> )
        
        
        this.rootTreeView = new TreeView<>(rootNode);

        

        treeRoot.getChildren().add(rootTreeView);

        chartRoot.setLeft(treeRoot);
    }

    private TreeItem<String> addTaskNodes(TreeItem<String> rootNode) {
        for (Task task : this.tasks) {
            TreeItem<String> taskLeaf = new TreeItem<>(task.getName());
            rootNode.getChildren().add(taskLeaf);
        }
                    
                    
        return rootNode;
    }
    
    private TreeItem<String> addYearNodes(TreeItem<String> taskNode){
        String nodeName = taskNode.getValue();
        Task selectedTask = null;
        for(Task task : tasks){
            if(task.getName().equalsIgnoreCase(nodeName)){
                selectedTask  = task;
            }
        }
        
        // Task found... now get the years for which there are records as leafs
        if(selectedTask != null){
            ArrayList<TreeItem<String>> yearNodes = new ArrayList<>();
            yearNodes = getYearNodes(selectedTask);
            
            // Add the year nodes to the task
            for(TreeItem<String> node : yearNodes){
                taskNode.getChildren().add(node);
            }
            
        }
        return taskNode;
    }
    
    private ArrayList<TreeItem<String>> getYearNodes(Task task){
        
            ArrayList<TreeItem<String>> yearNodes = new ArrayList<>();

            HashMap<Calendar, Long> sessions = task.getSessions();


            for(Calendar key : sessions.keySet()){
                String year = String.valueOf(key.get(Calendar.YEAR));
                // Check if it exists
                boolean found = false;
                
                for(TreeItem<String> yearNode : yearNodes){
                    
                    
                    if(yearNode.getValue().equalsIgnoreCase(year))
                        found = true;
                }
                
                if(!found){
                    TreeItem<String> yearLeaf = new TreeItem<>(year);
                    yearNodes.add(yearLeaf);
                }
            }

        

        
        
        
        return yearNodes;
    }



    // Called when the root tree view node is selected
    private void buildPieChartView() {
        chartRoot.setCenter(new PieChartController().getPieChart(tasks));
    }

    // Called when a task node is selected
    private void buildTaskChartView() {

    }

    // Called when a year node is selected
    private void buildYearChartView() {

    }

    // Called when a month node is selected
    private void buildMonthChartView() {

    }

    // Called when a node is clicked... determine what type was clicked and call
    // the build view method
    @FXML
    protected void nodeClicked(MouseEvent event) {
        
    }

    @FXML
    protected void close(ActionEvent event) {
        this.stage.close();
    }

    public boolean isDoneWithCharts() {
        return doneWithCharts;
    }
    


}
