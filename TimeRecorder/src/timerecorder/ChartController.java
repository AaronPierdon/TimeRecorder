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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem.TreeModificationEvent;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
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
        for(TreeItem<String> taskNode : rootNode.getChildren()){
            taskNode = addYearNodes(taskNode);
        }
        
        // Build and add month nodes for and to Ceach year node in each task
        for(TreeItem<String> taskNode : rootNode.getChildren()){
             for(TreeItem<String> yearNode : taskNode.getChildren()){
                yearNode = addMonthNodes(yearNode);
             }
        }
        
        
        this.rootTreeView = new TreeView<>(rootNode);
        this.rootTreeView.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
            @Override
            public TreeCell<String> call(TreeView<String> p) {
                
                // Set up event handlers for responding to tree view selections and inputs
                p.setOnKeyPressed(new EventHandler<KeyEvent>(){
                    @Override
                    public void handle(KeyEvent e){
                        if(e.getCode() == KeyCode.ENTER){
                            TreeView tree = (TreeView) e.getSource();
                            if(tree.getSelectionModel().getSelectedItem() != null){
                                TreeItem<String> item = (TreeItem<String>) tree.getSelectionModel().getSelectedItem();
                                nodeClicked(item);
                            }
                        }
                    }
                });
                
                p.setOnMouseClicked(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent e){
                        
                        TreeView tree = (TreeView) e.getSource();
                        if(tree.getSelectionModel().getSelectedItem() != null){
                            TreeItem<String> item = (TreeItem<String>) tree.
                                    getSelectionModel().getSelectedItem();
                            nodeClicked(item);
                        }
                    }
                });
                
                
                return new TreeViewFactoryImp();
            }
        });
        

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

    private TreeItem<String> addMonthNodes(TreeItem<String> yearNode){
        // Get parent task name
        String taskName = yearNode.getParent().getValue();
        
        // Index of task being analyzed
        int taskIndex = -1;
        
        //Find the task being analyzed
        for(Task task : tasks)
            if(task.equals(taskName))
                taskIndex = tasks.indexOf(task);
        
        // The year that is being analyzed
        int sessionYear = Integer.valueOf(yearNode.getValue());
        
                
        if(taskIndex >= 0){
            ArrayList<TreeItem<String>> monthNodes = new ArrayList<>();
        
            ArrayList<Integer> months = new ArrayList<>();
            
            for(Calendar key : tasks.get(taskIndex).getSessions().keySet()){
                boolean found = false;
                
                for(Integer month : months){
                    if(key.get(Calendar.MONTH) == month)
                        found = true;
                }
                
                if(found == false && key.get(Calendar.YEAR) == sessionYear)
                    months.add(key.get(Calendar.MONTH));
                
                        
            }
            
            // Convert the ints to string months
            for(Integer month : months){
                switch(month){
                    case 0: monthNodes.add(new TreeItem<>("Jan"));
                    break;
                    
                    case 1: monthNodes.add(new TreeItem<>("Feb"));
                    break;
                    
                    case 2: monthNodes.add(new TreeItem<>("Mar"));
                    break;
                    
                    case 3: monthNodes.add(new TreeItem<>("Apr"));
                    break;
                    
                    case 4: monthNodes.add(new TreeItem<>("May"));
                    break;
                    
                    case 5: monthNodes.add(new TreeItem<>("Jun"));
                    break;
                    
                    case 6: monthNodes.add(new TreeItem<>("Jul"));
                    break;
                    
                    case 7 : monthNodes.add(new TreeItem<>("Aug"));
                    break;
                    
                    case 8 : monthNodes.add(new TreeItem<>("Sep"));
                    break;
                    
                    case 9: monthNodes.add(new TreeItem<>("Oct"));
                    break;
                    
                    case 10: monthNodes.add(new TreeItem<>("Nov"));
                    break;
                    
                    case 11: monthNodes.add(new TreeItem<>("Dec"));
                    break;
                    
                    default:;
                }
            }
            
            for(TreeItem<String> month : monthNodes)
                yearNode.getChildren().add(month);
            
            return yearNode;
        }

        
        return null;
        
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
    
    
    // This handles when a node is clicked or enter is pressed while a node is selected
    // This method will call other methods to build the appropriate chart with respect
    // To the node that is selected
    protected void nodeClicked(TreeItem<String> clickedItem) {
        
        
        if(clickedItem.getValue().equalsIgnoreCase("tasks")){
        
            chartRoot.setCenter(new PieChartController().getPieChart(tasks));
        
        // Nest level 1, meaning a task node
        } else if(clickedItem.getParent().getValue().equalsIgnoreCase("tasks")){
            // Find the task that corresponds to the clicked item and send to the by year chart controller for
            // Building the chart
            chartRoot.setCenter(new ByYearChartController().getByYearChart(findTask(clickedItem.getValue())));
        
        // Nest level 2, meaning a year
        }else if(clickedItem.getParent().getParent().getValue().equalsIgnoreCase("tasks")){
            chartRoot.setCenter(new ByMonthController().getByMonthChart(findTask(clickedItem.getParent().getValue())));
            
        // Nest level 3, meaning a month
        }else if(clickedItem.getParent().getParent().getParent().getValue().equalsIgnoreCase("tasks")){
            chartRoot.setCenter (new ByDayController().getByDayChart(findTask(clickedItem.getParent().getParent().getValue())));
            
        // Unkown nest level
        }else {
            Label lblNoData = new Label("No Data");
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            lblNoData.setStyle("-fx-font-size: 72px");
            box.getChildren().add(lblNoData);
            chartRoot.setCenter(box);
        }
        
        
    }
    
    public Task findTask(String taskName){
        for(Task task : tasks){
            if(task.getName().equalsIgnoreCase(taskName))
                return task;
        } 
        
        // If no match 
        return null;
    }

    @FXML
    protected void close(ActionEvent event) {
        this.stage.close();
    }

    public boolean isDoneWithCharts() {
        return doneWithCharts;
    }

    
    public class TreeViewFactoryImp extends TreeCell<String>{

        private TextField textField;
        
        @Override
        public void updateItem(String item, boolean empty){
            super.updateItem(item, empty);
            
                        if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                    /**
                    getTreeItem().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent e){
                            nodeClicked(getTreeItem());
                        }
                    });*/
                    
                    
             
                }
        }
    }
        
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
        
    }
}
